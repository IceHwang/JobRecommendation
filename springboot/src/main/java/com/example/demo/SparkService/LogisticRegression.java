package com.example.demo.SparkService;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import scala.Tuple2;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.classification.LogisticRegressionWithLBFGS;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogisticRegression {
    public static void main(String[] args)
    {
        ArrayList<String> l = new ArrayList<>();
        l.add("android开发工程师 android sdk ");
        l.add("android开发工程师 android kotlin app c++ native java ");
        LogisticRegression lr = new LogisticRegression(l);

    }
    private LogisticRegressionModel model;
    private String modelPath = "";
    LogisticRegression()
    {
        System.setProperty("hadoop.home.dir","C:\\winutils");
        this.modelPath = getSelectedModel();
        String path = "../data/"+this.modelPath+"/VectorData/vectorData.txt";
        SparkConf sparkConf = new SparkConf().setAppName("LogisticRegression").setMaster("local");
        sparkConf.set("spark.driver.allowMultipleContexts","true");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        try{
            this.model = LoadModel(sc,"../data/"+this.modelPath+"/mod/RegressionModel");
        }
        catch(Exception e){
            System.out.println(e.toString());
            this.model = trainLogisticRegressionModel(sc,path);
            SaveModel(model,sc,"../data/"+this.modelPath+"/mod/RegressionModel");
        }
        sc.stop();
    }
    LogisticRegression(ArrayList<String> newData)
    {
        System.setProperty("hadoop.home.dir","C:\\winutils");
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MMdd_HHmm");
        this.modelPath = df.format(new Date());
        File file = new File("../data/"+this.modelPath);
        if(!file.exists()){
            file.mkdirs();
            File f1 = new File("../data/"+this.modelPath+"/cleanData");
            f1.mkdirs();
            File f2 = new File("../data/"+this.modelPath+"/mod");
            f2.mkdirs();
            File f4 = new File("../data/"+this.modelPath+"/mod/FPTreeModel");
            f4.mkdirs();
            File f5 = new File("../data/"+this.modelPath+"/mod/RegressionModel");
            f5.mkdirs();
            File f3 = new File("../data/"+this.modelPath+"/VectorData");
            f3.mkdirs();
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("../data/config.txt",true));
                out.write(this.modelPath+"\n");
                out.close();
            }
            catch (IOException e) {
                System.out.println(e.toString());
                }
        }
        this.saveVector(newData);
        String path = "../data/"+this.modelPath+"/VectorData/vectorData.txt";
        SparkConf sparkConf = new SparkConf().setAppName("LogisticRegression").setMaster("local");
        sparkConf.set("spark.driver.allowMultipleContexts","true");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        try{
            this.model = LoadModel(sc,"../data/"+this.modelPath+"/mod/RegressionModel");
        }
        catch(Exception e){
            System.out.println(e.toString());
            this.model = trainLogisticRegressionModel(sc,path);
            SaveModel(model,sc,"../data/"+this.modelPath+"/mod/RegressionModel");
        }
        sc.stop();
    }


    public void saveVector(ArrayList<String> newData)
    {
        ArrayList<String> skills = new ArrayList<>();
        String skill = "";
        ArrayList<String> jobs = new ArrayList<>();
        String job = "";
        for(int i = 0;i<newData.size();i++)
        {
            String[] l = newData.get(i).split(" ");
            if(!jobs.contains(l[0]))
            {
                jobs.add(l[0]);
                job = job + l[0] + " ";
            }
            for(int j = 1;j<l.length;j++)
            {
                if(!skills.contains(l[j]))
                {
                    skills.add(l[j]);
                    skill = skill + l[j] + " ";
                }
            }
        }
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter("../data/"+this.modelPath+"/cleanData/data.txt"));
            for(int i = 0;i<newData.size();i++)
            {
                out.write(newData.get(i)+"\n");
            }
            out.close();
        }
        catch(IOException e){
            System.out.println(e.toString());
        }
        try{
            BufferedWriter in = new BufferedWriter(new FileWriter(new File("../data/"+this.modelPath+"/VectorData/skills.txt")));
            in.write(skill);
            in.close();
            BufferedWriter ou = new BufferedWriter(new FileWriter(new File("../data/"+this.modelPath+"/VectorData/jobs.txt")));
            ou.write(job);
            ou.close();
            BufferedWriter out = new BufferedWriter(new FileWriter(new File("../data/"+this.modelPath+"/VectorData/vectorData.txt")));
            for(int i = 0;i<newData.size();i++)
            {
                String[] l = newData.get(i).split(" ");
                if(l.length !=1&&l.length!=2)
                {
                    String k = jobs.indexOf(l[0]) + " ";
                    for(int j = 0;j<skills.size();j++)
                    {
                        int m = 0;
                        for(int p = 1;p<l.length;p++)
                        {
                            if(l[p].equals(skills.get(j)))
                            {
                                m = 1;
                                break;
                            }
                        }
                        k = k + (j+1) + ":"+m+" ";
                    }
                    out.write(k+"\n");
                }
            }
            out.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    public ArrayList<Map.Entry<String,Double>> getPredictedJobWithScore(ArrayList<String> skillList)
    {
        String[] job = this.getJobs();
        ArrayList<Double> confidenceScore = (getConfidenceScore(this.model,this.word2Vec(skillList)));
        HashMap<String,Double> jobWithScore = new HashMap<String,Double>();
        for(int i = 0;i<job.length;i++)
        {
            jobWithScore.put(job[i], confidenceScore.get(i));
        }
        ArrayList<Map.Entry<String,Double>> list= new ArrayList<>(jobWithScore.entrySet());
        Collections.sort(list,(a,b)->b.getValue().compareTo(a.getValue()));
        return list;
    }

    public static ArrayList<String> getModelConfig()
    {
        ArrayList<String> modelConfigs = new ArrayList<>();
        try{
            BufferedReader in = new BufferedReader(new FileReader("../data/config.txt"));
            String line;
            while ((line = in.readLine()) != null) {
                modelConfigs.add(line);
            }
            in.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
        return modelConfigs;
    }

    public static String getSelectedModel()
    {
        String line = "";
        try{
            BufferedReader in = new BufferedReader(new FileReader("../data/SelectedModel.txt"));
            line = in.readLine();
            in.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
        return line;
    }
    public String getModelPath()
    {
        return this.modelPath;
    }

    public String[] getJobs()
    {
        String jobs = "";
        try{
            BufferedReader in = new BufferedReader(new FileReader("../data/"+this.modelPath+"/VectorData/jobs.txt"));
            jobs = in.readLine();
            in.close();
        }
        catch(IOException e) {
            System.out.println(e.toString());
        }
        return jobs.split(" ");
    }

    private static LogisticRegressionModel trainLogisticRegressionModel(JavaSparkContext sc,String path)
    {
        JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(sc.sc(), path).toJavaRDD();

        JavaRDD<LabeledPoint>[] splits = data.randomSplit(new double[] {0.9, 0.1}, 11L);
        JavaRDD<LabeledPoint> training = splits[0].cache();
        JavaRDD<LabeledPoint> test = splits[1];
        LogisticRegressionModel model = new LogisticRegressionWithLBFGS()
                .setNumClasses(25)
                .run(training.rdd());
        JavaPairRDD<Object, Object> predictionAndLabels = test.mapToPair(p ->
                new Tuple2<>(model.predict(p.features()), p.label()));
        System.out.println(predictionAndLabels.collect());
        MulticlassMetrics metrics = new MulticlassMetrics(predictionAndLabels.rdd());
        double accuracy = metrics.accuracy();
        System.out.println("Accuracy = " + accuracy);
        return model;
    }
    private static void SaveModel(LogisticRegressionModel model,JavaSparkContext sc,String path)
    {
        model.save(sc.sc(),path);
    }
    private static LogisticRegressionModel LoadModel(JavaSparkContext sc,String path)
    {
        return LogisticRegressionModel.load(sc.sc(),
                path);
    }

    private Vector word2Vec(ArrayList<String> words)
    {
        String[] skills = {""};
        try {
            BufferedReader in = new BufferedReader(new FileReader("../data/"+this.modelPath+"/VectorData/skills.txt"));
            skills = in.readLine().split(" ");
            in.close();

        }
        catch (IOException e){
            System.out.println(e);
        }
        double[] data = new double[skills.length];
        for(int i = 0;i<skills.length;i++)
        {
            if(words.contains(skills[i]))
            {
                data[i] = 1.0;
            }
            else
            {
                data[i] = 0.0;
            }
        }
        return Vectors.dense(data);
    }

    private static ArrayList<Double> getConfidenceScore(
            final LogisticRegressionModel lrModel, final Vector vector) {
        Vector weights = lrModel.weights();
        int numClasses = lrModel.numClasses();
        int dataWithBiasSize = weights.size() / (numClasses -1);
        boolean withBias = (vector.size() + 1) == dataWithBiasSize;
        double maxMargin = 0.0;
        double margin = 0.0;
        ArrayList<Double> confidences = new ArrayList<>();
        for (int j = 0; j < (numClasses-1); j++) {
            margin = 0.0;
            for (int k = 0; k < vector.size(); k++) {
                double value = vector.toArray()[k];
                if (value != 0.0) {
                    margin += value
                            * weights.toArray()[(j * dataWithBiasSize) + k];
                }
            }
            if (withBias) {
                margin += weights.toArray()[(j * dataWithBiasSize)
                        + vector.size()];
            }

            confidences.add(margin);
        }
        ArrayList<Double> confidenceScore = new ArrayList<>();
        for(int i = 0;i<confidences.size();i++)
        {
            double conf = 1.0 / (1.0 + Math.exp(-confidences.get(i)));

            confidenceScore.add(Double.valueOf(conf));
            if (conf > maxMargin) {
                maxMargin = conf;
            }
        }
        confidenceScore.add(0,Double.valueOf(1-maxMargin));
        return confidenceScore;
    }
}
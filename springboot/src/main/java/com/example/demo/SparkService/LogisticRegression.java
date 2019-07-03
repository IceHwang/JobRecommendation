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
import java.util.*;

public class LogisticRegression {
    LogisticRegressionModel model;
    LogisticRegression()
    {
        String path = "input/data.txt";
        SparkConf sparkConf = new SparkConf().setAppName("Regression").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        this.model = trainLogisticRegressionModel(jsc,path);
    }
    public ArrayList<Map.Entry<String,Double>> getPredictedJobWithScore(ArrayList<String> skillList)
    {
        String jobs = "算法工程师 c/c++开发工程师 android开发工程师 hadoop开发工程师 java开发工程师 php开发工程师 数据库管理员 flash动画师 html5开发工程师 ios开发工程师 python开发工程师 u3d开发工程师 区块链工程师 图像处理算法工程师 技术总监 技术经理 机器学习算法工程师 视觉算法工程师 架构师 测试工程师 网络安全工程师 网络工程师 自然语言处理工程师 运维工程师 深度学习算法工程师";
        String[] job = jobs.split(" ");
        ArrayList<Double> confidenceScore = (getConfidenceScore(this.model,word2Vec(skillList)));
        HashMap<String,Double> jobWithScore = new HashMap<String,Double>();
        for(int i = 0;i<job.length;i++)
        {
            jobWithScore.put(job[i], confidenceScore.get(i));
        }
        ArrayList<Map.Entry<String,Double>> list= new ArrayList<>(jobWithScore.entrySet());
        Collections.sort(list,(a,b)->b.getValue().compareTo(a.getValue()));
        return list;
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

    private static Vector word2Vec(ArrayList<String> words)
    {
        String[] skills = {""};
        try {
            BufferedReader in = new BufferedReader(new FileReader("input/skills.txt"));
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
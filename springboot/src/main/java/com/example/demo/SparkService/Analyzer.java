package com.example.demo.SparkService;


import com.alibaba.fastjson.JSONObject;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.fpm.AssociationRules;
import org.apache.spark.mllib.fpm.FPGrowth;
import org.apache.spark.mllib.fpm.FPGrowthModel;
import scala.Tuple3;

import java.io.*;
import java.util.*;

public class Analyzer implements Serializable {

    private ArrayList<String> skillList=null;
    private String preferedJob=null;
    private ArrayList<String> relativeSkillList=null;
    private double weightElement=1.2;

    private static List<AssociationRules.Rule<String>> model=null;
    private static LogisticRegression logisticRegression=null;

    public static void main(String[] args) {
        System.out.println(test());
    }

    public static String test() {

//        getSkillList().forEach(System.out::println);

        saveSelectModelPath("2019_0705_1601");
        String[] skillArray={"tensorflow","ajax","docker","html","javascript","mysql","java","sql","c++","tomcat","spring","svm"};
        ArrayList<String> list = arrayToStringArrayList(skillArray);
        Analyzer.getJobList();
        Analyzer analyzer = new Analyzer(list,"机器学习算法工程师");
        HashMap<String,Object> hashMap=analyzer.getResultHashMap();

        JSONObject object = new JSONObject(hashMap);
        return object.toString();




    }

    public static HashMap<String,Object> getTestResultHashMap() {

        String[] skillArray={"tensorflow","pytorch","python","html","javascript","mysql","java","sql","c++","tomcat","cnn","svm"};
        ArrayList<String> list = arrayToStringArrayList(skillArray);

        Analyzer analyzer = new Analyzer(list,"");
        HashMap<String,Object> hashMap=analyzer.getResultHashMap();

//        JSONObject object = new JSONObject(hashMap);
//        return object.toString();
        return hashMap;
    }

    public static void init()
    {
        createModel();
//        logisticRegression=new LogisticRegression();
    }

    public static ArrayList<String> getModelPath()
    {
        return LogisticRegression.getModelConfig();
    }

    public static void saveSelectModelPath(String dpath)
    {
        try{
            BufferedWriter w = new BufferedWriter(new FileWriter("../data/SelectedModel.txt"));
            w.write(dpath);
            w.close();
        }
        catch(IOException e){

        }
    }
    public static String getSelectedModelPath()
    {
        return LogisticRegression.getSelectedModel();
    }
    public Analyzer(ArrayList<String> skillList, String preferedJob)
    {
        this.skillList=skillList;
        this.preferedJob=preferedJob;
        if (logisticRegression==null)
            logisticRegression=new LogisticRegression();
        createModel();
    }

    public static void creatNewModel(ArrayList<String> newData)
    {
       LogisticRegression m = new LogisticRegression(newData);
    }



    public Analyzer(HashMap<String,Object> hashMap)
    {
        if (logisticRegression==null)
            logisticRegression=new LogisticRegression();
        createModel();
        String preferedJob=(String) (hashMap.get("preferedJob"));
        ArrayList<String> skillList= (ArrayList<String>) (hashMap.get("skillList"));
        this.skillList=skillList;
        this.preferedJob=preferedJob;
    }

    public static Object[] arrayListToArray(ArrayList<String> arrayList)
    {
        return (arrayList.toArray());
    }

    public static ArrayList<String> arrayToStringArrayList(String[] array)
    {
        ArrayList<String>  list = new ArrayList<>();
        for(String s:array)
        {
            list.add(s);
        }
        return list;
    }

    public HashMap<String,Object> getResultHashMap()
    {
        HashMap<String,Object> hashMap=new HashMap<>();
        String[] jobs=new String[3];
        ArrayList<Object[]> recommendSkillLists=new ArrayList<>();
        ArrayList<Object[]> coreSkillLists=new ArrayList<>();
        ArrayList<Map.Entry<String,Double>> regressionResult=getRecommendedJobList_Logistic();

        for (int i = 0; i < 3; i++) {
            String recommendJob = regressionResult.get(i).getKey();
//            String recommendJob=this.getRecommendedJob(i);
            jobs[i]=recommendJob;
            ArrayList<String> recommendSkillList=this.getRecommendedSkillList(recommendJob);
            ArrayList<String> coreSkillList=this.getCoreSkillList(recommendJob);
            recommendSkillLists.add(recommendSkillList.toArray());
            coreSkillLists.add(coreSkillList.toArray());
        }
        hashMap.put("jobs",jobs);
        hashMap.put("recommendSkills",recommendSkillLists.toArray());
        hashMap.put("coreSkills",coreSkillLists.toArray());

        return hashMap;
    }

    public String getRecommendedJob(int num)
    {


        HashMap<String, Double> jobMap=new HashMap<>();
        skillList.forEach(skill->this.transformSkillsToJobs(skill,jobMap));
        List<HashMap.Entry<String, Double>> recommendJobList = new ArrayList<Map.Entry<String, Double>>(jobMap.entrySet());
        Collections.sort(recommendJobList, new Comparator<Map.Entry<String, Double>>() {

            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

//        recommendJobList.forEach(x-> System.out.println(x.toString()));

        return recommendJobList.get(num).getKey();
    }

    public ArrayList<Map.Entry<String,Double>> getRecommendedJobList_Logistic()
    {
        ArrayList<Map.Entry<String,Double>> regressionResult=logisticRegression.getPredictedJobWithScore(skillList);
        regressionResult.forEach(x->{
            if((x.getKey()+"职位").equals(preferedJob))
                x.setValue(x.getValue()*weightElement);
        });
        Collections.sort(regressionResult,(a,b)->b.getValue().compareTo(a.getValue()));
        return regressionResult;
    }

    private ArrayList<String> getRelativeSkillList(String recommendJob)
    {
        if(model==null)
            Analyzer.createModel();
        HashMap<String,Double> hashMap=new HashMap<>();
        model.forEach(x->{
            if(!x.toString().contains(recommendJob))
                return;
            List<String> skillList=x.javaAntecedent();

            double weight=x.confidence();
            skillList.forEach(skill->
            {
                if (skill.contains("职位"))
                    return;
                if(!hashMap.containsKey(skill))
                {
                    hashMap.put(skill,weight);
                }
                else
                {
                    hashMap.put(skill,weight+hashMap.get(skill));
                }
            });

        });
        List<HashMap.Entry<String, Double>> skillList = new ArrayList<Map.Entry<String, Double>>(hashMap.entrySet());
        Collections.sort(skillList, new Comparator<Map.Entry<String, Double>>() {

            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
//        skillList.forEach(System.out::println);
//        System.out.println(skillList.size());
        ArrayList<String> result=new ArrayList<>();
        for (int i = 0; i < skillList.size(); i++) {
            String skill=skillList.get(i).getKey();
            if(skill.equals(recommendJob))
                continue;
            else
                result.add(skillList.get(i).getKey());

        }

        return result;
    }

    public ArrayList<String> getRecommendedSkillList(String recommendJob)
    {
        relativeSkillList=this.getRelativeSkillList(recommendJob);
        ArrayList<String> result=new ArrayList<>();
        for (int i = 0; i < relativeSkillList.size(); i++) {
            String skill=relativeSkillList.get(i);
            if (result.size()>=5)
                break;
            if (skillList.contains(skill))
                continue;
            else
                result.add(skill);
        }
        return result;
    }

    public ArrayList<String> getCoreSkillList(String recommendJob)
    {
        relativeSkillList=this.getRelativeSkillList(recommendJob);
        ArrayList<String> result=new ArrayList<>();
        for (int i = 0; i < relativeSkillList.size(); i++) {
            String skill=relativeSkillList.get(i);
            if (!skillList.contains(skill))
                continue;
            else
                result.add(skill);
        }
        return result;
    }

    public static ArrayList<String> getSkillList()
    {
        if(model==null)
            Analyzer.createModel();
        HashMap<String,Integer> hashMap=new HashMap<>();
        model.forEach(x->{
            if(!x.javaConsequent().toString().contains("职位"))
                return;
            List<String> skillList=x.javaAntecedent();

            skillList.forEach(skill->
            {
                if(!hashMap.containsKey(skill))
                {
                    hashMap.put(skill,1);
                }
                else
                {
                    hashMap.put(skill,1+hashMap.get(skill));
                }
            });

        });
        List<HashMap.Entry<String, Integer>> skillList = new ArrayList<Map.Entry<String, Integer>>(hashMap.entrySet());
        Collections.sort(skillList, new Comparator<Map.Entry<String, Integer>>() {

            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
//        skillList.forEach(System.out::println);
//        System.out.println(skillList.size());
        return new ArrayList<String>(hashMap.keySet());
    }

    public static ArrayList<String> getJobList()
    {
        if (logisticRegression==null)
            logisticRegression=new LogisticRegression();
        if(model==null)
            Analyzer.createModel();
        HashMap<String,Integer> hashMap=new HashMap<>();
        model.forEach(x->{
            if(!x.javaConsequent().toString().contains("职位"))
                return;
            String job=x.javaConsequent().get(0);
            hashMap.put(job,1);


        });
        List<HashMap.Entry<String, Integer>> jobList = new ArrayList<Map.Entry<String, Integer>>(hashMap.entrySet());
        Collections.sort(jobList, new Comparator<Map.Entry<String, Integer>>() {

            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return new ArrayList<String>(hashMap.keySet());
    }

    private void transformSkillsToJobs(String skill, HashMap<String, Double> jobMap) {
        model.forEach(x->{
            if(!x.javaConsequent().toString().contains("职位"))
                return;
            String job=x.javaConsequent().get(0);
            List<String> skillList=x.javaAntecedent();

            if(skillList.size()>1)
                return;

            Boolean flag=false;
            for(int i=0;i<skillList.size();i++)
            {
                if (skillList.get(i).equals(skill))
                {
                    flag=true;
                }
            }
            if (flag==false)
                return;
            double weight=x.confidence();
            if(job.equals(preferedJob))
                weight*=weightElement;
            if (!jobMap.containsKey(job))
                jobMap.put(job,weight);
            else
                jobMap.put(job,jobMap.get(job)+weight);
//            System.out.println(job+"=>"+skillList.toString()+";"+x.confidence());

        });
        return ;

    }

    public static void createModel()
    {
        System.setProperty("hadoop.home.dir","C:\\winutils");
        SparkConf sparkConf= new SparkConf().setAppName("Analyzer");
        sparkConf.set("spark.driver.allowMultipleContexts","true");
        sparkConf.setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        FPGrowthModel FPTreemodel;
        if(model!=null)
            return;
        else {
            try {
                FPTreemodel = FPGrowthModel.load(sc.sc(), "../data/" + logisticRegression.getModelPath() + "/mod/FPTreeModel");

            } catch (Exception e) {
                String inputFile = "../data/" + logisticRegression.getModelPath() + "/cleanData/data.txt";
                double minSupport = 0.003;
                int numPartition = -1;


                JavaRDD<List<String>> transactions = sc.textFile(inputFile)
                        .map(s -> Arrays.asList(s.split(" ")))
                        .map(x -> {
                            x.set(0, x.get(0) + "职位");
                            return x;
                        })
                        .filter(s -> s.size() > 2);


                FPTreemodel = new FPGrowth()
                        .setMinSupport(minSupport)
                        .run(transactions);
                FPTreemodel.save(sc.sc(), "../data/" + logisticRegression.getModelPath() + "/mod/FPTreeModel");
            }


            ArrayList<Tuple3<String, String, Double>> list = new ArrayList<>();
            AssociationRules arules = new AssociationRules()
                    .setMinConfidence(0.15);
            JavaRDD<AssociationRules.Rule<String>> results = arules.run(FPTreemodel.freqItemsets().toJavaRDD());


            List<AssociationRules.Rule<String>> result = results.collect();
            sc.stop();
            model = result;
            return;
        }
    }
}

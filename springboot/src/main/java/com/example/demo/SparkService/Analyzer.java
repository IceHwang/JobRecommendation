package com.example.demo.SparkService;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.fpm.AssociationRules;
import org.apache.spark.mllib.fpm.FPGrowth;
import org.apache.spark.mllib.fpm.FPGrowthModel;
import scala.Tuple3;

import java.io.Serializable;
import java.util.*;

public class Analyzer implements Serializable {



    private ArrayList<String> skillList=null;
    private String preferedJob=null;
    private static List<AssociationRules.Rule<String>> model=null;
    private ArrayList<String> relativeSkillList=null;
    private double weightElement=1.2;

    public static void main(String[] args) {

        getSkillList().forEach(System.out::println);

        String[] skillArray={"tensorflow","pytorch","python","html","javascript","mysql","java","sql","c++","tomcat","cnn","svm"};
        ArrayList<String>  list = new ArrayList<>();
        for(String s:skillArray)
        {
            list.add(s);
        }
        Analyzer analyzer = new Analyzer(list,"机器学习算法工程师");

        System.out.println("###########################");
        String recommendJob=analyzer.getFirstRecommendedJob();
        System.out.println(recommendJob);
        System.out.println("###########################");
        ArrayList<String> recommendSkillList=analyzer.getRecommendedSkillList(recommendJob);
        recommendSkillList.forEach(System.out::println);
        System.out.println("###########################");
        ArrayList<String> coreSkillList=analyzer.getCoreSkillList(recommendJob);
        coreSkillList.forEach(System.out::println);
        System.out.println("###########################");

        System.out.println("###########################");
        recommendJob=analyzer.getSecondRecommendedJob();
        System.out.println(recommendJob);
        System.out.println("###########################");
        recommendSkillList=analyzer.getRecommendedSkillList(recommendJob);
        recommendSkillList.forEach(System.out::println);
        System.out.println("###########################");
        coreSkillList=analyzer.getCoreSkillList(recommendJob);
        coreSkillList.forEach(System.out::println);
        System.out.println("###########################");

    }


    public Analyzer(ArrayList<String> skillList, String preferedJob)
    {
        this.skillList=skillList;
        this.preferedJob=preferedJob;
        createModel();
    }

    public String getFirstRecommendedJob()
    {
        HashMap<String, Double> jobMap=new HashMap<>();
        skillList.forEach(skill->this.transformSkillsToJobs(skill,jobMap));
        List<HashMap.Entry<String, Double>> recommendJobList = new ArrayList<Map.Entry<String, Double>>(jobMap.entrySet());
        Collections.sort(recommendJobList, new Comparator<Map.Entry<String, Double>>() {

            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        recommendJobList.forEach(x-> System.out.println(x.toString()));

        return recommendJobList.get(0).getKey();
    }

    public String getSecondRecommendedJob()
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

        return recommendJobList.get(1).getKey();
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
        if(relativeSkillList==null)
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
        if(relativeSkillList==null)
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
        if(model!=null)
            return;
        String inputFile = "../data/test.txt";
        double minSupport=0.003;
        int numPartition=-1;

        SparkConf sparkConf= new SparkConf().setAppName("FPTreeAnalyzer");
        sparkConf.setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);

        JavaRDD<List<String>> transactions = sc.textFile(inputFile)
                .map(s-> Arrays.asList(s.split(" ")))
                .map(x->{
                    x.set(0,x.get(0)+"职位");
                    return x;
                })
                .filter(s->s.size()>2);


        FPGrowthModel<String> FPTreemodel = new FPGrowth()
                .setMinSupport(minSupport)
                .run(transactions);


//        ArrayList<String> list= new ArrayList<>();
//        for(FPGrowth.FreqItemset<String> s: model.freqItemsets().toJavaRDD().collect())
//        {
//            list.add("["+ Joiner.on(",").join(s.javaItems())+"]");
//        }

        ArrayList<Tuple3<String,String,Double>> list= new ArrayList<>();
        AssociationRules arules = new AssociationRules()
                .setMinConfidence(0.15);
        JavaRDD<AssociationRules.Rule<String>> results = arules.run(FPTreemodel.freqItemsets().toJavaRDD());

//        for (AssociationRules.Rule<String> rule : results.collect()) {
//            list.add(
//                    new Tuple3(rule.javaAntecedent().toString(),rule.javaConsequent().toString(),rule.confidence()));
//        }

        List<AssociationRules.Rule<String>> result=results.collect();
        sc.stop();
        model=result;
        return;
    }
}

package net.spark;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.fpm.FPGrowth;
import org.apache.spark.mllib.fpm.FPGrowthModel;
import org.spark_project.guava.base.Joiner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FPTreeAnalyzer implements Serializable {
    public static void main(String[] args) {
        ArrayList<String> list=run();
        list.forEach(System.out::println);
        System.out.println(list.size());

    }

    public static ArrayList<String> run()
    {
//        String inputFile = "hdfs://192.168.152.129:9000/usr/test.txt";
        String inputFile = "../data/test.txt";
        double minSupport=0.04;
        int numPartition=-1;

        SparkConf sparkConf= new SparkConf().setAppName("FPTreeAnalyzer");
        sparkConf.setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        //sc.addJar("/opt/test.jar");

        JavaRDD<List<String>> transactions = sc.textFile(inputFile)
                .map(s-> Arrays.asList(s.split(" ")))
                .filter(s->s.size()>3);


        FPGrowthModel<String> model = new FPGrowth()
                .setMinSupport(minSupport)
                .run(transactions);

        ArrayList<String> list= new ArrayList<>();

        for(FPGrowth.FreqItemset<String> s: model.freqItemsets().toJavaRDD().collect())
        {
            list.add("["+ Joiner.on(",").join(s.javaItems())+"]");
        }
        sc.stop();
        return list;
    }
}

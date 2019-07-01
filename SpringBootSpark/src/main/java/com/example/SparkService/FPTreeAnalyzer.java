package com.example.SparkService;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.fpm.FPGrowth;
import org.apache.spark.mllib.fpm.FPGrowthModel;
import org.spark_project.guava.base.Joiner;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class FPTreeAnalyzer implements Serializable {


    public String run() {
        String inputFile = "../data/test.txt";
        double minSupport = 0.09;
        int numPartition = -1;

        SparkConf sparkConf = new SparkConf().setAppName("FPTreeAnalyzer");
        sparkConf.setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        //sc.addJar("/opt/test.jar");

        JavaRDD<List<String>> transactions = sc.textFile(inputFile)
                .map(s -> Arrays.asList(s.split(" ")))
                .filter(s -> s.size() > 3);


        FPGrowthModel<String> model = new FPGrowth()
                .setMinSupport(minSupport)
                .run(transactions);

        String result = "";
        for (FPGrowth.FreqItemset<String> s : model.freqItemsets().toJavaRDD().collect()) {
            result += ("[" + Joiner.on(",").join(s.javaItems()) + "]");
        }
        sc.stop();
        return result;
    }


}

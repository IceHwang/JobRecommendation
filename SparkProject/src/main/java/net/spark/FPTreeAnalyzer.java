package net.spark;

import com.clearspring.analytics.util.Lists;
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

    public static void main(String[] args)
    {
        String inputFile = "hdfs://node-master:9000/usr/test.txt";
        double minSupport=0.09;
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
        for(FPGrowth.FreqItemset<String> s: model.freqItemsets().toJavaRDD().collect())
        {
            System.out.println("["+ Joiner.on(",").join(s.javaItems())+"]");
            System.out.println("$$$$$$$$$$$$$$$$$$$$");
        }
        sc.stop();
    }
}

package job.test.ipcounter;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;


public class SparkSolution {
    public static void main(String[] args) {


        final SparkConf conf = new SparkConf().setAppName("UniqueIPCounterSpark").setMaster("local[*]");
        final JavaSparkContext sc = new JavaSparkContext(conf);

        final String filePath = "ip.txt";
        final JavaRDD<String> lines = sc.textFile(filePath);

        final long uniqueIPs = lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator())
                .distinct()
                .count();

        System.out.println("Total unique IPs: " + uniqueIPs);

        sc.stop();

    }



}
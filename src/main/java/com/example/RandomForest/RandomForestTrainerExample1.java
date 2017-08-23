package com.example.RandomForest;

 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
 
import java.util.HashMap;
 


import org.apache.commons.io.FileUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.RandomForest;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import org.apache.spark.mllib.util.MLUtils;
import org.apache.spark.sql.SparkSession;
 

public class RandomForestTrainerExample1 {
    
public static void main(String[] args) {
    // hadoop home dir [path to bin folder containing winutils.exe]
    System.setProperty("hadoop.home.dir", "C:\\hadoop-2.7.4\\");
      
    // Configuring spark
   /* SparkConf sparkConf = new SparkConf().setAppName("RandomForestExample")
            .setMaster("local[2]")
            .set("spark.executor.memory","3g")
            .set("spark.driver.memory", "3g");
    */

    // local[*] : For multithreaded environment - run in number of cores present in machine
    // local[1] : Will run on 1 core only.
  
   SparkSession spark =  SparkSession.builder().master("local[*]").appName("RandomForestTrainerExample").config("spark.sql.warehouse.dir", "file:///C:/RandomForestSpark-master/RandomForestSpark-master/target/").getOrCreate();
    
    // initializing the spark context
    //JavaSparkContext jsc = new JavaSparkContext(sparkConf);
    
    // Load and parse the data file.
    String datapath = "input/new-result/trainResult.txt";
    JavaRDD trainingData;
    try {
    	//jsc.sc();
        trainingData = MLUtils.loadLibSVMFile(spark.sparkContext(), datapath).toJavaRDD();
    } catch (Exception e1) {
        System.out.println("No training data available.");
        e1.printStackTrace();
        return;
    }
	  /*
	   *
			Integer numClasses = 26; // 26 alphabets means 26 classes
			HashMap<Integer, Integer> categoricalFeaturesInfo = new HashMap<>(); // Empty categoricalFeaturesInfo indicates all features are continuous.
			Integer numTrees = 10; // Deafult is 5 but it is better practice to have more trees. If >1 then it is considered as a forest.
			String featureSubsetStrategy = "auto"; // Let the algorithm choose the best feature subset strategy.
			String impurity = "gini"; // For information gain
			Integer maxDepth = 20; //Maximum depth of the tree
			Integer maxBins = 40; // Number of maximum beans to be used
			Integer seed = 12345L; // Random seed
	   *   
	   *   
	   *   
	   */
  
    
    HashMap<Integer, Integer> categoricalFeaturesInfo =new HashMap<Integer, Integer>();
    String featureSubsetStrategy = "sqrt";  //"auto"  // Let the algorithm choose, which set of features to be made as subsets
    String impurity = "gini";    // adds impurity to the experiments/samples in the training data : gini is a choice
    Integer numClasses = 2; //3 - When Actual Result is selected as Category
    Integer numTrees = 1000;//3 
    Integer maxDepth = 4;    //3
    Integer maxBins = 100;//32   
    Integer seed = 200;  //12345  
 
    // training the classifier with all the hyper-parameters defined above
    final RandomForestModel model = RandomForest.trainClassifier(trainingData, numClasses,
      categoricalFeaturesInfo, numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins,
      seed);
 
    System.out.print("**************************");
    // Delete if model already present, and Save the new model
    try {
        FileUtils.forceDelete(new File("RandForestClsfrMdl1"));
        System.out.println("\nDeleting old model completed.");
    } catch (FileNotFoundException e1) {
    } catch (IOException e) {
    }
    
 // printing the random forest model (collection of decision trees)
    System.out.println(model.toDebugString());
    
    // saving the random forest model that is generated
    model.save(spark.sparkContext(), "RandForestClsfrMdl1"+File.separator+"model");
    System.out.println("\nRandForestClsfrMdl1/model has been created and successfully saved.");
    
    // printing the random forest model (collection of decision trees)
    System.out.println(model.toDebugString());
    
    spark.sparkContext().stop();
    
  }
}
 
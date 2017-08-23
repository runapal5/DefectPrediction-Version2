package com.example.RandomForest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.linalg.DenseVector;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.regression.LinearRegressionModel;
import org.apache.spark.mllib.regression.LinearRegressionWithSGD;
import org.apache.spark.mllib.tree.DecisionTree;
import org.apache.spark.mllib.tree.RandomForest;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import com.example.SparkSession.UtilityForSparkSession;

import javassist.bytecode.Descriptor.Iterator;
import scala.Tuple2;



public class JavaRandomForestClassificationDP {
	//static SparkSession spark = UtilityForSparkSession.mySession();
	  static List<TrainedData> predResultList = new ArrayList<TrainedData>();
  public static void main(String[] args) {
	  
	System.setProperty("hadoop.home.dir", "C:\\hadoop-2.7.4\\");
	
	SparkSession spark =  SparkSession.builder().master("local[*]").appName("RandomForestTrainerExample").config("spark.sql.warehouse.dir", "file:///C:/RandomForestSpark-master/RandomForestSpark-master/target/").getOrCreate();
	
    
	
    String input = "input/new-result/trainResult.data";
    Dataset<Row> my_data = spark.read().format("com.databricks.spark.csv").load(input);
    my_data.show(false);
    RDD<String> linesRDD = spark.sparkContext().textFile(input, 2);
	
    JavaRDD<LabeledPoint> data = linesRDD.toJavaRDD().map(new Function<String, LabeledPoint>() {
        @Override
        public LabeledPoint call(String row) throws Exception {
          String line = row.replaceAll("\\?", "999999.0");
          String[] tokens = line.split(",");
          Integer last = Integer.parseInt(tokens[3]);
          double[] features = new double[3];
          for (int i = 0; i < 3; i++) {
            features[i] = Double.parseDouble(tokens[i]);
          }
          Vector v = new DenseVector(features);
          Double value = 0.0;
          if (last.intValue() > 0)
            value = 1.0;
          LabeledPoint lp = new LabeledPoint(value, v);
          return lp;
        }
      });
    
	    double[] weights = {0.7, 0.3};
	    long split_seed = 12345L;
	    JavaRDD<LabeledPoint>[] split = data.randomSplit(weights, split_seed);
	    JavaRDD<LabeledPoint> training = split[0];
	    JavaRDD<LabeledPoint> test = split[1];

     
	    HashMap<Integer, Integer> categoricalFeaturesInfo =new HashMap<Integer, Integer>();
	    String featureSubsetStrategy = "sqrt";  //"auto"  // Let the algorithm choose, which set of features to be made as subsets
	    String impurity = "gini";    // adds impurity to the experiments/samples in the training data : gini is a choice
	    Integer numClasses = 2; //3 - When Actual Result is selected as Category
	    Integer numTrees = 1000;//3 
	    Integer maxDepth = 4;    //3
	    Integer maxBins = 100;//32   
	    Integer seed = 200;  //12345  
	   
	    
		  
	  final RandomForestModel model = RandomForest.trainClassifier(training, numClasses,categoricalFeaturesInfo, numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins, seed);
		
	  
	  
	  JavaPairRDD<Double, Double> predictionAndLabel =
	          test.mapToPair(new PairFunction<LabeledPoint, Double, Double>() {
	            @Override
	            public Tuple2<Double, Double> call(LabeledPoint p) {
	                //System.out.println("Label:::"+p.label() +", Features::"+p.features());
					System.out.println("Predict:::"+model.predict(p.features()) +", Actual::"+p.label());
					double[] featArr = p.features().toArray();
	                predResultList.add(new TrainedData(p.label(),featArr[0],featArr[1],featArr[2],model.predict(p.features())));
	            	return new Tuple2<>(model.predict(p.features()), p.label());
	              
	            }
	          });  
	  
	  
	   double accuracy = predictionAndLabel.filter(new Function<Tuple2<Double, Double>, Boolean>() {
          @Override
          public Boolean call(Tuple2<Double, Double> pl) {
            return pl._1().equals(pl._2());
          }
        }).count() / (double) test.count();
       System.out.println("Accuracy of the classification: "+accuracy);  
	  
    
       spark.sparkContext().stop();
       
       System.out.println("predResultList size=="+predResultList.size());
       
       String fileName = "input/new-result/trainedDataPredicted-Partion.csv";
       CsvFileWriter.writeCsvFile(fileName, predResultList);
       
  }
}

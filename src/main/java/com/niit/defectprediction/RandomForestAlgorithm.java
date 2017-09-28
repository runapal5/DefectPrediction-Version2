package com.niit.defectprediction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.predictionio.controller.java.P2LJavaAlgorithm;
import org.apache.predictionio.data.api.Stats;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.mllib.linalg.Matrix;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.RandomForest;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import org.apache.spark.mllib.util.MLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

import com.niit.defectprediction.CsvFileWriter;

//import org.apache.spark.mllib.linalg.Matrix;




public class RandomForestAlgorithm extends P2LJavaAlgorithm<PreparedData, RandomForestModel, Query, PredictedResult> {
	
	private static final Logger logger = LoggerFactory.getLogger(RandomForestAlgorithm.class);
    private final RandomForestAlgorithmsParams ap;

    
	public RandomForestAlgorithm(RandomForestAlgorithmsParams ap) {
        this.ap = ap;
    }
	
    

	@SuppressWarnings("unchecked")
	@Override
	public PredictedResult predict(RandomForestModel randomForestModel, Query query) {		
		logger.info("**********************Prediction on the model:predict************************");
		System.out.println("**********************Prediction on the model:predict************************");
		List<TestDataResult> predictList = new ArrayList<TestDataResult>();

		final RandomForestModel model = randomForestModel;
		//logger.info("Learned classification tree model:\n" + model.toDebugString()); 

		String testDatas = query.getTestDataPath();
		logger.info("Test Data Path:\n" +testDatas); 
		
		//GETTING THE SPARK CONTEXT
		SparkConf conf = new SparkConf().setAppName("defectPrediction");
		JavaSparkContext ctx = JavaSparkContext.fromSparkContext(SparkContext.getOrCreate(conf));
		JavaStreamingContext context = new JavaStreamingContext(ctx, Durations.seconds(60));
		
		//GETTING THE SPARK CONTEXT
		
		
		 JavaRDD<LabeledPoint> loadedTestdata = MLUtils.loadLibSVMFile(context.sparkContext().sc(), testDatas).toJavaRDD();  
		 logger.info("*************Test Data Path Loaded**********" +loadedTestdata.count()); 
		 //logger.info("*************Test Data Path Loaded**********" +loadedTestdata.toDebugString()); 
		 long totalDataCount = loadedTestdata.count();
		 long diffCount = 0;
		
		 
		 
		 
		 List<LabeledPoint> loadedTestdataList =   loadedTestdata.collect();
		 JavaRDD<LabeledPoint> parallelLabelled =  ctx.parallelize(loadedTestdataList);
		 List<LabeledPoint> parallelLoadedTestdataList =   parallelLabelled.collect(); 
			 
			 
		 parallelLoadedTestdataList.forEach(labelData -> { 
			 
			     double[] featArr = labelData.features().toArray();
				 double actual = labelData.label();
				 double predicted = randomForestModel.predict(labelData.features());
				 System.out.println(String.format("Predicted: %.1f, Label: %.1f", randomForestModel.predict(labelData.features()), labelData.label())); 
				 predictList.add(new TestDataResult(actual,featArr[0],featArr[1],featArr[2],featArr[3],featArr[4],featArr[5],predicted));
			  
			});
		 
		 JavaPairRDD<Object, Object> predictionsAndLabels = loadedTestdata.mapToPair(
			        p -> new Tuple2<Object, Object>(model.predict(p.features()), p.label())
			    );

		 logger.info("predictionAndLabelsCount: \n" + predictionsAndLabels.count());
		 
		  // Writing Output to CSV File
		   String testDataResultpath =  ap.getTestDataFile();
	       logger.info("TestData File:\n" + testDataResultpath); 
	       CsvFileWriter.writeCsvFile(testDataResultpath, predictList); 
		 
	       logger.info("*************Output Saved**********"); 
		 
		 
		 try{
		 
				 // Get evaluation metrics.
				 MulticlassMetrics metrics = new MulticlassMetrics(predictionsAndLabels.rdd());
				 Matrix confusionMatrix = metrics.confusionMatrix();
				 
				 
				 logger.info( "\nConfusion metrics: \n" + metrics.confusionMatrix());
				 
				// logger.info( "\nAccuracy \n" +  metrics.accuracy());
				 logger.info( "\nPrecision \n" +  metrics.precision());
				 logger.info( "\nRecall \n" +  metrics.recall());
				 
				// logger.info("predictionAndLabels: \n" + predictionsAndLabels.toDebugString());
		 }catch(Exception ex){
			 logger.info("Exception = " + ex.getMessage());
		 }
			 
			
		 logger.info("*************Prediction Done**********"+predictList.size()); 
		 		
		 
		
		return new PredictedResult(predictList) ;
		
	}

	@Override
	public RandomForestModel train(SparkContext sparkContext, PreparedData data) {
		logger.info("**********************Train the model************************");
		if(data != null && data.getTrainingData().getLabelledPoints() != null){
			System.out.println("Counts :::"+data.getTrainingData().getLabelledPoints().count());
			logger.info("**********************Train the model*****Counts :::"+data.getTrainingData().getLabelledPoints().count());
		}
		
		logger.info("Parmeter Values::"+ap.getNumClasses() +","+ap.getNumTrees()+","+ap.getMaxDepth());
		System.out.println("Parmeter Values::"+ap.getNumClasses() +","+ap.getNumTrees()+","+ap.getMaxDepth());
		
		HashMap<Integer, Integer> categoricalFeaturesInfo =new HashMap<Integer, Integer>();
		RandomForestModel model = RandomForest.trainClassifier(data.getTrainingData().getLabelledPoints(), ap.getNumClasses(),
			      categoricalFeaturesInfo, ap.getNumTrees(), ap.getFeatureSubsetStrategy(), ap.getImpurity(), ap.getMaxDepth(), ap.getMaxBins(),
			      ap.getSeed());
	
		
		return model;
	}
	
	
	
	
}

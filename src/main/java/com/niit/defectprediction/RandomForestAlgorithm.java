package com.niit.defectprediction;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.niit.defectprediction.RequestDetails;


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
		
		 
		

		String testDatas = ap.getTestDataPath().trim() + query.getProjectId().trim()+".txt";
		logger.info("Test Data Path:\n" +testDatas); 
		
		String reqDatas = ap.getTestDataPath().trim() + query.getProjectId().trim()+".csv";
		logger.info("Requirement Datas:\n" +reqDatas); 
		
		
		HashMap<Double,String> reqIdNameMap = RequirementMapReader.readCsvFile(reqDatas) ;
		
		
		//GETTING THE SPARK CONTEXT
		SparkConf conf = new SparkConf().setAppName("defectPrediction");
		JavaSparkContext ctx = JavaSparkContext.fromSparkContext(SparkContext.getOrCreate(conf));
		JavaStreamingContext context = new JavaStreamingContext(ctx, Durations.seconds(60));
		
		RandomForestModel model1 = RandomForestModel.load(context.sparkContext().sc(),"DefectPredicted"+File.separator+"model");
		logger.info("Learned classification tree model:\n" + model.toDebugString()); 
		
		//GETTING THE SPARK CONTEXT
		
		
		 JavaRDD<LabeledPoint> loadedTestdata = MLUtils.loadLibSVMFile(context.sparkContext().sc(), testDatas).toJavaRDD();  
		 logger.info("*************Test Data Path Loaded**********" +loadedTestdata.count()); 
		 //logger.info("*************Test Data Path Loaded**********" +loadedTestdata.toDebugString()); 
		 long totalDataCount = loadedTestdata.count();
		 long diffCount = 0;
		
		 
		 
		 
		 List<LabeledPoint> loadedTestdataList =   loadedTestdata.collect();
		 JavaRDD<LabeledPoint> parallelLabelled =  ctx.parallelize(loadedTestdataList);
		 List<LabeledPoint> parallelLoadedTestdataList =   parallelLabelled.collect(); 
		 HashMap<Double,ArrayList<TestDataResult>> reqMap = new HashMap<Double,ArrayList<TestDataResult>>();	 
			 
		 parallelLoadedTestdataList.forEach(labelData -> { 
			 
			     double[] featArr = labelData.features().toArray();
				 double actual = labelData.label();
				 double predicted = randomForestModel.predict(labelData.features());
				 System.out.println(String.format("Predicted: %.1f, Label: %.1f", randomForestModel.predict(labelData.features()), labelData.label())); 
				 
				 String reqName =  reqIdNameMap.get(featArr[3]);
				 
				 TestDataResult testDataResult = new TestDataResult(actual,featArr[0],featArr[1],featArr[2],featArr[3],featArr[4],featArr[5],featArr[6],predicted,reqName);
				 
				 predictList.add(testDataResult);
			  
				 //REQID : featArr[3]
				 if(reqMap.containsKey(featArr[3]))
				  {
					  ArrayList<TestDataResult> testCaseDtlList = reqMap.get(featArr[3]);
					  testCaseDtlList.add(testDataResult);
					  reqMap.put(featArr[3], testCaseDtlList);
					  
				  }else{
					  ArrayList<TestDataResult> testCaseDtlList = new ArrayList<TestDataResult>(); 
					  testCaseDtlList.add(testDataResult);
					  reqMap.put(featArr[3], testCaseDtlList);
					  
				  }
				 
				 
			});
		 
		 JavaPairRDD<Object, Object> predictionsAndLabels = loadedTestdata.mapToPair(
			        p -> new Tuple2<Object, Object>(model.predict(p.features()), p.label())
			    );

		 logger.info("predictionAndLabelsCount: \n" + predictionsAndLabels.count());
		 
		 ArrayList<RequestDetails> reqDetailsSummary = setRequestSummaryDtls(reqMap,reqIdNameMap);
		 Collections.sort(reqDetailsSummary); // sorting on the basis of the Total No. of Defected Failure
		 
		 
		 
		  // Writing Detailed Output to CSV File
		   String testDataResultpath =  ap.getTestDataFile();
	       logger.info("TestData File:\n" + testDataResultpath); 
	       CsvFileWriter.writeCsvFile(testDataResultpath, predictList); 
		 
	       logger.info("*************Output Saved**********"); 
	       
	       
	       // Writing Summary Output to CSV File
		   String summtestDataResultpath =  ap.getTestDataSummaryFile();
	       logger.info("Summmary TestData File:\n" + summtestDataResultpath); 
	       SummaryCSVFileWriter.writeCsvFile(summtestDataResultpath, reqDetailsSummary); 
		 
	       logger.info("*************Output Saved**********"); 
	       
		   int totalNumberOfRequirement = 0;
		   int totalNumberOfTestCases = 0;
		   int totalNumberOfDefectPredicted = 0;
		   
	       try{
	    	   if(reqDetailsSummary != null && !reqDetailsSummary.isEmpty()){
	    		   Iterator<RequestDetails> requestDetailsItr = reqDetailsSummary.iterator();
	    		   while(requestDetailsItr.hasNext()){
	    			   RequestDetails reqDtl = requestDetailsItr.next();
	    			   totalNumberOfRequirement = totalNumberOfRequirement + 1;
	    			   totalNumberOfTestCases = totalNumberOfTestCases + reqDtl.getTotalTC();
	    			   totalNumberOfDefectPredicted = totalNumberOfDefectPredicted + reqDtl.getTotalFail();
	    		   }
	    		   
	    	   }
	    	   
	       }catch(Exception ex){
	    	   ex.printStackTrace();
	       }
	       
	     ProgramData programData = new ProgramData(totalNumberOfRequirement, totalNumberOfTestCases,totalNumberOfDefectPredicted ,reqDetailsSummary);
		 /*
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
		*/	 
			
		logger.info("*************Prediction Done**********"+predictList.size()); 
		 		
		 
		
		return new PredictedResult(programData) ;
		
	}

	private ArrayList<RequestDetails> setRequestSummaryDtls(
			HashMap<Double, ArrayList<TestDataResult>> requirmentTestCaseMap,HashMap<Double,String> reqIdNameMap) {
		 Iterator<Map.Entry<Double,ArrayList<TestDataResult>>> iterator = requirmentTestCaseMap.entrySet().iterator();
		 ArrayList<RequestDetails>  requirmentDetailsMap = new ArrayList<RequestDetails>();
		 while(iterator.hasNext()){
	            Map.Entry<Double,ArrayList<TestDataResult>> entry = iterator.next();
	           /* System.out.printf("Key : %s and Value: %s %n", entry.getKey(), 
	                                                           entry.getValue().size());*/
	            //iterator.remove(); // right way to remove entries from Map,  avoids ConcurrentModificationException
	            Double reqId = entry.getKey(); // Combination of ReqID 
	            ArrayList<TestDataResult> testCaseList = entry.getValue();
	            int size = testCaseList.size();
	            int totalPassCount = 0; //PASS = 0
	            int totalFailCount = 0; //FAIL = 1
	            int totalLastCycleRun = 0;
	            Set<Double> testCaseIdSet = new HashSet<Double>();
	            for(int i=0; i < size ; i++){
	            	TestDataResult testCase = testCaseList.get(i);
	            	if(testCase.getPredicted() == 0) {
	            		totalPassCount = totalPassCount + 1;
	            	}else{
	            		totalFailCount = totalFailCount + 1;
	            	}
	            	testCaseIdSet.add(testCase.getTestId());
	            	totalLastCycleRun = totalLastCycleRun + (int)(testCase.getRunCycle());
	            }
	            int totalTCPerReqId =  testCaseIdSet.size(); // Total Number of TC's per module/requirement
	           // int totalTCRun = totalPassCount + totalFailCount ; // Total Number of TC's run which should be equal to size of testCaseList
	            
	            
	            
	           // double failurePercentage = (double)totalFailCount*100/totalTCRun;
	            
	            String reqName =  reqIdNameMap.get(reqId);
	            RequestDetails reqDtl = new RequestDetails(reqId,totalTCPerReqId, totalLastCycleRun,totalFailCount,reqName);
	            requirmentDetailsMap.add(reqDtl);
	            //System.out.println("ReqId:::"+reqId.intValue()+ ", No.OfTC::"+totalTCPerReqId + ", totalTCRun:::"+totalTCRun +", SizOfTCList::"+size +", TotalPass:::"+totalPassCount +", TotalFail::"+ totalFailCount +", MeanOfFailedTC::"+ meanOfFailedTC + ", MeanOfTCRun:::"+ meanOfTCRun + ", MeanOfTCPerReq:::"+meanOfTCPerReq);
		 }
         // System.out.println(requirmentDetailsMap);
          
         
          System.out.println(requirmentDetailsMap);
		 
		 
		  
		  return requirmentDetailsMap;
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
		
		
		model.save(sparkContext, "DefectPredicted"+File.separator+"model");
		logger.info("Model Saved :::"+ model.toDebugString());
		
		return model;
	}
	
	
	
	
}

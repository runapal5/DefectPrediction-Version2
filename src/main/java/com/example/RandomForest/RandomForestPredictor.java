package com.example.RandomForest;


import scala.Tuple2;

import java.io.File;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.Number;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import org.apache.spark.mllib.util.MLUtils;
import org.apache.spark.sql.SparkSession;
 
/** RandomForest Classification Example using Spark MLlib
* @author tutorialkart.com
*/
public class RandomForestPredictor {
    static RandomForestModel model;
    
    static List<TrainedData> predResultList = new ArrayList<TrainedData>();
    
 
    public static void main(String[] args) {
        // hadoop home dir [path to bin folder containing winutils.exe]
    	 System.setProperty("hadoop.home.dir", "C:\\hadoop-2.7.4\\");
       
    	 
    	 
        
        SparkSession spark =  SparkSession.builder().master("local[*]").appName("RandomForestTrainerExample").config("spark.sql.warehouse.dir", "file:///C:/RandomForestSpark-master/RandomForestSpark-master/target/").getOrCreate();
        
        
        
        // loading the model, that is generated during training
        model = RandomForestModel.load(spark.sparkContext(),"RandForestClsfrMdl1"+File.separator+"model");
        System.out.println("Learned classification tree model:\n" + model.toDebugString()); 

        
        // Load and parse the test data file.
        String datapath = "input/new-result/testValues.txt";
        JavaRDD data = MLUtils.loadLibSVMFile(spark.sparkContext(), datapath).toJavaRDD();
        
        
        
        // Evaluate model on test instances and compute test error
        JavaPairRDD<Double, Double> predictionAndLabel =
                data.mapToPair(pf);
        
        // compute error of the model to predict the categories for test samples/experiments
        Double testErr =
                1.0 * predictionAndLabel.filter(f).count() / data.count();
        
        
		System.out.println("Accuracy of the classification: "+testErr);  
        
        
     // Evaluation-2: evaluate the model on test instances and compute the related performance measure statistics        
        JavaRDD<Tuple2<Object, Object>> predictionAndLabels = data.map(
      	      new Function<LabeledPoint, Tuple2<Object, Object>>() {
      			public Tuple2<Object, Object> call(LabeledPoint p) {
      	          Double prediction = model.predict(p.features());
      	          return new Tuple2<Object, Object>(prediction, p.label());
      	        }
      	      }
      	    ); 
        
        
        /*
         
        // Get evaluation metrics.
        MulticlassMetrics metrics = new MulticlassMetrics(predictionAndLabels.rdd());
        System.out.println("Confusion Metrics::"+ metrics.confusionMatrix());
        //System.out.println("Metrics Labels::"+ metrics.labels());
       
        double precision = metrics.precision(metrics.labels()[0]);
        double recall = metrics.recall(metrics.labels()[0]);
        double f_measure = metrics.fMeasure();
        double query_label = 4.0;
        double TP = metrics.truePositiveRate(query_label);
        double FP = metrics.falsePositiveRate(query_label);
        double WTP = metrics.weightedTruePositiveRate();
        double WFP =  metrics.weightedFalsePositiveRate();
        System.out.println("Precision = " + precision);
        System.out.println("Recall = " + recall);
        System.out.println("F-measure = " + f_measure);
        System.out.println("True Positive Rate(TP) = " + TP);
        System.out.println("False Positive Rate(FP) = " + FP);
        System.out.println("Weighted True Positive Rate(WTP) = " + WTP);
        System.out.println("Weighted False Positive Rate(WFP) = " + WFP);
        */
        spark.sparkContext().stop();
        
        System.out.println("predResultList size=="+predResultList.size());
        
        String fileName = "input/new-result/trainedDataPredicted.csv";
        CsvFileWriter.writeCsvFile(fileName, predResultList);
        
       
    }
 
    private static PairFunction<LabeledPoint, Double, Double> pf =  new PairFunction<LabeledPoint, Double, Double>() {
        @Override
        public Tuple2<Double, Double> call(LabeledPoint p) {
            Double prediction= null;
            double actualResult = 0;
            try {
            	
            	
            	//Vector featureData = p.features();
            	//System.out.println("***************FEATURE DATA*****************************************"+featureData.toString());
            	//System.out.println("***************LABELORCATEGORY*****************************************"+p.label());
            	/*System.out.println(featureData.toString());
            	double[] featArr = featureData.toArray();
            	System.out.println("RequirementId:"+ featArr[0]);
            	System.out.println("TestId:"+featArr[1]);
            	System.out.println("RegWt:"+featArr[2]);
            	System.out.println("ReqSize:"+featArr[3]);
            	System.out.println("ReqQuality:"+featArr[4]);
            	System.out.println("Actual Result:"+featArr[5]);
            	actualResult = featArr[5];
            	
            	double predictedResult = featArr[2] + featArr[3] + featArr[4] ;
            	System.out.println("Predicted Result:"+predictedResult);*/
           
                prediction = model.predict(p.features());
               
                
                System.out.println("Predict:::"+prediction +", Actual::"+p.label());
                double[] featArr = p.features().toArray();
                
                predResultList.add(new TrainedData(p.label(),featArr[0],featArr[1],featArr[2],prediction));
                
                // Only when ReqId is considered as Category
               /*
  			    double diff = p.label() - prediction;
                double predFlag = 0.0;
                if(diff == 0.0){
                	//System.out.println("***************PREDICTION:"+0+" , For ReqId: "+p.label());
                	predFlag = 0.0;
                }else
                {
                	//System.out.println("***************PREDICTION:"+1+" , For ReqId: "+p.label());
                	predFlag = 1.0;
                }
                
               System.out.println("***************PREDICTION:"+predFlag+" , For ReqId: "+p.label());
                */
            /*    // FOR CSV
                
                double[] featArr = p.features().toArray();
                StringBuilder predBuilder = new StringBuilder();
    	          predBuilder.append(p.label())
    	          .append("-").append(featArr[0])
    	          .append("-").append(featArr[1])
    	          .append("-").append(featArr[2])
    	          .append("-").append(predFlag);
    	          
    	          predResultList.add(predBuilder.toString());
                  //System.out.println(predBuilder.toString());
                */
                
            } catch (Exception e) {
                //logger.error(ExceptionUtils.getStackTrace(e));
                e.printStackTrace();
            }
          
           
            
            //System.out.println(prediction+" : "+p.label());
            return new Tuple2<>(prediction, p.label());
            //return new Tuple2<>(prediction, actualResult);
        }
    };
    
    
   
    
    
    private static Function<Tuple2<Double, Double>, Boolean> f = new Function<Tuple2<Double, Double>, Boolean>() {
        @Override
        public Boolean call(Tuple2<Double, Double> pl) {
            return !pl._1().equals(pl._2());
        }
    };
    
    
    
    
}
package com.niit.defectprediction;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.niit.defectprediction.TrainingData;

import org.apache.predictionio.controller.EmptyParams;
import org.apache.predictionio.controller.java.PJavaDataSource;
import org.apache.predictionio.data.storage.Event;
import org.apache.predictionio.data.storage.PropertyMap;
import org.apache.predictionio.data.store.java.OptionHelper;
import org.apache.predictionio.data.store.java.PJavaEventStore;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.linalg.DenseVector;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
//import org.apache.spark.sql.Row;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.Option;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.JavaConversions;
import scala.collection.JavaConversions$;
import scala.collection.Seq;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataSource extends PJavaDataSource<TrainingData, EmptyParams, Query, Set<String>> {
	
	private static final Logger logger = LoggerFactory.getLogger(DataSource.class);

    private final DataSourceParams dsp;

    public DataSource(DataSourceParams dsp) {
        this.dsp = dsp;
    }

    @Override
    public TrainingData readTraining(SparkContext sc) {
      
    	
    	String datapath = "/quickstartapp/trainResult.txt";//"input/new-result/trainResult.txt";
        JavaRDD labelledPoints = null;
        try {
        	labelledPoints = MLUtils.loadLibSVMFile(sc, datapath).toJavaRDD();
        } catch (Exception e1) {
            System.out.println("No training data available.");
            e1.printStackTrace();
           
        }
        
    	
        return new TrainingData(labelledPoints);
    }


}

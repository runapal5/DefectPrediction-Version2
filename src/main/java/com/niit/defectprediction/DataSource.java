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
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import org.apache.spark.rdd.RDD;
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
       /*
    	JavaRDD labelledPoints1 = null;
    	
    	labelledPoints1 = PJavaEventStore.aggregateProperties(
                dsp.getAppName(),
                "user",
                OptionHelper.<String>none(),
                OptionHelper.<DateTime>none(),
                OptionHelper.<DateTime>none(),
                OptionHelper.<List<String>>none(),
                sc).map(
                		new Function<Tuple2<String, PropertyMap>,LabeledPoint>() {

                			
							@Override
							public LabeledPoint call(
									Tuple2<String, PropertyMap> entityIdProperty)
									throws Exception {
								System.out.println("entityIdProperty loaded from events store :::"+entityIdProperty);
								Set<String> keys = JavaConversions$.MODULE$.setAsJavaSet(entityIdProperty._2().keySet());
		                        Map<String, String> properties = new HashMap<>();
		                        for (String key : keys) {
		                            properties.put(key, entityIdProperty._2().get(key, String.class));
		                        }
		                        System.out.println("Properties loaded from events store :::"+properties.toString());
								return null;
							}                  			
                  	      }
                		
                		);
    	
    	 System.out.println("readTraining :::"+labelledPoints1);
    	*/
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

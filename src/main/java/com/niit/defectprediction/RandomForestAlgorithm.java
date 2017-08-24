package com.niit.defectprediction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.predictionio.controller.java.P2LJavaAlgorithm;
import org.apache.predictionio.data.storage.Model;
import org.apache.spark.SparkContext;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.tree.RandomForest;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class RandomForestAlgorithm extends P2LJavaAlgorithm<PreparedData, RandomForestModel, Query, PredictedResult> {
	
	private static final Logger logger = LoggerFactory.getLogger(RandomForestAlgorithm.class);
    private final RandomForestAlgorithmsParams ap;

    public RandomForestAlgorithm(RandomForestAlgorithmsParams ap) {
        this.ap = ap;
    }
	

	@Override
	public PredictedResult predict(RandomForestModel randomForestModel, Query query) {		
		System.out.println("**********************Prediction on the model************************");
		System.out.println("Query:Features ::::"+query.getPlan()+","+query.getRegwt()+","+query.getReqsize()+","+query.getReqquality());
		logger.info("Query:Features ::::"+query.getPlan()+","+query.getRegwt()+","+query.getReqsize()+","+query.getReqquality());
		logger.info("Predicted Result:"+randomForestModel.predict(Vectors.dense(query.getPlan(),query.getRegwt(),query.getReqsize(),query.getReqquality())));
			
		return new PredictedResult(randomForestModel.predict(Vectors.dense(query.getPlan(),query.getRegwt(),query.getReqsize(),query.getReqquality()))) ;
		
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

package com.niit.defectprediction;

import org.apache.predictionio.controller.Params;


public class RandomForestAlgorithmsParams implements Params {

	private final Integer numClasses;
	
	private final Integer numTrees;
	
	private final String featureSubsetStrategy;
	
	private final String impurity;
	
	private final Integer maxDepth;
	
	private final Integer maxBins;
	
	private final Integer seed;
	
	private final String testDataFile;
	
	private final String testDataSummaryFile;
	
	private final String testDataPath;
	
	private final String trainedDataSummaryFile;


	public RandomForestAlgorithmsParams(Integer numClasses ,Integer numTrees ,String featureSubsetStrategy , String impurity , Integer maxDepth , Integer maxBins , Integer seed, String testDataFile, String testDataSummaryFile, String testDataPath, String trainedDataSummaryFile){
		
		this.numClasses = numClasses;
		this.numTrees = numTrees;
		this.featureSubsetStrategy = featureSubsetStrategy;
		this.impurity = impurity;
		this.maxDepth = maxDepth;
		this.maxBins = maxBins;
		this.seed = seed;
		this.testDataFile = testDataFile;
		this.testDataSummaryFile = testDataSummaryFile;
		this.testDataPath = testDataPath;
		this.trainedDataSummaryFile = trainedDataSummaryFile;
	}
	
	
	public String getTrainedDataSummaryFile() {
		return trainedDataSummaryFile;
	}


	public String getTestDataPath() {
		return testDataPath;
	}


	public String getTestDataSummaryFile() {
		return testDataSummaryFile;
	}


	public String getTestDataFile() {
		return testDataFile;
	}
	
	public Integer getSeed() {
		return seed;
	}


	public Integer getNumClasses() {
		return numClasses;
	}

	public Integer getNumTrees() {
		return numTrees;
	}

	public String getFeatureSubsetStrategy() {
		return featureSubsetStrategy;
	}

	public String getImpurity() {
		return impurity;
	}

	public Integer getMaxDepth() {
		return maxDepth;
	}

	public Integer getMaxBins() {
		return maxBins;
	}
	
	
	@Override
    public String toString() {
        return "RandomForestAlgorithmsParams{" +
                "numClasses=" + numClasses +
                ", numTrees=" + numTrees +
                ", featureSubsetStrategy=" + featureSubsetStrategy +
                ", impurity=" + impurity +
                ", maxDepth='" + maxDepth + 
                ", maxBins=" + maxBins +
                ", seed=" + seed +
                '}';
    }
	
	
	
	
}

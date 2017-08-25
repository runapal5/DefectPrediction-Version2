package com.niit.defectprediction;

import org.apache.predictionio.controller.Params;

public class DataSourceParams implements Params{
    private final String appName;
    private final String trainingData;

   

	public DataSourceParams(String appName, String trainingData) {
        this.appName = appName;
        this.trainingData = trainingData;
    }

    public String getAppName() {
        return appName;
    }
    
    public String getTrainingData() {
		return trainingData;
	}
}

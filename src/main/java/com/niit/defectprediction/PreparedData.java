package com.niit.defectprediction;

import java.io.Serializable;

import org.apache.spark.api.java.JavaRDD;

public class PreparedData implements Serializable {
    private final TrainingData trainingData;

    public TrainingData getTrainingData() {
		return trainingData;
	}

	public PreparedData(TrainingData trainingData) {
        this.trainingData = trainingData;
    }

    
   
}

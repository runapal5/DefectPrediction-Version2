package com.niit.defectprediction;

import java.io.Serializable;

import org.apache.spark.api.java.JavaRDD;

public class PreparedData implements Serializable {
    private final JavaRDD trainingData;

    public PreparedData(JavaRDD trainingData) {
        this.trainingData = trainingData;
    }

    public JavaRDD getTrainingData() {
        return trainingData;
    }
}

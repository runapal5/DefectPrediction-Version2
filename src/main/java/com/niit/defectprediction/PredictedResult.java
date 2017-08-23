package com.niit.defectprediction;

import java.io.Serializable;
import java.util.List;

public class PredictedResult implements Serializable{
    private final Double predicted;

    public PredictedResult(Double predicted) {
        this.predicted = predicted;
    }

    
	public Double getPredicted() {
		return predicted;
	}


	@Override
    public String toString() {
        return "PredictedResult{" +
                "predictedResult=" + predicted +
                '}';
    }
}

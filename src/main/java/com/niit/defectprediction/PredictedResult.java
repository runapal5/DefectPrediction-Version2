package com.niit.defectprediction;

import java.io.Serializable;
import java.util.List;

public class PredictedResult implements Serializable{
    private final Double label;

    public PredictedResult(Double label) {
        this.label = label;
    }

    

    public Double getLabel() {
		return label;
	}



	@Override
    public String toString() {
        return "PredictedResult{" +
                "predictedResult=" + label +
                '}';
    }
}

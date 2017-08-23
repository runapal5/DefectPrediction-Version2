package com.niit.defectprediction;

import java.io.Serializable;

import org.apache.spark.api.java.JavaRDD;

public class PreparedData implements Serializable {
    private final JavaRDD labelledPoint;

    public PreparedData(JavaRDD labelledPoint) {
        this.labelledPoint = labelledPoint;
    }

	public JavaRDD getLabelledPoint() {
		return labelledPoint;
	}

   
}

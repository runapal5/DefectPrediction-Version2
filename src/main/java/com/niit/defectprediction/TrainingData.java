package com.niit.defectprediction;

import org.apache.predictionio.controller.SanityCheck;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.regression.LabeledPoint; 
import java.io.Serializable;

public class TrainingData implements Serializable, SanityCheck {
    private final JavaRDD<LabeledPoint> labelledPoints;
   

    public JavaRDD<LabeledPoint> getLabelledPoints() {
		return labelledPoints;
	}


	public TrainingData(JavaRDD<LabeledPoint> labelledPoints) {
        this.labelledPoints = labelledPoints;       
    }

   
    @Override
    public void sanityCheck() {
        if (labelledPoints == null) {
            throw new AssertionError("Labelled Point is empty");
        }
        
    }
}

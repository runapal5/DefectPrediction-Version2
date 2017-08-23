package com.niit.defectprediction;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

public class Query implements Serializable{
    private final double[] features;
    

    public Query(double[] features) {
        this.features = features;
        
    }


    public double[] getFeatures() {
		return features;
	}


	@Override
    public String toString() {
    	
    	int sizeOfFeatures = this.features.length; 
    	StringBuilder featuresBuilder = new StringBuilder();
    	for(int i=0 ; i < sizeOfFeatures ; i++){
    		featuresBuilder.append(this.features[i]).append(",");
    	}
    	
        return "Query{" + featuresBuilder.toString() + '}';
               
    }
}

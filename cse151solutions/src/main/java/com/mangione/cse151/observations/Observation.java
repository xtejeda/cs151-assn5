package com.mangione.cse151.observations;

public class Observation {
    private final double[] features;

    public Observation(double[] features) {
        this.features = features;
    }

    public double[] getFeatures() {
        return features;
    }
}

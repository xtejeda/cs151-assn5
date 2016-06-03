package com.mangione.cse151.sampling;

import org.apache.commons.math3.random.RandomGenerator;

public class SamplingWithoutReplacement {
    private final RandomGenerator random;
    private double numberNeeded;
    private double numberLeft;

    public SamplingWithoutReplacement(double samplePercent, long totalNumber, RandomGenerator random) {
        numberNeeded = Math.round(samplePercent * totalNumber);
        numberLeft = totalNumber;
        this.random = random;
    }

    public boolean select() {
        double currentProbability = numberNeeded / numberLeft;
        final boolean selected = random.nextDouble() < currentProbability;
        numberLeft--;
        if (selected)
            numberNeeded--;
        return selected;
    }
}

package com.mangione.cse151.assignments.assignment1;

import com.mangione.cse151.observationproviders.CsvObservationProvider;
import com.mangione.cse151.observations.ObservationFactory;
import com.mangione.cse151.sampling.SamplingWithoutReplacement;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.io.File;
import java.net.URL;

public class Assignment1 {
    public static void main(String[] args) throws Exception {
        URL abaloneURL = Assignment1.class.getClassLoader().getResource("com/mangione/cse151/abalone/abalone.data.txt");

        assert abaloneURL != null;
        long totalNumberOfLines = new CsvObservationProvider<>(new File(abaloneURL.toURI()), new ObservationFactory()).getNumberOfLines();

        MersenneTwister random = new MersenneTwister(198273);

        int[] numbersOfRuns = {10, 100, 1000, 10000, 10000};
        Stats[] allStats = new Stats[numbersOfRuns.length];

        for (int i = 0; i < numbersOfRuns.length; i++) {
            allStats[i] = getStats(totalNumberOfLines, random, numbersOfRuns[i]);
        }
        new XYRenderer(allStats, numbersOfRuns);
    }

    private static Stats getStats(long totalNumberOfLines, MersenneTwister random, int numberOfRuns) {
        double[] indexSelections = new double[(int)totalNumberOfLines];
        for (int i = 0; i < numberOfRuns; i++) {
            SamplingWithoutReplacement samplingWithoutReplacement = new SamplingWithoutReplacement(.10, totalNumberOfLines, random);
            for (int j = 0; j < totalNumberOfLines; j++) {
                if (samplingWithoutReplacement.select())
                    indexSelections[j]++;
            }
        }
        StandardDeviation sd = new StandardDeviation(false);
        Mean mean = new Mean();
        return new Stats(mean.evaluate(indexSelections) / numberOfRuns, sd.evaluate(indexSelections) / numberOfRuns);
    }

}

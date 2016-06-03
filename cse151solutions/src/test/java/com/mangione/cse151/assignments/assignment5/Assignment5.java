package com.mangione.cse151.assignments.assignment5;

import com.mangione.cse151.observationproviders.CsvObservationProvider;
import com.mangione.cse151.observations.Observation;
import com.mangione.cse151.observations.ObservationFactory;
import com.mangione.cse151.observationproviders.SampledObservationProvider;
import org.apache.commons.math3.random.MersenneTwister;

import java.io.File;

/**
 * Created by Xavier Tejeda on 6/2/2016.
 */
public class Assignment5
{
    public static final int SEED = 104395301;

    public static void main(String[] args) throws Exception
    {
        int spamTotal = 0;
        int hamTotal = 0;

        double[] spamValues = new double[9275];
        double[] hamValues = new double[9275];

        double[] spamProb = new double[9275];
        double[] hamProb = new double[9275];

        double[][] trainingArray = new double[2798][9275];

        final MersenneTwister randomForSampling = new MersenneTwister(SEED);
        File file = new File(Assignment5.class.getClassLoader()
                .getResource("com/mangione/cse151/assignment5/SpamDataPruned.csv").toURI());
        CsvObservationProvider<Observation> csvObsProvider = new CsvObservationProvider<>(file, new ObservationFactory());

        SampledObservationProvider trainingSet = new SampledObservationProvider(.10, csvObsProvider, randomForSampling, false);

        int k = 0;
        while(trainingSet.hasNext() && k < 2798)
        {
            System.out.println(k);
            trainingArray[k] = trainingSet.next().getFeatures();
            k++;
        }

        for(int i = 0; i < 2518; i++)
        {
            for(int j = 0; j < 9275; j++)
            {
                if(trainingArray[i][9274] == 1)
                {
                    spamValues[j] = spamValues[j] + trainingArray[i][j];
                    spamTotal++;
                }
                else
                {
                    hamValues[j] = hamValues[j] + trainingArray[i][j];
                    hamTotal++;
                }
            }
        }

        for(int a = 0; a < 9275; a++)
        {
            spamProb[a] = ( spamValues[a] + 1 ) / ( spamTotal + 9275 );
        }

        for(int b = 0; b < 9275; b++)
        {
            hamProb[b] = ( hamValues[b] + 1 ) / ( hamTotal + 9275 );
        }
    }
}

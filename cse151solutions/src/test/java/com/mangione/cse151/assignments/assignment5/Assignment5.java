package com.mangione.cse151.assignments.assignment5;

import com.mangione.cse151.observationproviders.CsvObservationProvider;
import com.mangione.cse151.observations.Observation;
import com.mangione.cse151.observations.ObservationFactory;

import java.io.File;

/**
 * Created by Xavier Tejeda on 6/2/2016.
 */
public class Assignment5
{
    public static void main(String[] args) throws Exception
    {
        int spamTotal = 0;
        int hamTotal = 0;

        int[] spamValues = new int[9275];
        int[] hamValues = new int[9275];

        int[] spamProb = new int[9275];
        int[] hamProb = new int[9275];

        int[][] trainingArray = new double[2798][9275];

        final RandomGenerator randomForSampling = new MersenneTwister(SEED);
        File file = new File(Assignment5.class.getClassLoader()
                .getResource("com/mangione/cse151/assignment5/SpamDataPruned.csv").toURI());
        CsvObservationProvider<Observation> csvObsProvider = new CsvObservationProvider<>(file, new ObservationFactory());

        SampledObservationProvider trainingSet = new SampledObservationProvider(.90, csvObsProvider, randomForSampling, false);

        int k = 0;
        while(trainingSet.hasNext && i < 2798)
        {
            trainingArray[k] = trainingSet.next().getFeatures();
            k++;
        }

        for(int i = 0; i < 2798; i++)
        {
            for(int j = 0; j < 9275; j++)
            {
                if(trainingArray[i][9274] = 1)
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

        for(int b = 0; b < 9275; a++)
        {
            hamProb[b] = ( hamValues[b] + 1 ) / ( hamTotal + 9275 );
        }
    }
}

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

        int[][] confusionMatrix = new int[2][2];

        double spamTest = 0.0;
        double hamTest = 0.0;

        double[] spamValues = new double[9275];
        double[] hamValues = new double[9275];

        double[] spamProb = new double[9275];
        double[] hamProb = new double[9275];

        double[][] trainingArray = new double[2798][9275];
        double[][] testArray = new double[2798][9275];

        final MersenneTwister randomForSampling = new MersenneTwister(SEED);
        File file = new File(Assignment5.class.getClassLoader()
                .getResource("com/mangione/cse151/assignment5/SpamDataPruned.csv").toURI());
        CsvObservationProvider<Observation> csvObsProvider = new CsvObservationProvider<>(file, new ObservationFactory());

        SampledObservationProvider trainingSet = new SampledObservationProvider(.10, csvObsProvider, randomForSampling, false);

        int k = 0;
        while(trainingSet.hasNext() && k < 2798)
        {
            //System.out.println(k);
            trainingArray[k] = trainingSet.next().getFeatures();
            k++;
        }

        csvObsProvider.reset();
        SampledObservationProvider testSet = new SampledObservationProvider(.10, csvObsProvider, randomForSampling, true);
        int l = 0;
        while(testSet.hasNext() && l < 279)
        {
            System.out.println(l);
            testArray[l] = testSet.next().getFeatures();
            l++;
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


        for(int c = 0; c < 279; c++)
        {
            hamTest=0.0;
            spamTest=0.0;
            int test=0;
            for (int d = 0; d < 9275; d++)
            {
                //System.out.print(Math.log10(spamProb[10]));
                test += testArray[c][d];
                spamTest = spamTest + testArray[c][d] * Math.abs( Math.log10(spamProb[d]) );
                hamTest = hamTest +  testArray[c][d] * Math.abs( Math.log10(hamProb[d]) );
            }
            System.out.println("\n"+spamTest+" >? "+hamTest + "\t" +test);
            if(spamTest > hamTest)
            {
                if(testArray[c][9274] == 1)
                {
                    confusionMatrix[1][1]++;
                }
                else
                {
                    confusionMatrix[1][0]++;
                }
            }
            else
            {
                if(testArray[c][9274] == 0)
                {
                    confusionMatrix[0][0]++;
                }
                else
                {
                    confusionMatrix[0][1]++;
                }
            }
        }

        System.out.println("Predicted 1, Actual 1: " + confusionMatrix[1][1]);
        System.out.println("Predicted 1, Actual 0: " + confusionMatrix[1][0]);
        System.out.println("Predicted 0, Actual 0: " + confusionMatrix[0][0]);
        System.out.println("Predicted 0, Actual 1: " + confusionMatrix[0][1]);
    }
}

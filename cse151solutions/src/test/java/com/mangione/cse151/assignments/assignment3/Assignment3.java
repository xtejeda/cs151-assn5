package com.mangione.cse151.assignments.assignment3;

import com.mangione.cse151.observationproviders.CsvObservationProvider;
import com.mangione.cse151.observationproviders.SampledObservationProvider;
import com.mangione.cse151.observations.Observation;
import com.mangione.cse151.observations.ObservationFactory;
import com.mangione.cse151.householders.HouseHolders;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;

import java.io.File;


/**
 * Created by jmwample on 5/7/16.
 */
public class Assignment3 {

    public static final int SEED = 104395301;

    public static void main(String[] args) throws Exception {
        String[] files = {"regression-0.05.csv", "regression-A.csv", "regression-B.csv", "regression-C.csv"};
        for (String currentFile : files){
            System.out.println(currentFile);
            processSingleFile(currentFile,10000, 31);
        }
        System.out.println("abaloneC.csv");
        processSingleFile("abaloneC.csv", 4177, 11);
    }

    private static void processSingleFile(String currentFile, int rows, int cols) throws Exception{

        double percentage = .7;
        int rowsTrain =(int)(rows*percentage);
        int rowsTest =(int)(rows*(1-percentage));
        double squareError = 0;
        double[][] trainingArray = new double[rowsTrain][cols];


        final RandomGenerator randomForSampling = new MersenneTwister(SEED);
        File file = new File(Assignment3.class.getClassLoader()
                .getResource("com/mangione/cse151/assignment3/"+currentFile).toURI());
        CsvObservationProvider<Observation> csvObsProvider = new CsvObservationProvider<>(file, new ObservationFactory());


        SampledObservationProvider trainingSet = new SampledObservationProvider(1-percentage,
                csvObsProvider, randomForSampling, false);

        int i=0;
        while (trainingSet.hasNext() && i <rowsTrain) {
            //System.out.println(i);
            trainingArray[i] = trainingSet.next().getFeatures();
            i++;
        }

        HouseHolders qrHouse = new HouseHolders();
        qrHouse.HouseHolders(trainingArray);

        RandomGenerator sameAsSampling = new MersenneTwister(SEED);

        csvObsProvider.reset();
        SampledObservationProvider testSet = new SampledObservationProvider(1-percentage, csvObsProvider,
                sameAsSampling, true);

        double prediction;
        double[] nextObs;

        for (i=0; i<rowsTest; i++) {
            if (( nextObs = testSet.next().getFeatures()) == null)
                break;

            prediction = qrHouse.solve(nextObs);

            squareError += Math.pow(prediction-nextObs[cols-1], 2)/rowsTest;
        }
        System.out.println("L2Norm = " + Math.sqrt(squareError));
    }

}

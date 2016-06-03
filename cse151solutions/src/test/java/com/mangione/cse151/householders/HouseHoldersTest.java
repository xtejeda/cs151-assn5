package com.mangione.cse151.householders;

import com.mangione.cse151.observationproviders.*;
import com.mangione.cse151.observations.Observation;
import com.mangione.cse151.observations.ObservationFactory;
import org.ejml.simple.SimpleMatrix;
import org.junit.Test;

import java.io.File;


public class HouseHoldersTest extends HouseHolders{
    public static int SEED = 104395301;


    @Test
    public void testHouseHolders() throws Exception {
        // houseHoldersOnX(X);
        //String[] files = {"regression-A.csv", "regression-B.csv", "regression-C.csv", "regression-0.05.csv"};
        //for (String currentFile : files){
        //    processSingleFile(currentFile);
        //}
        processSingleFile("regression-test.csv");

    }

    private void processSingleFile(String currentFile) throws Exception {
        int rows =0;
        int cols =0;

        File file = new File(HouseHoldersTest.class.getClassLoader()
                .getResource("com/mangione/cse151/assignment3/" + currentFile).toURI());
        CsvObservationProvider<Observation> observationProvider = new CsvObservationProvider<>(file, new ObservationFactory());

        //System.out.println("Making it here");

        while (observationProvider.hasNext()){
            rows++;
            cols = observationProvider.next().getFeatures().length;
        }

        double[][] array = new double[rows][cols];
        observationProvider.reset();
        //System.out.println("File = "+currentFile+";   Rows = "+rows+";  Cols = " +cols);

        int i=0;
        while (observationProvider.hasNext()) {
            array[i] = observationProvider.next().getFeatures();
            i++;
        }
        SimpleMatrix data = new SimpleMatrix(array);

        System.out.println("File = "+currentFile+";   Rows = "+data.numRows()+";  Cols = " +data.numCols());
        //System.out.println( data);
        HouseHolders H =  HouseHolders(array);
        SimpleMatrix solutions = H.original.getSolutions();



        observationProvider.reset();

        double squareError=0;
        for (int j=0; j<rows; j++) {
            double prediction = H.solve(observationProvider.next().getFeatures());
            //System.out.println(prediction + "   " +solutions.get(j,0));
            squareError += Math.pow(prediction-solutions.get(j, 0), 2)/rows;
        }
        System.out.println("L2Norm = " + Math.sqrt(squareError));

    }

//            2.0,  1.0,  2.0
//            2.0,  2.0,  -4.0
//            3.0,  1.0,  -3.0
//            1.0,  -2.0,  -4.0
//            -3.0,  2.0,  -3.0
}

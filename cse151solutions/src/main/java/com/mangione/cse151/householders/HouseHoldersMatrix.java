package com.mangione.cse151.householders;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by jmwample on 5/5/16.
 */
public class HouseHoldersMatrix {
    private SimpleMatrix fullMatrix;
    private int numRows;
    private int numCols;


    public HouseHoldersMatrix(SimpleMatrix m) {
        fullMatrix = m;
        numRows = m.numRows();
        numCols = m.numCols();
    }

    public HouseHoldersMatrix(double[][] m) {
        fullMatrix = new SimpleMatrix(m);
        numRows = fullMatrix.numRows();
        numCols = fullMatrix.numCols();
    }

    public HouseHoldersMatrix nextStep(){
        double[][] next = new double[numRows-1][numCols-1];
        for (int i=0;i < numRows-1; i++){
            for (int j=0; j < numCols-1; j++) {
                next[i][j] = fullMatrix.get(i+1, j+1);

            }
        }
        HouseHoldersMatrix Next = new HouseHoldersMatrix(next);
        return Next;
    }


    public HouseHoldersMatrix assemble(HouseHoldersMatrix nextMatrix) {
        SimpleMatrix submatrix = nextMatrix.getFullMatrix();
        SimpleMatrix newMatrix = new SimpleMatrix(fullMatrix);
        int x = numCols-nextMatrix.numCols();
        if (nextMatrix.numCols() == numCols && nextMatrix.numRows() == numRows){
            return nextMatrix;
        }

        for (int i=0; i<nextMatrix.numRows(); i++) {
            for (int j=0; j<nextMatrix.numCols(); j++) {

                newMatrix.set(i+x, j+x, submatrix.get(i, j));
            }
        }
        return new HouseHoldersMatrix(newMatrix);
    }

    public SimpleMatrix getFirstColumn() {
        double[][] x = new double[numRows][1];
        for(int row = 0; row < numRows; row++)
        {
            x[row][0] = fullMatrix.get(row, 0);
        }
        SimpleMatrix X = new SimpleMatrix(x);
        return X;
    }

    public SimpleMatrix getSolutions(){
        double[][] x = new double[numRows][1];
        for(int row = 0; row < numRows; row++)
        {
            x[row][0] = fullMatrix.get(row, numCols-1);
        }
        SimpleMatrix X = new SimpleMatrix(x);
        return X;
    }

    public int numCols() { return numCols;}

    public int numRows() { return numRows; }

    public SimpleMatrix getFullMatrix() {
        return fullMatrix;
    }

}

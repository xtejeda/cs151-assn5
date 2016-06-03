package com.mangione.cse151.householders;
/**
 * Created by jmwample on 5/5/16.
 */

import com.mangione.cse151.observations.Observation;
import org.ejml.simple.SimpleMatrix;
import sun.java2d.pipe.SpanShapeRenderer;

public class HouseHolders {

    public static boolean DEBUG = false;
    public HouseHoldersMatrix original;
    private SimpleMatrix R;
    private SimpleMatrix Q;
    private SimpleMatrix I;
    private double[] Beta;
    private SimpleMatrix yHat;
    private boolean isSolved = false;

    public HouseHolders HouseHolders(double[][] a){
        original = new HouseHoldersMatrix(a);
        Q = SimpleMatrix.identity(a.length);
        I = SimpleMatrix.identity(a.length);
        //System.out.println("Original = "+ original.getFullMatrix());
        R = recurseThroughHouseHolders(original).getFullMatrix();
        //System.out.println("Final = "+ R);
        //System.out.println("Q = "+Q);
        yHat = Q.transpose().mult( original.getSolutions() );
        //System.out.println("YHat = "+ yHat);
        Beta = backSolve(R, yHat, original.numCols());
        System.out.println("nBeta = ");
        for (double b: Beta) System.out.print(b+",  ");
        System.out.print("\n\n");
        return this;
    }

    public HouseHolders HouseHolders(SimpleMatrix a){
        original = new HouseHoldersMatrix(a);
        Q = SimpleMatrix.identity(a.numRows());
        I = SimpleMatrix.identity(a.numRows());
        //System.out.println("Original = "+ original.getFullMatrix());
        R = recurseThroughHouseHolders(original).getFullMatrix();
        //System.out.println("Final = "+ R);
        yHat = Q.transpose().mult( original.getSolutions() );
        //System.out.println("YHat = "+ yHat);
        Beta = backSolve(R, yHat, original.numCols());
        //System.out.println("Beta = ");
        //for (double b: Beta) System.out.print(" " +b);
        //System.out.print("\n");
        return this;
    }

    public SimpleMatrix getR(){ return R; }

    public SimpleMatrix getQ(){ return Q; }


    private HouseHoldersMatrix recurseThroughHouseHolders(HouseHoldersMatrix houseHoldersMatrix){

        HouseHoldersMatrix transformed = runOneHouseHolders(houseHoldersMatrix);
        if (DEBUG) System.out.println("transformed = " + transformed.getFullMatrix());
        if (houseHoldersMatrix.numCols() == 1 ) {
            return transformed;
        }

        HouseHoldersMatrix next = recurseThroughHouseHolders(transformed.nextStep());
        final HouseHoldersMatrix assembled = transformed.assemble(next);
        if (DEBUG) System.out.println("Assembled = " + assembled.getFullMatrix());

        return assembled;
    }

    private HouseHoldersMatrix runOneHouseHolders(HouseHoldersMatrix householdersMatrix) {
        SimpleMatrix x = householdersMatrix.getFullMatrix();
        SimpleMatrix X1 = householdersMatrix.getFirstColumn();
        System.out.print(".");

        double magX1 = Math.sqrt(X1.dot(X1));

        SimpleMatrix E1 = createE1(X1.numRows());
        double minusOrPlus = X1.get(0,0) >= 0 ? 1 : -1;

        SimpleMatrix uminus = X1.plus(E1.scale(magX1).scale(minusOrPlus));

        double mag2 = uminus.dot(uminus);
        final SimpleMatrix uminusT = uminus.transpose();
        final SimpleMatrix uminusXuminusT = uminus.mult(uminusT);
        SimpleMatrix pi =  SimpleMatrix.identity(uminus.numRows()).minus(uminusXuminusT.scale(2/mag2));
        HouseHoldersMatrix Pi = new HouseHoldersMatrix(pi);
        if (DEBUG) System.out.println("Pi = "+Pi.getFullMatrix());
        //HouseHoldersMatrix Qi = new HouseHoldersMatrix(I).assemble(Pi);
        //if (DEBUG) System.out.println("Qi = "+Qi.getFullMatrix());
        Q = new HouseHoldersMatrix(I).assemble(Pi).getFullMatrix().mult(Q);
        if (DEBUG) System.out.println("Q = "+Q);
        SimpleMatrix HX = x.minus(uminusXuminusT.mult(x).scale(2/mag2));
        Pi = null;
        return new HouseHoldersMatrix(HX);
    }

    private double[] backSolve(SimpleMatrix r, SimpleMatrix y, int n) {
        double sum;
        double[] b = new double[n];

        for (int i = n-2; i >= 0; i--) {
            sum = 0;
            for (int j = i+1; j < n-1; j++) {
                if (DEBUG) System.out.print(r.get(i,j)+ " * "+ b[j]+ " + ");
                sum += r.get(i, j) * b[j];
            }
            b[i] = (y.get(i,0) - sum) / r.get(i, i);
            if (DEBUG) System.out.println("\n"+b[i]+" = "+(y.get(i,0)-sum)+" / " +r.get(i,i));
        }

        if (DEBUG) System.out.println(b);
        isSolved = true;

        return b;
    }


    public SimpleMatrix createE1(int dimension) {
        double[][] e1 = new double[dimension][1];
        for (int i = 0; i < dimension; i++) {
            if (i == 0) e1[i][0] = 1;
            else e1[i][0] = 0;
        }
        SimpleMatrix E1 = new SimpleMatrix(e1);
        e1=null;
        return E1;
    }


    // Create a prediction based a test observation
    public double solve(double[] x){
        if (! isSolved){
            if (DEBUG) System.out.println("Soling Now");
             Beta = backSolve( R, yHat, original.numCols());
        }
//        double[] x = observation.getFeatures();
//        for (double i: x) System.out.print(i)
//        System.out.print("\n");
        if (x.length != Beta.length) throw new RuntimeException("Dimensions don't agree");
        double sum = 0.0;
        for (int i = 0; i < x.length; i++)
            sum = sum + (x[i] * Beta[i]);
        return sum;

    }

}

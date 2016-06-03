package com.mangione.cse151.classifiers;

import com.mangione.cse151.observationproviders.ArrayObservationProvider;
import com.mangione.cse151.observationproviders.ObservationProvider;
import com.mangione.cse151.observations.DiscreteExemplar;
import com.mangione.cse151.observations.Observation;
import com.mangione.cse151.observations.ObservationFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NNearestNeighborTest {

    @Test
    public void threePointsOneClass() throws Exception {
        double[][] training = {{1, 1, 1}, {2, 2, 1}, {1, 1.5, 1}};

        ObservationProvider<Observation> provider = new ArrayObservationProvider<>(training, new ObservationFactory());
        int n = 2;
        NNearestNeighbor nearestNeighbor = new NNearestNeighbor(provider, n);
        assertEquals(1, nearestNeighbor.classify(new DiscreteExemplar(new double[]{2, 3}, 1.0, 1)));
    }

    @Test
    public void sixPointsTwoClassesSeparable() throws Exception {
        double[][] training = {{1, 1, 1}, {2, 2, 1}, {1, 1.5, 1}, {-4, -3, 0}, {-6, -7, 0}, {-5, -6, 0}};

        ObservationProvider<Observation> provider = new ArrayObservationProvider<>(training, new ObservationFactory());
        int n = 2;
        NNearestNeighbor nearestNeighbor = new NNearestNeighbor(provider, n);
        assertEquals(1, nearestNeighbor.classify(new DiscreteExemplar(new double[]{2, 3}, 1.0, 1)));
    }

    @Test
    public void sixPointsOneMisclassified() throws Exception {
        double[][] training = {{1, 1, 1}, {2, 2, 1}, {1, 1.5, 1}, {-4, -3, 1}, {-6, -7, 0}, {-5, -6, 0}};

        ObservationProvider<Observation> provider = new ArrayObservationProvider<>(training, new ObservationFactory());
        int n = 2;
        NNearestNeighbor nearestNeighbor = new NNearestNeighbor(provider, n);
        assertEquals(1, nearestNeighbor.classify(new DiscreteExemplar(new double[]{2, 3}, 1.0, 1)));
    }
}

package com.mangione.cse151.observationproviders;

import com.mangione.cse151.observations.Observation;
import com.mangione.cse151.observations.ObservationFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayObservationProviderTest {
    @Test
    public void oneColumn() throws Exception {
        double[][] data = {{3},{5},{6}, {7}};
        ArrayObservationProvider<Observation> aop = new ArrayObservationProvider<>(data, new ObservationFactory());
        int number = 0;
        while (aop.hasNext()) {
            final Observation next = aop.next();
            double[] features = next.getFeatures();
            assertEquals(1, features.length);
            assertEquals(data[number][0], features[0], 0);
            number++;
        }
        assertEquals(data.length, number);
    }

}
package com.mangione.cse151.observations;

public class ObservationFactory implements ObservationFactoryInterface<Observation> {
    @Override
    public Observation create(double[] data) {
        return new Observation(data);
    }
}

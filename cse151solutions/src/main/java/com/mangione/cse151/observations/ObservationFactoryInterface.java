package com.mangione.cse151.observations;

public interface ObservationFactoryInterface<T extends Observation> {
    T create(double[] data);
}

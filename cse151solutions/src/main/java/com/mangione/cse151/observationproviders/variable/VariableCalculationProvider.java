package com.mangione.cse151.observationproviders.variable;

import com.mangione.cse151.observationproviders.ObservationProvider;
import com.mangione.cse151.observationproviders.VariableCalculator;
import com.mangione.cse151.observations.Observation;
import com.mangione.cse151.observations.ObservationFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VariableCalculationProvider extends ObservationProvider<Observation> {
    private final ObservationProvider<Observation> provider;
    private final Map<Integer, VariableCalculator> indexToCalculator;

    public VariableCalculationProvider(ObservationProvider<Observation> provider,
            Map<Integer, VariableCalculator> indexToCalculator) {
        super(new ObservationFactory());
        this.provider = provider;
        this.indexToCalculator = indexToCalculator;
    }


    @Override
    public boolean hasNext() throws Exception {
        return provider.hasNext();
    }

    @Override
    public Observation next() throws Exception {
        return null;
    }

    @Override
    public void reset() throws Exception {
        provider.reset();
    }

    @Override
    public long getNumberOfLines() throws IOException {
        return provider.getNumberOfLines();
    }



}

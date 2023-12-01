package com.wakfu.function.otherOperators;

import com.wakfu.function.Function;
import com.wakfu.stats.Stats;

public class Constant implements Function {
    
    private double value;

    public Constant(double value) {

        this.value = value;
    }

    @Override
    public Double run(Stats stats) {

        return value;
    }

}

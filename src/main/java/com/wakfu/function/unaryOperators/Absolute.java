package com.wakfu.function.unaryOperators;

import com.wakfu.function.Function;
import com.wakfu.stats.Stats;

public class Absolute extends UnaryOperator {

    public Absolute(Function func) {
        super(func);
    }

    @Override
    public Double run(Stats stats) {

        return Math.abs(func.run(stats));
    }
}

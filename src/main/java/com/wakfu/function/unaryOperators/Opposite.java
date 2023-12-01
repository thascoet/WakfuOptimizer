package com.wakfu.function.unaryOperators;

import com.wakfu.function.Function;
import com.wakfu.stats.Stats;

public class Opposite extends UnaryOperator {

    public Opposite(Function func) {
        super(func);
    }

    @Override
    public Double run(Stats stats) {
        
        return -func.run(stats);
    }

}

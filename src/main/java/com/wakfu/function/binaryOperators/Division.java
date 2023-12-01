package com.wakfu.function.binaryOperators;

import com.wakfu.function.Function;
import com.wakfu.stats.Stats;

public class Division extends BinaryOperator {

    public Division(Function func1, Function func2) {
        super(func1, func2);
    }

    @Override
    public Double run(Stats stats) {
        
        return func1.run(stats) / func2.run(stats);
    }   
}

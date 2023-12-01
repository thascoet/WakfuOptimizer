package com.wakfu.function.binaryOperators;

import com.wakfu.function.Function;
import com.wakfu.stats.Stats;

public class Substraction extends BinaryOperator {
    
    public Substraction(Function func1, Function func2) {
        super(func1, func2);
    }

    @Override
    public Double run(Stats stats) {
        
        return func1.run(stats) - func2.run(stats);
    }
}

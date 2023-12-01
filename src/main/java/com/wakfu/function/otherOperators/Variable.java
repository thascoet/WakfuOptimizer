package com.wakfu.function.otherOperators;

import java.util.Objects;

import com.wakfu.function.Function;
import com.wakfu.stats.Stat;
import com.wakfu.stats.Stats;

public class Variable implements Function {
    
    private Stat stat;

    public Variable(Stat stat) {

        this.stat = stat;
    }

    @Override
    public Double run(Stats stats) {

        return Double.valueOf(Objects.requireNonNullElse(stats.get(stat), 0));
    }
}

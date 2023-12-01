package com.wakfu.function.unaryOperators;

import com.wakfu.function.Function;

public abstract class UnaryOperator implements Function {
    
    protected Function func;

    public UnaryOperator(Function func) {

        this.func = func;
    }
}

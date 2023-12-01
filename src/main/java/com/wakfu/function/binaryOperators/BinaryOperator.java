package com.wakfu.function.binaryOperators;

import com.wakfu.function.Function;

public abstract class BinaryOperator implements Function {
    
    protected Function func1;
    protected Function func2;

    public BinaryOperator(Function func1, Function func2) {

        this.func1 = func1;
        this.func2 = func2;
    }
}

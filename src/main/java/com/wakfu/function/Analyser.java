package com.wakfu.function;

import com.wakfu.function.binaryOperators.Addition;
import com.wakfu.function.binaryOperators.Division;
import com.wakfu.function.binaryOperators.Exponentiation;
import com.wakfu.function.binaryOperators.Multiplication;
import com.wakfu.function.binaryOperators.Substraction;
import com.wakfu.function.otherOperators.Constant;
import com.wakfu.function.otherOperators.Variable;
import com.wakfu.function.unaryOperators.Absolute;
import com.wakfu.function.unaryOperators.Opposite;
import com.wakfu.stats.Stat;

public class Analyser {

    private int count;
    private String functionString;

    private Analyser(String functionString) {

        this.count = 0;
        this.functionString = functionString;
    }

    private boolean current(char c) {

        return count < functionString.length() && functionString.charAt(count) == c;
    }

    private char getCurrent() {

        return functionString.charAt(count);
    }

    private void consume(char c) throws Exception {

        if (!current(c))
            throw new Exception(c + "expected at index " + count);

        count++;
    }

    private double consumeConstant() throws Exception {

        int startingCount = count;
        boolean dot = true;

        while (((functionString.charAt(count) >= 48) && functionString.charAt(count) <= 57)
                || (dot && (functionString.charAt(count) == '.'))) {
            if (functionString.charAt(count) == '.')
                dot = false;
            count++;
        }

        try {
            return Double.valueOf(functionString.substring(startingCount, count));
        } catch (NumberFormatException e) {
            throw new Exception(functionString.substring(startingCount, count) + " is not a valid double value");
        }
    }

    private Stat consumeParameter() throws Exception {

        int startingCount = count;

        while ((functionString.charAt(count) >= 65) && (functionString.charAt(count) <= 90))
            count++;

        try {
            return Stat.valueOf(functionString.substring(startingCount, count));
        } catch (IllegalArgumentException e) {
            throw new Exception(functionString.substring(startingCount, count) + " is not a valid Stat");
        }
    }

    private Function expressionAddSub() throws Exception {

        Function func = expressionMultDiv();
        return expressionAddSubRec(func);
    }

    private Function expressionAddSubRec(Function funcAcc) throws Exception {

        if (current('+')) {

            consume('+');
            Function func = expressionAddSub();
            return new Addition(funcAcc, func);
        }

        if (current('-')) {

            consume('-');
            Function func = expressionAddSub();
            return new Substraction(funcAcc, func);
        }

        return funcAcc;
    }

    private Function expressionMultDiv() throws Exception {

        Function func = expressionExp();
        return expressionMultDivRec(func);
    }

    private Function expressionMultDivRec(Function funcAcc) throws Exception {

        if (current('*')) {

            consume('*');
            Function func = expressionMultDiv();
            return new Multiplication(funcAcc, func);
        }

        if (current('/')) {

            consume('/');
            Function func = expressionMultDiv();
            return new Division(funcAcc, func);
        }

        return funcAcc;
    }

    private Function expressionExp() throws Exception {

        Function func = expressionUnary();
        return expressionExpRec(func);
    }

    private Function expressionExpRec(Function funcAcc) throws Exception {

        if (current('^')) {

            consume('^');
            Function func = expressionExp();
            return new Exponentiation(funcAcc, func);
        }

        return funcAcc;
    }

    private Function expressionUnary() throws Exception {

        if (current('-')) {

            consume('-');
            return new Opposite(primitive());
        }

        if (current('@')) {

            consume('@');
            return new Absolute(primitive());
        }

        return primitive();
    }

    private Function primitive() throws Exception {

        if (current('(')) {

            consume('(');
            Function func = expressionAddSub();
            consume(')');
            return func;
        }

        if (current('.') || (getCurrent() >= 48 && getCurrent() <= 57)) {

            Function func = new Constant(consumeConstant());
            return func;
        }

        if ((getCurrent() >= 65) && (getCurrent() <= 90)) {

            Function func = new Variable(consumeParameter());
            return func;
        }

        throw new Exception();
    }

    public static Function analyse(String functionString) throws Exception {

        Analyser analyser = new Analyser(functionString);

        return analyser.expressionAddSub();
    }
}

package com.wakfu.function;

import com.wakfu.stats.Stat;

public class Analyser {

    private int count;
    private String functionString;

    private Analyser(String functionString) {

        this.count = 0;
        this.functionString = functionString;
    }

    private Function analyse() {
        return null;
    }

    public boolean current(char c) {

        return count < functionString.length() && functionString.charAt(count) == c;
    }

    public void consume(char c) throws Exception {

        if (!current(c))
            throw new Exception(c + "expected at index " + count);

        count++;
    }

    public double consumeConstant() throws Exception {

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

    public Stat consumeParameter() throws Exception {

        int startingCount = count;

        while ((functionString.charAt(count) >= 65) && (functionString.charAt(count) <= 90)) count++;

        try {
            return Stat.valueOf(functionString.substring(startingCount, count));
        } catch(IllegalArgumentException e) {
            throw new Exception(functionString.substring(startingCount, count) + " is not a valid Stat");
        }
    }

    public static Function analyse(String functionString) {

        Analyser analyser = new Analyser(functionString);

        return analyser.analyse();
    }
}

package com.wakfu.stats;

import java.util.EnumMap;
import java.util.Map;

public class Stats {
    private int[] statsArray;

    // Constructeur prenant une HashMap en paramètre
    public Stats(int[] statsArray) {

        this.statsArray = statsArray;
    }

    public Stats() {

        this.statsArray = new int[Stat.values().length];

        for (int i=0; i<Stat.values().length; i++) this.statsArray[i] = 0;
    }

    // Méthode pour obtenir la valeur d'une stat spécifique
    public int get(Stat stat) {

        for (int i=0; i<Stat.values().length; i++) if (Stat.values()[i] == stat) return this.statsArray[i];
        return 0;
    }

    // Méthode pour obtenir la HashMap complète
    public int[] getAll() {
        return this.statsArray;
    }

    public static Stats combineStats(Iterable<Stats> statsList) {

        Stats combineStats = new Stats();

        return combineStats;
    }

    @Override
    public String toString() {

        Map<Stat, Integer> notNullStatsMap = new EnumMap<>(Stat.class);

        return "Stats" + notNullStatsMap;
    }
}
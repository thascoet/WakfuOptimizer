package com.wakfu.stats;

import java.util.HashMap;
import java.util.Map;

public class Stats {
    private Map<Stat, Integer> statsMap;

    // Constructeur prenant une HashMap en paramètre
    public Stats(Map<Stat, Integer> initialStats) {

        this.statsMap = new HashMap<>();

        // Remplir la statsMap avec les valeurs initiales, mettant 0 pour les clés
        // manquantes
        for (Stat stat : Stat.values()) {
            if (initialStats.containsKey(stat)) {
                this.statsMap.put(stat, initialStats.get(stat));
            } else {
                this.statsMap.put(stat, 0);
            }
        }
    }

    public Stats() {

        this.statsMap = new HashMap<>();

        for (Stat stat : Stat.values())
            this.statsMap.put(stat, 0);
    }

    // Méthode pour obtenir la valeur d'une stat spécifique
    public int get(Stat stat) {
        Integer value = statsMap.get(stat);
        if (value == null) {
            throw new IllegalArgumentException("La stat " + stat + " n'est pas présente dans la HashMap.");
        }
        return value;
    }

    private void set(Stat stat, Integer value) {

        statsMap.put(stat, value);
    }

    // Méthode pour obtenir la HashMap complète
    public Map<Stat, Integer> getAll() {
        return statsMap;
    }

    public static Stats combineStats(Iterable<Stats> statsList) {

        Stats combineStats = new Stats();

        statsList.forEach((stats) -> {
            for (Stat stat : Stat.values())
                combineStats.set(stat, combineStats.get(stat) + stats.get(stat));
        });

        return combineStats;
    }

    @Override
    public String toString() {

        Map<Stat, Integer> notNullStatsMap = new HashMap<>();

        statsMap.forEach((stat, value) -> {
            if (value != 0) notNullStatsMap.put(stat, value);
        });

        return "Stats" + notNullStatsMap;
    }
}
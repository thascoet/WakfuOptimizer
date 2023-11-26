package com.wakfu.stats;

import java.util.HashMap;
import java.util.Map;

public class Stats {
    private Map<Stat, Integer> statsMap;

    // Constructeur prenant une HashMap en paramètre
    public Stats(Map<Stat, Integer> initialStats) {
        
        this.statsMap = new HashMap<>();

        // Remplir la statsMap avec les valeurs initiales, mettant 0 pour les clés manquantes
        for (Stat stat : Stat.values()) {
            if (initialStats.containsKey(stat)) {
                this.statsMap.put(stat, initialStats.get(stat));
            } else {
                this.statsMap.put(stat, 0);
            }
        }
    }

    // Méthode pour obtenir la valeur d'une stat spécifique
    public int get(Stat stat) {
        Integer value = statsMap.get(stat);
        if (value == null) {
            throw new IllegalArgumentException("La stat " + stat + " n'est pas présente dans la HashMap.");
        }
        return value;
    }

    // Méthode pour obtenir la HashMap complète
    public Map<Stat, Integer> getAll() {
        return statsMap;
    }

    // Autres méthodes selon vos besoins...

    @Override
    public String toString() {
        return "Stats" + statsMap;
    }
}
package com.wakfu.item;

public enum ItemRarity {
    COMMUNE,
    INHABITUEL,
    RARE,
    MYTHIQUE,
    LEGENDAIRE,
    RELIQUE,
    SOUVENIR,
    EPIQUE;

    // Fonction pour obtenir la ItemRarity correspondante à l'entier donné
    public static ItemRarity getRarityByValue(int value) {
        switch (value) {
            case 0:
                return COMMUNE;
            case 1:
                return INHABITUEL;
            case 2:
                return RARE;
            case 3:
                return MYTHIQUE;
            case 4:
                return LEGENDAIRE;
            case 5:
                return RELIQUE;
            case 6:
                return SOUVENIR;
            case 7:
                return EPIQUE;
            default:
                throw new IllegalArgumentException("La valeur doit être entre 0 et 7");
        }
    }
}

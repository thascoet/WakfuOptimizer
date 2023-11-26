package com.wakfu.item;

import com.wakfu.equipment.EquipmentType;
import com.wakfu.stats.Stats;

public class Item {
    private int id;
    private String name;
    private int level;
    private ItemRarity rarity;
    private EquipmentType type;
    private int setId;
    private Stats stats;

    // Constructeur prenant un tableau JSON en paramètre pour les stats
    public Item(int id, String name, int level, ItemRarity rarity, EquipmentType type, int setId, Stats stats) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.rarity = rarity;
        this.type = type;
        this.setId = setId;
        this.stats = stats;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public ItemRarity getRarity() {
        return rarity;
    }

    public EquipmentType getType() {
        return type;
    }

    public int getSetId() {
        return setId;
    }

    public Stats getStats() {
        return stats;
    }

    // Méthode toString pour afficher les informations de l'objet Item
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", rarity=" + rarity +
                ", type=" + type +
                ", setId=" + setId +
                ", stats=" + stats +
                '}';
    }
}

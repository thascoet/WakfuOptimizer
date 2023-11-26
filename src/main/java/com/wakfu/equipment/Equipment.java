package com.wakfu.equipment;

public class Equipment {
    private int id;
    private String name;
    private EquipmentType type;

    // Constructeur
    public Equipment(int id, String name, EquipmentType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public EquipmentType getType() {
        return type;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(EquipmentType type) {
        this.type = type;
    }

    // ToString
    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}

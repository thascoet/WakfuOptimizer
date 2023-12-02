package com.wakfu.assortment;

import java.util.Arrays;
import java.util.List;

import com.wakfu.equipment.EquipmentType;
import com.wakfu.item.Item;
import com.wakfu.stats.Stats;

public class Assortment {

    private final static EquipmentType[] EQUIPMENT_DEFINE_ORDER_1 = {
            EquipmentType.CASQUE,
            EquipmentType.AMULETTE,
            EquipmentType.PLASTRON,
            EquipmentType.ANNEAU,
            EquipmentType.ANNEAU,
            EquipmentType.BOTTES,
            EquipmentType.CAPE,
            EquipmentType.EPAULETTES,
            EquipmentType.CEINTURE,
            EquipmentType.ARME_A_DEUX_MAINS
    };

    private final static EquipmentType[] EQUIPMENT_DEFINE_ORDER_2 = {
            EquipmentType.CASQUE,
            EquipmentType.AMULETTE,
            EquipmentType.PLASTRON,
            EquipmentType.ANNEAU,
            EquipmentType.ANNEAU,
            EquipmentType.BOTTES,
            EquipmentType.CAPE,
            EquipmentType.EPAULETTES,
            EquipmentType.CEINTURE,
            EquipmentType.ARME_PRINCIPALE,
            EquipmentType.ARME_SECONDAIRE
    };

    private Item casque;
    private Item amulette;
    private Item plastron;
    private Item anneau1;
    private Item anneau2;
    private Item bottes;
    private Item cape;
    private Item epaulettes;
    private Item ceinture;
    private Item armePrincipale;
    private Item armeSecondaire;

    private Stats stats;

    public Assortment(List<Item> items) {

        validateItemsList(items);

        this.casque = items.get(0);
        this.amulette = items.get(1);
        this.plastron = items.get(2);
        this.anneau1 = items.get(3);
        this.anneau2 = items.get(4);
        this.bottes = items.get(5);
        this.cape = items.get(6);
        this.epaulettes = items.get(7);
        this.ceinture = items.get(8);
        this.armePrincipale = items.get(9);
        this.armeSecondaire = items.size() == 11 ? items.get(10) : null;

        this.stats = Stats.combineStats(items.stream().map((item) -> item.getStats()).toList());
    }

    private void validateItemsList(List<Item> equipmentsList) {

        // Vérifier si la liste respecte le schéma 1 ou le schéma 2
        boolean isValidOrder1 = validateEquipmentsTypeOrder(equipmentsList, EQUIPMENT_DEFINE_ORDER_1);
        boolean isValidOrder2 = validateEquipmentsTypeOrder(equipmentsList, EQUIPMENT_DEFINE_ORDER_2);

        if (!isValidOrder1 && !isValidOrder2) {
            throw new IllegalArgumentException("La liste d'équipements ne respecte aucun des schémas spécifiés.");
        }
    }

    private boolean validateEquipmentsTypeOrder(List<Item> equipmentList, EquipmentType[] order) {

        if (equipmentList.size() != order.length)
            return false;

        for (int i = 0; i < order.length; i++)
            if (order[i] != equipmentList.get(i).getType())
                return false;

        return true;
    }

    // Getter pour récupérer une liste d'équipements
    public List<Item> getEquipmentsList() {
        return Arrays.asList(
                casque, amulette, plastron, anneau1, anneau2,
                bottes, cape, epaulettes, ceinture,
                armePrincipale, armeSecondaire);
    }

    public Item getCasque() {
        return casque;
    }

    public Item getAmulette() {
        return amulette;
    }

    public Item getPlastron() {
        return plastron;
    }

    public Item getAnneau1() {
        return anneau1;
    }

    public Item getAnneau2() {
        return anneau2;
    }

    public Item getBottes() {
        return bottes;
    }

    public Item getCape() {
        return cape;
    }

    public Item getEpaulettes() {
        return epaulettes;
    }

    public Item getCeinture() {
        return ceinture;
    }

    public Item getArmePrincipale() {
        return armePrincipale;
    }

    public Item getArmeSecondaire() {
        return armeSecondaire;
    }

    public Stats getStats() {
        return stats;
    }
}

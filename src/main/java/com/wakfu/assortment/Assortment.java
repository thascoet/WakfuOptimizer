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

    public Assortment(Item[] items) {

        validateItemsList(items);

        this.casque = items[0];
        this.amulette = items[1];
        this.plastron = items[2];
        this.anneau1 = items[3];
        this.anneau2 = items[4];
        this.bottes = items[5];
        this.cape = items[6];
        this.epaulettes = items[7];
        this.ceinture = items[8];
        this.armePrincipale = items[9];
        this.armeSecondaire = items.length == 11 ? items[10] : null;
    }

    private void validateItemsList(Item[] equipmentsList) {

        // Vérifier si la liste respecte le schéma 1 ou le schéma 2
        if (validateEquipmentsTypeOrder(equipmentsList, EQUIPMENT_DEFINE_ORDER_1))
            return;
        if (validateEquipmentsTypeOrder(equipmentsList, EQUIPMENT_DEFINE_ORDER_2))
            return;

        throw new IllegalArgumentException("La liste d'équipements ne respecte aucun des schémas spécifiés.");

    }

    private boolean validateEquipmentsTypeOrder(Item[] equipmentList, EquipmentType[] order) {

        if (equipmentList.length != order.length)
            return false;

        for (int i = 0; i < order.length; i++)
            if (order[i] != equipmentList[i].getType())
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
}

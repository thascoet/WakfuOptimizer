package com.wakfu.assortment;

import java.util.Arrays;
import java.util.List;

import com.wakfu.equipment.Equipment;
import com.wakfu.equipment.EquipmentType;
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

    private Equipment casque;
    private Equipment amulette;
    private Equipment plastron;
    private Equipment anneau1;
    private Equipment anneau2;
    private Equipment bottes;
    private Equipment cape;
    private Equipment epaulettes;
    private Equipment ceinture;
    private Equipment armePrincipale;
    private Equipment armeSecondaire;

    private Stats stats;

    public Assortment(List<Equipment> equipments) {

        validateEquipmentsList(equipments);

        
    }

    private void validateEquipmentsList(List<Equipment> equipmentsList) {

        // Vérifier si la liste respecte le schéma 1 ou le schéma 2
        boolean isValidOrder1 = validateEquipmentsOrder(equipmentsList, EQUIPMENT_DEFINE_ORDER_1);
        boolean isValidOrder2 = validateEquipmentsOrder(equipmentsList, EQUIPMENT_DEFINE_ORDER_2);

        if (!isValidOrder1 && !isValidOrder2) {
            throw new IllegalArgumentException("La liste d'équipements ne respecte aucun des schémas spécifiés.");
        }
    }

    private boolean validateEquipmentsOrder(List<Equipment> equipmentList, EquipmentType[] order) {

        if (equipmentList.size() != order.length)
            return false;

        for (int i = 0; i < order.length; i++)
            if (order[i] != equipmentList.get(i).getType())
                return false;

        return true;
    }

    // Getter pour récupérer une liste d'équipements
    public List<Equipment> getEquipmentsList() {
        return Arrays.asList(
                casque, amulette, plastron, anneau1, anneau2,
                bottes, cape, epaulettes, ceinture,
                armePrincipale, armeSecondaire);
    }
}

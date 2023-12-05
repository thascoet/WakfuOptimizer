package com.wakfu.assortment;

import java.util.Arrays;
import java.util.Map;

import com.wakfu.equipment.EquipmentType;
import com.wakfu.function.Function;
import com.wakfu.item.Item;
import com.wakfu.item.ItemRarity;
import com.wakfu.setup.Setup;
import com.wakfu.stats.Stat;
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

    private static Map<Stat, Integer> conditionStats;
    private static Map<ItemRarity, Integer> conditionMaxParRarete;
    private static Map<Stat, Function> poids;

    private Item[] items;

    private int[] itemsRarity;

    private Stats stats;

    private boolean oneWeaponAssortment;

    public static void init() {

        conditionStats = Setup.getInstance().getConditionStats();

        conditionMaxParRarete = Setup.getInstance().getConditionMaxParRarete();

        poids = Setup.getInstance().getPoids();
    }

    public Assortment(Item[] items) {

        this.oneWeaponAssortment = validateItemsList(items);

        this.items = items;
    }

    public void build() {

        int[] statsArray = new int[Stat.values().length];
        Arrays.fill(statsArray, 0);

        int[] itemsRarity = new int[ItemRarity.values().length];
        Arrays.fill(itemsRarity, 0);

        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < Stat.values().length; j++) {
                statsArray[j] += items[i].getStats().getAll()[j];
            }
            itemsRarity[items[i].getRarity().ordinal()]++;
        }

        this.stats = new Stats(statsArray);
        this.itemsRarity = itemsRarity;
    }

    public boolean matchConditions() {

        int i = 0;
        
        for (ItemRarity itemRarity: ItemRarity.values()) {
            if (conditionMaxParRarete.get(itemRarity) >= 0
                    && itemsRarity[i] >= conditionMaxParRarete.get(itemRarity))
                return false;
            i++;
        }
    
        i=0;

        for (Stat stat : Stat.values()) {
            if (stats.getAll()[i] < conditionStats.get(stat))
                return false;
            i++;
        }

        return true;
    }

    public double getPoid() {

        double poid = 0;

        for (int i = 0; i < Stat.values().length; i++) {
            poid += this.stats.getAll()[i] * poids.get(Stat.values()[i]).run(this.stats);
        }

        return poid;
    }

    private boolean validateItemsList(Item[] equipmentsList) {

        // Vérifier si la liste respecte le schéma 1 ou le schéma 2
        if (validateEquipmentsTypeOrder(equipmentsList, EQUIPMENT_DEFINE_ORDER_1))
            return true;
        if (validateEquipmentsTypeOrder(equipmentsList, EQUIPMENT_DEFINE_ORDER_2))
            return false;

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

    public boolean isOneWeaponAssortment() {
        return oneWeaponAssortment;
    }
}

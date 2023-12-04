package com.wakfu.assortment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wakfu.equipment.EquipmentType;
import com.wakfu.item.Item;

public class AssortmentBuilder implements Iterator<Assortment> {

    private final static EquipmentType[] EQUIPMENT_ORDER = {
            EquipmentType.CASQUE,
            EquipmentType.AMULETTE,
            EquipmentType.PLASTRON,
            EquipmentType.ANNEAU,
            EquipmentType.BOTTES,
            EquipmentType.CAPE,
            EquipmentType.EPAULETTES,
            EquipmentType.CEINTURE,
            EquipmentType.ARME_PRINCIPALE
    };

    private final static int EQUIPMENT_ORDER_LENGTH = EQUIPMENT_ORDER.length;

    private final Map<EquipmentType, List<Item>> groupByItemsMap;

    private int[] sizes;
    private int[] counts;
    private boolean hasNext;

    private final int armesADeuxMainsNumber;
    private final int armesPrincipalesNumber;
    private final int anneauxNumber;
    private int[] anneauFirst;
    private int[] anneauSecond;

    public AssortmentBuilder(Map<EquipmentType, List<Item>> groupByItemsMap) {

        this.groupByItemsMap = groupByItemsMap;

        this.sizes = new int[EQUIPMENT_ORDER_LENGTH];
        this.counts = new int[EQUIPMENT_ORDER_LENGTH];

        for (int i = 0; i < EQUIPMENT_ORDER_LENGTH; i++) {

            EquipmentType equipmentType = EQUIPMENT_ORDER[i];

            int size = groupByItemsMap.get(equipmentType).size();

            if (equipmentType == EquipmentType.ANNEAU)
                size = size * (size - 1) / 2;

            else if (equipmentType == EquipmentType.ARME_PRINCIPALE)
                size = size * groupByItemsMap.get(EquipmentType.ARME_SECONDAIRE).size()
                        + groupByItemsMap.get(EquipmentType.ARME_A_DEUX_MAINS).size();

            this.sizes[i] = size;
            this.counts[i] = 0;
            this.hasNext = true;
        }

        this.anneauxNumber = groupByItemsMap.get(EquipmentType.ANNEAU).size();
        this.armesADeuxMainsNumber = groupByItemsMap.get(EquipmentType.ARME_A_DEUX_MAINS).size();
        this.armesPrincipalesNumber = groupByItemsMap.get(EquipmentType.ARME_PRINCIPALE).size();

        this.anneauFirst = new int[this.sizes[3]];
        this.anneauSecond = new int[this.sizes[3]];

        int cpt = 0;

        for (int i = 0; i < this.anneauxNumber - 1; i++)
            for (int j = i + 1; j < this.anneauxNumber; j++) {
                this.anneauFirst[cpt] = i;
                this.anneauSecond[cpt] = j;
                cpt++;
            }
    }

    private void incrementCount() {

        int i = 0;

        do {
            this.counts[i]++;
            if (this.counts[i] < this.sizes[i])
                return;
            this.counts[i] = 0;
            i++;
        } while (EQUIPMENT_ORDER_LENGTH - i != 0);

        this.hasNext = false;
    }

    private Assortment getAssortment() {

        if (this.counts[EQUIPMENT_ORDER_LENGTH - 1] < this.armesADeuxMainsNumber) {

            Item[] items = {
                    groupByItemsMap.get(EquipmentType.CASQUE).get(this.counts[0]),
                    groupByItemsMap.get(EquipmentType.AMULETTE).get(this.counts[1]),
                    groupByItemsMap.get(EquipmentType.PLASTRON).get(this.counts[2]),
                    groupByItemsMap.get(EquipmentType.ANNEAU).get(this.anneauFirst[this.counts[3]]),
                    groupByItemsMap.get(EquipmentType.ANNEAU).get(this.anneauSecond[this.counts[3]]),
                    groupByItemsMap.get(EquipmentType.BOTTES).get(this.counts[4]),
                    groupByItemsMap.get(EquipmentType.CAPE).get(this.counts[5]),
                    groupByItemsMap.get(EquipmentType.EPAULETTES).get(this.counts[6]),
                    groupByItemsMap.get(EquipmentType.CEINTURE).get(this.counts[7]),
                    groupByItemsMap.get(EquipmentType.ARME_A_DEUX_MAINS).get(this.counts[8])
            };

            return new Assortment(items);

        } else {

            int currentIndex = this.counts[EQUIPMENT_ORDER_LENGTH-1] - this.armesADeuxMainsNumber;
            int armePrincipaleIndex = currentIndex / this.armesPrincipalesNumber;
            int armeSecondaireIndex = currentIndex % this.armesPrincipalesNumber;

            Item[] items = {
                    groupByItemsMap.get(EquipmentType.CASQUE).get(this.counts[0]),
                    groupByItemsMap.get(EquipmentType.AMULETTE).get(this.counts[1]),
                    groupByItemsMap.get(EquipmentType.PLASTRON).get(this.counts[2]),
                    groupByItemsMap.get(EquipmentType.ANNEAU).get(this.anneauFirst[this.counts[3]]),
                    groupByItemsMap.get(EquipmentType.ANNEAU).get(this.anneauSecond[this.counts[3]]),
                    groupByItemsMap.get(EquipmentType.BOTTES).get(this.counts[4]),
                    groupByItemsMap.get(EquipmentType.CAPE).get(this.counts[5]),
                    groupByItemsMap.get(EquipmentType.EPAULETTES).get(this.counts[6]),
                    groupByItemsMap.get(EquipmentType.CEINTURE).get(this.counts[7]),
                    groupByItemsMap.get(EquipmentType.ARME_PRINCIPALE).get(armePrincipaleIndex),
                    groupByItemsMap.get(EquipmentType.ARME_SECONDAIRE).get(armeSecondaireIndex)
            };

            return new Assortment(items);
        }
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public Assortment next() {

        Assortment assortment = getAssortment();
        incrementCount();
        return assortment;
    }
}

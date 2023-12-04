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

    private long[] sizes;
    private long[] count;
    private boolean hasNext;

    public AssortmentBuilder(Map<EquipmentType, List<Item>> groupByItemsMap) {

        this.sizes = new long[EQUIPMENT_ORDER.length];
        this.count = new long[EQUIPMENT_ORDER.length];

        for (int i = 0; i < EQUIPMENT_ORDER.length; i++) {

            EquipmentType equipmentType = EQUIPMENT_ORDER[i];

            long size = (long) groupByItemsMap.get(equipmentType).size();

            if (equipmentType == EquipmentType.ANNEAU)
                size = size * (size - 1) / 2;

            else if (equipmentType == EquipmentType.ARME_PRINCIPALE)
                size = size * (long) groupByItemsMap.get(EquipmentType.ARME_SECONDAIRE).size()
                        + (long) groupByItemsMap.get(EquipmentType.ARME_A_DEUX_MAINS).size();

            this.sizes[i] = size;
            this.count[i] = 0;
            this.hasNext = true;
        }

        for (int i=0; i<EQUIPMENT_ORDER.length; i++) {
            System.out.println(this.sizes[i]);
            System.out.println(this.count[i]);
        }
    }

    private void incrementCount() {

        int i = 0;

        do {
            this.count[i]++;
            if (this.count[i] < this.sizes[i]) return;
            this.count[i] = 0;
            i++;
        } while (EQUIPMENT_ORDER.length-i!=0);

        this.hasNext = false;
    }

    @Override
    public String toString() {
        return "AssortmentBuilder [sizes=" + sizes + "]";
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public Assortment next() {

        incrementCount();
        return null;
    }
}

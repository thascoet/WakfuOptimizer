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

    private Map<EquipmentType, List<Item>> groupByItemsMap;

    private Map<EquipmentType, Long> sizes;
    private Map<EquipmentType, Long> accumulatedSizes;

    private long count;
    private long maxValue;

    public AssortmentBuilder(Map<EquipmentType, List<Item>> groupByItemsMap) {

        this.groupByItemsMap = groupByItemsMap;

        this.sizes = new HashMap<>();

        for (int i = 0; i < EquipmentType.values().length; i++) {

            EquipmentType equipmentType = EquipmentType.values()[i];

            this.sizes.put(equipmentType, (long) groupByItemsMap.get(equipmentType).size());
        }

        this.accumulatedSizes = new HashMap<>();

        long acc = 1;

        for (int i = 0; i < EQUIPMENT_ORDER.length; i++) {

            EquipmentType equipmentType = EQUIPMENT_ORDER[i];

            long size = this.sizes.get(equipmentType);

            if (equipmentType == EquipmentType.ANNEAU)
                size = size * (size - 1) / 2;

            if (equipmentType == EquipmentType.ARME_PRINCIPALE)
                size = size * this.sizes.get(EquipmentType.ARME_SECONDAIRE)
                        + this.sizes.get(EquipmentType.ARME_A_DEUX_MAINS);

            acc *= size;

            this.sizes.put(equipmentType, size);
            this.accumulatedSizes.put(equipmentType, acc);
        }

        this.count = 0;
        this.maxValue = this.accumulatedSizes.get(EquipmentType.ARME_PRINCIPALE);
    }

    @Override
    public String toString() {
        return "AssortmentBuilder [sizes=" + sizes + ", accumulatedSizes="
                + accumulatedSizes + "]";
    }

    @Override
    public boolean hasNext() {

        return count < maxValue;
    }

    @Override
    public Assortment next() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'next'");
    }

}

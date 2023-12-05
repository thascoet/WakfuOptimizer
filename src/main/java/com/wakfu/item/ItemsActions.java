package com.wakfu.item;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wakfu.equipment.EquipmentType;
import com.wakfu.setup.Setup;
import com.wakfu.stats.Stat;
import com.wakfu.stats.Stats;

public class ItemsActions {

    public static List<Item> filter(Map<Integer, Item> itemsList) {

        List<Item> filteredItemsList = new ArrayList<>();

        int levelMin = Setup.getInstance().getContrainteNiveauMin();
        int levelMax = Setup.getInstance().getContrainteNiveauMax();
        Set<ItemRarity> raritiesAllowed = Setup.getInstance().getContrainteRaretesAutorisees();
        Set<Stat> statsForbiden = Setup.getInstance().getContrainteStatsInterdites();

        itemsList.forEach((id, item) -> {

            if ((item.getLevel() >= levelMin) &&
                    (item.getLevel() <= levelMax) &&
                    (raritiesAllowed.contains(item.getRarity())) &&
                    (hasNoStatsInterdites(item.getStats(), statsForbiden)))
                filteredItemsList.add(item);
        });

        return filteredItemsList;
    }

    private static boolean hasNoStatsInterdites(Stats stats, Set<Stat> statsInterdites) {

        return statsInterdites.stream().allMatch((stat) -> stats.get(stat) <= 0);
    }

    public static Map<EquipmentType, List<Item>> groupByEquipmentType(List<Item> itemsList, Map<Integer, Item> itemsMap) throws Exception {

        Map<EquipmentType, List<Item>> groupByEquipmentTypeItemsMap = new EnumMap<>(EquipmentType.class);

        for (int i = 0; i < EquipmentType.values().length; i++) {

            EquipmentType equipmentType = EquipmentType.values()[i];

            List<Integer> predifinedEquipmentList = Setup.getInstance().getContrainteEquipementsPredefinis()
                    .get(equipmentType);

            if (predifinedEquipmentList.size() <= 0) {

                groupByEquipmentTypeItemsMap.put(equipmentType,
                        itemsList.stream().filter((item) -> item.getType() == equipmentType).toList());
            } else {

                groupByEquipmentTypeItemsMap.put(equipmentType, new ArrayList<>());

                for (int j = 0; j < predifinedEquipmentList.size(); j++) {

                    int predifinedEquipmentId = predifinedEquipmentList.get(j);

                    if (!itemsMap.containsKey(predifinedEquipmentId))
                        throw new Exception(
                                "L'item d'id " + predifinedEquipmentId + " n'existe pas");

                    Item predifinedEquipmentItem = itemsMap.get(predifinedEquipmentId);

                    if (predifinedEquipmentItem.getType() != equipmentType)
                        throw new Exception(
                                "L'item d'id " + predifinedEquipmentId + " n'est pas de type " + equipmentType);

                    groupByEquipmentTypeItemsMap.get(equipmentType).add(predifinedEquipmentItem);
                }
            }
        }

        return groupByEquipmentTypeItemsMap;
    }
}

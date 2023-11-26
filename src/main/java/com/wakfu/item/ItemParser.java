package com.wakfu.item;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.wakfu.equipment.Equipment;
import com.wakfu.stats.Stat;
import com.wakfu.stats.Stats;
import com.wakfu.stats.StatsParser;

public class ItemParser {

    // Méthode statique pour créer une liste d'items à partir d'un JSONArray
    public static List<Item> fromJson(String itemsJsonString, Map<Integer, Equipment> equipmentMap) {

        List<Item> itemList = new ArrayList<>();

        JSONArray itemsArray = new JSONArray(itemsJsonString);

        for (int i = 0; i < itemsArray.length(); i++) {

            JSONObject itemObject = itemsArray.getJSONObject(i);

            try {

                // Extraire les propriétés nécessaires
                JSONObject definition = itemObject.getJSONObject("definition");
                JSONObject title = itemObject.getJSONObject("title");

                int id = definition.getJSONObject("item").getInt("id");
                String name = title.getString("fr");
                int level = definition.getJSONObject("item").getInt("level");
                int rarityValue = definition.getJSONObject("item").getJSONObject("baseParameters").getInt("rarity");

                // Si le niveau est inférieur ou égal à 0, on ne crée pas l'item
                if (level <= 0)
                    continue;

                // Conversion de la valeur de rareté en enum ItemRarity
                ItemRarity itemRarity = ItemRarity.getRarityByValue(rarityValue);

                // Récupération de l'équipement associé à l'itemTypeId
                int itemTypeId = definition.getJSONObject("item").getJSONObject("baseParameters")
                        .getInt("itemTypeId");
                Equipment itemEquipment = equipmentMap.get(itemTypeId);

                // Si aucun équipement associé à l'itemTypeId, on ne crée pas l'item
                if (itemEquipment == null)
                    continue;

                // Récupération des statistiques à partir du JSONArray equipEffects
                JSONArray equipEffectsArray = definition.getJSONArray("equipEffects");
                Stats itemStats = StatsParser.fromJson(equipEffectsArray);

                // Création de l'objet Item
                Item item = new Item(id, name, level, itemRarity, itemEquipment.getType(),
                        itemEquipment.getId(), itemStats);

                // Ajout de l'item à la liste
                itemList.add(item);

            } catch (Exception e) {

                System.out.println(itemObject);
                System.err.println(e);
            }

        }

        return itemList;
    }


    public static void toCsv(String filePath, String fileName, List<Item> itemList) {
        String csvFile = filePath + "/" + fileName;

        try (FileWriter writer = new FileWriter(csvFile)) {
            // Ajouter la première ligne avec les noms de colonnes et le séparateur
            writer.append("sep=;");
            writer.append(System.lineSeparator());

            writer.append("ID;NAME;LEVEL;RARITY;TYPE;SETID;PV;PA;PM;PW;RESISTANCE_ELEM;RESISTANCE_ELEM_1;RESISTANCE_ELEM_2;RESISTANCE_ELEM_3;RESISTANCE_EAU;RESISTANCE_AIR;RESISTANCE_SOL;RESISTANCE_FEU;MAITRISE_ELEM;MAITRISE_ELEM_1;MAITRISE_ELEM_2;MAITRISE_ELEM_3;MAITRISE_EAU;MAITRISE_AIR;MAITRISE_SOL;MAITRISE_FEU;TCC;PARADE;INITIATIVE;PO;ESQUIVE;TACLE;CONTROLE;MAITRISE_CRITIQUE;RESISTANCE_CRITIQUE;MAITRISE_DOS;RESISTANCE_DOS;MAITRISE_MELEE;MAITRISE_DISTANCE;MAITRISE_SOIN;MAITRISE_BERSERK");
            writer.append(System.lineSeparator());

            // Ajouter les données des items
            for (Item item : itemList) {
                writer.append(item.getId() + ";");
                writer.append(item.getName() + ";");
                writer.append(item.getLevel() + ";");
                writer.append(item.getRarity().name() + ";");
                writer.append(item.getType().name() + ";");
                writer.append(item.getSetId() + ";");

                // Ajouter les valeurs de la HashMap des Stats
                for (Stat stat : Stat.values()) {
                    Integer value = item.getStats().get(stat);
                    writer.append(value != null ? value.toString() : "0");

                    // Ajouter un point-virgule sauf pour la dernière colonne
                    if (stat != Stat.MAITRISE_BERSERK) {
                        writer.append(";");
                    }
                }

                writer.append(System.lineSeparator());
            }

            System.out.println("CSV file created successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

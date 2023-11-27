package com.wakfu.item;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import com.wakfu.equipment.Equipment;
import com.wakfu.equipment.EquipmentType;
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


    public static void toCsv(String filePath, List<Item> items) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Écrire la première ligne indiquant le séparateur utilisé
            writer.write("sep=;\n");

            // Écrire la deuxième ligne avec le header
            StringBuilder header = new StringBuilder("ID;NAME;LEVEL;RARITY;TYPE;SETID");
            for (Stat stat : Stat.values()) {
                header.append(";").append(stat.name());
            }
            writer.write(header.toString() + "\n");

            // Écrire les lignes de données
            for (Item item : items) {
                StringBuilder line = new StringBuilder();
                line.append(item.getId()).append(";")
                    .append(item.getName()).append(";")
                    .append(item.getLevel()).append(";")
                    .append(item.getRarity().name()).append(";")
                    .append(item.getType().name()).append(";")
                    .append(item.getSetId());

                // Ajouter les valeurs des statistiques
                for (Stat stat : Stat.values()) {
                    line.append(";").append(Objects.requireNonNullElse(item.getStats().get(stat), 0));
                }

                writer.write(line.toString() + "\n");
            }

            System.out.println("Écriture du fichier CSV réussie : " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<Item> fromCsv(String filePath) {
        List<Item> items = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Lire la première ligne (sep=;) pour ignorer le séparateur
            reader.readLine();

            // Lire la deuxième ligne (header) pour obtenir les noms des statistiques
            String headerLine = reader.readLine();
            String[] headerValues = headerLine.split(";");

            // Lire chaque ligne du fichier CSV
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");

                // Créer un nouvel objet Item
                int id = Integer.parseInt(values[0]);
                String name = values[1];
                int level = Integer.parseInt(values[2]);
                ItemRarity rarity = ItemRarity.valueOf(values[3]);
                EquipmentType type = EquipmentType.valueOf(values[4]);
                int setId = Integer.parseInt(values[5]);

                // Remplir les statistiques de l'objet Item à partir du header
                HashMap<Stat, Integer> statsHashMap = new HashMap<>();

                for (int i = 6; i < values.length; i++) {

                    Stat stat = Stat.valueOf(headerValues[i]);
                    statsHashMap.put(stat, Integer.parseInt(values[i]));
                }

                Stats stats = new Stats(statsHashMap);

                // Ajouter l'objet Item à la liste
                items.add(new Item(id, name, level, rarity, type, setId, stats));
            }

            System.out.println("Lecture du fichier CSV réussie : " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

}

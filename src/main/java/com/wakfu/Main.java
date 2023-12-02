package com.wakfu;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wakfu.equipment.Equipment;
import com.wakfu.equipment.EquipmentParser;
import com.wakfu.function.Analyser;
import com.wakfu.function.Function;
import com.wakfu.item.Item;
import com.wakfu.item.ItemParser;
import com.wakfu.setup.Setup;
import com.wakfu.stats.Stat;
import com.wakfu.stats.Stats;

public class Main {

    public static void main(String[] args) throws Exception {
        
        String filePath = "setup.json";

        if (!Files.exists(Paths.get(filePath))) {
            throw new IOException("Le fichier setup.json est absent du répertoire courant.");
        }

        byte[] encoded = Files.readAllBytes(Paths.get(filePath));

        String setupJsonString = new String(encoded, StandardCharsets.UTF_8);

        Setup.init(setupJsonString);

        String version = Setup.getInstance().getVersion();

        if (version.equals("last")) version = Connect.getVersion();

        String dataFilePath = Setup.getInstance().getPath();
        String dataFilePrefix = Setup.getInstance().getPrefix();

        String dataFile = dataFilePath + dataFilePrefix + version.replace(".", "_") + ".csv";


        Function function = Analyser.analyse("1-0.5^(1+PO)");

        HashMap<Stat, Integer> variables = new HashMap<>();

        variables.put(Stat.PO, 2);

        Stats stats = new Stats(variables);

        System.out.println(function.run(stats));

        if (!Files.exists(Paths.get(dataFile))) {
            
            
        }
    }

    public static void getItems() throws Exception {

        // URL de la première requête
        String version = Connect.getVersion();

        // Construire l'URL pour la deuxième requête
        String equipmentTypesUrl = "https://wakfu.cdn.ankama.com/gamedata/" + version + "/equipmentItemTypes.json";

        // Effectuer la deuxième requête HTTP
        String typesJsonString = Connect.sendHttpRequest(equipmentTypesUrl);

        // Utiliser la liste d'équipements
        Map<Integer, Equipment> equipmentMap = EquipmentParser.createEquipmentsMap(typesJsonString);

        // Construire l'URL pour la troisième requête
        String itemsUrl = "https://wakfu.cdn.ankama.com/gamedata/" + version + "/items.json";

        // Effectuer la troisième requête HTTP
        String itemsJsonString = Connect.sendHttpRequest(itemsUrl);

        // Utiliser la liste d'items
        List<Item> itemList = ItemParser.fromJson(itemsJsonString, equipmentMap);

        // Enregistrer les items au format CSV
        String csvFilePath = "C:/Users/thasc/Documents/Wakfu_Opti/data/";
        String csvFileName = "data_items_wakfu_" + version.replace(".", "_") + ".csv";
        ItemParser.toCsv(csvFilePath + csvFileName, itemList);
    }

    public static void listFilesInCurrentDirectory() {
        // Obtenir le chemin du répertoire courant
        String currentDirectory = System.getProperty("user.dir");

        // Créer un objet File représentant le répertoire courant
        File directory = new File(currentDirectory);

        // Obtenir la liste des fichiers dans le répertoire courant
        File[] files = directory.listFiles();

        if (files != null) {
            System.out.println("Liste des fichiers dans le répertoire courant:");

            // Parcourir et afficher les noms des fichiers
            for (File file : files) {
                System.out.println(file.getName());
            }
        } else {
            System.out.println("Erreur lors de la récupération de la liste des fichiers.");
        }
    }
}
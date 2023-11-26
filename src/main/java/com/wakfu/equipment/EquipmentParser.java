package com.wakfu.equipment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class EquipmentParser {

    public static Map<Integer, Equipment> createEquipmentsMap(String typesJsonString) {
        Map<Integer, Equipment> equipmentMap = new HashMap<>();

        try {
            // Parser le JSON des types d'items
            JSONArray typesArray = new JSONArray(typesJsonString);

            // Parcourir le tableau des types d'items
            for (int i = 0; i < typesArray.length(); i++) {
                JSONObject typeObject = typesArray.getJSONObject(i);
                JSONObject definitionObject = typeObject.getJSONObject("definition");
                JSONObject titleObject = typeObject.getJSONObject("title");

                // Récupérer les données
                int typeId = definitionObject.getInt("id");
                String frTitle = titleObject.getString("fr");
                JSONArray equipmentPositions = definitionObject.getJSONArray("equipmentPositions");
                JSONArray equipmentDisabledPositions = definitionObject.getJSONArray("equipmentDisabledPositions");

                // Vérifier les règles et créer l'instance d'Equipment si nécessaire
                Equipment equipment = createEquipment(typeId, frTitle, equipmentPositions, equipmentDisabledPositions);
                if (equipment != null) {
                    // Ajouter l'instance à la Map avec l'id comme clé
                    equipmentMap.put(typeId, equipment);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retourner la Map d'Equipment
        return equipmentMap;
    }

    private static Equipment createEquipment(int id, String name, JSONArray equipmentPositions,
            JSONArray equipmentDisabledPositions) {
        EquipmentType type = null;

        // Convertir JSONArray en liste d'objets
        List<Object> positionsList = equipmentPositions.toList();
        List<Object> disabledPositionsList = equipmentDisabledPositions.toList();

        // Vérifier les règles pour déterminer le type
        if (positionsList.contains("HEAD")) {
            type = EquipmentType.CASQUE;
        } else if (positionsList.contains("NECK")) {
            type = EquipmentType.AMULETTE;
        } else if (positionsList.contains("CHEST")) {
            type = EquipmentType.PLASTRON;
        } else if (positionsList.contains("LEFT_HAND") || positionsList.contains("RIGHT_HAND")) {
            type = EquipmentType.ANNEAU;
        } else if (positionsList.contains("LEGS")) {
            type = EquipmentType.BOTTES;
        } else if (positionsList.contains("BACK")) {
            type = EquipmentType.CAPE;
        } else if (positionsList.contains("SHOULDERS")) {
            type = EquipmentType.EPAULETTES;
        } else if (positionsList.contains("BELT")) {
            type = EquipmentType.CEINTURE;
        } else if (positionsList.contains("FIRST_WEAPON") && disabledPositionsList.isEmpty()) {
            type = EquipmentType.ARME_PRINCIPALE;
        } else if (positionsList.contains("FIRST_WEAPON") && !disabledPositionsList.isEmpty()) {
            type = EquipmentType.ARME_A_DEUX_MAINS;
        } else if (positionsList.contains("SECOND_WEAPON")) {
            type = EquipmentType.ARME_SECONDAIRE;
        }

        // Si le type est null, cela signifie que l'instance ne doit pas être créée
        if (type == null) {
            return null;
        }

        // Créer et retourner l'instance d'Equipment
        return new Equipment(id, name, type);
    }
}

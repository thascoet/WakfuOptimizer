package com.wakfu.setup;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.wakfu.equipment.EquipmentType;
import com.wakfu.item.ItemRarity;
import com.wakfu.stats.Stat;

public class SetupVerifier {

    public static void verify(JSONObject setupJson) throws Exception {

        // Vérifier la présence et le type de CONFIG
        if (!setupJson.has("CONFIG") || !(setupJson.get("CONFIG") instanceof JSONObject)) {
            throw new Exception(
                    "Le champ CONFIG est absent ou n'est pas de type JsonObject dans le fichier setup.Json");
        }

        // Vérifier la présence et le type de CONTRAINTES
        if (!setupJson.has("CONTRAINTES") || !(setupJson.get("CONTRAINTES") instanceof JSONObject)) {
            throw new Exception(
                    "Le champ CONTRAINTES est absent ou n'est pas de type JsonObject dans le fichier setup.Json");
        }

        // Vérifier la présence et le type de CONDITIONS
        if (!setupJson.has("CONDITIONS") || !(setupJson.get("CONDITIONS") instanceof JSONObject)) {
            throw new Exception(
                    "Le champ CONDITIONS est absent ou n'est pas de type JsonObject dans le fichier setup.Json");
        }

        // Vérifier la présence et le type de POIDS
        if (!setupJson.has("POIDS") || !(setupJson.get("POIDS") instanceof JSONObject)) {
            throw new Exception(
                    "Le champ POIDS est absent ou n'est pas de type JsonObject dans le fichier setup.Json");
        }

        // Vérifier les types des éléments requis
        verifyConfig(setupJson.getJSONObject("CONFIG"));
        verifyContraintes(setupJson.getJSONObject("CONTRAINTES"));
        verifyConditions(setupJson.getJSONObject("CONDITIONS"));
        verifyPoids(setupJson.getJSONObject("POIDS"));
    }

    private static void verifyConfig(JSONObject configJson) throws Exception {
        // Vérifier la présence et le type des éléments spécifiques de CONFIG
        if (!configJson.has("DATA_PATH") || !(configJson.get("DATA_PATH") instanceof String)) {
            throw new Exception(
                    "Le champ DATA_PATH est absent ou n'est pas de type String dans CONFIG du fichier setup.json");
        }

        if (!configJson.has("FILE_PREFIX") || !(configJson.get("FILE_PREFIX") instanceof String)) {
            throw new Exception(
                    "Le champ FILE_PREFIX est absent ou n'est pas de type String dans CONFIG du fichier setup.json");
        }

        if (!configJson.has("VERSION") || !(configJson.get("VERSION") instanceof String)) {
            throw new Exception(
                    "Le champ VERSION est absent ou n'est pas de type String dans CONFIG du fichier setup.json");
        }
    }

    private static void verifyContraintes(JSONObject contraintesJson) throws Exception {
        // Vérifier la présence et le type de NIVEAU_MIN
        if (!contraintesJson.has("NIVEAU_MIN") || !(contraintesJson.get("NIVEAU_MIN") instanceof Integer)) {
            throw new Exception(
                    "Le champ NIVEAU_MIN est absent ou n'est pas de type Integer dans CONFIG.CONTRAINTES du fichier setup.json");
        }

        // Vérifier la présence et le type de NIVEAU_MAX
        if (!contraintesJson.has("NIVEAU_MAX") || !(contraintesJson.get("NIVEAU_MAX") instanceof Integer)) {
            throw new Exception(
                    "Le champ NIVEAU_MAX est absent ou n'est pas de type Integer dans CONFIG.CONTRAINTES du fichier setup.json");
        }

        // Vérifier la présence et le type de RARETE_ALLOWED
        if (!contraintesJson.has("RARETES_AUTORISEES")
                || !(contraintesJson.get("RARETES_AUTORISEES") instanceof JSONArray)) {
            throw new Exception(
                    "Le champ RARETES_AUTORISEES est absent ou n'est pas de type JsonArray dans CONFIG.CONTRAINTES du fichier setup.json");
        }

        // Vérifier la présence et le type de EQUIPEMENTS_PREDEFINIS
        if (!contraintesJson.has("EQUIPEMENTS_PREDEFINIS")
                || !(contraintesJson.get("EQUIPEMENTS_PREDEFINIS") instanceof JSONObject)) {
            throw new Exception(
                    "Le champ EQUIPEMENTS_PREDEFINIS est absent ou n'est pas de type JsonObject dans CONFIG.CONTRAINTES du fichier setup.json");
        }

        verifyRaretesAutorisees(contraintesJson.getJSONArray("RARETES_AUTORISEES"));
        verifyEquipementsPredefinis(contraintesJson.getJSONObject("EQUIPEMENTS_PREDEFINIS"));
    }

    private static void verifyRaretesAutorisees(JSONArray raretesAutoriseesJson) throws Exception {

        for (int i = 0; i < raretesAutoriseesJson.length(); i++) {

            if (!(raretesAutoriseesJson.get(i) instanceof String)) {
                throw new Exception(
                        "Le champ " + i
                                + " n'est pas de type STRING dans CONFIG.CONTRAINTES.RARETES_AUTORISEES du fichier setup.json");
            }

            try {

                ItemRarity.valueOf(raretesAutoriseesJson.getString(i));

            } catch (IllegalArgumentException e) {

                throw new Exception("Le champ " + i
                        + " n'est pas une valeur de ItemRarity valide dans CONFIG.CONTRAINTES.RARETES_AUTORISEES du fichier setup.json");
            }
        }
    }

    private static void verifyEquipementsPredefinis(JSONObject equipementsPredefinisJson) throws Exception {

        Iterator<String> equipementsPredefinisJsonKeys = equipementsPredefinisJson.keys();

        while (equipementsPredefinisJsonKeys.hasNext()) {

            String equipementsPredefinisJsonKey = equipementsPredefinisJsonKeys.next();

            try {

                EquipmentType.valueOf(equipementsPredefinisJsonKey);

            } catch (IllegalArgumentException e) {

                throw new Exception("Le champ " + equipementsPredefinisJsonKey
                        + " n'est pas un EquipmentType valide dans CONFIG.CONTRAINTES.EQUIPEMENTS_PREDEFINIS du fichier setup.json");
            }

            if (!(equipementsPredefinisJson.get(equipementsPredefinisJsonKey) instanceof JSONArray)) {

                throw new Exception("Le champ " + equipementsPredefinisJsonKey
                        + " n'est pas de type JsonArray dans CONFIG.CONTRAINTES.EQUIPEMENTS_PREDEFINIS du fichier setup.json");
            }

            JSONArray equipementsPredefinisJsonArray = equipementsPredefinisJson
                    .getJSONArray(equipementsPredefinisJsonKey);

            for (int i = 0; i < equipementsPredefinisJsonArray.length(); i++) {

                if (!(equipementsPredefinisJsonArray.get(i) instanceof Integer)) {
                    throw new Exception(
                            "Le champ " + i
                                    + " n'est pas de type STRING dans CONFIG.CONTRAINTES.RARETES_AUTORISEES du fichier setup.json"
                                    + equipementsPredefinisJsonKey);
                }
            }
        }
    }

    private static void verifyConditions(JSONObject conditionsJson) throws Exception {

        // Vérifier la présence et le type de CONFIG
        if (!conditionsJson.has("STATS") || !(conditionsJson.get("STATS") instanceof JSONObject)) {
            throw new Exception(
                    "Le champ CONFIG est absent ou n'est pas de type JsonObject dans CONDITIONS du fichier setup.json");
        }

        // Vérifier la présence et le type de CONTRAINTES
        if (!conditionsJson.has("MAX_PAR_RARETE") || !(conditionsJson.get("MAX_PAR_RARETE") instanceof JSONObject)) {
            throw new Exception(
                    "Le champ CONTRAINTES est absent ou n'est pas de type JsonObject dans CONDITIONS du fichier setup.json");
        }

        verifyStats(conditionsJson.getJSONObject("STATS"));
        verifyMaxParRarete(conditionsJson.getJSONObject("MAX_PAR_RARETE"));
    }

    private static void verifyStats(JSONObject statsJson) throws Exception {

        Iterator<String> statsJsonKeys = statsJson.keys();

        while (statsJsonKeys.hasNext()) {

            String statsJsonKey = statsJsonKeys.next();

            try {

                Stat.valueOf(statsJsonKey);

            } catch (IllegalArgumentException e) {

                throw new Exception("Le champ " + statsJsonKey
                        + " n'est pas une Stat valide dans CONDITIONS.STATS du fichier setup.json");
            }

            if (!(statsJson.get(statsJsonKey) instanceof Integer)) {

                throw new Exception("Le champ " + statsJsonKey
                        + " n'est pas de type Integer dans CONDITIONS.STATS du fichier setup.json");
            }
        }
    }

    private static void verifyMaxParRarete(JSONObject maxParRareteJson) throws Exception {

        Iterator<String> maxParRareteJsonKeys = maxParRareteJson.keys();

        while (maxParRareteJsonKeys.hasNext()) {

            String maxParRareteJsonKey = maxParRareteJsonKeys.next();

            try {

                ItemRarity.valueOf(maxParRareteJsonKey);

            } catch (IllegalArgumentException e) {

                throw new Exception("Le champ " + maxParRareteJsonKey
                        + " n'est pas un ItemRarity valide dans CONDITIONS.MAX_PAR_RARETE du fichier setup.json");
            }

            if (!(maxParRareteJson.get(maxParRareteJsonKey) instanceof Integer)) {

                throw new Exception("Le champ " + maxParRareteJsonKey
                        + " n'est pas de type Integer dans CONDITIONS.MAX_PAR_RARETE du fichier setup.json");
            }
        }
    }

    private static void verifyPoids(JSONObject poidsJson) throws Exception {

        Iterator<String> poidsJsonKeys = poidsJson.keys();

        while (poidsJsonKeys.hasNext()) {

            String poidsJsonKey = poidsJsonKeys.next();

            try {

                Stat.valueOf(poidsJsonKey);

            } catch (IllegalArgumentException e) {

                throw new Exception(
                        "Le champ " + poidsJsonKey + " n'est pas une Stat valide dans POIDS du fichier setup.json");
            }

            if (!(poidsJson.get(poidsJsonKey) instanceof String)) {

                throw new Exception(
                        "Le champ " + poidsJsonKey + " n'est pas de type String dans POIDS du fichier setup.json");
            }
        }
    }
}
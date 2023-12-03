package com.wakfu.setup;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.wakfu.equipment.EquipmentType;
import com.wakfu.function.Analyser;
import com.wakfu.function.Function;
import com.wakfu.item.ItemRarity;
import com.wakfu.stats.Stat;

public class Setup {

    private static Setup instance = null;

    // Méthode pour obtenir l'instance unique de Setup
    public static Setup getInstance() {

        return instance;
    }

    public static void init(String setupJsonString) throws Exception {

        instance = new Setup(new JSONObject(setupJsonString));
    }

    private String path;

    private String prefix;

    private String version;

    private int contrainteNiveauMin;

    private int contrainteNiveauMax;

    private Set<ItemRarity> contrainteRaretesAutorisees;

    private Set<Stat> contrainteStatsInterdites;

    private Map<EquipmentType, List<Integer>> contrainteEquipementsPredefinis;

    private Map<Stat, Integer> conditionStats;

    private Map<ItemRarity, Integer> conditionMaxParRarete;

    private Map<Stat, Function> poids;

    // Constructeur privé pour empêcher l'instanciation directe
    private Setup(JSONObject setupJson) throws Exception {

        SetupVerifier.verify(setupJson);

        this.path = setupJson.getJSONObject("CONFIG").getString("PATH");
        this.prefix = setupJson.getJSONObject("CONFIG").getString("PREFIX");
        this.version = setupJson.getJSONObject("CONFIG").getString("VERSION");

        this.contrainteNiveauMin = setupJson.getJSONObject("CONTRAINTES").getInt("NIVEAU_MIN");
        this.contrainteNiveauMax = setupJson.getJSONObject("CONTRAINTES").getInt("NIVEAU_MAX");

        JSONArray contrainteRaretesAutoriseesJson = setupJson.getJSONObject("CONTRAINTES")
                .getJSONArray("RARETES_AUTORISEES");
        this.contrainteRaretesAutorisees = EnumSet.noneOf(ItemRarity.class);

        for (int i = 0; i < contrainteRaretesAutoriseesJson.length(); i++) {

            ItemRarity itemRarity = ItemRarity.valueOf(contrainteRaretesAutoriseesJson.getString(i));
            this.contrainteRaretesAutorisees.add(itemRarity);
        }

        JSONArray contrainteStatsInterditesJson = setupJson.getJSONObject("CONTRAINTES")
                .getJSONArray("STATS_INTERDITES");
        this.contrainteStatsInterdites = EnumSet.noneOf(Stat.class);

        for (int i = 0; i < contrainteStatsInterditesJson.length(); i++) {

            Stat stat = Stat.valueOf(contrainteStatsInterditesJson.getString(i));
            this.contrainteStatsInterdites.add(stat);
        }

        JSONObject contrainteEquipementsPredefinisJson = setupJson.getJSONObject("CONTRAINTES")
                .getJSONObject("EQUIPEMENTS_PREDEFINIS");
        Iterator<String> contrainteEquipementsPredefinisJsonKeys = contrainteEquipementsPredefinisJson.keys();
        this.contrainteEquipementsPredefinis = new HashMap<>();

        while (contrainteEquipementsPredefinisJsonKeys.hasNext()) {

            String key = contrainteEquipementsPredefinisJsonKeys.next();
            EquipmentType equipmentType = EquipmentType.valueOf(key);

            JSONArray equipementsPredefinisJson = contrainteEquipementsPredefinisJson.getJSONArray(key);
            List<Integer> equipementsPredefinis = new ArrayList<>();

            for (int i = 0; i < equipementsPredefinisJson.length(); i++)
                equipementsPredefinis.add(equipementsPredefinisJson.getInt(i));

            this.contrainteEquipementsPredefinis.put(equipmentType, equipementsPredefinis);
        }

        if (contrainteEquipementsPredefinis.get(EquipmentType.ANNEAU).size() == 1)
            throw new Exception(
                    "Merci de prédéfinir au minimum deux anneaux dans " +
                            "CONTRAINTES.EQUIPEMENTS_PREDEFINIS.ANNEAU du fichier setup.json");

        JSONObject conditionStatsJson = setupJson.getJSONObject("CONDITIONS").getJSONObject("STATS");
        Iterator<String> conditionStatsJsonKeys = conditionStatsJson.keys();
        this.conditionStats = new HashMap<>();

        while (conditionStatsJsonKeys.hasNext()) {

            String key = conditionStatsJsonKeys.next();
            this.conditionStats.put(Stat.valueOf(key), conditionStatsJson.getInt(key));
        }

        JSONObject conditionMaxParRareteJson = setupJson.getJSONObject("CONDITIONS").getJSONObject("MAX_PAR_RARETE");
        Iterator<String> conditionMaxParRareteJsonKeys = conditionMaxParRareteJson.keys();
        this.conditionMaxParRarete = new HashMap<>();

        while (conditionMaxParRareteJsonKeys.hasNext()) {

            String key = conditionMaxParRareteJsonKeys.next();
            this.conditionMaxParRarete.put(ItemRarity.valueOf(key), conditionMaxParRareteJson.getInt(key));
        }

        JSONObject poidsJson = setupJson.getJSONObject("POIDS");
        Iterator<String> poidsJsonKeys = poidsJson.keys();
        this.poids = new HashMap<>();

        while (poidsJsonKeys.hasNext()) {

            String key = poidsJsonKeys.next();
            Stat stat = Stat.valueOf(key);
            String functionString = poidsJson.getString(key);

            try {

                this.poids.put(stat, Analyser.analyse(functionString));

            } catch (Exception e) {

                throw new Exception("La fonction " + functionString + " n'est pas valide dans POIDS." + key
                        + " du fichier setup.json");
            }
        }
    }

    public String getPath() {
        return path;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getVersion() {
        return version;
    }

    public int getContrainteNiveauMin() {
        return contrainteNiveauMin;
    }

    public int getContrainteNiveauMax() {
        return contrainteNiveauMax;
    }

    public Set<ItemRarity> getContrainteRaretesAutorisees() {
        return contrainteRaretesAutorisees;
    }

    public Set<Stat> getContrainteStatsInterdites() {
        return contrainteStatsInterdites;
    }

    public Map<EquipmentType, List<Integer>> getContrainteEquipementsPredefinis() {
        return contrainteEquipementsPredefinis;
    }

    public Map<Stat, Integer> getConditionStats() {
        return conditionStats;
    }

    public Map<ItemRarity, Integer> getConditionMaxParRarete() {
        return conditionMaxParRarete;
    }

    public Map<Stat, Function> getPoids() {
        return poids;
    }
}

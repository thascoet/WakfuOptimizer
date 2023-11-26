package com.wakfu.stats;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class StatsParser {

    // Méthode statique pour créer une instance de Stats à partir d'un objet JSON
    public static Stats fromJson(JSONArray effectsArray) {

        HashMap<Stat, Integer> statsMap = new HashMap<>();
        for (Stat stat : Stat.values()) {
            statsMap.put(stat, 0);
        }

        // Parcourir le JSONArray
        for (int i = 0; i < effectsArray.length(); i++) {
            JSONObject effectObject = effectsArray.getJSONObject(i);
            JSONObject definitionObject = effectObject.getJSONObject("effect").getJSONObject("definition");
            int actionId = definitionObject.getInt("actionId");
            int param = definitionObject.getJSONArray("params").getBigDecimal(0).intValue();

            // Modifier la valeur correspondante dans la HashMap en fonction de l'actionId et du paramètre
            switch (actionId) {
                case 20:
                    statsMap.compute(Stat.PV, (key, value) -> value + param);
                    break;
                case 21:
                    statsMap.compute(Stat.PV, (key, value) -> value - param);
                    break;
                case 30:
                    statsMap.compute(Stat.MAITRISE_SOIN, (key, value) -> value + param);
                    break;
                case 31:
                    statsMap.compute(Stat.PA, (key, value) -> value + param);
                    break;
                case 41:
                    statsMap.compute(Stat.PM, (key, value) -> value + param);
                    break;
                case 42:
                    statsMap.compute(Stat.PM, (key, value) -> value - param);
                    break;
                case 56:
                    statsMap.compute(Stat.PA, (key, value) -> value - param);
                    break;
                case 57:
                    statsMap.compute(Stat.PM, (key, value) -> value - param);
                    break;
                case 71:
                    statsMap.compute(Stat.RESISTANCE_DOS, (key, value) -> value + param);
                    break;
                case 80:
                    statsMap.compute(Stat.RESISTANCE_ELEM, (key, value) -> value + param);
                    break;
                case 82:
                    statsMap.compute(Stat.RESISTANCE_FEU, (key, value) -> value + param);
                    break;
                case 83:
                    statsMap.compute(Stat.RESISTANCE_EAU, (key, value) -> value + param);
                    break;
                case 84:
                    statsMap.compute(Stat.RESISTANCE_SOL, (key, value) -> value + param);
                    break;
                case 85:
                    statsMap.compute(Stat.RESISTANCE_AIR, (key, value) -> value + param);
                    break;
                case 90:
                    statsMap.compute(Stat.RESISTANCE_ELEM, (key, value) -> value - param);
                    break;
                case 96:
                    statsMap.compute(Stat.RESISTANCE_SOL, (key, value) -> value - param);
                    break;
                case 97:
                    statsMap.compute(Stat.RESISTANCE_FEU, (key, value) -> value - param);
                    break;
                case 98:
                    statsMap.compute(Stat.RESISTANCE_EAU, (key, value) -> value - param);
                    break;
                case 100:
                    statsMap.compute(Stat.RESISTANCE_ELEM, (key, value) -> value - param);
                    break;
                case 120:
                    statsMap.compute(Stat.MAITRISE_ELEM, (key, value) -> value + param);
                    break;
                case 122:
                    statsMap.compute(Stat.MAITRISE_FEU, (key, value) -> value + param);
                    break;
                case 123:
                    statsMap.compute(Stat.MAITRISE_SOL, (key, value) -> value + param);
                    break;
                case 124:
                    statsMap.compute(Stat.MAITRISE_EAU, (key, value) -> value + param);
                    break;
                case 125:
                    statsMap.compute(Stat.MAITRISE_AIR, (key, value) -> value + param);
                    break;
                case 130:
                    statsMap.compute(Stat.MAITRISE_ELEM, (key, value) -> value - param);
                    break;
                case 132:
                    statsMap.compute(Stat.MAITRISE_FEU, (key, value) -> value - param);
                    break;
                case 149:
                    statsMap.compute(Stat.MAITRISE_CRITIQUE, (key, value) -> value + param);
                    break;
                case 150:
                    statsMap.compute(Stat.TCC, (key, value) -> value + param);
                    break;
                case 160:
                    statsMap.compute(Stat.PO, (key, value) -> value + param);
                    break;
                case 161:
                    statsMap.compute(Stat.PO, (key, value) -> value - param);
                    break;
                case 168:
                    statsMap.compute(Stat.TCC, (key, value) -> value - param);
                    break;
                case 171:
                    statsMap.compute(Stat.INITIATIVE, (key, value) -> value + param);
                    break;
                case 172:
                    statsMap.compute(Stat.INITIATIVE, (key, value) -> value - param);
                    break;
                case 173:
                    statsMap.compute(Stat.TACLE, (key, value) -> value + param);
                    break;
                case 174:
                    statsMap.compute(Stat.TACLE, (key, value) -> value - param);
                    break;
                case 175:
                    statsMap.compute(Stat.ESQUIVE, (key, value) -> value + param);
                    break;
                case 176:
                    statsMap.compute(Stat.ESQUIVE, (key, value) -> value - param);
                    break;
                case 180:
                    statsMap.compute(Stat.MAITRISE_DOS, (key, value) -> value + param);
                    break;
                case 181:
                    statsMap.compute(Stat.MAITRISE_DOS, (key, value) -> value - param);
                    break;
                case 184:
                    statsMap.compute(Stat.CONTROLE, (key, value) -> value + param);
                    break;
                case 191:
                    statsMap.compute(Stat.PW, (key, value) -> value + param);
                    break;
                case 192:
                    statsMap.compute(Stat.PW, (key, value) -> value - param);
                    break;
                case 875:
                    statsMap.compute(Stat.PARADE, (key, value) -> value + param);
                    break;
                case 876:
                    statsMap.compute(Stat.PARADE, (key, value) -> value - param);
                    break;
                case 988:
                    statsMap.compute(Stat.RESISTANCE_CRITIQUE, (key, value) -> value + param);
                    break;
                case 1052:
                    statsMap.compute(Stat.MAITRISE_MELEE, (key, value) -> value + param);
                    break;
                case 1053:
                    statsMap.compute(Stat.MAITRISE_DISTANCE, (key, value) -> value + param);
                    break;
                case 1055:
                    statsMap.compute(Stat.MAITRISE_BERSERK, (key, value) -> value + param);
                    break;
                case 1056:
                    statsMap.compute(Stat.MAITRISE_CRITIQUE, (key, value) -> value - param);
                    break;
                case 1059:
                    statsMap.compute(Stat.MAITRISE_MELEE, (key, value) -> value - param);
                    break;
                case 1060:
                    statsMap.compute(Stat.MAITRISE_DISTANCE, (key, value) -> value - param);
                    break;
                case 1061:
                    statsMap.compute(Stat.MAITRISE_BERSERK, (key, value) -> value - param);
                    break;
                case 1062:
                    statsMap.compute(Stat.RESISTANCE_CRITIQUE, (key, value) -> value - param);
                    break;
                case 1063:
                    statsMap.compute(Stat.RESISTANCE_DOS, (key, value) -> value - param);
                    break;
                case 1068:
                    int elemParam = definitionObject.getJSONArray("params").getBigDecimal(2).intValue();
                    switch (elemParam) {
                        case 1:
                            statsMap.compute(Stat.MAITRISE_ELEM_1, (key, value) -> value + param);
                            break;
                        case 2:
                            statsMap.compute(Stat.MAITRISE_ELEM_2, (key, value) -> value + param);
                            break;
                        case 3:
                            statsMap.compute(Stat.MAITRISE_ELEM_3, (key, value) -> value + param);
                            break;
                        default:
                            throw new IllegalArgumentException("la valeur params[2] " + elemParam + " n'est pas valide pour l'action 1068");
                    }
                    break;
                case 1069:
                    int resistElemParam = definitionObject.getJSONArray("params").getBigDecimal(2).intValue();
                    switch (resistElemParam) {
                        case 1:
                            statsMap.compute(Stat.RESISTANCE_ELEM_1, (key, value) -> value + param);
                            break;
                        case 2:
                            statsMap.compute(Stat.RESISTANCE_ELEM_2, (key, value) -> value + param);
                            break;
                        case 3:
                            statsMap.compute(Stat.RESISTANCE_ELEM_3, (key, value) -> value + param);
                            break;
                        default:
                            throw new IllegalArgumentException("la valeur params[2] " + resistElemParam + " n'est pas valide pour l'action 1069");
                    }
                    break;
            }
        }

        return new Stats(statsMap);
    }
}

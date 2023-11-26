package com.wakfu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class Connect {

    private static String baseURL = "https://wakfu.cdn.ankama.com/gamedata/";
    
    public static String sendHttpRequest(String urlString) throws Exception {
        URL url = new URL(baseURL + urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Définir la méthode de la requête
        connection.setRequestMethod("GET");

        // Spécifier l'encodage UTF-8
        connection.setRequestProperty("Accept-Charset", "UTF-8");

        // Lire la réponse en spécifiant l'encodage
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();

        return response.toString();
    }

    public static String getVersion() throws Exception {
        String jsonString = sendHttpRequest("config.json");
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getString("version");
    }
}

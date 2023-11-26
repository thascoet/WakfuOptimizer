package com.wakfu.setup;

import org.json.JSONObject;

public class Setup {

    private static Setup instance;

    private JSONObject setupJson;

    // Constructeur privé pour empêcher l'instanciation directe
    private Setup(JSONObject setupJson) throws Exception {
        
        SetupVerifier.verify(setupJson);
        this.setupJson = setupJson;
    }

    // Méthode pour obtenir l'instance unique de Setup
    public static JSONObject getInstance() {
        if (instance == null) {
            return null;
        }
        return instance.setupJson;
    }

    public static void init(String setupJsonString) throws Exception {

        instance = new Setup(new JSONObject(setupJsonString));
    }
}

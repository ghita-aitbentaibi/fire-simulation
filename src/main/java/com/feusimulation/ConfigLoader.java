package com.feusimulation;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ConfigLoader {
    private static ConfigLoader instance;
    private int hauteur;
    private int largeur;
    private double probabilite;
    private List<List<Integer>> feuxInitiaux;

    private ConfigLoader() {}

    public static ConfigLoader getInstance() {
        if (instance == null) {
            instance = new ConfigLoader();
        }
        return instance;
    }

    public void chargerConfig(String cheminFichier) {
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = new FileInputStream(cheminFichier);
        System.err.println(inputStream);
        if (inputStream == null) {
            throw new FileNotFoundException("Fichier de configuration introuvable : " + cheminFichier);
        }

        Map<String, Object> config = objectMapper.readValue(inputStream, Map.class);
        hauteur = (int) config.get("hauteur");
        largeur = (int) config.get("largeur");
        probabilite = (double) config.get("probabilite");
        feuxInitiaux = (List<List<Integer>>) config.get("feuxInitiaux");

    } catch (IOException e) {
        System.err.println("Erreur lors de la lecture du fichier de configuration : " + e.getMessage());
        e.printStackTrace();
    }
}


    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    public double getProbabilite() {
        return probabilite;
    }

    public List<List<Integer>> getFeuxInitiaux() {
        return feuxInitiaux;
    }
}

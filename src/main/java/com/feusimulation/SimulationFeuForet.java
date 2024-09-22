package com.feusimulation;

public class SimulationFeuForet {
    public static void main(String[] args) {
        ConfigLoader configLoader = ConfigLoader.getInstance();
        configLoader.chargerConfig("src/main/ressource/config.json");

        GrilleForet grilleForet = new GrilleForet(configLoader.getHauteur(), configLoader.getLargeur(), configLoader.getProbabilite());
        grilleForet.initGrille(configLoader.getFeuxInitiaux());

        System.out.println("État initial de la grille :");
        grilleForet.afficherGrille();

        PropagationStrategy strategy = new PropagationAleatoire();
        
        while (grilleForet.existeCaseEnFeu()) {
            grilleForet.propagation(strategy);
            grilleForet.afficherGrille();
        }

        System.out.println("La simulation est terminée.");
    }
}

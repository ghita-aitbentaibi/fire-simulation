package com.feusimulation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class SimulationFeuForetTest {

    // Vérifiez que la grille est initialisée avec toutes les cases à l'état intact
    // (valeur 2).
    @Test
    public void testInitGrille() {
        GrilleForet grille = new GrilleForet(10, 10, 0.3);
        List<List<Integer>> feuxInitiaux = List.of(List.of(5, 5)); // Position initiale du feu
        grille.initGrille(feuxInitiaux);

        // Vérifiez que toutes les cases sont initialisées à 2 (intact)
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 5 && j == 5) {
                    assertEquals(1, grille.getCaseEtat(i, j)); // La case (5, 5) doit être en feu
                } else {
                    assertEquals(2, grille.getCaseEtat(i, j)); // Les autres cases doivent être intactes
                }
            }
        }
    }

    //Vérifiez que les cases adjacentes prennent feu selon la probabilité spécifiée.
    @Test
    public void testPropagation() {
        GrilleForet grille = new GrilleForet(10, 10, 1.0); // 100% de probabilité
        List<List<Integer>> feuxInitiaux = List.of(List.of(5, 5));
        grille.initGrille(feuxInitiaux);
        grille.propagation(new PropagationAleatoire());

        assertEquals(0, grille.getCaseEtat(5, 5)); // Le feu doit s'éteindre
        assertEquals(1, grille.getCaseEtat(4, 5)); // Vérifiez si la case du haut prend feu
        assertEquals(1, grille.getCaseEtat(6, 5)); // Vérifiez si la case du bas prend feu
        // Ajoutez des vérifications pour les cases gauche et droite
    }

}

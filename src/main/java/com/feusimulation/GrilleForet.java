package com.feusimulation;

import java.util.Arrays;
import java.util.List;

public class GrilleForet {
    private int[][] grille;
    private int hauteur;
    private int largeur;
    private double probabilite;

    public GrilleForet(int hauteur, int largeur, double probabilite) {
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.probabilite = probabilite;
        this.grille = new int[hauteur][largeur];
    }

    public void initGrille(List<List<Integer>> feuxInitiaux) {
        for (int i = 0; i < hauteur; i++) {
            Arrays.fill(grille[i], 2);
        }
        for (List<Integer> feu : feuxInitiaux) {
            grille[feu.get(0)][feu.get(1)] = 1;
        }
    }

    public boolean existeCaseEnFeu() {
        for (int[] ligne : grille) {
            for (int caseEtat : ligne) {
                if (caseEtat == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public void propagation(PropagationStrategy strategy) {
        int[][] nouvelleGrille = new int[hauteur][largeur];

        for (int i = 0; i < hauteur; i++) {
            nouvelleGrille[i] = grille[i].clone();
        }

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                if (grille[i][j] == 1) {
                    nouvelleGrille[i][j] = 0;

                    propagerSiPossible(i - 1, j, nouvelleGrille, strategy);  // Haut
                    propagerSiPossible(i + 1, j, nouvelleGrille, strategy);  // Bas
                    propagerSiPossible(i, j - 1, nouvelleGrille, strategy);  // Gauche
                    propagerSiPossible(i, j + 1, nouvelleGrille, strategy);  // Droite
                }
            }
        }
        grille = nouvelleGrille;
    }

    private void propagerSiPossible(int x, int y, int[][] nouvelleGrille, PropagationStrategy strategy) {
        if (x >= 0 && x < hauteur && y >= 0 && y < largeur && grille[x][y] == 2) {
            strategy.propager(x, y, nouvelleGrille, probabilite);
        }
    }

    public int getCaseEtat(int x, int y) {
        return grille[x][y];
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    public void afficherGrille() {
        for (int[] ligne : grille) {
            for (int caseEtat : ligne) {
                System.out.print(caseEtat + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    
}

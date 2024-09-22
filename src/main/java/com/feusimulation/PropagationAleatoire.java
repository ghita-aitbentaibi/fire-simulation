package com.feusimulation;


public class PropagationAleatoire implements PropagationStrategy {
    @Override
    public void propager(int x, int y, int[][] nouvelleGrille, double probabilite) {
        if (Math.random() < probabilite) {
            nouvelleGrille[x][y] = 1;  // La case prend feu
        }
    }
}

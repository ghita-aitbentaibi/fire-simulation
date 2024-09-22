package com.feusimulation;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SimulationFeuForet extends JPanel {
    private GrilleForet grilleForet;
    private final int CELL_SIZE = 30;  // Taille de chaque cellule augmentée

    public SimulationFeuForet(GrilleForet grilleForet) {
        this.grilleForet = grilleForet;
        setPreferredSize(new Dimension(grilleForet.getLargeur() * CELL_SIZE, grilleForet.getHauteur() * CELL_SIZE));
        setBackground(Color.LIGHT_GRAY); // Couleur de fond
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < grilleForet.getHauteur(); i++) {
            for (int j = 0; j < grilleForet.getLargeur(); j++) {
                int etat = grilleForet.getCaseEtat(i, j);

                // Coloration des cellules selon leur état
                if (etat == 1) {
                    g.setColor(Color.RED); // Case en feu
                } else if (etat == 2) {
                    g.setColor(new Color(34, 139, 34)); // Case d'arbre non brûlée
                } else {
                    g.setColor(Color.GRAY); // Case brûlée
                }

                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Dessiner la bordure des cellules
            }
        }
    }

    public void update() {
        repaint(); // Redessine le composant pour afficher les nouvelles données
    }

    public static void main(String[] args) {
        // Charger la configuration
        ConfigLoader configLoader = ConfigLoader.getInstance();
        configLoader.chargerConfig("src/main/ressource/config.json");

        // Initialiser la grille et la stratégie de propagation
        GrilleForet grilleForet = new GrilleForet(configLoader.getHauteur(), configLoader.getLargeur(), configLoader.getProbabilite());
        grilleForet.initGrille(configLoader.getFeuxInitiaux());
        PropagationStrategy strategy = new PropagationAleatoire();

        // Créer la fenêtre
        JFrame frame = new JFrame("Simulation de Feu de Forêt");
        SimulationFeuForet simulationPanel = new SimulationFeuForet(grilleForet);
        JButton startButton = new JButton("Démarrer Simulation");
        JButton resetButton = new JButton("Réinitialiser");

        // Panneau de contrôle
        JPanel controlPanel = new JPanel();
        controlPanel.add(startButton);
        controlPanel.add(resetButton);

        frame.setLayout(new BorderLayout());
        frame.add(simulationPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setSize(800, 600); // Taille de la fenêtre (largeur, hauteur)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Action sur le bouton Démarrer
        startButton.addActionListener(e -> {
            new Thread(() -> {
                while (grilleForet.existeCaseEnFeu()) {
                    grilleForet.propagation(strategy);
                    simulationPanel.update();
                    try {
                        Thread.sleep(500); // Attendre un peu pour visualiser la propagation (500ms)
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                System.out.println("Simulation terminée.");
            }).start();
        });

        // Action sur le bouton Réinitialiser
        resetButton.addActionListener(e -> {
            grilleForet.initGrille(configLoader.getFeuxInitiaux());
            simulationPanel.update();
            System.out.println("Grille réinitialisée.");
        });
    }
}
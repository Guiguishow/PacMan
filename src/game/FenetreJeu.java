package game;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FenetreJeu extends JFrame {

    public FenetreJeu() {
        this.setTitle("Pacman");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new PanneauDeJeu());
        this.setVisible(true);
    }

    public class PanneauDeJeu extends JPanel {

        private int[][] arenes = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 0, 1, 1, 0, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 0, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 1, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        };

        private List<Piece> pieces;
        private Personnage.Pacman pacman;
        private List<Personnage.Fantome> fantomes;
        private List<int[]> casesLibres;
        private int tileSize;
        private Timer fantomeTimer;

        public PanneauDeJeu() {
            pieces = new ArrayList<>();
            fantomes = new ArrayList<>();
            casesLibres = new ArrayList<>();

            tileSize = Math.min(getWidth() / arenes[0].length, getHeight() / arenes.length);

            for (int row = 0; row < arenes.length; row++) {
                for (int col = 0; col < arenes[row].length; col++) {
                    if (arenes[row][col] == 0) {
                        casesLibres.add(new int[]{col, row});
                        pieces.add(new Piece(col, row, tileSize));
                    }
                }
            }

            int[] positionPacman = genererPositionAleatoire();
            pacman = new Personnage.Pacman(positionPacman[0], positionPacman[1], tileSize);

            int[] positionFantome1 = genererPositionAleatoire();
            int[] positionFantome2 = genererPositionAleatoire();
            fantomes.add(new Personnage.Fantome(positionFantome1[0], positionFantome1[1], Color.RED, tileSize));
            fantomes.add(new Personnage.Fantome(positionFantome2[0], positionFantome2[1], Color.GREEN, tileSize));

            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    int newX = pacman.getX();
                    int newY = pacman.getY();

                    if (key == KeyEvent.VK_UP) {
                        newY--;
                    } else if (key == KeyEvent.VK_DOWN) {
                        newY++;
                    } else if (key == KeyEvent.VK_LEFT) {
                        newX--;
                    } else if (key == KeyEvent.VK_RIGHT) {
                        newX++;
                    }

                    if (isPositionValid(newX, newY)) {
                        pacman.setX(newX);
                        pacman.setY(newY);
                        mangerPiece(newX, newY);
                        repaint();
                    }
                }
            });

            setFocusable(true);

            fantomeTimer = new Timer(300, e -> {
                deplacerFantomes(); // Déplacer les fantômes à chaque intervalle
                repaint();
            });
            fantomeTimer.start();
        }

        private void deplacerFantomes() {
            for (Personnage.Fantome fantome : fantomes) {
                int direction = new Random().nextInt(4); // 0: haut, 1: bas, 2: gauche, 3: droite
                int newX = fantome.getX();
                int newY = fantome.getY();

                switch (direction) {
                    case 0: // Haut
                        newY--;
                        break;
                    case 1: // Bas
                        newY++;
                        break;
                    case 2: // Gauche
                        newX--;
                        break;
                    case 3: // Droite
                        newX++;
                        break;
                }

                // Vérifier si la nouvelle position est valide avant de déplacer
                if (isPositionValid(newX, newY)) {
                    fantome.setX(newX);
                    fantome.setY(newY);
                }
            }
        }

        private boolean isPositionValid(int x, int y) {
            return x >= 0 && x < arenes[0].length && y >= 0 && y < arenes.length && arenes[y][x] == 0;
        }

        private void mangerPiece(int x, int y) {
            // Vérifier si Pac-Man est sur une case avec une pièce
            for (int i = 0; i < pieces.size(); i++) {
                Piece piece = pieces.get(i);
                if (piece.getX() == x && piece.getY() == y) {
                    pieces.remove(i); // Retirer la pièce de la liste
                    break; // Sortir de la boucle après avoir mangé la pièce
                }
            }
        }

        private int[] genererPositionAleatoire() {
            Random random = new Random();
            int index = random.nextInt(casesLibres.size());
            return casesLibres.get(index);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            tileSize = Math.min(getWidth() / arenes[0].length, getHeight() / arenes.length);

            for (int row = 0; row < arenes.length; row++) {
                for (int col = 0; col < arenes[row].length; col++) {
                    if (arenes[row][col] == 1) {
                        g.setColor(Color.BLUE);
                        g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
                    } else {
                        g.setColor(Color.BLACK);
                        g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
                    }
                }
            }

            for (Piece piece : pieces) {
                piece.setTileSize(tileSize);
                piece.dessiner(g);
            }

            if (arenes[pacman.getY()][pacman.getX()] == 0) {
                pacman.setTileSize(tileSize);
                pacman.dessiner(g);
            }

            for (Personnage.Fantome fantome : fantomes) {
                if (arenes[fantome.getY()][fantome.getX()] == 0) {
                    fantome.setTileSize(tileSize);
                    fantome.dessiner(g);
                }
            }
        }
    }
}

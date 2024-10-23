package game;

import java.awt.Color;
import java.awt.Graphics;

public class Personnage {
    private int x;
    private int y;
    private Color couleur;
    private int tileSize;

    public Personnage(int x, int y, Color couleur, int tileSize) {
        this.x = x;
        this.y = y;
        this.couleur = couleur;
        this.tileSize = tileSize;
    }

    public void dessiner(Graphics g) {
        g.setColor(couleur);
        g.fillOval(x * tileSize, y * tileSize, tileSize, tileSize);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public static class Pacman extends Personnage {
        public Pacman(int x, int y, int tileSize) {
            super(x, y, Color.YELLOW, tileSize);
        }
    }

    public static class Fantome extends Personnage {
        public Fantome(int x, int y, Color couleur, int tileSize) {
            super(x, y, couleur, tileSize);
        }
    }

}



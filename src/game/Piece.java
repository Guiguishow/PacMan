package game;

import java.awt.*;

public class Piece {
    private int x;
    private int y;
    private int tileSize;

    public Piece(int x, int y, int tileSize) {
        this.x = x;
        this.y = y;
        this.tileSize = tileSize;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public void dessiner(Graphics g) {
        int pieceSize = tileSize / 3;
        g.setColor(Color.YELLOW);
        g.fillOval(x * tileSize + (tileSize - pieceSize) / 2,
                y * tileSize + (tileSize - pieceSize) / 2,
                pieceSize, pieceSize);
    }
}


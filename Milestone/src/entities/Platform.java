package entities;

import game.*;

import java.awt.*;

/**
 * Class: Platform
 * Represents a static platform in the game world.
 */
public class Platform extends GameObject {

    public Platform(GameComponent game, double x, double y, int width, int height) {
        super(game, x, y, width, height);
    }

    @Override
    public void update() {
        // Platforms are static
    }

    @Override
    public void drawOn(Graphics2D g) {
        g.setColor(new Color(120, 72, 0));
        g.fillRect((int) x, (int) y, width, height);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}

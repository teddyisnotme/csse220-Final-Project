package entities;

import game.*;

import java.awt.*;

/**
 * Class: Platform
 * Author: Agnes Wang
 * <br>Purpose: Represents a static platform in the game world.
 * Platforms provide solid surfaces for the player and enemies to stand on.
 * They do not move or interact with other objects aside from collision detection.
 * <br>Restrictions: Must be added to a GameComponent to be drawn and updated in the game loop.
 * <br>Example:
 * <pre>
 *    GameComponent game = new GameComponent();
 *    Platform p = new Platform(game, 100, 300, 200, 20);
 *    game.addPlatform(p);
 * </pre>
 */
public class Platform extends GameObject {

	/**
     * ensures: Creates a new Platform at the specified position and size within the game world.
     * @param game the GameComponent this platform belongs to
     * @param x the x-coordinate of the platform's top-left corner
     * @param y the y-coordinate of the platform's top-left corner
     * @param width the width of the platform
     * @param height the height of the platform
     */
    public Platform(GameComponent game, double x, double y, int width, int height) {
        super(game, x, y, width, height);
    }

    @Override
    public void update() {
    	
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

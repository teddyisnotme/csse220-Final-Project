package entities;

import game.*;
import java.awt.*;

/**
 * Class: Collectible
 * @author Agnes Wang, Shi Liang, Teddy Wei
 * <br>Purpose: Represents an item that the player can pick up to increase score.
 * <br>Restrictions: Becomes inactive once collected.
 */
public class Collectible extends GameObject {
    private int value;
    private boolean collected = false;

    /**
     * ensures: Creates a collectible at the given position and with a point value.
     * @param game reference to the GameComponent
     * @param x x-coordinate
     * @param y y-coordinate
     * @param value points added to player's score when collected
     */
    public Collectible(GameComponent game, double x, double y, int value) {
        super(game, x, y, 20, 20);
        this.value = value;
    }

    /**
     * ensures: Returns collectible’s point value.
     */
    public int getValue() {
        return value;
    }

    /**
     * ensures: Returns whether this collectible has been picked up.
     */
    public boolean isCollected() {
        return collected;
    }

    /**
     * ensures: Marks the collectible as collected.
     */
    public void collect() {
        collected = true;
    }

    /**
     * ensures: Draws collectible as a yellow circle unless collected.
     * @param g Graphics2D context
     */
    @Override
    public void drawOn(Graphics2D g) {
        if (!collected) {
            g.setColor(Color.ORANGE);
            g.fillOval((int)x, (int)y, width, height);
        }
    }
}


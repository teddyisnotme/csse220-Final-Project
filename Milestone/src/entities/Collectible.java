package entities;

import game.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Class: Collectible
 * @author Agnes Wang, Shi Liang, Teddy Wei
 * <br>Purpose: Represents an item that the player can pick up to increase score.
 * <br>Restrictions: Becomes inactive once collected.
 */
public class Collectible extends GameObject {
    private int value;
    private boolean collected = false;
    private BufferedImage sprite;

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
        try {
            sprite = ImageIO.read(getClass().getResource("/assets/sprites/collectible.png"));
        } catch (Exception e) {
            System.err.println("Collectible sprite missing, using placeholder.");
        }
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
     * ensures: Updates collectible state (currently does nothing).
     */
    @Override
    public void update() {
        
    }

    /**
     * ensures: Draws collectible as a yellow circle unless collected.
     * @param g Graphics2D context
     */
    @Override
    public void drawOn(Graphics2D g) {
        if (!collected) {
            if (sprite != null)
                g.drawImage(sprite, (int)x, (int)y, width, height, null);
            else {
                g.setColor(Color.ORANGE);
                g.fillOval((int)x, (int)y, width, height);
            }
        }
    }
}

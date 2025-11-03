package entities;

import game.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Class: Enemy
 * @author 
 * <br>Purpose: Represents an automatically moving enemy object in the game.
 * <br>Restrictions: Movement is restricted between specified left and right bounds.
 * <br>For example:
 * <pre>
 *    Enemy enemy = new Enemy(gameComponent, 400, 400, 100, 700);
 * </pre>
 */

public class Enemy extends GameObject {
    private double leftBound;
    private double rightBound;
    private boolean movingRight = true;
    private BufferedImage sprite;
    
    /**
     * ensures: Initializes the enemy position and patrol bounds, and loads its sprite image.
     * @param game reference to the GameComponent for world boundaries and updates
     * @param x initial x-coordinate
     * @param y initial y-coordinate
     * @param leftBound leftmost x position before the enemy changes direction
     * @param rightBound rightmost x position before the enemy changes direction
     */
    public Enemy(GameComponent game, double x, double y, double leftBound, double rightBound) {
        super(game, x, y, 40, 40);
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        try {
            sprite = ImageIO.read(getClass().getResource("/assets/sprites/enemy.png"));
        } catch (Exception e) {
            System.err.println("Enemy sprite missing. Using placeholder.");
        }
    } // Enemy

    /**
     * ensures: Updates the enemy’s horizontal movement and reverses direction when reaching patrol bounds.
     */
    @Override
    public void update() {
        if (movingRight) 
        	x += 2; 
        	
        else 
        	x -= 2;
        
        if (x > rightBound || x < leftBound) 
        	movingRight = !movingRight;
    } // update
    
    
    /**
     * ensures: Draws the enemy on the screen using its sprite, or a red rectangle if sprite is missing.
     * @param g Graphics2D object for drawing the enemy
     */
    @Override
    public void drawOn(Graphics2D g) {
        if (sprite != null)
            g.drawImage(sprite, (int)x, (int)y, width, height, null);
        else {
            g.setColor(Color.RED);
            g.fillRect((int)x, (int)y, width, height);
        }
    } // drawOn
} // end Enemy
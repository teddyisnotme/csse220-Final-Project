package entities;

import game.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Class: Player
 * @author Agnes Wang, Shi Liang, Teddy Wei
 * <br>Purpose: Represents the controllable player character with movement, jumping, and life count.
 * <br>Restrictions: The player cannot jump multiple times without landing.
 * <br>For example:
 * <pre>
 *    Player player = new Player(gameComponent, 100, 400);
 *    player.moveLeft(true);
 *    player.jump();
 * </pre>
 */
public class Player extends GameObject {
    private boolean left;
    private boolean right;
    private boolean jumping;
    private final double speed = 4;
    private double gravity = 0.6;
    private double jumpStrength = -12;
    private int lives = 3;
    private BufferedImage sprite;

    /**
     * ensures: Initializes player position, size, and loads player sprite image.
     * @param game reference to the GameComponent
     * @param x initial x-coordinate
     * @param y initial y-coordinate
     */
    public Player(GameComponent game, double x, double y) {
        super(game, x, y, 40, 40);
        try {
            sprite = ImageIO.read(getClass().getResource("/assets/sprites/player.png"));
        } catch (Exception e) {
            System.err.println("Player sprite missing. Using placeholder.");
        }
    } // Player

    /**
     * ensures: Starts or stops leftward movement.
     * @param pressed true if left key pressed, false if released
     */
    public void moveLeft(boolean pressed) { 
    	left = pressed; 
    } // moveLeft
    
    /**
     * ensures: Starts or stops rightward movement.
     * @param pressed true if right key pressed, false if released
     */
    public void moveRight(boolean pressed) { 
    	right = pressed;
    } //moveRight
    
    /**
     * ensures: Makes the player jump if currently not jumping.
     */
    public void jump() {
    	if (!jumping) { 
    		vy = jumpStrength; jumping = true; 
    	} 
    } // jump

    /**
     * ensures: Returns current player lives.
     * @return number of remaining lives
     */
    public int getLives() { 
    	return lives; 
    } //getLives
    
    /**
     * ensures: Decreases player’s life count and prints “Game over!” when lives reach zero.
     */
    public void loseLife() {
        if (--lives <= 0) {
            System.out.println("Game over!");
        }
    } // loseLife
    
    /**
     * ensures: Updates player velocity, position, and applies gravity and boundary wrapping.
     */
    @Override
    public void update() {
        if (left)
        	vx = -speed;
        else if (right)
        	vx = speed;
        else 
        	vx = 0;

        vy += gravity;
        x += vx;
        y += vy;

        if (y >= game.getHeight() - 80) {
            y = game.getHeight() - 80;
            vy = 0;
            jumping = false;
        }

        if (x < 0) 
        	x = game.getWidth();
        if (x > game.getWidth()) 
        	x = 0;
    } // update

    /**
     * ensures: Draws the player sprite or a green rectangle if missing.
     * @param g Graphics2D object for drawing
     */
    @Override
    public void drawOn(Graphics2D g) {
        if (sprite != null)
            g.drawImage(sprite, (int)x, (int)y, width, height, null);
        else {
            g.setColor(Color.GREEN);
            g.fillRect((int)x, (int)y, width, height);
        }
    } // drawOn
} // end Player
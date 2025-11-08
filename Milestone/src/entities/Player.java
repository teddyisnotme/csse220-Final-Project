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
    private int score = 0;
    private boolean pressingDown = false;
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
    } // getLives
    
    /**
     * ensures: Decreases player’s life count.
     */
    public void loseLife() {
        --lives;
        if (lives < 0) {
        	lives = 0;
        }
    } // loseLife
    
    /**
     * ensures: Returns whether the player is still alive (has > 0 lives).
     * @return true if player has at least one life
     */
    public boolean isAlive() {
        return lives > 0;
    } // isAlive
    
    /**
     * ensures: Called by input handling to set whether the down key is pressed.
     * @param pressed true if down arrow is currently pressed
     */
    public void setPressingDown(boolean pressed) {
        pressingDown = pressed;
    } // setPressingDown
   
    /**
     * ensures: Try to collect a Collectible when overlapping and down is pressed.
     * Returns true if collected (caller can use this to remove the collectible from world).
     * @param c the collectible to attempt to pick up
     * @return true if the collectible was picked up
     */
    public boolean tryCollect(Collectible c) {
        if (c == null) return false;
        if (!c.isCollected() && getBounds().intersects(c.getBounds()) && pressingDown) {
            score += c.getValue(); 
            c.collect();
            return true;
        }
        return false;
    } // tryCollect
    
    /**
     * ensures: Returns player's score for HUD display.
     * @return current score
     */
    public int getScore() {
        return score;
    } // getScore
    
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
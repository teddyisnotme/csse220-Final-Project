package entities;

import game.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Player extends GameObject {
    private boolean left;
    private boolean right;
    private boolean jumping;
    private final double speed = 4;
    private double gravity = 0.6;
    private double jumpStrength = -12;
    private int lives = 3;
    private BufferedImage sprite;

    public Player(GameComponent game, double x, double y) {
        super(game, x, y, 40, 40);
        try {
            sprite = ImageIO.read(getClass().getResource("/assets/sprites/player.png"));
        } catch (Exception e) {
            System.err.println("Player sprite missing. Using placeholder.");
        }
    }

    public void moveLeft(boolean pressed) { 
    	left = pressed; 
    }
    public void moveRight(boolean pressed) { 
    	right = pressed;
    }
    public void jump() {
    	if (!jumping) { 
    		vy = jumpStrength; jumping = true; 
    	} 
    }

    public int getLives() { 
    	return lives; 
    }
    public void loseLife() {
        if (--lives <= 0) {
            System.out.println("Game over!");
        }
    }

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
    }

    @Override
    public void drawOn(Graphics2D g) {
        if (sprite != null)
            g.drawImage(sprite, (int)x, (int)y, width, height, null);
        else {
            g.setColor(Color.GREEN);
            g.fillRect((int)x, (int)y, width, height);
        }
    }
}
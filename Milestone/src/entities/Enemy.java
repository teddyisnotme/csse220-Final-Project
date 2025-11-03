package entities;

import game.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Enemy extends GameObject {
    private double leftBound;
    private double rightBound;
    private boolean movingRight = true;
    private BufferedImage sprite;

    public Enemy(GameComponent game, double x, double y, double leftBound, double rightBound) {
        super(game, x, y, 40, 40);
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        try {
            sprite = ImageIO.read(getClass().getResource("/assets/sprites/enemy.png"));
        } catch (Exception e) {
            System.err.println("Enemy sprite missing. Using placeholder.");
        }
    }

    @Override
    public void update() {
        if (movingRight) 
        	x += 2; 
        	
        else 
        	x -= 2;
        
        if (x > rightBound || x < leftBound) 
        	movingRight = !movingRight;
    }
    

    @Override
    public void drawOn(Graphics2D g) {
        if (sprite != null)
            g.drawImage(sprite, (int)x, (int)y, width, height, null);
        else {
            g.setColor(Color.RED);
            g.fillRect((int)x, (int)y, width, height);
        }
    }
}
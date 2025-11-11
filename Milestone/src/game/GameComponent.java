package game;

import entities.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.*;     
import javax.swing.Timer;     
import java.awt.image.BufferedImage;

/**
 * Class: GameComponent
 * Author: Agnes Wang, Shi Liang, Teddy Wei
 * <br>Purpose: Serves as the main game panel responsible for updating, drawing, and managing all game objects.
 * <br>Restrictions: Must be added to a JFrame to be displayed properly.
 * <br>Example:
 * <pre>
 *    JFrame frame = new JFrame("Game");
 *    GameComponent game = new GameComponent();
 *    frame.add(game);
 *    frame.setVisible(true);
 * </pre>
 */
public class GameComponent extends JComponent {
    private final java.util.List<GameObject> objects = new ArrayList<>();
    private final java.util.List<Collectible> collectibles = new ArrayList<>();
    private Player player;
    private Enemy enemy;
    private final Timer timer;
    private BufferedImage background;

    /**
     * ensures: Initializes player, enemy, collectibles, and timer for automatic game updates.
     */
    public GameComponent() {
    	try {
    	    background = ImageIO.read(getClass().getResource("/assets/sprites/background.png"));
    	} catch (Exception e) {
    	    System.err.println("Background image not found.");
    	}
    	
        player = new Player(this, 100, 400);
        enemy = new Enemy(this, 400, 400, 100, 700);
        objects.add(player);
        objects.add(enemy);

        collectibles.add(new Collectible(this, 250, 450, 10));
        collectibles.add(new Collectible(this, 200, 430, 20)); 
        collectibles.add(new Collectible(this, 300, 475, 20)); 

        addKeyListener(new GameController(player, collectibles));
        setFocusable(true);

        timer = new Timer(16, e -> gameLoop());
        timer.start();
    }


    /** 
     * ensures: Runs the game loop by updating state and repainting the screen. 
     */
    private void gameLoop() {
        updateState();
        repaint();
    } // gameLoop

    /**
     * ensures: Updates all game objects and checks for collisions. 
     */
    private void updateState() {
        for (GameObject obj : objects) {
        	obj.update();
        }    
        for (Collectible c : collectibles) {
        	c.update();
        }   
        handleCollisions();
    } // updateState

    /**
     * ensures: Detects collisions between player and enemy, reduces player lives, handles collectible pickups.
     */
    private void handleCollisions() {
        if (player.overlaps(enemy) && player.canBeHit()) {
            player.loseLife();
            player.markHit();
            SoundEffect.play("/assets/sprites/collision.wav");

            double tempVx = player.vx;
            double tempVy = player.vy;
            player.vx = -enemy.vx;
            player.vy = -enemy.vy;
            enemy.vx = -tempVx;
            enemy.vy = -tempVy;

            double overlapX = (player.x + player.width/2) - (enemy.x + enemy.width/2);
            double overlapY = (player.y + player.height/2) - (enemy.y + enemy.height/2);

            player.x += Math.signum(overlapX);
            player.y += Math.signum(overlapY);
            enemy.x -= Math.signum(overlapX);
            enemy.y -= Math.signum(overlapY);
        } else if (!player.overlaps(enemy)) {
            player.resetHit();
        }

        Iterator<Collectible> it = collectibles.iterator();
        while (it.hasNext()) {
            Collectible c = it.next();
            if (player.tryCollect(c)) {
                it.remove();
                SoundEffect.play("/assets/sprites/pickup.wav");
            }
        }

        if (collectibles.isEmpty()) {
            int groundY = getHeight() - 80;
            collectibles.add(new Collectible(this, 200, groundY - 30, 10));
            collectibles.add(new Collectible(this, 400, groundY - 30, 20));
            collectibles.add(new Collectible(this, 600, groundY - 60, 50));
        }

        if (!player.isAlive()) {
            timer.stop();
            JOptionPane.showMessageDialog(this, 
                "Game Over! Final Score: " + player.getScore(), 
                "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
    } // handleCollision



    /**
     * ensures: Adds a new collectible at a random position with random value.
     */
    private Collectible spawnCollectible() {
        double x = 50 + Math.random() * (getWidth() - 100);
        double y = 50 + Math.random() * (getHeight() - 100);
        int value = 10 + (int)(Math.random() * 50);
        return new Collectible(this, x, y, value);
    } // spawnCollectible

    
    /**
     * ensures: Draws all game objects, collectibles, background, and HUD.
     * @param g Graphics for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(210, 240, 255)); 
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        if (background != null) {
            g2.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        } else {
            g2.setColor(new Color(210, 240, 255));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

        for (Collectible c : collectibles) {
        	c.drawOn(g2);
        }
            
        for (GameObject obj : objects) {
        	obj.drawOn(g2);
        }
            
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString("Lives: " + player.getLives(), 20, 30);
        g2.drawString("Score: " + player.getScore(), 20, 55);
    } // paintComponent
} // end GameComponent

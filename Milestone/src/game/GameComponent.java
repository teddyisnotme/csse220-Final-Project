package game;

import entities.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.Timer;
import java.util.List;
import java.util.Iterator;

/**
 * Class: GameComponent
 * Main game panel handling game loop, drawing, objects, and levels.
 */
public class GameComponent extends JComponent {
    private final List<GameObject> objects = new ArrayList<>();
    private final List<Collectible> collectibles = new ArrayList<>();
    private Player player;
    private Enemy enemy;
    private final Timer timer;
    private BufferedImage background;
    private int currentLevel = 0;
    private LevelController levelController;

    public GameComponent() {
        try {
            background = ImageIO.read(getClass().getResource("/assets/sprites/background.png"));
        } catch (Exception e) {
            System.err.println("Background image not found.");
        }

        player = new Player(this, 100, 400);
        objects.add(player);

        loadLevel(0);

        addKeyListener(new GameController(player, collectibles) {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case java.awt.event.KeyEvent.VK_N -> loadNextLevel();
                    case java.awt.event.KeyEvent.VK_P -> loadPreviousLevel();
                    case java.awt.event.KeyEvent.VK_R -> {
                        if (!player.isAlive()) reloadCurrentLevel();
                    }
                }
            }
        });
        setFocusable(true);

        timer = new Timer(16, e -> gameLoop());
        timer.start();
    }

    private void gameLoop() {
        updateState();
        repaint();
    }

    private void updateState() {
        for (GameObject obj : objects) obj.update();
        for (Collectible c : collectibles) c.update();
        handleCollisions();
    }

    private void handleCollisions() {
        for (GameObject obj : objects) {
            if (obj instanceof Enemy enemy && player.overlaps(enemy) && player.canBeHit()) {
                player.loseLife();
                player.markHit();
                SoundEffect.play("/assets/sounds/collision.wav");

                double tempVx = player.vx;
                double tempVy = player.vy;
                player.vx = -enemy.vx;
                player.vy = -enemy.vy;
                enemy.vx = -tempVx;
                enemy.vy = -tempVy;

                double overlapX = (player.x + player.width / 2) - (enemy.x + enemy.width / 2);
                double overlapY = (player.y + player.height / 2) - (enemy.y + enemy.height / 2);

                player.x += Math.signum(overlapX);
                player.y += Math.signum(overlapY);
                enemy.x -= Math.signum(overlapX);
                enemy.y -= Math.signum(overlapY);
            }
        }

        Iterator<Collectible> it = collectibles.iterator();
        while (it.hasNext()) {
            Collectible c = it.next();
            if (player.tryCollect(c)) {
                it.remove();
                SoundEffect.play("/assets/sounds/pickup.wav");
            }
        }

        if (collectibles.isEmpty()) {
            // Player collected all items -> load next level automatically
        	timer.stop();
            int response = JOptionPane.showConfirmDialog(
                this,
                "You win Level " + (currentLevel + 1) + "! Proceed to next level?",
                "Level Complete",
                JOptionPane.YES_NO_OPTION
            );

            if (response == JOptionPane.YES_OPTION) {
                loadNextLevel();
            } else {
                System.exit(0);
            }

            timer.start();
        }

        if (!player.isAlive()) {
            timer.stop();
            int response = JOptionPane.showConfirmDialog(
                    this,
                    "Game Over! Final Score: " + player.getScore() + "\nPlay again?",
                    "Game Over",
                    JOptionPane.YES_NO_OPTION
                );

                if (response == JOptionPane.YES_OPTION) {
                    reloadCurrentLevel();
                    player.resetLives(); // add this helper below
                    timer.start();
                } else {
                    System.exit(0);
                }
        }
    }

    private void loadLevel(int levelIndex) {
        levelController = new LevelController(this, levelIndex);
        objects.clear();
        collectibles.clear();

        objects.add(player);
        objects.addAll(levelController.getPlatforms());
        objects.addAll(levelController.getEnemies());
        collectibles.addAll(levelController.getCollectibles());

        currentLevel = levelIndex;

        player.x = 100;
        player.y = 400;
        player.resetState();
    }

    private void reloadCurrentLevel() {
        player = new Player(this, 100, 400);
        loadLevel(currentLevel);
        for (KeyListener kl : getKeyListeners()) {
            removeKeyListener(kl);
        }
        addKeyListener(new GameController(player, collectibles) {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case java.awt.event.KeyEvent.VK_N -> loadNextLevel();
                    case java.awt.event.KeyEvent.VK_P -> loadPreviousLevel();
                    case java.awt.event.KeyEvent.VK_R -> {
                        if (!player.isAlive()) reloadCurrentLevel();
                    }
                }
            }
        });

        timer.start();
        requestFocusInWindow();
    }

    private void loadNextLevel() {
        int nextLevel = currentLevel + 1;
        if (nextLevel >= LevelController.TOTAL_LEVELS) nextLevel = 0; // wrap around
        loadLevel(nextLevel);
    }

    private void loadPreviousLevel() {
        int prevLevel = currentLevel - 1;
        if (prevLevel < 0) prevLevel = LevelController.TOTAL_LEVELS - 1;
        loadLevel(prevLevel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(210, 240, 255));
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (background != null) g2.drawImage(background, 0, 0, getWidth(), getHeight(), null);

        for (Collectible c : collectibles) c.drawOn(g2);
        for (GameObject obj : objects) obj.drawOn(g2);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString("Lives: " + player.getLives(), 20, 30);
        g2.drawString("Score: " + player.getScore(), 20, 55);
        g2.drawString("Level: " + (currentLevel + 1), 20, 80);
    }
    
    public List<GameObject> getObjects() {
        return objects;
    }
}

package game;

import entities.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;           // keep for collections
import javax.swing.Timer;     // ✅ correct Timer import
import java.awt.event.*;

/**
 * Class: GameComponent
 * @author Agnes Wang, Shi Liang, Teddy Wei
 * <br>Purpose: Serves as the main game panel responsible for updating, drawing, and managing all game objects.
 * <br>Restrictions: Must be added to a JFrame to be displayed properly.
 * <br>For example:
 * <pre>
 *    JFrame frame = new JFrame("Game");
 *    GameComponent game = new GameComponent();
 *    frame.add(game);
 *    frame.setVisible(true);
 * </pre>
 */
public class GameComponent extends JComponent {
    private final java.util.List<GameObject> objects = new ArrayList<>();
    private Player player;
    private Enemy enemy;
    private final Timer timer;

    /**
     * ensures: Initializes player, enemy, and timer for automatic game updates.
     */
    public GameComponent() {
        player = new Player(this, 100, 400);
        enemy = new Enemy(this, 400, 400, 100, 700);
        objects.add(player);
        objects.add(enemy);

        addKeyListener(new GameController(player));
        setFocusable(true);

        timer = new Timer(16, e -> gameLoop());
        timer.start();
    } // GameComponent

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
        for (GameObject obj : objects)
        	obj.update();
        handleCollisions();
    } // updateState

    /**
     * ensures: Detects collisions between player and enemy, reducing player lives when overlap occurs.
     */
    private void handleCollisions() {
        if (player.overlaps(enemy)) {
            player.loseLife();
        }
    } // handleCollisions

    /**
     * ensures: Draws all game objects and HUD (lives counter).
     * @param g Graphics context for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (GameObject obj : objects) obj.drawOn(g2);

        g2.setColor(Color.BLACK);
        g2.drawString("Lives: " + player.getLives(), 20, 20);
    } // paintComponent
} // end GameComponent
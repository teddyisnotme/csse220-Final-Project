package game;

import entities.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;           // keep for collections
import javax.swing.Timer;     // ✅ correct Timer import
import java.awt.event.*;

public class GameComponent extends JComponent {
    private final java.util.List<GameObject> objects = new ArrayList<>();
    private Player player;
    private Enemy enemy;
    private final Timer timer;   //using javax.swing.Timer now

    public GameComponent() {
        player = new Player(this, 100, 400);
        enemy = new Enemy(this, 400, 400, 100, 700);
        objects.add(player);
        objects.add(enemy);

        addKeyListener(new GameController(player));
        setFocusable(true);

        // ✅ Swing Timer constructor: Timer(int delay, ActionListener)
        timer = new Timer(16, e -> gameLoop());
        timer.start();  // ✅ works with javax.swing.Timer
    }

    private void gameLoop() {
        updateState();
        repaint();
    }

    private void updateState() {
        for (GameObject obj : objects)
        	obj.update();
        handleCollisions();
    }

    private void handleCollisions() {
        if (player.overlaps(enemy)) {
            player.loseLife();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (GameObject obj : objects) obj.drawOn(g2);

        g2.setColor(Color.BLACK);
        g2.drawString("Lives: " + player.getLives(), 20, 20);
    }
}
package game;

import entities.Player;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * The GameController listens for user keyboard input and
 * issues corresponding actions to the Player character.
 *
 * Left/Right arrows - movement
 * Up arrow - jump
 * Invalid keys are caught and logged without crashing the game.
 */
public class GameController extends KeyAdapter {
    private final Player player;

    public GameController(Player player) {
        this.player = player;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> player.moveLeft(true);
                case KeyEvent.VK_RIGHT -> player.moveRight(true);
                case KeyEvent.VK_UP -> player.jump();
                default -> throw new IllegalArgumentException("Invalid key pressed: " + e.getKeyCode());
            }
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) 
        	player.moveLeft(false);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) 
        	player.moveRight(false);
    }
}
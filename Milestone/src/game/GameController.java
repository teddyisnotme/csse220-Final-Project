package game;

import entities.Player;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Class: GameController
 * @author Agnes Wang, Shi Liang, Teddy Wei
 * <br>Purpose: Handles keyboard input to control the player's movement and actions.
 * <br>Restrictions: Only LEFT, RIGHT, and UP keys are valid; other keys are caught safely.
 * <br>For example:
 * <pre>
 *    GameController controller = new GameController(player);
 *    component.addKeyListener(controller);
 * </pre>
 */
public class GameController extends KeyAdapter {
    private final Player player;
    
    /**
     * ensures: Links this controller to a specific player object.
     * @param player reference to the player being controlled
     */
    public GameController(Player player) {
        this.player = player;
    } // GameController

    /**
     * ensures: Handles key press events for left, right, and jump actions.
     * @param e KeyEvent representing the key pressed
     */
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
    } // keyPressed

    /**
     * ensures: Handles key release events to stop player movement.
     * @param e KeyEvent representing the key released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) 
        	player.moveLeft(false);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) 
        	player.moveRight(false);
    } //keyReleased
} // end GameController
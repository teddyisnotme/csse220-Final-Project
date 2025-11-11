package game;

import entities.Player;
import entities.Collectible;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Class: GameController
 * @author Agnes Wang, Shi Liang, Teddy Wei
 * <br>Purpose: Handles keyboard input to control the player's movement and actions, including item collection.
 */
public class GameController extends KeyAdapter {
    private final Player player;
    private final List<Collectible> collectibles;

    /**
     * ensures: Links controller to player and collectible list.
     * @param player reference to the player being controlled
     * @param collectibles reference to collectible items in the game
     */
    public GameController(Player player, List<Collectible> collectibles) {
        this.player = player;
        this.collectibles = collectibles;
    }

    /**
     * ensures: Handles key press events for movement, jump, and collection. 
     */
    @Override
    public void keyPressed(KeyEvent e) {
        try {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> player.moveLeft(true);
                case KeyEvent.VK_RIGHT -> player.moveRight(true);
                case KeyEvent.VK_UP -> player.jump();
                case KeyEvent.VK_DOWN -> {
                    player.setPressingDown(true);
                    for (Collectible c : collectibles) {
                        if (player.tryCollect(c)) {
                            SoundEffect.play("/assets/sounds/pickup.wav");
                            c.collect();
                        }
                    }
                }
                default -> throw new IllegalArgumentException("Invalid key pressed: " + e.getKeyCode());
            }
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
        }
    } // keyPressed

    /** 
     * ensures: Handles key release events to stop movement and collection. 
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) player.moveLeft(false);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) player.moveRight(false);
        if (e.getKeyCode() == KeyEvent.VK_DOWN) player.setPressingDown(false);
    } // keyReleased
} // end GameController

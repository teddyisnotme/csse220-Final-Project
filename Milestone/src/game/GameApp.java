package game;

import javax.swing.*;
import java.awt.*;

/**
 * Class: GameApp
 * @author Agnes Wang, Shi Liang, Teddy Wei
 * <br>Purpose: Serves as the main entry point for launching the Block Build Game application.
 * <br>This class initializes the main game window and attaches the {@link GameComponent}
 * to display and handle game logic and rendering.
 * <br>Restrictions: Must be run in an environment that supports Java Swing (GUI applications).
 * <br>For example:
 * <pre>
 *    // Launches the Block Build Game
 *    GameApp.main(new String[]{});
 * </pre>
 */

public class GameApp {
	
	/**
     * ensures: Creates and displays the main JFrame for the Block Build Game application.
     * <br>Uses Swing's event-dispatch thread (EDT) to safely build the UI.
     * <br>The game window includes a {@link GameComponent} that handles game rendering and input.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Block Build Game");
            GameComponent component = new GameComponent();
            frame.add(component, BorderLayout.CENTER);
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    } // main
} // end GameApp 
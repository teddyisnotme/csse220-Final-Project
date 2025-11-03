package game;

import javax.swing.*;
import java.awt.*;

public class GameApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Block Build Game");
            GameComponent component = new GameComponent();
            frame.add(component, BorderLayout.CENTER);
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
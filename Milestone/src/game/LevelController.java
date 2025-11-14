package game;

import entities.*;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Class: LevelController
 * Handles loading platforms, enemies, and collectibles for a specific level.
 */
public class LevelController {
    public static final int TOTAL_LEVELS = 3;

    private final List<Platform> platforms = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Collectible> collectibles = new ArrayList<>();

    public LevelController(GameComponent game, int levelIndex) {
        loadPlatformsFromFile(game, "level" + (levelIndex + 1) + ".txt");

        // Add enemies for demonstration (can be customized per level)
        enemies.add(new Enemy(game, 400, 400, 100, 700));
        if (levelIndex > 0) enemies.add(new Enemy(game, 200, 300, 150, 600));

        // Add collectibles on top of platforms
        for (Platform p : platforms) {
            collectibles.add(new Collectible(game, p.getX() + p.getWidth() / 2 - 10, p.getY() - 25, 10 + levelIndex * 5));
        }
    }

    private void loadPlatformsFromFile(GameComponent game, String fileName) {
        try (InputStream in = getClass().getResourceAsStream("/assets/levels/" + fileName);
             Scanner scanner = new Scanner(in)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);
                int w = Integer.parseInt(parts[2]);
                int h = Integer.parseInt(parts[3]);
                platforms.add(new Platform(game, x, y, w, h));
            }
        } catch (Exception e) {
            System.err.println("Error loading level file: " + fileName);
        }
    }

    public List<Platform> getPlatforms() { return platforms; }
    public List<Enemy> getEnemies() { return enemies; }
    public List<Collectible> getCollectibles() { return collectibles; }
}

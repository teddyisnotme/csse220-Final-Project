package game;

import entities.*;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Class: LevelController
 * Author: Agnes Wang
 * <br>Purpose: Handles loading and managing the platforms, enemies, and collectibles for a specific level.
 * Supports multiple levels stored as text files, with configurable enemies and collectible placement.
 * <br>Restrictions: Level files must exist in /assets/levels/ and be formatted correctly as "x,y,width,height" per line for platforms.
 * <br>Example:
 * <pre>
 *    LevelController level = new LevelController(gameComponent, 0);
 *    List&lt;Platform&gt; platforms = level.getPlatforms();
 *    List&lt;Enemy&gt; enemies = level.getEnemies();
 *    List&lt;Collectible&gt; items = level.getCollectibles();
 * </pre>
 */
public class LevelController {
    public static final int TOTAL_LEVELS = 3;

    private final List<Platform> platforms = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Collectible> collectibles = new ArrayList<>();

    /**
     * ensures: Loads all platforms, enemies, and collectibles for the given level index.
     * Adds default enemies and places collectibles on top of platforms.
     * @param game the GameComponent instance that the level belongs to
     * @param levelIndex the index of the level to load (0-based)
     */
    public LevelController(GameComponent game, int levelIndex) {
        loadPlatformsFromFile(game, "level" + (levelIndex + 1) + ".txt");

        enemies.add(new Enemy(game, 400, 400, 100, 700));
        if (levelIndex > 0) enemies.add(new Enemy(game, 200, 300, 150, 600));

        for (Platform p : platforms) {
            collectibles.add(new Collectible(game, p.getX() + p.getWidth() / 2 - 10, p.getY() - 25, 10 + levelIndex * 5));
        }
    }

    /**
     * ensures: Loads platform data from a text file and populates the platforms list.
     * Each line in the file should contain: x,y,width,height
     * @param game the GameComponent instance for creating Platform objects
     * @param fileName the name of the level file to load
     */
    private void loadPlatformsFromFile(GameComponent game, String fileName) {
    	try (InputStream in = getClass().getResourceAsStream("/assets/levels/" + fileName);
    	         Scanner scanner = new Scanner(in)) {

    	        while (scanner.hasNextLine()) {
    	            String line = scanner.nextLine().trim();
    	            if (line.isEmpty() || line.startsWith("#")) continue;

    	            String[] parts = line.split(",");
    	            String type = parts[0].trim().toUpperCase();

    	            switch (type) {
    	                case "PLATFORM" -> {
    	                    double x = Double.parseDouble(parts[1]);
    	                    double y = Double.parseDouble(parts[2]);
    	                    int w = Integer.parseInt(parts[3]);
    	                    int h = Integer.parseInt(parts[4]);
    	                    platforms.add(new Platform(game, x, y, w, h));
    	                }
    	                case "ENEMY" -> {
    	                    double x = Double.parseDouble(parts[1]);
    	                    double y = Double.parseDouble(parts[2]);
    	                    double left = Double.parseDouble(parts[3]);
    	                    double right = Double.parseDouble(parts[4]);
    	                    enemies.add(new Enemy(game, x, y, left, right));
    	                }
    	                default -> System.err.println("Unknown type in level file: " + type);
    	            }
    	        }
    	    } catch (Exception e) {
    	        System.err.println("Error loading level file: " + fileName);
    	    }
    }

    public List<Platform> getPlatforms() { return platforms; }
    public List<Enemy> getEnemies() { return enemies; }
    public List<Collectible> getCollectibles() { return collectibles; }
}

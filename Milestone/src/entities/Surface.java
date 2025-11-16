package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Class: Surface
 * Author: Agnes Wang
 * <br>Purpose: Represents a moving or static platform (surface) in the game world.
 * Surfaces can move horizontally or vertically between set bounds, and can be collided with by players or enemies.
 * <br>Restrictions: Must be updated and drawn within a game loop to function correctly.
 * <br>Example:
 * <pre>
 *    Surface s = new Surface(100, 300, 200, 20, 2, true, 50, 400);
 *    s.update();
 *    s.draw(g);  
 * </pre>
 */
public class Surface {

    private int x, y;
    private final int w, h;
    private final int minBound, maxBound;
    private final int speed;
    private final boolean horizontal;
    private int dir = 1;

    /**
     * ensures: Creates a new Surface at the specified position, size, speed, and movement bounds.
     * @param x the x-coordinate of the top-left corner
     * @param y the y-coordinate of the top-left corner
     * @param w the width of the surface
     * @param h the height of the surface
     * @param speed the speed of movement
     * @param horizontal true if surface moves horizontally, false for vertical
     * @param minBound the minimum x or y bound for movement
     * @param maxBound the maximum x or y bound for movement
     */
    public Surface(int x, int y, int w, int h, int speed,
                   boolean horizontal, int minBound, int maxBound) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.speed = speed;
        this.horizontal = horizontal;
        this.minBound = minBound;
        this.maxBound = maxBound;
    }

    public void update() {
        if (speed == 0) return;

        if (horizontal) {
            x += speed * dir;
            if (x < minBound || x + w > maxBound) dir *= -1;
        } else {
            y += speed * dir;
            if (y < minBound || y + h > maxBound) dir *= -1;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(120, 80, 40));
        g.fillRect(x, y, w, h);
    }
}

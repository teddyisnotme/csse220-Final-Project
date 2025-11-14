package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Surface {

    private int x, y;
    private final int w, h;
    private final int minBound, maxBound;
    private final int speed;
    private final boolean horizontal;
    private int dir = 1;

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

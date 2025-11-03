package game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class GameObject {
    protected double x;
    protected double y;
    protected double vx;
    protected double vy;
    protected final GameComponent game;

    protected int width;
    protected int height;

    public GameObject(GameComponent game, double x, double y, int w, int h) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public abstract void update();
    public abstract void drawOn(Graphics2D g);

    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    public boolean overlaps(GameObject other) {
        return getBounds().intersects(other.getBounds());
    }
}
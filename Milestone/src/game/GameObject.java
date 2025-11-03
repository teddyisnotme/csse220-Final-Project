package game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Class: GameObject
 * @author Agnes Wang, Shi Liang, Teddy Wei
 * <br>Purpose: Serves as an abstract base class for all interactive elements in the game,
 * such as the player and enemies. It defines core properties like position, velocity, size,
 * and methods for updating and drawing objects on screen.
 * <br>Restrictions: Must be subclassed and must implement {@code update()} and {@code drawOn(Graphics2D)}.
 * <br>For example:
 * <pre>
 *    // Example subclass: Player
 *    public class Player extends GameObject {
 *        public Player(GameComponent game, double x, double y) {
 *            super(game, x, y, 40, 40);
 *        }
 *
 *        public void update() {
 *            // Define movement and gravity updates here
 *        }
 *
 *        public void drawOn(Graphics2D g) {
 *            // Draw player sprite or rectangle here
 *            g.setColor(Color.GREEN);
 *            g.fillRect((int)x, (int)y, width, height);
 *        }
 *    }
 * </pre>
 */
public abstract class GameObject {
    protected double x;
    protected double y;
    protected double vx;
    protected double vy;
    protected final GameComponent game;

    protected int width;
    protected int height;

    /**
     * ensures: Initializes the object's position, dimensions, and reference to the main game component.
     * @param game reference to the GameComponent controlling the world
     * @param x initial x-coordinate of the object
     * @param y initial y-coordinate of the object
     * @param w width of the object in pixels
     * @param h height of the object in pixels
     */
    public GameObject(GameComponent game, double x, double y, int w, int h) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    } // GameObject

    /**
     * ensures: Updates the object's position, state, or animation each frame.
     * Must be implemented by all subclasses.
     */
    public abstract void update(); // update
    
    /**
     * ensures: Draws the object on screen using the specified Graphics2D context.
     * Must be implemented by all subclasses.
     * @param g Graphics2D object used for rendering
     */
    public abstract void drawOn(Graphics2D g); // drawOn
    
    /**
     * ensures: Returns the rectangular bounding box of this object for collision detection.
     * @return Rectangle2D.Double representing the object's current bounds
     */
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(x, y, width, height);
    } // getBounds

    /**
     * ensures: Returns true if this object overlaps another object based on their bounding boxes.
     * @param other another GameObject to check for intersection
     * @return true if the two objects overlap, false otherwise
     */
    public boolean overlaps(GameObject other) {
        return getBounds().intersects(other.getBounds());
    } // overlaps
} // end GameObject
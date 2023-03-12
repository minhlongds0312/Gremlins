package gremlins;
import processing.core.PApplet;
public class Fireball extends Projectile {
    public char dir = 'n';
    /**
     * constructs a Fireball
     * @param x x coordinate
     * @param y y coordinate
     */
    public Fireball(int x, int y) {
        super(x, y);
    }
    /**
     * set the direction of the fireballs to the right
     */
    public void Right() {
        this.x += 4;
    }
    /**
     * set the direction of the fireballs to the left
     */
    public void Left(){
        this.x -= 4;
    }
    /**
     * set the direction of the fireballs upwards
     */
    public void Up() {
        this.y -= 4;
    }
    /**
     * set the direction of the fireballs downwards
     */
    public void Down() {
        this.y += 4;
    }
        
}
    

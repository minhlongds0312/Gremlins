package gremlins;
import processing.core.PApplet;
public class Fireball extends Projectile {
    public char dir = 'n';
    public Fireball(int x, int y) {
        super(x, y);
    }

    public void Right() {
        this.x += 4;
    }
    public void Left(){
        this.x -= 4;
    }
    public void Up() {
        this.y -= 4;
    }
    public void Down() {
        this.y += 4;
    }
        
}
    

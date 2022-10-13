package gremlins;
import processing.core.PImage;
import processing.core.PApplet;

public class life {
    //constructor of x,y, a sprite 
    public int x;
    public int y;
    public PImage sprite;
    public life(int x, int y, PImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }
    public void draw(PApplet app) {
        app.image(this.sprite, this.x, this.y);
    }
}

package gremlins;
import processing.core.PImage;
import processing.core.PApplet;

public abstract class Char {
    protected int size;
    //character's coordinate
    protected int x;
    protected int y;
    protected int speed;
    //character's sprite
    protected PImage sprite;
    //construct
    public Char(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void draw(PApplet app) {
        // The image() method is used to draw PImages onto the screen.
        app.image(this.sprite, this.x, this.y);
    }
    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    //update sprite every frame with tick()
    public abstract void tick();

}   

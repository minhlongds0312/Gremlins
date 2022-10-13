package gremlins;
import processing.core.PImage;
import processing.core.PApplet;

public abstract class Projectile {
    protected int x;
    protected int y;
    protected PImage sprite;

    public Projectile(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void draw(PApplet app) {
        // The image() method is used to draw PImages onto the screen.
        // The first argument is the image, the second and third arguments are coordinates
        app.image(this.sprite, this.x, this.y);
    }
    //set sprite
    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }
    // public abstract void Up();
    // public abstract void Down();
    // public abstract void Left();
    // public abstract void Right();
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
}

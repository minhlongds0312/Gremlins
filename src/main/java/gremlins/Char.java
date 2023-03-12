package gremlins;
import processing.core.PImage;
import processing.core.PApplet;

public abstract class Char {
    protected int size;
    protected int x;
    protected int y;
    protected int speed;
    protected PImage sprite;
    /**
     * construct the character
     * @param x x coordinate
     * @param y y coordinate
     */
    public Char(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * display the character
     * @param app where the character is displayed
     */
    public void draw(PApplet app) {
        // The image() method is used to draw PImages onto the screen.
        app.image(this.sprite, this.x, this.y);
    }
    /**
     * change/set the character's sprite
     * @param sprite the PIMage for the character's sprite
     */
    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }
    /**
     * change/set the character's speed
     * @param speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    /**
     * updates the character sprite every frame
     */
    public abstract void tick();

}   

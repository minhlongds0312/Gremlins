package gremlins;
import processing.core.PImage;
import processing.core.PApplet;

public class life {
    public int x;
    public int y;
    public PImage sprite;
    /**
     * construct the life tile
     * @param x x coordinate
     * @param y y coordinate
     * @param sprite the PImage for the life's sprite
     */
    public life(int x, int y, PImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }
    /**
     * display the life tile
     * @param app where the life tile is displayed
     */
    public void draw(PApplet app) {
        app.image(this.sprite, this.x, this.y);
    }
}

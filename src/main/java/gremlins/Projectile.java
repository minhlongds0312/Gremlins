package gremlins;
import processing.core.PImage;
import processing.core.PApplet;

public abstract class Projectile {
    protected int x;
    protected int y;
    protected PImage sprite;

    /**
     * construct the projectile
     * @param x x coordinate
     * @param y y coordinate
     */
    public Projectile(int x, int y){
        this.x = x;
        this.y = y;
    }
    /**
     * display the projectile
     * @param app where the projectile is displayed
     */
    public void draw(PApplet app) {
        app.image(this.sprite, this.x, this.y);
    }
    /**
     * change/set the projectile's sprite
     * @param sprite the PIMage for the projectile's sprite
     */
    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }
    /**
     * get the x coordinate of the projectile   
     */
    public int getX(){
        return this.x;
    }
    /**
     * get the y coordinate of the projectile
     */
    public int getY(){
        return this.y;
    }
}

package gremlins;
import processing.core.PApplet;
import processing.core.PImage;

public class portal {
    public int x;
    public int y;
    public PImage sprite;
    /**
     * construct the portal tile
     */
    public portal(){
    }
    /**
     * set the x coordinate of the portal
     * @param x the x coordinate of the portal
     */
    public void setX(int x){
        this.x = x;
    }
    /**
     * set the y coordinate of the portal
     * @param y the y coordinate of the portal
     */
    public void setY(int y){
        this.y = y;
    }
    /**
     * get the x coordinate of the portal
     * @return x coordinate
     */
    public int getX(){
        return this.x;
    }
    /**
     * get the y coordinate of the portal
     * @return y coordinate
     */
    public int getY(){
        return this.y;
    }
    /**
     * set the sprite of the portal
     * @param sprite
     */
    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }
    /**
     * display the portal
     * @param app where the portal is displayed
     */
    public void draw(PApplet app){
        app.image(this.sprite, this.x, this.y);
    }
}
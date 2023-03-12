package gremlins;
import processing.core.PImage;
import processing.core.PApplet;

public abstract class wall{
    protected int x;
    protected int y;
    protected PImage sprite;
    protected boolean destroyed;
    protected int startTime;
    public int counter;
    public static PImage[] sprites = new PImage[4];
    
    /**
     * Constructor for objects of class wall
     * @param x the x coordinate of the wall
     * @param y the y coordinate of the wall
     */
    public wall(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    /**
     * Sets the sprite of the wall
     * @param sprite the sprite of the wall
     */
    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }
    public void draw(PApplet app){
            app.image(this.sprite, this.x, this.y);
            //when wall is destroyed, change sprite to destroyed sprite after 150ms (4 frames at 60fps)
            if(this.destroyed){
                if((App.currenttime - startTime) > 150){
                    startTime = App.currenttime; 
                    if(counter < 4){
                        this.setSprite(sprites[counter]); 
                    }
                    counter++;
                }
            }   
        }
    
    /**
     * Gets the x coordinate of the wall
     * @return the x coordinate of the wall
     */
    public int getX(){
        return this.x;
    }
    /**
     * Gets the y coordinate of the wall
     * @return the y coordinate of the wall
     */
    public int getY(){
        return this.y;
    }
}
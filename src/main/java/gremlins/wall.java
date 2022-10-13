package gremlins;
import processing.core.PImage;
import processing.core.PApplet;

public abstract class wall extends PApplet {
    protected int x;
    protected int y;
    protected PImage sprite;
    protected int size;
    protected boolean destroyed;
    protected int startTime;
    public int counter;
    public static PImage[] sprites = new PImage[4];
    
    public wall(int x, int y){
        this.x = x;
        this.y = y;
        //load sprites into array

    }
    
    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }
    public void draw(PApplet app){
            app.image(this.sprite, this.x, this.y);
            if(this.destroyed){
                if((App.currenttime - startTime) > 400){
                    startTime = App.currenttime; 
                    if(counter < 4){
                        this.setSprite(sprites[counter]); 
                    }
                    counter++;
                }
            }   
        }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
}
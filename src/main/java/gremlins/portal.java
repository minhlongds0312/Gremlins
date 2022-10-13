package gremlins;
import processing.core.PApplet;
import processing.core.PImage;

public class portal {
    public int x;
    public int y;
    public PImage sprite;
    public portal(){
        this.x = x;
        this.y = y;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }
    public void draw(PApplet app){
        app.image(this.sprite, this.x, this.y);
    }
}
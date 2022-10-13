package gremlins;
import processing.core.PImage;
import processing.core.PApplet;

public class stonewall extends wall {
    public PImage sprite;
    protected PImage[] destroyed = new PImage[4];

    public stonewall(int x, int y){
        super(x, y);
    }
    public boolean wallExist(){
        return true;
    }
}
    

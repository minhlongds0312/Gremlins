package gremlins;
import processing.core.PApplet;

public class Wizard extends Char {
    public char direction = 'n';
    public char lastDirection;
    public int shootTimer;
    public boolean shoot = true;
    //construct
    public Wizard() {
        super(0, 0);
        this.speed = 2;
    }
    //update sprite every frame
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public void moveUp(){
        this.direction = 'u';
    }
    public void moveDown(){
        this.direction = 'd';
    }
    public void moveLeft(){
        this.direction = 'l';
    }
    public void moveRight(){
        this.direction = 'r';
    }
    public void stop(){
        this.direction = 'n';
    }
    public void completelyStop(){
        //return to the previous location
        this.lastDirection = this.direction;
        if(this.lastDirection == 'u'){
            this.y += this.speed;
        }
        else if(this.lastDirection == 'd'){
            this.y -= this.speed;
        }
        else if(this.lastDirection == 'l'){
            this.x += this.speed;
        }
        else if(this.lastDirection == 'r'){
            this.x -= this.speed;
        }}

    public void tick() {
        if (this.direction == 'u') {
            this.y -= speed;
        }
        if (this.direction == 'd') {
            this.y += speed;
        }
        if (this.direction == 'l') {
            this.x -= speed;
        }
        if (this.direction == 'r') {
            this.x += speed;
        }
        if (this.direction == 'n'){
            this.x = this.x;
            this.y = this.y;
        }
    }
}
    
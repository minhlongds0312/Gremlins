package gremlins;
import processing.core.PApplet;

public class Wizard extends Char {
    public char direction = 'n';
    public char lastDirection;
    public int shootTimer;
    public boolean shoot = true;
    //construct
    /**
     * constructs a wizard
     */
    public Wizard() {
        super(0, 0);
        this.speed = 2;
    }
    //update sprite every frame
    /**
     * set the x coordinate
     * @param x x coordinate
     */
    public void setX(int x){
        this.x = x;
    }
    /**
     * set the y coordinate
     * @param y y coordinate
     */
    public void setY(int y){
        this.y = y;
    }
    /**
     * get the x coordinate
     * @return x coordinate
     */
    public int getX(){
        return this.x;
    }
    /**
     * get the y coordinate
     * @return y coordinate
     */
    public int getY(){
        return this.y;
    }
    /**
     * move the wizard according to its direction
     */
    public void moveUp(){
        this.direction = 'u';
    }
    /**
     * move the wizard according to its direction
     */
    public void moveDown(){
        this.direction = 'd';
    }
    /**
     * move the wizard according to its direction
     */
    public void moveLeft(){
        this.direction = 'l';
    }
    /**
     * move the wizard according to its direction
     */
    public void moveRight(){
        this.direction = 'r';
    }
    //stop moving when key is released
    /**
     * stop the wizard from moving
     */
    public void stop(){
        this.direction = 'n';
    }
    //stop moving when collision occurs
    /**
     * stop the wizard from moving when it hits a wall
     */
    public void completelyStop(){
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
    /**
     * update the wizard's position
     */
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
    
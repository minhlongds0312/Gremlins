package gremlins;
import processing.core.PApplet;
import java.util.Random;
import java.util.ArrayList;
public class gremlin extends Char {
    public char direction = 'n';
    public char lastDirection;
    //arraylist of directions
    public ArrayList<Character> directions = new ArrayList<Character>();
    public boolean crashed = false;
    public boolean shoot = false;
    public int timer;
    public static int cd;

    //construct
    public gremlin() {
        super(0, 0);
        this.speed = 1;
        this.directions = new ArrayList<Character>();
        //append directions
        this.directions.add('u');
        this.directions.add('d');
        this.directions.add('l');
        this.directions.add('r');
        this.direction = 'l';
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
        if((App.currenttime - timer) > cd){
            timer = App.currenttime; 
            this.shoot = true;
        }
    }
}
    
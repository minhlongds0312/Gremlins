package gremlins;

public class slime extends Projectile {
    public char dir = 'n';
    /**
     * constructs a slime
     * @param x x coordinate
     * @param y y coordinate
     */
    public slime(int x, int y) {
        super(x, y);
    }

    /**
     * set the direction of the slime accordingly
     */
    public void tick(){
        if(this.dir == 'u'){
            this.y -= 4;
        }
        else if(this.dir == 'd'){
            this.y += 4;
        }
        else if(this.dir == 'l'){
            this.x -= 4;
        }
        else if(this.dir == 'r'){
            this.x += 4;
        }
    }

}

package gremlins;

public class slime extends Projectile {
    public char dir = 'n';
    public slime(int x, int y) {
        super(x, y);
    }

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

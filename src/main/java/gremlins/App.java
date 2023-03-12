package gremlins;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;
import java.util.ArrayList;



import java.util.Random;
import java.io.*;


public class App extends PApplet {

    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    public static final int BOTTOMBAR = 60;
    public static final int FPS = 60;
    public static int currenttime;
    //public static int gremlinShot;

    //public PImage[] sprites = new PImage[4];
    //string array to load level content
    public String[] level = new String[1000];
    public static final Random randomGenerator = new Random();
    public PFont f;
    public String configPath;
    public int lvlcode;
    public float wizardCd;
    public Wizard wizard;
    public portal p;
    
    public char direction;
    public ArrayList<brickwall> brickwalls = new ArrayList<brickwall>();
    public ArrayList<stonewall> stonewalls = new ArrayList<stonewall>();
    public ArrayList<gremlin> gremlins = new ArrayList<gremlin>();
    public ArrayList<life> lives = new ArrayList<life>();
    public ArrayList<Fireball> fireballs = new ArrayList<Fireball>();
    public ArrayList<slime> slimes = new ArrayList<slime>();
    public ArrayList<powerUp> pu = new ArrayList<powerUp>();
    public ArrayList<portal> tp = new ArrayList<portal>();
    public boolean teleportAvailable = true;
    public int teleportTimer = 0;
    public boolean endgame = false;
    public int powerUpTimer = 0;
    public boolean powerUpExist = false;
    public boolean powerUpActive = false;
    public life powUpIcon;
    public int hit = 0;
    public ArrayList<Fireball> special = new ArrayList<Fireball>();
    public App() {
        this.configPath = "config.json";
        this.wizard = new Wizard();
        this.wizard.size = SPRITESIZE;
        this.direction = 'l';
        this.lvlcode = 0;
}
    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public  void setup() {
        frameRate(FPS);
        // Load images during setup
        this.wizard.setSprite(loadImage("src/main/resources/gremlins/wizard0.png"));
        JSONObject conf = loadJSONObject(new File(this.configPath));
        for(int i = 0; i < conf.getInt("lives"); i++){
            life l = new life(400+i*20, 680, loadImage("src/main/resources/gremlins/wizard0.png"));
            lives.add(l);
        }
        this.powUpIcon = new life(350, 680, loadImage("src/main/resources/gremlins/powerIcon.png"));  
        gremlin.cd = conf.getJSONArray("levels").getJSONObject(lvlcode).getInt("enemy_cooldown")*1000;
        this.wizardCd = conf.getJSONArray("levels").getJSONObject(lvlcode).getFloat("wizard_cooldown")*1000;
        level = loadStrings(conf.getJSONArray("levels").getJSONObject(lvlcode).getString("layout"));
        for(int i = 0; i < level.length; i++){
            for(int j = 0; j < level[i].length(); j++){
                //read walls placement
                if(level[i].charAt(j) == 'B'){
                    brickwall bw = new brickwall(j*SPRITESIZE, i*SPRITESIZE);
                    bw.setSprite(loadImage("src/main/resources/gremlins/brickwall.png"));
                    this.brickwalls.add(bw);
                    for(int w = 0; w < 4; w++){
                        wall.sprites[w] = loadImage("src/main/resources/gremlins/brickwall_destroyed" + w + ".png");
                    }
                    bw.startTime = millis();
                }
                if(level[i].charAt(j) == 'X'){
                    stonewall sw = new stonewall(j*SPRITESIZE, i*SPRITESIZE);
                    sw.setSprite(loadImage("src/main/resources/gremlins/stonewall.png"));
                    this.stonewalls.add(sw);
                }
                //read wizard placement
                if(level[i].charAt(j) == 'W'){
                    this.wizard.x = j*SPRITESIZE;
                    this.wizard.y = i*SPRITESIZE;
                }
                //read gremlin placement
                if(level[i].charAt(j) == 'G'){
                    gremlin g = new gremlin();
                    g.x = j*SPRITESIZE;
                    g.y = i*SPRITESIZE;
                    g.setSprite(loadImage("src/main/resources/gremlins/gremlin.png"));
                    this.gremlins.add(g);
                    g.timer = 0;
                }
                //read portal placement
                if(level[i].charAt(j) == 'E'){
                    this.p = new portal();
                    this.p.x = j*SPRITESIZE;
                    this.p.y = i*SPRITESIZE;
                    this.p.setSprite(loadImage("src/main/resources/gremlins/portal.png"));
                }
                //read teleport placement
                if(level[i].charAt(j) == 'T'){
                    portal t = new portal();
                    t.setSprite(loadImage("src/main/resources/gremlins/teleportal.png"));
                    t.x = j*SPRITESIZE;
                    t.y = i*SPRITESIZE;
                    this.tp.add(t);
                }
                //read powerup placement. This powerup will give the wizard a special attack by pressing E. When shot, 
                //it will create an energy blast that destroys all brickwalls on its path, as well as eliminating any gremlins caught within.
                if(level[i].charAt(j) == 'P'){
                    powerUp p = new powerUp();
                    p.x = j*SPRITESIZE;
                    p.y = i*SPRITESIZE;
                    p.setSprite(loadImage("src/main/resources/gremlins/powerUp.png"));
                    this.pu.add(p);
                }
            }
        }
        PFont f = createFont("src/main/resources/gremlins/NemoyBold.otf", 20);
        textFont(f, 20);
        print(this.wizardCd);
    }

    /**
     * Receive key pressed signal from the keyboard.
    */
    public void keyPressed() {
        // Left: 37
        // Up: 38
        // Right: 39
        // Down: 40

        if (this.keyCode == 37) {
            this.wizard.setSprite(loadImage("src/main/resources/gremlins/wizard0.png"));
            this.wizard.moveLeft();
            this.direction = 'l';
        } else if (this.keyCode == 38) {
            this.wizard.setSprite(loadImage("src/main/resources/gremlins/wizard2.png"));
            this.wizard.moveUp();
            this.direction = 'u';
        }
        if (this.keyCode == 39){
            this.wizard.setSprite(loadImage("src/main/resources/gremlins/wizard1.png"));
            this.wizard.moveRight();
            this.direction = 'r';
        }
        if (this.keyCode == 40){
            this.wizard.setSprite(loadImage("src/main/resources/gremlins/wizard3.png"));
            this.wizard.moveDown();
            this.direction = 'd';
        }
        if (this.keyCode == 32 && this.wizard.shoot){
            this.wizard.shoot = false;
            this.wizard.shootTimer = millis();
            Fireball fb = new Fireball(this.wizard.getX(), this.wizard.getY());
            fb.setSprite(loadImage("src/main/resources/gremlins/fireball.png"));
            if(this.direction == 'u'){
                fb.dir = 'u';
            }
            if(this.direction == 'd'){
                fb.dir = 'd';
            }
            if(this.direction == 'l'){
                fb.dir = 'l';
            }
            if(this.direction == 'r'){
                fb.dir = 'r';
            }
            this.fireballs.add(fb);
            for(Fireball f : this.fireballs){
                if(f == null){
                    f = fb;
                }
            }
        }
        if(keyPressed && this.endgame){
            this.endgame = false;
            this.lvlcode=0;
            reset();
            this.setup();
            loop();
        }
        //press E to shoot special fireball
        if(this.keyCode == 69 && this.powerUpActive){
            this.powerUpActive = false;
            Fireball fb = new Fireball(this.wizard.getX(), this.wizard.getY());
            fb.setSprite(loadImage("src/main/resources/gremlins/powerIcon.png"));
            this.special.add(fb);
            if(this.direction == 'u'){
                fb.dir = 'u';
            }
            if(this.direction == 'd'){
                fb.dir = 'd';
            }
            if(this.direction == 'l'){
                fb.dir= 'l';
            }
            if(this.direction == 'r'){
                fb.dir = 'r';
            }
            for(Fireball f : this.special){
                if(f == null){
                    f = fb;
                }
            }
            this.powerUpTimer = App.currenttime;
        }

    }
    
    /**
     * Receive key released signal from the keyboard.
    */
    public void keyReleased(){ 
        if (this.keyCode == 37 ) {
            this.wizard.stop();
        }
        if (this.keyCode == 38) {
            this.wizard.stop();
        }
        if (this.keyCode == 39){
            this.wizard.stop();
        }
        if (this.keyCode == 40){
            this.wizard.stop();
        }
        //stops at next whole tile
        if(this.wizard.x % SPRITESIZE != 0){
            if(this.direction == 'r'){
                this.wizard.x += SPRITESIZE - this.wizard.x % SPRITESIZE;
            }
            if(this.direction == 'l'){
                this.wizard.x -= this.wizard.x % SPRITESIZE;
            }
        }
        if(this.wizard.y % SPRITESIZE != 0){
            if(this.direction == 'u'){
                this.wizard.y -= this.wizard.y % SPRITESIZE;
            }
            if(this.direction == 'd'){
                this.wizard.y += SPRITESIZE - this.wizard.y % SPRITESIZE;
            }
        }
    }
    


    /**
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
        App.currenttime = millis();
        //set background
        background(255, 255, 204);
        //display the lives
        for(life i : this.lives){
            i.draw(this);
        }
        //draw the portal
        this.p.draw(this);
        for(portal p : this.tp){
            p.draw(this);
        }
        //draw the wizard
        this.wizard.tick();
        this.wizard.draw(this);
        //initialize the cooldown bar
        rect(500, 680, 200, 20); 
        fill(255,255,255);
        if(this.wizard.shoot){
            rect(500, 680, 200, 20);
        }
        else{
            rect(500, 680, (float)((App.currenttime - this.wizard.shootTimer)/(this.wizardCd*0.005)), 20);
            if(App.currenttime - this.wizard.shootTimer >= this.wizardCd){
                this.wizard.shoot = true;
            }
        }
        fill(0,0,0);
        //draw the gremlins
        for(gremlin g : this.gremlins){
            g.draw(this);
            g.tick();
        }
        //draw the walls
        for(brickwall bw : this.brickwalls){
            bw.draw(this);
        }
        for(stonewall sw : this.stonewalls){
            sw.draw(this);
        }
        //draw the powerups
        if(App.currenttime - this.powerUpTimer > 10000 && this.powerUpActive == false){
            for(powerUp p : this.pu){
                p.setSprite(loadImage("src/main/resources/gremlins/powerUp.png"));
                p.draw(this);
            }   
            this.powerUpExist = true;
        }
        //consumes the powerup if the wizard is on it
        for (int i = 0; i < this.pu.size(); i++){
            if(this.wizard.x == this.pu.get(i).x && this.wizard.y == this.pu.get(i).y && this.powerUpExist){
                //set sprite to null
                this.pu.get(i).setSprite(null);
                this.powerUpExist = false;
                this.powerUpActive = true;
            }
        }
        for(Fireball f : this.special){
            if (f.dir == 'l'){
                f.Left();
            }
            else if (f.dir == 'r'){
                f.Right();
            }
            else if (f.dir == 'u'){
                f.Up();
            }
            else if (f.dir == 'd'){
                f.Down();
            }
            f.draw(this);
        }
        if(this.powerUpActive){
            this.powUpIcon.draw(this);
        }
        //shoot the fireballs
        for(Fireball f : this.fireballs){
            if (f.dir == 'l'){
                f.Left();
            }
            else if (f.dir == 'r'){
                f.Right();
            }
            else if (f.dir == 'u'){
                f.Up();
            }
            else if (f.dir == 'd'){
                f.Down();
            }
            f.draw(this);
        }
        //special fireball clears everything on its path
        for(Fireball f : this.special){
            for(int g = 0; g < this.gremlins.size(); g++){
                if((f.getX() <= this.gremlins.get(g).getX()+10 & f.getX() >= this.gremlins.get(g).getX()-10) && 
                (f.getY() <=this.gremlins.get(g).getY()+10 & f.getY() >=this.gremlins.get(g).getY()-10)){{
                    this.gremlins.remove(g);
                }
            }
            for(int b = 0; b < this.brickwalls.size(); b++){
                if(f.x == this.brickwalls.get(b).x && f.y == this.brickwalls.get(b).y){
                    this.brickwalls.get(b).destroyed = true;
                }
            }
        }
    }
        
        //when fireball hits wall, fireball disappears and wall is destroyed
        for(int i = 0; i < this.fireballs.size(); i++){
            //detect collision between fireball and brickwall
            for(int b = 0; b < this.brickwalls.size(); b++){
                if((this.fireballs.get(i).getX() <= this.brickwalls.get(b).getX()+10 & this.fireballs.get(i).getX() >= this.brickwalls.get(b).getX()-10) && 
                (this.fireballs.get(i).getY() <= this.brickwalls.get(b).getY()+10 & this.fireballs.get(i).getY() >= this.brickwalls.get(b).getY()-10)){
                    this.brickwalls.get(b).destroyed = true;
                    this.fireballs.remove(i);
                    if(i == this.fireballs.size()){
                        break;
                    }
                    if(b == this.brickwalls.size()){
                        break;
                    }
                }             
            }
        }
        //remove the broken walls
        for(int w = 0; w < this.brickwalls.size(); w++){
            if(this.brickwalls.get(w).counter >4 ){
                this.brickwalls.remove(w);
            }
        }

        for(int i = 0; i < this.fireballs.size(); i++){
            //detect collision between fireball and stonewall
            for(int b = 0; b < this.stonewalls.size(); b++){
                if((this.fireballs.get(i).getX() <= this.stonewalls.get(b).getX()+10 & this.fireballs.get(i).getX() >= this.stonewalls.get(b).getX()-10) && 
                (this.fireballs.get(i).getY() <= this.stonewalls.get(b).getY()+10 & this.fireballs.get(i).getY() >= this.stonewalls.get(b).getY()-10)){
                    this.fireballs.remove(i);
                    if(i == this.fireballs.size()){
                        break;
                    }
                    if(b == this.stonewalls.size()){
                        break;
                    }
                }
        } 
    }
        //check if wizard touches the walls
        for(brickwall bw : this.brickwalls){
            if((this.wizard.getX() < bw.getX()+20 & this.wizard.getX() > bw.getX()-20) &&
            (this.wizard.getY() < bw.getY()+20 & this.wizard.getY() > bw.getY()-20)){
               this.wizard.completelyStop();
           }
        }
        for(stonewall sw : this.stonewalls){
            if((this.wizard.getX() < sw.getX()+20 & this.wizard.getX() > sw.getX()-20) &&
            (this.wizard.getY() < sw.getY()+20 & this.wizard.getY() > sw.getY()-20)){
               this.wizard.completelyStop();
           }
        }
        //check if the gremlins touch the walls
        for(gremlin g : this.gremlins){
            for(brickwall bw : this.brickwalls){
                if((g.getX() < bw.getX()+20 & g.getX() > bw.getX()-20) &&
                (g.getY() < bw.getY()+20 & g.getY() > bw.getY()-20)){
                   g.crashed = true;
               }
            }
            for(stonewall sw : this.stonewalls){
                if((g.getX() < sw.getX()+20 & g.getX() > sw.getX()-20) &&
                (g.getY() < sw.getY()+20 & g.getY() > sw.getY()-20)){
                   g.crashed = true;
               }
            }
        }
        //change direction after hitting wall
        for(gremlin g : this.gremlins){
            if(g.crashed){
                g.crashed = false;
                g.completelyStop();
                Random random = new Random();
                int index = random.nextInt(g.directions.size());
                if(g.directions.get(index) == 'l' && g.direction == 'r'){
                    index = random.nextInt(g.directions.size());
                }
                if(g.directions.get(index) == 'r' && g.direction == 'l'){
                    index = random.nextInt(g.directions.size());
                }
                if(g.directions.get(index) == 'u' && g.direction == 'd'){
                    index = random.nextInt(g.directions.size());
                }
                if(g.directions.get(index) == 'd' && g.direction == 'u'){
                    index = random.nextInt(g.directions.size());
                }
                g.direction = g.directions.get(index);
            
        }
    }
        //check if fireball hits the gremlins
        for(int i = 0; i < this.fireballs.size(); i++){
            for(int b = 0; b < this.gremlins.size(); b++){
                if((this.fireballs.get(i).getX() <= this.gremlins.get(b).getX()+10 & this.fireballs.get(i).getX() >= this.gremlins.get(b).getX()-10) && 
                (this.fireballs.get(i).getY() <= this.gremlins.get(b).getY()+10 & this.fireballs.get(i).getY() >= this.gremlins.get(b).getY()-10)){
                    this.fireballs.remove(i);
                    Random random = new Random();
                    //check if x and y is inside the walls
                    int x = 20*(random.nextInt(10)+10);
                    int y = 20*(random.nextInt(10)+10);
                    //exclude x and y from the walls
                    for(stonewall sw: this.stonewalls){
                        if((x < sw.getX()+20 & x > sw.getX()-20) &&
                        (y < sw.getY()+20 & y > sw.getY()-20)){
                            x = 20*(random.nextInt(10)+10);
                            y = 20*(random.nextInt(10)+10);
                        }
                        else{
                            this.gremlins.get(b).setX(x);
                            this.gremlins.get(b).setY(y);
                        }
                    }
                    for(brickwall bw : this.brickwalls){
                        if((x < bw.getX()+20 & x > bw.getX()-20) &&
                        (y < bw.getY()+20 & y > bw.getY()-20)){
                            x = 20*(random.nextInt(10)+10);
                            y = 20*(random.nextInt(10)+10);
                        }
                        else{
                            this.gremlins.get(b).setX(x);
                            this.gremlins.get(b).setY(y);
                        }
                    }
                    if(i == this.fireballs.size()){
                        break;
                    }
                    if(b == this.gremlins.size()){
                        break;
                    }
                }
            }
        }
        //vaporize the slimes if they touch a fireball
        if(this.fireballs.size() != 0 && this.slimes.size() != 0){
            for(int i = 0; i < this.slimes.size(); i++){
                for(int b = 0; b < this.fireballs.size(); b++){
                    if((this.slimes.get(i).getX() <= this.fireballs.get(b).getX()+10 & this.slimes.get(i).getX() >= this.fireballs.get(b).getX()-10) && 
                    (this.slimes.get(i).getY() <= this.fireballs.get(b).getY()+10 & this.slimes.get(i).getY() >= this.fireballs.get(b).getY()-10)){
                        this.slimes.remove(i);
                        this.fireballs.remove(b);
                        if(i == this.slimes.size()){
                            break;
                        }
                        if(b == this.fireballs.size()){
                            break;
                        }
                    }
                }
            }
        }
        //slime shooting timer
        for(gremlin g : gremlins){
            if (g.shoot){
                g.shoot = false;
                slime s = new slime(g.getX(),g.getY());
                s.setSprite(loadImage("src/main/resources/gremlins/slime.png"));
                if(g.direction == 'u'){
                    s.dir = 'u';
                }
                if(g.direction == 'd'){
                    s.dir = 'd';
                }
                if(g.direction == 'l'){
                    s.dir = 'l';
                }
                if(g.direction == 'r'){
                    s.dir = 'r';
                }
                this.slimes.add(s);
                for(slime ss : this.slimes){
                    if(ss == null){
                        ss = s;
                    }
                }
            }
        }
        for(int s = 0; s < this.slimes.size(); s++){
            this.slimes.get(s).draw(this);
            this.slimes.get(s).tick();
            //check if slime hits the walls
            for(int i = 0; i < this.stonewalls.size(); i++){
                if((this.slimes.get(s).getX() < this.stonewalls.get(i).getX()+20 & this.slimes.get(s).getX() > this.stonewalls.get(i).getX()-20) &&
                (this.slimes.get(s).getY() < this.stonewalls.get(i).getY()+20 & this.slimes.get(s).getY() > this.stonewalls.get(i).getY()-20)){
                   this.slimes.remove(s);
                    if(i == this.stonewalls.size()){
                    break;
                    }
                    if(s == this.slimes.size()){
                    break;
                }
               }
            }
        }
        for(int i = 0; i < this.slimes.size(); i++){
            //detect collision between fireball and stonewall
            for(int b = 0; b < this.brickwalls.size(); b++){
                if((this.slimes.get(i).getX() <= this.brickwalls.get(b).getX()+10 & this.slimes.get(i).getX() >= this.brickwalls.get(b).getX()-10) && 
                (this.slimes.get(i).getY() <= this.brickwalls.get(b).getY()+10 & this.slimes.get(i).getY() >= this.brickwalls.get(b).getY()-10)){
                    this.slimes.remove(i);
                    if(i == this.slimes.size()){
                        break;
                    }
                    if(b == this.brickwalls.size()){
                        break;
                    }
                }
        } 
        }
        //check if slime hits the wizard
        for(int i = 0; i < this.slimes.size(); i++){
            if((this.slimes.get(i).getX() <= this.wizard.getX()+10 & this.slimes.get(i).getX() >= this.wizard.getX()-10) && 
            (this.slimes.get(i).getY() <= this.wizard.getY()+10 & this.slimes.get(i).getY() >= this.wizard.getY()-10)){
                this.hit +=1;
                reset();
                setup();
                if(this.hit < this.lives.size()){
                    for(int b = 0; b < this.hit; b++){
                        this.lives.remove(b);
                    }
                }
                else{
                    //clear the canvas
                    this.clear();
                    //set beige background again
                    this.background(255, 255, 204);
                    //set game over text
                    this.textSize(50);
                    this.text("Game Over", 300, 300);
                    noLoop();
                    this.endgame = true;
                }
            }
        }
        //when the wizard steps on a teleporter, teleports to the coords of the other teleporter
        if((this.wizard.getX() < this.tp.get(0).getX()+20 & this.wizard.getX() > this.tp.get(0).getX()-20) &&
                (this.wizard.getY() < this.tp.get(0).getY()+20 & this.wizard.getY() > this.tp.get(0).getY()-20)&& this.teleportAvailable){
            this.teleportAvailable = false;
            this.wizard.setX(this.tp.get(1).getX());
            this.wizard.setY(this.tp.get(1).getY());
            this.teleportTimer = millis();
                }
        if((this.wizard.getX() < this.tp.get(1).getX()+20 & this.wizard.getX() > this.tp.get(1).getX()-20) &&
        (this.wizard.getY() < this.tp.get(1).getY()+20 & this.wizard.getY() > this.tp.get(1).getY()-20) &&this.teleportAvailable){
            this.teleportAvailable = false;
            this.wizard.setX(this.tp.get(0).getX());
            this.wizard.setY(this.tp.get(0).getY());
            this.teleportTimer = millis();
        }
        if(this.teleportAvailable == false){
            if(millis() - this.teleportTimer >= 5000){
                this.teleportAvailable = true;
            }
        }

        //check if wizard hits the gremlin
        for(int i = 0; i < this.gremlins.size(); i++){
            if((this.gremlins.get(i).getX() <= this.wizard.getX()+10 & this.gremlins.get(i).getX() >= this.wizard.getX()-10) && 
            (this.gremlins.get(i).getY() <= this.wizard.getY()+10 & this.gremlins.get(i).getY() >= this.wizard.getY()-10)){
                this.hit +=1;
                reset();
                setup();
                if(this.hit < this.lives.size()){
                    for(int b = 0; b < this.hit; b++){
                        this.lives.remove(b);
                    }
                }
                else{
                    //clear the canvas
                    this.clear();
                    //set beige background again
                    this.background(255, 255, 204);
                    //set game over text
                    this.textSize(50);
                    this.text("Game Over", 300, 300);
                    noLoop();
                    this.endgame = true;
                }
            }
        }
        text("Level "+ Integer.toString(lvlcode+1), 70, 700);
        fill(0);
        //when portal reached
        if((this.wizard.getX() < this.p.x+20 & this.wizard.getX() > this.p.x-20) &&
        (this.wizard.getY() < this.p.y+20 & this.wizard.getY() > this.p.y-20)){
            if(lvlcode < 1){
                //clear canvas
                reset();
                lvlcode++;
                setup();
            }
            else{
                //clear canvas
                this.clear();
                //clear all objects
                reset();
                //set beige background again
                this.background(255, 255, 204);
                //set game over text
                this.textSize(50);
                this.text("You Win!", 300, 300);
                noLoop();
                this.endgame = true;
            }
        }
    }
    public void reset(){
        //clear all objects
        this.brickwalls.clear();
        this.stonewalls.clear();
        this.gremlins.clear();
        this.fireballs.clear();
        this.pu.clear();
        this.slimes.clear();
        this.lives.clear();
        this.powerUpActive = false;
        this.powerUpExist = false;
        this.powerUpTimer = App.currenttime;
    }
    
    public static void main(String[] args) {
        //run
        PApplet.main("gremlins.App");
    }
}

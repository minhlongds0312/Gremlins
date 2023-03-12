package gremlins;


import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.AWTException;
import java.awt.Robot;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class SampleTest {
    App app;
    @BeforeEach
    public void setup(){
        app = new App();
        PApplet.runSketch(new String[] {"App"}, app);
        app.setup();
        app.noLoop();
        app.delay(10000);
    }
    @Test
    public void simpleTest() {
        app.draw();
        //test the wizard movements
        Wizard wiz = new Wizard();
        wiz.setX(50);
        wiz.setY(50);
        wiz.tick();
        assertEquals(50, wiz.getY());
        wiz.moveUp();
        wiz.tick();
        assertEquals(48, wiz.getY());
        wiz.moveDown();
        wiz.tick();
        assertEquals(50, wiz.getY());
        wiz.moveLeft();
        wiz.tick();
        assertEquals(48, wiz.getX());
        wiz.moveRight();
        wiz.tick();
        assertEquals(50, wiz.getX());
        wiz.completelyStop();
        wiz.tick();
        assertEquals(50, wiz.getX());
        wiz.stop();
        wiz.direction = 'u';
        wiz.completelyStop();
        wiz.direction = 'd';
        wiz.completelyStop();
        wiz.direction = 'l';
        wiz.completelyStop();
        wiz.direction = 'r';
        wiz.completelyStop();
        //test the keyPressed moving method
        app.keyCode = 37; //left
        app.keyPressed();
        app.wizard.setX(23);
        app.wizard.setY(23);
        app.keyReleased();
        app.keyCode = 38; //up
        app.keyPressed();
        app.wizard.setX(23);
        app.wizard.setY(23);
        app.keyReleased();
        app.keyCode = 39; //right
        app.keyPressed();
        app.wizard.setX(23);
        app.wizard.setY(23);
        app.keyReleased();
        app.keyCode = 40; //down
        app.keyPressed();
        app.wizard.setX(23);
        app.wizard.setY(23);
        app.keyReleased();//test the rounding tile
        //test the wizard's shooting
        app.keyCode = 32;
        app.wizard.shoot = true;
        app.direction = 'u'; //direction up
        app.keyPressed();
        app.keyCode = 32;
        app.wizard.shoot = true;
        app.direction = 'd'; //direction down
        app.keyPressed();
        app.keyCode = 32;
        app.wizard.shoot = true;
        app.direction = 'l'; //direction left
        app.keyPressed();
        app.keyCode = 32;
        app.wizard.shoot = true;
        app.direction = 'r'; //direction right
        app.keyPressed();
        app.keyCode = 32;
        app.wizard.shoot = true;
        app.fireballs.add(null); //test the edge case where the fireball arraylist is null
        app.keyPressed();
        app.keyCode = 42;
        app.wizard.shoot = true;
        app.keyPressed();
        app.keyCode = 32;
        app.wizard.shoot = false;
        app.keyPressed();
        app.wizard.shoot = false;
        app.loop();
        app.wizard.shoot = false;
        app.wizard.shootTimer = 0;
        App.currenttime = 10000;
        app.wizardCd = 0; //test the wizard's cooldown
        app.loop();
        //test for the special buttons
        app.wizard.shoot = true;
        app.keyCode = 69;
        app.powerUpActive=true;
        app.keyPressed();
        assertTrue(app.special.size() == 1);
        app.keyCode = 69;
        app.powerUpActive = false;
        app.keyPressed();
        //test for the power up
        app.keyCode = 69;
        app.powerUpActive = true;
        app.keyPressed();
        app.keyCode = 69;
        app.powerUpActive = false;
        app.keyPressed();
        app.keyCode = 69;
        app.powerUpActive = true;
        //test directions for the special fireball
        app.keyCode = 69;
        app.powerUpActive = true;
        app.direction = 'u'; //direction up
        app.keyPressed();
        app.keyCode = 69;
        app.powerUpActive = true;
        app.direction = 'd'; //direction down
        app.keyPressed();
        app.keyCode = 69;
        app.powerUpActive = true;
        app.direction = 'l'; //direction left
        app.keyPressed();
        app.keyCode = 69;
        app.powerUpActive = true;
        app.direction = 'r'; //direction right
        app.keyPressed();
        app.keyCode = 69;
        app.powerUpActive = true;
        app.direction = 't'; //null input
        app.keyPressed();                            
        app.special.add(null);
        app.keyCode = 69;
        app.powerUpActive = true;
        app.keyPressed();
        app.loop();
        app.powerUpActive = false;
        app.loop();
        //test for the slime shooting and displaying
        for(gremlin g : app.gremlins){
            g.shoot = true;
            g.tick();
        }
        for(slime s : app.slimes){
            s.tick();
        }
        for(gremlin g : app.gremlins){
            g.shoot = false;
            g.tick();
        }
        //test for game restart when endgame becomes true
        app.endgame = true;
        app.loop();
        app.keyCode = 32;
        app.keyPressed = true;
        app.keyPressed();
        app.endgame = false;
        app.keyPressed = true;
        app.keyPressed();


    } 
    @Test
    //test the fireball count and directions
    public void fireballtest(){
        app.draw();
        app.wizard.shoot = true;
        app.keyCode = 32;
        app.direction = 'u';
        app.keyPressed();
        app.wizard.shoot = true;
        app.keyCode = 32;
        app.direction = 'd';
        app.keyPressed();
        app.wizard.shoot = true;
        app.keyCode = 32;
        app.direction = 'l';
        app.keyPressed();
        app.wizard.shoot = true;
        app.keyCode = 32;
        app.direction = 'r';
        app.keyPressed();
        app.loop();
        assertTrue(app.fireballs.size() == 4);
    }
    @Test
    //test for the powerup
    public void powerUpTest(){
        app.draw();
        app.powerUpActive = false;
        App.currenttime = 11000;
        app.powerUpTimer = 0;
        app.loop();
    }
}
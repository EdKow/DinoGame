package objectgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import util.Animation;
import util.Resource;

import static userinterface.GameScreen.GRAVITY;
import static userinterface.GameScreen.GROUNDY;


public class MainCharacter {
    private float x = 0;
    private float y = 0;
    private float speedY = 0;
    private Animation characterRun;
    private Rectangle rect;
    private boolean Alive = true;

    public MainCharacter(){
        //animation character running
        characterRun = new Animation(150);
        characterRun.addFrame(Resource.getResourceImage("data/main-character1.png"));
        characterRun.addFrame(Resource.getResourceImage("data/main-character2.png"));
        rect = new Rectangle();
    }

    public void update(){
        characterRun.update();
        //Jumping conditions type shi
        if(y >= GROUNDY - characterRun.getFrame().getHeight()){
            speedY = 0;
            y = GROUNDY - characterRun.getFrame().getHeight();
        } else{
            speedY+= GRAVITY;
            y+=speedY;
        }

        rect.x = (int) x;
        rect.y = (int) y;
        rect.width = characterRun.getFrame().getWidth();
        rect.height = characterRun.getFrame().getHeight();
    }

    public Rectangle getBound(){
        return rect;
    }

    public void draw(Graphics g){
        g.setColor(Color.black);
        g.drawRect((int)x, (int)y, characterRun.getFrame().getWidth(), characterRun.getFrame().getHeight());
        g.drawImage(characterRun.getFrame(), (int) x, (int) y, null);
    }

    public void Jump(){
        if(isOnGround()){
            speedY= -7;
            y += speedY;
        }
    }

    public boolean isOnGround(){
        return y >= GROUNDY - characterRun.getFrame().getHeight();
    }

    public float getX(){
        return x;
    }

    public void setX(float x){
        this.x = x;
    }

    public float getY(){
        return y;
    }

    public void setY(float y){
        this.y = y;
    }

    public float getSpeedY(){
        return speedY;
    }

    public void setSpeedY(float speedY){
        this.speedY = speedY;
    }

    public void setAlive(boolean alive){
        Alive = alive;
    }
    public boolean getAlive(){
        return Alive;
    }

}

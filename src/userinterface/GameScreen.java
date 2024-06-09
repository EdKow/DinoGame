package userinterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


import objectgame.Clouds;
import objectgame.EnemyManager;
import objectgame.Land;
import objectgame.MainCharacter;
import util.Resource;

public class GameScreen extends JPanel implements Runnable, KeyListener{
    public static final int GAME_INITIAL_STATE = 0;
    public static final int GAMEPLAY_STATE = 1;
    public static final int GAME_FINAL_STATE = 2;
    public static final float GRAVITY = 0.4f;
    public static final float GROUNDY = 105; //pixels
   
    private MainCharacter mainCharacter;
    private Thread thread;
    private Land land;
    private Clouds clouds;
    private EnemyManager enemyManager;
    private int score;

    private int gameState;
    
    private BufferedImage imageGameOverText;
    private BufferedImage RestartButton;

    public GameScreen(){
        thread = new Thread(this);
        mainCharacter = new MainCharacter();
        mainCharacter.setX(50);
        mainCharacter.setY(60);
        land = new Land(this);
        clouds = new Clouds();
        enemyManager = new EnemyManager(mainCharacter);
        imageGameOverText = Resource.getResourceImage("data/gameover_text.png");
        RestartButton = Resource.getResourceImage("data/replay_button.png");
        score = 0;
    }

    public void startGame(){
        thread.start();
    }

    public void run(){
        while (true) {
            try {
                update();

                repaint();
                Thread.sleep(20);
            } 
            
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(){
        switch (gameState) {
            case GAME_INITIAL_STATE:
                mainCharacter.update();
                land.update();
                clouds.update();
                break;
            case GAMEPLAY_STATE:
                mainCharacter.update();
                land.update();
                clouds.update();
                enemyManager.update();
                score++;
                break;
        }
        
        
        
    }
    @Override
    public void paint(Graphics g){
        g.setColor(Color.decode("#f7f7f7"));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.red);
        g.drawLine(0, (int)GROUNDY, getWidth(), (int)GROUNDY);

        switch (gameState) {
            case GAME_INITIAL_STATE:  
                clouds.draw(g);
                land.draw(g); 
                mainCharacter.draw(g);     
                break;
            case GAMEPLAY_STATE:
                clouds.draw(g);
                land.draw(g);
                mainCharacter.draw(g);
                enemyManager.draw(g);
                g.drawString("Score:" + score, 500, 20);
                if (!mainCharacter.getAlive()) {
                    gameState = GAME_FINAL_STATE;
                }
                break;
            case GAME_FINAL_STATE:
                clouds.draw(g);
                land.draw(g);
                mainCharacter.draw(g);
                enemyManager.draw(g);
                g.drawImage(imageGameOverText, 200, 50, null);
                g.drawImage(RestartButton, 280, 70, null);
                g.drawString("Score:" + score, 500, 20);
                break;
        }
        
        
    }


    @Override
    public void keyTyped(KeyEvent e){
        
    }
    @Override
    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                if (gameState == GAME_INITIAL_STATE) {
                    gameState = GAMEPLAY_STATE;
                } else if (gameState == GAMEPLAY_STATE) {
                    mainCharacter.Jump();
                } else if (gameState == GAME_FINAL_STATE) {
                    mainCharacter = new MainCharacter();
                    mainCharacter.setX(50);
                    mainCharacter.setY(60);
                    land = new Land(this);
                    clouds = new Clouds();
                    enemyManager = new EnemyManager(mainCharacter);
                    score = 0;
                    gameState = GAMEPLAY_STATE;
                }
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e){}
        
}

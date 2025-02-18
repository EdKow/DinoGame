package objectgame;

import static userinterface.GameScreen.GROUNDY;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import userinterface.GameScreen;
import util.Resource;

public class Land {
    private List<ImageLand> listImage;
    private BufferedImage imageLand1, imageLand2, imageLand3;
    private Random random;

    public Land(GameScreen game){
        random = new Random();
        imageLand1 = Resource.getResourceImage("data/land1.png");
        imageLand2 = Resource.getResourceImage("data/land2.png");
        imageLand3 = Resource.getResourceImage("data/land3.png");
        listImage = new ArrayList<ImageLand>();
        
        
        int numberOfLandTitle = 600 / imageLand1.getWidth() + 2;
        System.out.println(numberOfLandTitle);
        
        
        for(int i = 0; i < numberOfLandTitle; i++){
            ImageLand imageLand = new ImageLand();
            imageLand.posX = (int) (i* imageLand1.getWidth());
            imageLand.image = getImageLand();
            listImage.add(imageLand);
        }
    }

    public void update(){
        for(ImageLand imageLand : listImage){
            imageLand.posX -= 4;
        }

        ///land will loop in the screen
        ImageLand firstElement = listImage.get(0);
        if(firstElement.posX + imageLand1.getWidth() < 0){
            firstElement.posX = listImage.get(listImage.size() - 1).posX + imageLand1.getWidth();
            listImage.add(listImage.get(0));
            listImage.remove(0);
        }
    }
    //posision of land tiles
    public void draw(Graphics g){
        for(ImageLand imageLand:listImage){
            g.drawImage(imageLand.image, imageLand.posX, (int) GROUNDY - 20, null);
        }
        
        
    }
    //randomize the land tiles
    private BufferedImage getImageLand(){
        int i = random.nextInt(10);
        switch (i) {
            case 0: return imageLand1;
            case 1: return imageLand3;
            default: return imageLand2;
        }
    }
    //i dont remember
    private class ImageLand {
        int posX;
        BufferedImage image;
    }
}

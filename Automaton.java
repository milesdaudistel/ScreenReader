import java.awt.*;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.AWTException;

public class Automaton {
    /* 
    already have a subimage
    take a screenshot
    try to find the subimage within the screenshot
    */

    public static void main(String[] args) {

        if (args.length != 0) {
            

            try {
                Robot robo = new Robot();
                Rectangle rekt = new Rectangle(0, 0, 1500, 1500);
                BufferedImage img = robo.createScreenCapture(rekt);
                BufferedImage subImg = img.getSubimage(250, 250, 100, 100);
                File outputfile1 = new File("screenshot.png");
                File outputfile2 = new File("findThis.png");
                
                try {
                    ImageIO.write(img, "png", outputfile1);
                    ImageIO.write(subImg, "png", outputfile2);
                } catch (IOException e) {
                    System.out.println("You dumb");
                }

            } catch (AWTException e) {
                System.out.println("I dunno lol");
            }
        } else {
            try {
            Robot robo = new Robot();
            //you could try to get screen dimensions by doing two big numbers, then checking where all the black pixels are

            BufferedImage findThis = null;
            try {
                findThis = ImageIO.read(new File("findThis.png"));
            } catch (IOException e) {
                System.out.println("No image found.");
            }

            Rectangle rekt = new Rectangle(0, 0, 1500, 1500);
            BufferedImage img = robo.createScreenCapture(rekt);
            
            //File outputfile1 = new File("saved1.png");
            //File outputfile2 = new File("saved2.png");

            int thisHeight = findThis.getHeight();
            int thisWidth = findThis.getWidth();



            int firstPixel = findThis.getRGB(0, 0);
            
            for (int i = 0; i < img.getWidth() - thisWidth; i += 1) {
                for (int j = 0; j < img.getHeight() - thisHeight; j += 1) {
                    int c = img.getRGB(i, j);
                    if (c == firstPixel) {
                        //System.out.println("I found an identical pixel");
                        BufferedImage scanPic = img.getSubimage(i, j, thisWidth, thisHeight);
                        if (compareImages(scanPic, findThis)) {
                            System.out.println("Found at this location: \n" + i + " , " + j);
                            robo.mouseMove(i, j);
                            return;
                        }
                    }
                }
            }

            System.out.println("Couldn't find anything");

            
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }  
    }

    public static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
        int aW = imgA.getWidth();
        int aH = imgA.getHeight();
        int bW = imgB.getWidth();
        int bH = imgB.getHeight();

        if (aW != bW || aH != bH) {
            return false;
        }

        for (int i = 0; i < aW; i += 1) {
            for (int j = 0; j < bW; j += 1) {
                if (imgA.getRGB(i, j) != imgB.getRGB(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

}
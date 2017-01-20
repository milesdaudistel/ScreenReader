/**
 * Created by miles on 1/19/17.
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.AWTException;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//coolguy

public class myComponent extends JFrame implements MouseListener, MouseMotionListener {

    int startDragX;
    int startDragY;
    int endDragX;
    int endDragY;
    int _screenWidth;
    int _screenHeight;

    Robot _robo;



    myComponent() {
        addMouseListener(this);
        addMouseMotionListener(this);

        try {
            this._robo = new Robot();
        } catch (AWTException e) {
            System.out.println("It looks like your computer doesn't trust the robot class");
        }
    }

    public static void main(String[] args) {
        myComponent mc = new myComponent();

        mc.findScreenDimensions2();

        mc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mc.setSize(mc._screenWidth, mc._screenHeight);
        //mc.getContentPane().add(mc);
        mc.setUndecorated(true);
        mc.setOpacity(0.50f);
        mc.setVisible(true);

        /*
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setSize(mc._screenWidth, mc._screenHeight);
        f.getContentPane().add(mc);
        f.setUndecorated(true);
        f.setOpacity(0.50f);
        f.setVisible(true);
        */



        //sets screen width and height

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("x: " + e.getX());
        System.out.println("y: " + e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        startDragX = e.getX();
        startDragY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        this.setVisible(false);
        this.setOpacity(0.00f);

        //this thing takes a picture before the opacity can get set to 0
        //since swing is multithreaded or something.
        try {
            Thread.sleep(50);
        } catch (InterruptedException f) {
            System.out.println("your computer doesn't want to wait");
        }

        Rectangle rekt = new Rectangle(startDragX,
                startDragY,
                e.getX() - startDragX,
                e.getY() - startDragY);

        BufferedImage img = _robo.createScreenCapture(rekt);

        // FIXME
        //can only do maximum 1 selection, as any new selections replace old selections
        File outputfile = new File("selection.png");

        try {
            ImageIO.write(img, "png", outputfile);
            System.out.println("Screenshot created");
            System.out.print("bot: ");
            //System.exit(0);
            //setVisible(false);
        } catch (IOException f) {
            System.out.println("Failed to create screenshot");
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        endDragX = e.getX();
        endDragY = e.getY();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    /*
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(startDragX, startDragY, endDragX - startDragX, endDragY - startDragY);
    }
    */

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawRect(startDragX, startDragY, endDragX - startDragX, endDragY - startDragY);
    }

    private void findScreenDimensions() {
        Rectangle rekt = new Rectangle(0, 0, 3000, 3000);
        BufferedImage img = _robo.createScreenCapture(rekt);

        int pixel;
        int blackpixels = 0;

        for (int i = 0; i < 4000; i += 1) {
            pixel = img.getRGB(i, 0);
            //black, equivalent to 0x00FFFFFF in hex
            if (pixel == -16777216) {
                blackpixels += 1;
            } else {
                blackpixels = 0;
            }
            if (blackpixels >= 1000) {
                _screenWidth = i - blackpixels;
                break;
            }
        }

        blackpixels = 0;
        for (int i = 0; i < 4000; i += 1) {
            pixel = img.getRGB(0, i);
            //black, equivalent to 0x00FFFFFF in hex
            //was -16777216
            if (pixel == -16777216) {
                blackpixels += 1;
            } else {
                blackpixels = 0;
            }
            if (blackpixels >= 1000) {
                _screenHeight = i - blackpixels;
                break;
            }
        }
    }

    private void findScreenDimensions2() {
        int screenGuess = 3000;
        Rectangle rekt = new Rectangle(0, 0, screenGuess, screenGuess);
        BufferedImage img = _robo.createScreenCapture(rekt);

        for(int i = screenGuess - 1; i >= 0; i -= 1) {
            //0x00000000
            if (img.getRGB(i, 0) != -16777216) {
                _screenWidth = i;
                break;
            }
        }

        for(int i = screenGuess - 1; i >= 0; i -= 1) {
            if (img.getRGB(0, i) != -16777216) {
                _screenHeight = i;
                break;
            }
        }
    }


}

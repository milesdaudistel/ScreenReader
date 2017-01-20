import java.awt.*;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.AWTException;

public class SearchBot {

    private Robot _robo;
    private BufferedImage _screenshot;
    private BufferedImage _subshot;
    private BufferedImage _selection;
    private int _screenWidth;
    private int _screenHeight;

    SearchBot() {

        try {
            _robo = new Robot();
        } catch (AWTException e) {
            System.out.println("It looks like your computer doesn't trust the robot class");
        }

        setScreenDimensions();
        System.out.println(_screenWidth);
        System.out.println(_screenHeight);

        _screenshot = null;
        _subshot = null;

        try {
            _selection = ImageIO.read(new File("selection.png"));
        } catch (IOException e) {
            System.out.println("It looks like you haven't taken a picture.");
            return;
        }

    }

    public static void main(String[] args) {
        SearchBot s = new SearchBot();
        s.search();
    }

    public void camera() {
        Rectangle rekt = new Rectangle(0, 0, _screenWidth, _screenHeight);
        _screenshot = _robo.createScreenCapture(rekt);
        File outputfile = new File("screenshot.png");

        try {
            ImageIO.write(_screenshot, "png", outputfile);
        } catch (IOException e) {
            System.out.println("Failed to create screenshot");
        }
    }

    private void setScreenDimensions() {
        Rectangle rekt = new Rectangle(0, 0, 3000, 3000);
        BufferedImage img = _robo.createScreenCapture(rekt);

        int pixel;
        int blackpixels = 0;

        for (int i = 0; i < 3000; i += 1) {
            pixel = img.getRGB(i, 0);
            //black, equivalent to 0x00FFFFFF in hex
            if (pixel == -16777216) {
                blackpixels += 1;
            } else {
                blackpixels = 0;
            }
            if (blackpixels >= 500) {
                _screenWidth = i - blackpixels;
                break;
            }
        }

        blackpixels = 0;
        for (int i = 0; i < 3000; i += 1) {
            pixel = img.getRGB(0, i);
            //black, equivalent to 0x00FFFFFF in hex
            if (pixel == -16777216) {
                blackpixels += 1;
            } else {
                blackpixels = 0;
            }
            if (blackpixels >= 500) {
                _screenHeight = i - blackpixels;
                break;
            }
        }
    }

    public void search() {
        if (_selection == null) {
            System.out.println("You need to take a picture");
            return;
        }

        System.out.println("fuck");
        Rectangle rekt = new Rectangle(0, 0, _screenWidth, _screenHeight);
        BufferedImage img = _robo.createScreenCapture(rekt);

        int firstPixel = _selection.getRGB(0, 0);
        int subWidth = _selection.getWidth();
        int subHeight = _selection.getHeight();

        for (int i = 0; i < img.getWidth() - subWidth; i += 1) {
            for (int j = 0; j < img.getHeight() - subHeight; j += 1) {
                int c = img.getRGB(i, j);
                if (c == firstPixel) {
                    //System.out.println("I found an identical pixel");
                    BufferedImage scanPic = img.getSubimage(i, j, subWidth, subHeight);

                    if (compareImages(scanPic, _selection)) {
                        System.out.println("Found at this location: \n" + i + " , " + j);
                        _robo.mouseMove(i, j);
                        return;
                    }
                }
            }
        }

        System.out.println("Couldn't find anything");
    }

    private boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
        int aW = imgA.getWidth();
        int aH = imgA.getHeight();
        int bW = imgB.getWidth();
        int bH = imgB.getHeight();

        if (aW != bW || aH != bH) {
            return false;
        }

        /*
        File outputfile = new File("found.png");

        try {
            ImageIO.write(imgA, "png", outputfile);
        } catch (IOException f) {
            System.out.println("Failed to create screenshot");
        }
        */

        for (int i = 0; i < aW; i += 1) {
            for (int j = 0; j < aH; j += 1) {
                if (imgA.getRGB(i, j) != imgB.getRGB(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void printScreenDimensions() {
        System.out.println("Width:  " + _screenWidth + "\n" + "Height:  " + _screenHeight);
    }

    public void specify(String coords) {

        if (_screenshot == null) {
            try {
                System.out.println("You haven't taken a picture yet.  \n" +
                                "Searching memory banks for old screenshot...");
                _screenshot = ImageIO.read(new File("screenshot.png"));
            } catch (IOException e) {
                System.out.println("No image found.");
                return;
            }
        }

        String[] coords2 = coords.split(" ");

        try {
            _subshot = _screenshot.getSubimage(Integer.parseInt(coords2[0]),
                    Integer.parseInt(coords2[1]),
                    Integer.parseInt(coords2[2]),
                    Integer.parseInt(coords2[3]));
        } catch (NumberFormatException e) {
            System.out.println("Unrecognized coordinate format");
            return;
        }


        File outputfile = new File("subshot.png");

        try {
            ImageIO.write(_subshot, "png", outputfile);
        } catch (IOException e) {
            System.out.println("Failed to create subshot");
        }
    }

    public static void coords0() {
        Robot r;
        try {
            r = new Robot();
            r.mouseMove(0, 0);
        } catch (AWTException e) {
            System.out.println("It looks like your computer doesn't trust the robot class");
        }
    }

}
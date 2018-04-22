/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import GUI.Storage.Grid;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * This class contains some essential image tools used for the manipulation and
 * extraction of data.
 *
 * @author mathew
 */
public class ImageTools {

    public static int recur = 0;

    /**
     * Used for cropping an image
     *
     * @param img Image to be cropped
     * @param x Start x Coor
     * @param y Start y Coor
     * @param width The width of the cropping region
     * @param height The height of the cropping region
     * @return
     */
    public static BufferedImage cropImage(BufferedImage img, int x, int y, int width, int height) { // For legacy
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bi.getGraphics().drawImage(img, 0, 0, width, height, x, y, x + width, y + height, null);
        return bi;
    }

    /**
     * Used for cropping an Image directly from the JavaFX packages
     *
     * @param img
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage cropImage(Image img, int x, int y, int width, int height) { //For Javafx Use

        return cropImage(SwingFXUtils.fromFXImage(img, null), x, y, width, height);
    }

    /**
     * Converts the Image to Buffered Image so that it can be used with the
     * various other tools in this class
     *
     * @param img
     * @return
     */
    public static BufferedImage convertImgToBuf(Image img) {

        return SwingFXUtils.fromFXImage(img, null);

    }

    /**
     * For converting back from a BufferedImage to a normal Image
     *
     * @param bi
     * @return
     */
    public static Image convertBuffered(BufferedImage bi) {
        return SwingFXUtils.toFXImage(bi, null);
    }

    /**
     * Gives the option to binarise an image without the minimum threshold
     * applied
     *
     * @param img
     * @param binarise
     * @param max
     * @return
     */
    public static BufferedImage toGreyScale(BufferedImage img, boolean binarise, int max) {
        return toGreyScale(img, binarise, 0, max);
    }

    /**
     *
     * @param img
     * @param binarise Specifies whether it should be converted to 1-bit colour
     * @param min The minimum the average of the pixels rgb: (R+G+B)/3 can be to
     * be turned black
     * @param max The maximum average
     * @return
     */
    public static BufferedImage toGreyScale(BufferedImage img, boolean binarise, int min, int max) {
        int h = 0;
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int p = img.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = (p >> 0) & 0xff;

                int avg = (r + g + b) / 3;
                if (avg >= min && avg <= max) {
                    avg = 0;
                } else {
                    avg = 255;
                }

                p = (a << 24) | (avg << 16) | (avg << 8) | avg;
                img.setRGB(x, y, p);

            }

        }

        return img;
    }

    /**
     * This function converts an image to a binary pixel grid based on the width
     * and height proportions, creating a grid over a whole image and taking the
     * average from each section in the grid converting it to a lower
     * resolution, this method is used to convert an image so that it can be
     * inputted into a neural network.
     *
     * @param img
     * @param width Pixel width
     * @param height Pixel height
     * @param avgPoint The value of the average of all of the pixels in a region
     * to be turned into a black pixel
     * @return
     */
    public static Grid<Double> imageToBinaryGrid(BufferedImage img, int width, int height, int avgPoint) {
        Rectangle[][] BOUNDS = new Rectangle[width][height];

        Grid<Double> GRID = new Grid(width, height, 1.0);
        double rectWidth = (double) img.getWidth() / (double) width;
        double rectHeight = (double) img.getHeight() / (double) height;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double across = x * rectWidth;
                double down = y * rectHeight;
                BOUNDS[x][y] = new Rectangle((int) across, (int) down, (int) rectWidth, (int) rectHeight);
            }
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int avg = getPixelAvg(img, BOUNDS[x][y]);

                if (avg < avgPoint) {
                    GRID.set(x, y, 1.0);
                } else {
                    GRID.set(x, y, 0.0);
                }

            }
        }

        return GRID;

    }

    /**
     * Could be used as an alternative method to getPixelAvg(..) in
     * imageToBinaryGrid(..)
     *
     * @param img
     * @param bound
     * @return
     */
    private static boolean containsPixel(BufferedImage img, Rectangle bound) {

        for (int y = (int) bound.getY(); y < bound.getY() + bound.getHeight(); y++) {
            for (int x = (int) bound.getX(); x < bound.getX() + bound.getWidth(); x++) {

                double avg = getPixelAvg(img, x, y);
                if (avg > 127) {
                    return true;
                }

            }

        }

        return false;
    }

    /**
     * Calculates the average if all RGB components in all of the pixels
     * contained within the bound
     *
     * @param img
     * @param bound
     * @return
     */
    private static int getPixelAvg(BufferedImage img, Rectangle bound) {
        double avg = 0;
        int pixelCount = 0;

        for (int y = (int) bound.getY(); y < bound.getY() + bound.getHeight(); y++) {
            for (int x = (int) bound.getX(); x < bound.getX() + bound.getWidth(); x++) {

                avg += getPixelAvg(img, x, y);
                pixelCount++;

            }

        }
        avg /= pixelCount;

        return (int) avg;
    }

    /**
     * Gets the average of an individual pixel where avg = (R+G+B)/3
     *
     * @param img
     * @param x
     * @param y
     * @return
     */
    private static double getPixelAvg(BufferedImage img, int x, int y) {
        try {
            int p = img.getRGB(x, y);

            int r = (p >> 16) & 0xff;
            int g = (p >> 8) & 0xff;
            int b = (p >> 0) & 0xff;

            return (double) (r + g + b) / 3.0;
        } catch (Exception e) {
            System.out.println("Error: " + x + ", " + y);
            return 255.0;
        }

    }

    /**
     * A simpler adaptation of findEnclosedPixels(), used when a real-time
     * Recognition Output is not needed
     *
     * @param img
     * @return
     */
    public static List<PixelFormation> findEnclosedPixels(BufferedImage img) {
        return findEnclosedPixels(img, null, 0);
    }

    /**
     * This algorithm recursively checks through all of the pixels in a image,
     * if the methods comes across a black pixel, it will continuously explore
     * all of the neighboring pixels and so on, while all of these pixels are
     * being added as points to a Pixel Formation where they can be later
     * analyzed for characters
     *
     * @param img
     * @param update
     * @param totalInc
     * @return
     */
    public static List<PixelFormation> findEnclosedPixels(BufferedImage img, RecognitionOutput update, double totalInc) {
        List<PixelFormation> form = new ArrayList<PixelFormation>();
        List<Point> taken = new ArrayList<Point>();
        double inc = 0;
        boolean incr = false;
        if (update != null && totalInc > 0) {
            update.FORMATIONS = new ArrayList<PixelFormation>();
            form = update.FORMATIONS;
            incr = true;
            inc = totalInc / (img.getWidth() * img.getHeight());
        }
        System.out.println("inc: " + inc);
        PixelFormation currentForm = null;

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                if (incr) {
                    update.updateProgress(update.getProgress() + inc);
                }
                currentForm = new PixelFormation();
                currentForm = explorePixel(img, x, y, taken, currentForm);
                if (currentForm == null) {
                    return null;
                };
                if (!currentForm.isEmpty()) {
                    form.add(currentForm);
                }
            }
        }

        return form;
    }

    /**
     * Used by find enclosed pixels method to see whether the pixel has already
     * been explored
     *
     * @param x
     * @param y
     * @param taken
     * @return
     */
    private static boolean pointTaken(int x, int y, List<Point> taken) {
        for (Point p : taken) {
            if (p.equals(new Point(x, y))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Used by the find enclosed pixels method to recursively check a pixels
     * neighbors
     *
     * @param img
     * @param x
     * @param y
     * @param taken
     * @param pF
     * @return
     */
    private static PixelFormation explorePixel(BufferedImage img, int x, int y, List<Point> taken, PixelFormation pF) { // NEED TO FIX PIXEL BOUND PROBLEM!
        // System.out.println(recur++);

        if (x < 0 || y < 0 || y >= img.getHeight() || x >= img.getWidth()) { // Checks the pixel being explored is in range
            return pF;
        }
        // System.out.println((getPixelAvg(img,x,y)<127) +" "+!(pointTaken(x, y, taken)));
        if (getPixelAvg(img, x, y) < 127 && !(pointTaken(x, y, taken))) { // Checks to see if the pixel is black or white
            // System.out.println("FOUND");
            //System.out.println("Checking: "+x+", "+y);

            pF.addPoint(x, y); // Adds pixel to the current formation
            taken.add(new Point(x, y)); // Adds point and marks it as already visited
            try {
                pF = explorePixel(img, x + 1, y, taken, pF); // Horizontal right
                pF = explorePixel(img, x - 1, y, taken, pF); // Horizontal left
                pF = explorePixel(img, x, y + 1, taken, pF); // Vertical down
                pF = explorePixel(img, x, y - 1, taken, pF); // Vertical up
            } catch (StackOverflowError e) {
                System.out.println("Stack overflow?");// problem occurs when the image being processed is too big and the JVM stack limit is exceeded
                return null;
            }
            /*
             pF = explorePixel(img, x+1,y+1, taken,pF);
             pF = explorePixel(img, x-1,y-1, taken,pF);
             pF = explorePixel(img, x-1,y+1, taken,pF);
             pF = explorePixel(img, x+1,y-1, taken,pF);
             */
        }

        return pF;

    }

}

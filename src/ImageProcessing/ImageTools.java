/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import GUI.Storage.Grid;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 *
 * @author mathew
 */
public class ImageTools {
    
    public static BufferedImage cropImage(BufferedImage img, int x, int y, int width, int height){ // For legacy
    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    bi.getGraphics().drawImage(img, 0, 0, width, height, x, y, x + width, y + height, null);
    return bi;
}
    public static BufferedImage cropImage(Image img, int x, int y, int width, int height){ //For Javafx Use
        
    return cropImage( SwingFXUtils.fromFXImage(img, null),  x, y, width,height);
}
    
   public static BufferedImage convertImgToBuf(Image img){
      
      return SwingFXUtils.fromFXImage(img, null);
       
   }
    
    public static Image convertBuffered(BufferedImage bi){
        
        return SwingFXUtils.toFXImage(bi, null);
    }
    
    
    
    public static BufferedImage toGreyScale(BufferedImage img, boolean binarise, int threshold){
        int h = 0;
        for(int y =0; y< img.getHeight(); y++){
             for(int x =0; x< img.getWidth(); x++){
                 int p = img.getRGB(x, y);
                 int a = (p>>24)&0xff;
                 int r = (p>>16)&0xff;
                 int g = (p>>8)&0xff;
                 int b = (p>>0)&0xff;
                 
                 int avg = (r+g+b)/3;
                 if(avg>threshold) avg = 255;
                 else avg = 0;
                
                 
                 p= (a<<24) | (avg<<16) | (avg<<8) | avg;
                 img.setRGB(x, y, p);
                 
             }
            
        }
        
        return img;
    }
    
    public static Grid<Double> imageToBinaryGrid(BufferedImage img, int width, int height){
        Rectangle [][] BOUNDS = new Rectangle[width][height];
        
        Grid<Double> GRID = new Grid(width, height, 0.0);
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
                
                if(avg<127) GRID.set(x, y, 1.0);
                else GRID.set(x, y, 0.0);
                
            }
        }
        
        
        
        return GRID;
        
    }
    
    private static int getPixelAvg(BufferedImage img, Rectangle bound){
        double avg = 0;
        int pixelCount = 0;
        
         for(int y =(int)bound.getY(); y< bound.getY()+bound.getHeight(); y++){
             for(int x =(int)bound.getX(); x< bound.getX()+bound.getWidth(); x++){
                 int p = img.getRGB(x, y);
                 int r = (p>>16)&0xff;
                 int g = (p>>8)&0xff;
                 int b = (p>>0)&0xff;
                 
                 avg+=(double)(r+g+b)/3.0;
                 pixelCount++;
                 
             }
            
        }
         avg /=pixelCount;
        
        
        
        return (int)avg;
    }
    
    

}

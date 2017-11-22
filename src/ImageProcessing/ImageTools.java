/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

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
    
    
    
    public static BufferedImage toGreyScale(BufferedImage img){
        int h = 0;
        for(int y =0; y< img.getHeight(); y++){
             for(int x =0; x< img.getHeight(); x++){
                 int p = img.getRGB(x, y);
                 int a = (p>>24)&0xff;
                 int r = (p>>16)&0xff;
                 int g = (p>>8)&0xff;
                 int b = (p>>0)&0xff;
                 
                 int avg = (r+g+b)/3;
                 
                 if(avg> h) h =avg;
                 
                 p= (a<<24) | (avg<<16) | (avg<<8) | avg;
                 img.setRGB(x, y, p);
                 
             }
            
        }
         System.out.println("Highest: "+h);
        return img;
    }
    
    public static void binarizeImage(){
        
    }
    
    

}

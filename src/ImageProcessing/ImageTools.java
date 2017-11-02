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
    public static BufferedImage cropImage(Image img, int x, int y, int width, int height){
    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    bi.getGraphics().drawImage(SwingFXUtils.fromFXImage(img, null), 0, 0, width, height, x, y, x + width, y + height, null);
    return bi;
}
    
    public static Image convertBuffered(BufferedImage bi){
        
        return SwingFXUtils.toFXImage(bi, null);
    }
    
    

}

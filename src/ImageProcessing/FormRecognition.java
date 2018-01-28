/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import GUI.Storage.Grid;
import java.awt.image.BufferedImage;
import java.util.List;
import javafx.scene.image.Image;
import neural.Layer;
import neural.TrainingSet;

/**
 *
 * @author mathew
 */
public class FormRecognition {
    private Image FORM;
    private Layer NEURAL;
    private RecognitionOutput OUT = new RecognitionOutput();
    private int BINARY;
    public FormRecognition(Image form, Layer neural, int binaryThresh){
        FORM = form;
        NEURAL = neural;
        BINARY = binaryThresh;
    }
    
    
    
    public RecognitionOutput startJob(){
        BufferedImage bi = ImageTools.convertImgToBuf(FORM);
        bi = ImageTools.toGreyScale(bi, true, BINARY);
        List<PixelFormation> pf = ImageTools.findEnclosedPixels(bi);
        OUT.formations = ImageTools.sortLeftToRight(pf);
        
        for(PixelFormation p : OUT.formations) OUT.CHARS.add(this.evaluateCharacter(bi, p, NEURAL));
        System.out.println("Done.");
        
        return OUT;
    }
    
    public String evaluateCharacter(BufferedImage img, PixelFormation p, Layer neural){
        img = ImageTools.cropImage(img, (int)p.getBounds().getX(), (int)p.getBounds().getY(),(int) p.getBounds().getWidth(), (int)p.getBounds().getHeight());
        Grid<Double> binary = ImageTools.imageToBinaryGrid(img, 30,30, BINARY);
        List<Double> out = neural.forwardProp(new TrainingSet(binary.getList(), 0, neural.NEURONS.size()));
        double biggest = out.get(0);
        int neuronPos = 0;
        for (int i = 1; i < out.size(); i++) {
            if (out.get(i) > biggest) {
                biggest = out.get(i);
                neuronPos = i;
            }
        }
        return neural.NEURONS.get(neuronPos).NAME;
    }
}

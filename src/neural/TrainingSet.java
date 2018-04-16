/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neural;

import ImageProcessing.ImageTools;
import ImageProcessing.PixelFormation;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author mathew
 */
public class TrainingSet {
    
    public static void train (Layer l, PixelFormation pf, BufferedImage img, char toTrainAs, int thresh){
        int index =-1;
        for(int i =0; i< l.NEURONS.size(); i++){
            if(l.NEURONS.get(i).NAME.equals(toTrainAs+""))  index =i;
        }
        if(index != -1){
        Rectangle b = pf.getBounds();
        BufferedImage c = ImageTools.cropImage(img, (int)b.getX(), (int)b.getY(),(int) b.getWidth(), (int)b.getHeight());
        c= ImageTools.toGreyScale(c, true, thresh);
        List<Double> bin = ImageTools.imageToBinaryGrid(c, 30, 30, 127).getList();
        l.backwardProp(new TrainingSet(bin,index,l.NEURONS.size() ));
        }
        else{
            System.out.println("Error: Could not find character: "+toTrainAs);
        }
    }
    
    public List<Double> TRAINING_INPUTS;
    public List<Double> EXPECTED_OUTPUT;
    
    public TrainingSet(List<Double> toTrain ,int index, int size){
        
        EXPECTED_OUTPUT = new ArrayList<Double>();
        for(int i =0; i< size; i++){
            if(i ==index) EXPECTED_OUTPUT.add(1.0);
            else  EXPECTED_OUTPUT.add(0.0);
        }
        TRAINING_INPUTS = toTrain;
    }
}

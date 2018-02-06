/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import GUI.Storage.Grid;
import java.awt.image.BufferedImage;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javax.swing.JOptionPane;
import neural.Layer;
import neural.TrainingSet;

/**
 *
 * @author mathew
 */
public abstract class FormRecognition {
    private Image FORM;
    private Layer NEURAL;
    private RecognitionOutput OUT;
    private int BINARY;
    public FormRecognition(Image form, Layer neural, int binaryThresh){
        FORM = form;
        NEURAL = neural;
        BINARY = binaryThresh;
        
        Platform.runLater(()->{
        OUT = new RecognitionOutput(this);
        OUT.THRESHOLD = BINARY;
        });
       
    }
    public abstract void update(RecognitionOutput out);
    public abstract void complete(RecognitionOutput out);
    
    
    
    public RecognitionOutput startJob(){
        new Thread(()->{
            
               
        
        BufferedImage bi = ImageTools.convertImgToBuf(FORM);
        try{
        bi = ImageTools.toGreyScale(bi, true, BINARY);
        List<PixelFormation> pf = ImageTools.findEnclosedPixels(bi,OUT,0.5);
        if(pf ==null) throw new Error();
        
        SortingOutput sOut = ImageTools.sortLeftToRight(pf);
        OUT.FORMATIONS = sOut.FORMATIONS;
        for(AdditionalChar ch:sOut.FORMATTING) OUT.FORMATTING.add(ch); // Adds the additional chars (eg. '\n' or '\s')
        
       
        double inc = 0.5 /(double)OUT.FORMATIONS.size();
        for(int i =0; i< OUT.FORMATIONS.size(); i++) {
            try{
                String out = this.evaluateCharacter(bi, OUT.FORMATIONS.get(i), NEURAL);
                OUT.updateProgress(OUT.getProgress()+inc);
                
            OUT.CHARS.add(out);}
            catch(Exception e){
                System.out.println("Failed to read char");
                OUT.FORMATIONS.remove(i);
                i--;
            }
        
        }
        OUT.updateProgress(1.0);
              
                complete(OUT);
        System.out.println("Done.");
        
        } catch(Exception e){JOptionPane.showMessageDialog(null, "Processing Failed\nPlease check your parameters:\nEnsure the binary threshold is ajusted to reduce noise and to enhance charactar thickness\nAlso make sure you have loaded a neural network");}
        }).start();
        return OUT;
    }
   
    
    public String evaluateCharacter(BufferedImage img, PixelFormation p, Layer neural){
        img = ImageTools.cropImage(img, (int)p.getBounds().getX(), (int)p.getBounds().getY(),(int) p.getBounds().getWidth(), (int)p.getBounds().getHeight());
        Grid<Double> binary = ImageTools.imageToBinaryGrid(img, 30,30, BINARY);
        List<Double> out = neural.forwardProp(new TrainingSet(binary.getList(), 0, neural.NEURONS.size()));
        double biggest = out.get(0);
        int neuronPos = 0;
        double acc =biggest;
        for (int i = 1; i < out.size(); i++) {
            acc+=out.get(i);
            if (out.get(i) > biggest) {
                biggest = out.get(i);
                neuronPos = i;
            }
        }
        OUT.ACC_PROB+=(biggest/acc);
        OUT.TOTAL_PROB++;
        System.out.println(biggest/acc);
        return neural.NEURONS.get(neuronPos).NAME;
    }
}

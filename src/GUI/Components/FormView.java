/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Components;

import GUI.FormRecognitionController;
import ImageProcessing.ImageTools;
import ImageProcessing.PixelFormation;
import ImageProcessing.RecognitionOutput;
import Tools.GraphicsTools;
import java.awt.image.BufferedImage;
import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javax.swing.JOptionPane;
import neural.Layer;
import neural.TrainingSet;

/**
 *
 * @author mathew
 */
public class FormView extends ImageView{
    RecognitionOutput OUT = null;
    int THRESHOLD = 127;
    FormRecognitionController CON = null;
    public FormView(Image i, Canvas canv, FormRecognitionController con) {
        super(i, canv);
        CON = con;
    }
    
    public void displayJobOutput(RecognitionOutput out, int binaryThresh){
        OUT = out;
        THRESHOLD = binaryThresh;
        render();
    }

    @Override
    void tick(GraphicsContext g) {
        g.setStroke(Paint.valueOf("black"));
        g.setFill(Paint.valueOf("transparent"));
        g.setLineWidth(1.0);
        if(OUT !=null){
            drawFormationBounds(g, OUT.FORMATIONS, X_OFF, Y_OFF, SCALE,OUT.CHARS);
           
        }
    }

    @Override
    void clicked(MouseEvent m) {
       if(m.getClickCount() ==2 ){
           new Thread(()->{
               PixelFormation p = findFormIntercept(m,OUT.FORMATIONS);
               
               if(p!=null){
               BufferedImage bin = ImageTools.convertImgToBuf(this.ORIGINAL);
               bin = ImageTools.toGreyScale(bin, true, this.THRESHOLD);
               
               bin =ImageTools.cropImage(bin,(int) p.getBounds().getX(), (int)p.getBounds().getY(),(int)p.getBounds().getWidth(), (int)p.getBounds().getHeight());
               String ch = JOptionPane.showInputDialog("Input char to train as");
               
               int selected = -1;
               if(ch.length() ==1)for(int i =0; i< CON.FILE.NEURONS.size();i++) if(CON.FILE.NEURONS.get(i).NAME.equals(ch)) selected = i;
                  
                 if(selected != -1)  {
                     CON.FILE.backwardProp(new TrainingSet(ImageTools.imageToBinaryGrid(bin, 30, 30, 127).getList(), selected, CON.FILE.NEURONS.size()));
                     CON.setModified();
                 }
               
                 else JOptionPane.showConfirmDialog(null, "Invalid Input");
               
               }
           
           
           }).start();
       }
    }

    @Override
    void scroll(Event event) {
        
    }

    @Override
    void pressed(MouseEvent event) {
        
    }

    @Override
    void released(MouseEvent event) {
        
    }

    @Override
    boolean dragged(MouseEvent event) {
        return true;
    }
    
}

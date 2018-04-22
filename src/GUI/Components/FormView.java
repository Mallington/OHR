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
 * This particular extension of image view is used for displaying the output of a job that has been processed, it will display published outputs
 * such as character location and also allows for real-time updating by the instigating class, so that job output can be published in real time,
 * it is also possible for the user to train certain characters through the same interface.
 * @author mathew
 */
public class FormView extends ImageView{
    /**
     * When a job is either processing or complete, the output is contained in this class and then used to update the UI
     */
    RecognitionOutput OUT = null;
    int THRESHOLD = 127;
    /**
     * Contains the controller class it was instigated by, so that its fields can be used as parameters when retraining the network upon user request
     */
    FormRecognitionController CON = null;
    /**
     * Creates a new form view instance passing the canvas to the image view it is inheriting
     * @param i
     * @param canv
     * @param con 
     */
    public FormView(Image i, Canvas canv, FormRecognitionController con) {
        super(i, canv);
        CON = con;
    }
    /**
     * Allows threshold to be set for training
     * @param thresh 
     */
    public void setThreshold(int thresh){
       THRESHOLD = thresh;
    }
    /**
     * Updates the form view, redrawing any character boxes based on the new Recognition Output
     * @param out
     * @param binaryThresh 
     */
    public void displayJobOutput(RecognitionOutput out, int binaryThresh){
        OUT = out;
        THRESHOLD = binaryThresh;
        render();
    }
    /**
     * This is executed by the inherited class ImageView and allows for graphics to be drawn on top of the existing image view
     * @param g 
     */
    @Override
    void tick(GraphicsContext g) {
        g.setStroke(Paint.valueOf("black"));
        g.setFill(Paint.valueOf("transparent"));
        g.setLineWidth(1.0);
        if(OUT !=null){
            drawFormationBounds(g, OUT.FORMATIONS, X_OFF, Y_OFF, SCALE,OUT.CHARS);
           
        }
    }
    /**
     * This particular mouse event listens for a double click and allows for training the clicked character
     * @param m 
     */
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
               
                 else JOptionPane.showMessageDialog(null, "Invalid Input");
               
               }
           
           
           }).start();
       }
    }
// These methods will be used for future developement
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

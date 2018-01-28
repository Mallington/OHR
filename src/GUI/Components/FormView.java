/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Components;

import ImageProcessing.RecognitionOutput;
import Tools.GraphicsTools;
import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

/**
 *
 * @author mathew
 */
public class FormView extends ImageView{
    RecognitionOutput OUT = null;
    public FormView(Image i, Canvas canv) {
        super(i, canv);
    }
    
    public void displayJobOutput(RecognitionOutput out){
        OUT = out;
        render();
    }

    @Override
    void tick(GraphicsContext g) {
        g.setStroke(Paint.valueOf("grey"));
        g.setFill(Paint.valueOf("transparent"));
        g.setLineWidth(1.0);
        if(OUT !=null)drawFormationBounds(g, OUT.formations, X_OFF, Y_OFF, SCALE,OUT.CHARS);
    }

    @Override
    void clicked(MouseEvent m) {
       
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

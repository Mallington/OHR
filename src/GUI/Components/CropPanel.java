/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Components;

import GUI.Storage.Grid;
import java.awt.Color;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Paint;


/**
 *
 * @author 160471
 */
public class CropPanel {

    private Canvas CANVAS;
    private boolean GEN_FRAME = true;
    private Paint BACK_COLOUR = Paint.valueOf("BLACK");
    private Image DOCUMENT;
    private double X_OFF =0;
    private double Y_OFF =0;
    private double SCALE= 1.0;
    
    public CropPanel(Image i, Canvas canv) {
        
        CANVAS = canv;
        DOCUMENT = i;
        addListeners();
        tick();

        
    }
    
    public void setScale(double scale){
        SCALE = scale;
        tick();
    }
    
    public void setBackgroundColour(Paint c){
        BACK_COLOUR = c;
        this.tick();
    }
   
    public void loadImage(Image img){
        this.DOCUMENT = img;
        tick();
    }
    
    public Image getImage(){
        return this.DOCUMENT; // Need to put in cropping
    }
    

   

    private void addListeners() {
        CANVAS.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("Dragged");
               
            }
        });
        CANVAS.setOnScroll(new EventHandler() {
            @Override
            public void handle(Event event) {
                ScrollEvent s = (ScrollEvent) event;
                
              X_OFF +=s.getDeltaX();
                Y_OFF += s.getDeltaY();
                
                tick();
               
            }
    });
       
        /*
        CANVAS.getParent().getScene().widthProperty().addListener(new ChangeListener<Number>() {
    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
        System.out.println("Width: " + newSceneWidth);
    }
});*/

       
    }

    private void tick() {
        if (GEN_FRAME) {

            GraphicsContext g = CANVAS.getGraphicsContext2D();
            
            g.setFill(this.BACK_COLOUR);
           
          
            g.fillRect(0, 0,CANVAS.getWidth(), CANVAS.getHeight());
            g.scale(SCALE, SCALE);
            g.drawImage(DOCUMENT, this.X_OFF,this.Y_OFF);
             g.scale(1.0/SCALE, 1.0/SCALE); //reverts scale back to original
            
        }
    }

   
}

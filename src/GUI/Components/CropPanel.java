/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Components;

import GUI.Storage.Grid;
import GUI.Storage.Point;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


/**
 *
 * @author 160471
 */
public class CropPanel {

    private Canvas CANVAS;
    private boolean GEN_FRAME = true;
    private Paint BACK_COLOUR = Paint.valueOf("WHITE");
    private Image DOCUMENT;
    private double X_OFF =0;
    private double Y_OFF =0;
    private double SCALE= 1.0;
    
    private int RAT_X;
    private int RAT_Y;
    
    private Point prev = null;
    
    private Rectangle CROP_BOUND;
    
    public CropPanel(Image i, Canvas canv, int ratX, int ratY) {
        RAT_X = ratX;
        RAT_Y = ratY;
        
        
        CANVAS = canv;
        DOCUMENT = i;
        double height = CANVAS.getHeight() *0.8;
        double width = (height/ratY)*ratX;
        CROP_BOUND = new Rectangle((CANVAS.getWidth()/2)-(width/2),(CANVAS.getHeight()/2)-(height/2),width,height);
        
        
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
                if(!(prev == null)){
                    X_OFF += (event.getX() - prev.getX());
                    Y_OFF += (event.getY() - prev.getY());
                }
                
                prev = new Point(event.getX(),event.getY());
               
                tick();
            }
        });
         
         CANVAS.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
            
               prev = null;
            }
        });
         
         CANVAS.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                Color c = DOCUMENT.getPixelReader().getColor((int)event.getX(),(int) event.getY());
            System.out.println(c.getRed()+", "+c.getGreen()+", "+c.getBlue());
              
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
            
            drawImage(g);
            drawCropArea(g, this.CROP_BOUND);
           
            
        }
    }
    public void drawImage(GraphicsContext g){
           g.scale(SCALE, SCALE);
            g.drawImage(DOCUMENT, this.X_OFF,this.Y_OFF);
             g.scale(1.0/SCALE, 1.0/SCALE); //reverts scale back to original
    }
    
    public void drawCropArea(GraphicsContext g, Rectangle rect){
        g.setStroke(Color.BLACK);
        g.setFill(Color.TRANSPARENT);
        g.setLineWidth(3.0);
        g.setLineDashes(10);
        
        g.beginPath();
        g.lineTo(rect.getX(), rect.getY());
        g.lineTo(rect.getX()+rect.getWidth(), rect.getY());
        g.lineTo(rect.getX()+rect.getWidth(), rect.getY()+rect.getHeight());
        g.lineTo(rect.getX(), rect.getY()+rect.getHeight());
        g.lineTo(rect.getX(), rect.getY());
        g.stroke();
        
        
        
        
    }
   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Components;

import Tools.GraphicsTools;
import GUI.Storage.Point;
import ImageProcessing.PixelFormation;
import static Tools.GraphicsTools.pointIntersects;
import java.util.List;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author mathew
 */
public abstract class ImageView extends GraphicsTools {

    protected Canvas CANVAS;
    protected double X_OFF = 0;
    protected double Y_OFF = 0;
    protected double SCALE = 1.0;
    protected Image ORIGINAL;
    protected Image DOCUMENT;
    protected Point prev = null;
    protected Paint BACK_COLOUR = Paint.valueOf("WHITE");
    public ImageView(Image i, Canvas canv) {
        CANVAS = canv;
        ORIGINAL = i;
        DOCUMENT = ORIGINAL;

        Platform.runLater(() -> {
            addListeners();
            render();

        });
    }
    public PixelFormation findFormIntercept(MouseEvent m, List<PixelFormation> formations){
        if(formations == null) return null;
        for(PixelFormation p : formations) {
            Rectangle b = p.getBounds();
            if(pointIntersects(m.getX()/SCALE - X_OFF, m.getY()/SCALE - Y_OFF, b.getX(), b.getY(), b.getWidth(), b.getHeight())) return p;
        }
        return null;
            
        
    }
    
     public void loadImage(Image img){
        this.DOCUMENT = img;
        this.ORIGINAL = img;
        render();
    }
    
    
    public void setBackgroundColour(Paint c){
        BACK_COLOUR = c;
        render();
    }
    
    
    public void setScale(double scale){
        SCALE = scale;
        render();
    }

    void addListeners() {
        CANVAS.setOnMouseClicked(m->clicked(m));

        CANVAS.addEventHandler(MouseEvent.MOUSE_DRAGGED,
               event -> {
                if (!(prev == null)) {
                    boolean moveImage = dragged(event);
                    if (moveImage) {
                        X_OFF += (event.getX() - prev.getX())/SCALE;
                        Y_OFF += (event.getY() - prev.getY())/SCALE;
                    }
                }
                prev = new Point(event.getX(), event.getY());
                render();
        });

        CANVAS.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                prev = null;
                released(event);
            }
        });

        CANVAS.addEventHandler(MouseEvent.MOUSE_PRESSED, event->pressed(event));

        CANVAS.setOnScroll(event ->{
                ScrollEvent s = (ScrollEvent) event;
                X_OFF += s.getDeltaX()/SCALE;
                Y_OFF += s.getDeltaY()/SCALE;
                render();
        });

    }

    public void render() {
        GraphicsContext g = CANVAS.getGraphicsContext2D();
         g.setFill(this.BACK_COLOUR);
         g.fillRect(0, 0,CANVAS.getWidth(), CANVAS.getHeight());
        drawImage(g);
        tick(g);
    }
    
    public void drawImage(GraphicsContext g){
        
           g.scale(SCALE, SCALE);
            g.drawImage(DOCUMENT, this.X_OFF,this.Y_OFF);
             g.scale(1.0/SCALE, 1.0/SCALE); //reverts scale back to original
    }

    abstract void tick(GraphicsContext g);

    abstract void clicked(MouseEvent m);

    abstract void scroll(Event event);

    abstract void pressed(MouseEvent event);

    abstract void released(MouseEvent event);

    abstract boolean dragged(MouseEvent event);
}

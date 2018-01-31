/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Components;

import GUI.Storage.Grid;
import GUI.Storage.Point;
import ImageProcessing.ImageTools;
import ImageProcessing.PixelFormation;
import Tools.GraphicsTools;
import java.awt.image.BufferedImage;
import java.util.List;


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
public class CropPanel extends ImageView{

    
    private boolean GEN_FRAME = true;
   
    
    
    
    private int RAT_X;
    private int RAT_Y;
    
   
    
    private Rectangle CROP_BOUND;
    
    private boolean moveCrop = false;
    
    List<PixelFormation> FORMATIONS = null;
    PixelFormation PRIMARY_FORM = null;
    
    public CropPanel(Image i, Canvas canv, int ratX, int ratY) {
        super(i,canv);
        RAT_X = ratX;
        RAT_Y = ratY;
        
        
        
        
        double height = CANVAS.getHeight() *0.8;
        double width = (height/ratY)*ratX;
        CROP_BOUND = new Rectangle((CANVAS.getWidth()/2)-(width/2),(CANVAS.getHeight()/2)-(height/2),width,height);
        
        
        
        
    }
    
    
    
    public void fitFormation(PixelFormation p){
       Rectangle r = squareFitFormation(p);
       CROP_BOUND = new Rectangle((this.X_OFF+r.getX())*SCALE, (this.Y_OFF+r.getY())*SCALE, r.getWidth()*SCALE, r.getHeight()*SCALE);
    }   
    
    
   
   
    
    public Image getImage(){
        double x = ( (this.CROP_BOUND.getX()-(this.X_OFF*SCALE))) /SCALE;
        double y =  ((this.CROP_BOUND.getY()- (this.Y_OFF*SCALE)))/SCALE;
        double width = this.CROP_BOUND.getWidth()/SCALE;
        double height = this.CROP_BOUND.getHeight()/SCALE;
        
       return ImageTools.convertBuffered(ImageTools.cropImage(this.DOCUMENT, (int)x, (int)y, (int)width, (int)height));
       // return this.DOCUMENT;
      
    }
    

    
    public void modifyImg(int threshold, boolean binarise, boolean detectForms){
        
        if(binarise){
        BufferedImage toRender = ImageTools.convertImgToBuf(ORIGINAL);
       
        toRender = ImageTools.toGreyScale(toRender, true, threshold);
        DOCUMENT = ImageTools.convertBuffered(toRender);
          
        if(detectForms) {
            System.out.println("Finding Forms");
            FORMATIONS = ImageTools.findEnclosedPixels(toRender);
            FORMATIONS = ImageTools.sortLeftToRight(FORMATIONS);
        }
        }
        else{
            DOCUMENT = ORIGINAL;
        }
        render();
    }
   
    
    
    

    
     
    
   

    @Override
     void tick(GraphicsContext g) {
        if (GEN_FRAME) {
            
            
            if(this.prev != null && !this.moveCrop){
                g.setStroke(Color.TRANSPARENT);
                g.setFill(Color.rgb(0, 100,125, 0.5));
                this.drawRect(g, new Rectangle(this.X_OFF*SCALE, this.Y_OFF*SCALE, DOCUMENT.getWidth()*SCALE, DOCUMENT.getHeight()*SCALE));
            }
            
            drawCropArea(g, this.CROP_BOUND);
           
            if(this.FORMATIONS !=null) {
                this.drawFormationBounds(g, FORMATIONS);
            }
        }
    }
    
    
    
    
    
    private void drawFormationBounds(GraphicsContext g, List<PixelFormation> formation){
       
        if(this.PRIMARY_FORM == null ) drawFormationBounds(g, formation, X_OFF, Y_OFF, SCALE,null);
        
    }
    
    
    
    
    
    public void drawCropArea(GraphicsContext g, Rectangle rect){
        
        g.setStroke(Color.BLACK);
        
        if(moveCrop)g.setFill(Color.rgb(0, 100,125, 0.5));
        else g.setFill(Color.TRANSPARENT);
        
        g.setLineWidth(3.0);
        g.setLineDashes(10);
        
        drawRect(g, rect);
        
        
        
        
    }

    @Override
    void clicked(MouseEvent m) {
          PixelFormation clickedForm = findFormIntercept(m, FORMATIONS);
        
        if(clickedForm != null){
        if(m.getClickCount() == 1){
            PRIMARY_FORM = clickedForm;
            
            render();
        }
        if(m.getClickCount() == 2){
            PRIMARY_FORM = clickedForm;
            fitFormation(PRIMARY_FORM);
            render();
        }
        }
    }

    @Override
    void scroll(Event event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void pressed(MouseEvent event) {
         System.out.println("Starting");
        
                boolean intersectsDoc = pointIntersects(event.getX(),event.getY(), X_OFF, Y_OFF, DOCUMENT.getWidth()*SCALE, DOCUMENT.getHeight()*SCALE);
                boolean intersectsCrop = pointIntersects(event.getX(),event.getY(), CROP_BOUND.getX(), CROP_BOUND.getY(), CROP_BOUND.getWidth(), CROP_BOUND.getHeight());
              if(intersectsCrop) moveCrop = true;
              if (intersectsDoc && !intersectsCrop) moveCrop = false;
    }

    @Override
    void released(MouseEvent event) {
        System.out.println("Mouse released");
               moveCrop = false;
               render();
    }

    @Override
    boolean dragged(MouseEvent event) {
        if(moveCrop){
                        CROP_BOUND.setX(CROP_BOUND.getX()+(event.getX() - prev.getX()));
                        CROP_BOUND.setY(CROP_BOUND.getY()+(event.getY() - prev.getY()));
                    }
        return !moveCrop;
    }
   
}

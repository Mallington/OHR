/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import ImageProcessing.PixelFormation;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author mathew
 */
public class GraphicsTools {
    public static void drawRect(GraphicsContext g, Rectangle rect){
        g.beginPath();
        g.lineTo(rect.getX(), rect.getY());
        g.lineTo(rect.getX()+rect.getWidth(), rect.getY());
        g.lineTo(rect.getX()+rect.getWidth(), rect.getY()+rect.getHeight());
        g.lineTo(rect.getX(), rect.getY()+rect.getHeight());
        g.lineTo(rect.getX(), rect.getY());
        g.stroke();
        g.fill();
    }
    
     public static boolean pointIntersects(double xp, double yp, double xi, double yi, double width, double height){
        if((xp>xi)&&(yp>yi)&&(xp<xi+width)&&(yp<yi+height)) return true;
        
        return false;
    }
     public Rectangle squareFitFormation(PixelFormation p){
          Rectangle b = p.getBounds();
        Rectangle r;
        double xOff = 0;
        double yOff =0;
        
        double x=0;
        if(b.getWidth()> b.getHeight())  {
            x = b.getWidth();
            yOff = (x-b.getHeight())/2.0;
        } 
        else {
            x = b.getHeight();
            xOff = (x-b.getWidth())/2.0;
        }
        return new Rectangle(b.getX()-xOff,b.getY()-yOff,x,x);
     }
     
   
     
     public static void drawFormationBounds(GraphicsContext g, List<PixelFormation> formation, double X_OFF, double Y_OFF, double SCALE, List<String> LABELS){
      
        g.setStroke(Paint.valueOf("Grey"));
        int i=0;
        g.setLineDashes(null);
        g.setLineWidth(1);
        for(PixelFormation f : formation) {
          
             Rectangle r = f.getBounds();
             drawRect(g, new Rectangle((X_OFF+r.getX())*SCALE, (Y_OFF+r.getY())*SCALE, r.getWidth()*SCALE, r.getHeight()*SCALE));
            String label = "";
            if(LABELS !=null) label = LABELS.get(i);
             g.strokeText("("+label+") F: "+i++, (r.getX()+r.getWidth()+X_OFF)*SCALE, (r.getY()+r.getHeight()+Y_OFF)*SCALE);
        }
        
    }
     
     public static void drawPixelFormations(GraphicsContext g, List<PixelFormation> pF){
      for(PixelFormation p: pF) for(java.awt.Point po : p.getPoints()) g.getPixelWriter().setColor(po.x, po.y, Color.BLUE);
        
    }
}
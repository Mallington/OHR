/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;


import java.awt.Point;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author mathew
 */
public class PixelFormation {
    private List<Point> POINTS;
    
    public PixelFormation(){
        POINTS = new ArrayList<Point>();
    }
    
    public Rectangle getBounds(){ // need to implement
        Point topLeft = POINTS.get(0);
        Point bottomRight = POINTS.get(0);
        
        for(Point p: POINTS) {
            if(p.x < topLeft.x) topLeft = new Point(p.x, topLeft.y);
            if(p.y < topLeft.y)topLeft = new Point( topLeft.x, p.y);
            
            if(p.x > bottomRight.x) bottomRight = new Point(p.x, bottomRight.y);
            if(p.y > bottomRight.y)bottomRight = new Point( bottomRight.x, p.y);
                
            
            
        }
        
        
        return new Rectangle(topLeft.x, topLeft.y, bottomRight.x -topLeft.x , bottomRight.y -topLeft.y);
    }
    
    public List<Point> getPoints(){
        return  POINTS;
    }
    
    public void addPoint(int x, int y){
        POINTS.add(new Point(x,y));
    }
    
    public boolean isEmpty(){
        return (POINTS.size() == 0) ;
    }
}

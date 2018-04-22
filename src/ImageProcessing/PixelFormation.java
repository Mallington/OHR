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
 * This class is used for keeping track of all of the pixels in a formation, it can be used to mark the bound for characters 
 * @author mathew
 */
public class PixelFormation {
    private List<Point> POINTS;
    private boolean modified = true;
    private Rectangle lastBounds = null;
    public PixelFormation(){
        POINTS = new ArrayList<Point>();
    }
    /**
     * Gets the bounds of all of the pixel formations
     * @return 
     */
    public Rectangle getBounds(){ // need to implement
        
        if(modified){
        Point topLeft = POINTS.get(0);
        Point bottomRight = POINTS.get(0);
        
        for(Point p: POINTS) {
            if(p.x < topLeft.x) topLeft = new Point(p.x, topLeft.y);
            if(p.y < topLeft.y)topLeft = new Point( topLeft.x, p.y);
            
            if(p.x > bottomRight.x) bottomRight = new Point(p.x, bottomRight.y);
            if(p.y > bottomRight.y)bottomRight = new Point( bottomRight.x, p.y);
        }
        lastBounds =  new Rectangle(topLeft.x, topLeft.y, bottomRight.x -topLeft.x , bottomRight.y -topLeft.y);
        modified = false;
        }
       return lastBounds;
    }
    /**
     * Gets all of the points collected
     * @return 
     */
    public List<Point> getPoints(){
        return  POINTS;
    }
    /**
     * Adds a new point to the formations
     * @param x
     * @param y      */
    public void addPoint(int x, int y){
        modified = true;
        POINTS.add(new Point(x,y));
    }
    /**
     * Returns whether no pixels have been added to the formation
     * @return 
     */
    public boolean isEmpty(){
        return (POINTS.size() == 0) ;
    }
}

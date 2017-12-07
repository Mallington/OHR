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
            if(p.x < topLeft.x && p.y < topLeft.y ){
                topLeft = p;
            }
            if(p.x > bottomRight.x && p.y > bottomRight.y ){
                bottomRight = p;
            }
        }
        
        
        return new Rectangle(topLeft.x, topLeft.y, bottomRight.x -topLeft.x , bottomRight.y -topLeft.y);
    }
    
    public void addPoint(int x, int y){
        POINTS.add(new Point(x,y));
    }
    
    public boolean isEmpty(){
        return (POINTS.size() == 0) ;
    }
}

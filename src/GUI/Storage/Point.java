/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Storage;

/**
 * Used for storing x,y coordinate
 * @author mathew
 */
public class Point {
    private double X;
    private double Y;
    public Point(double x, double y){
        X = x;
        Y = y;
    }
    
    public double getX(){
        return X;
    }
    public double getY(){
        return Y;
    }
}

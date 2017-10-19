/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Storage;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 160471
 */
public class Grid <obj>{
private int X_LENGTH;
private int Y_LENGTH;
private List<obj> data;

public Grid(int x, int y, obj init){
    data = new ArrayList<obj>();
    for(int i =0; i< x*y; i++) data.add(init);
    X_LENGTH = x;
    Y_LENGTH =y;
}

public void setList(List<obj> list){
    data = list;
}
public List<obj> getList(){
    return data;
}

public void set(int x, int y, obj value){
    data.set(convertPoints(x,y), value);
}

public obj get(int x, int y){
    return data.get(convertPoints(x,y));
}
public void showContents(){
    for(int y=0; y< Y_LENGTH; y++) {
        
        for(int x=0; x<X_LENGTH; x++){
            System.out.print(data.get(convertPoints(x,y))+", ");
        }
        System.out.println("");
    }
}

private int convertPoints(int x, int y){
    
    return (x*Y_LENGTH)+y;
}
    

  public static void main (String[] args){
      Grid<Double> g = new Grid <Double>(4,6, 0.0);
      g.set(3, 2, 11.0);
      g.set(0, 0, 323.0);
      g.showContents();
  }

}

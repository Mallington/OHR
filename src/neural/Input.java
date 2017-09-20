/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neural;

import java.io.Serializable;

/**
 *
 * @author mathew
 */
public class Input implements Serializable{
    double inp= 0;
    double weight= 0;
    String label;
    public Input(double inp, double weight){
        this.inp = inp;
        this.weight = weight;
    }
    
     public Input(double inp){
        this.inp = inp;
    }
    
    public void setLabel(String l){
        label = l;
    }
}

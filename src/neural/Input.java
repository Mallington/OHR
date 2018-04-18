/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neural;

import java.io.Serializable;

/**
 * This class contains the current state of a particular input to a neuron, also contains the
 * weighting of this particular input which dictates significance of the input being in a 
 * certain state, where the weighting is a high number, it is of high significance to that 
 * particular neuron.
 * @author mathew
 */
public class Input implements Serializable{
    /**
     * Current input state
     */
    double inp= 0.0;
    /**
     * Dictates significance of this particular input
     */
    double weight= 0.0;
    String label;
    public Input(double inp, double weight){
        this.inp = inp;
        this.weight = weight;
    }
    public double getWeight(){
        return this.weight;
    }
    
     public Input(double inp){
        this.inp = inp;
    }
    
    public void setLabel(String l){
        label = l;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neural;

import GUI.BinaryGrid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;

/**
 *This class contains all of the neurons that are each independently responsible for a particular
 * detection/ classification, this used as the containing class, so that this entire object can be
 * serialised and written to a file
 * @author mathew
 */

public class Layer implements Serializable{
    /**
     * List of all of the neurons in this layer
     */
    public List<Neuron> NEURONS;
    /**
     * This contains the calculate outputs of all of the neurons
     */
    public List<Double> currentOut;
    /**
     * When training, this is used to contain the expected outputs
     */
    public List<Double> EXPECTED;
   // public String CHAR_SET ="";
    /**
     * This create a new layer
     */
    public Layer(){
        NEURONS = new ArrayList<Neuron>();
    }
    public Layer(List<Neuron> neurons){
        NEURONS = neurons;
    }
    /**
     * This sets the labels of all of the neurons
     * @param charSet 
     */
    public void setCharSet(String charSet){
        int i =0;
        for(char c: charSet.toCharArray()) {
            
            this.NEURONS.get(i++).NAME = c+"";
                    }
    }
    
    /**
     * This generates a list of all of the outputs of the neurons
     * @param train
     * @return 
     */
    public List<Double> forwardProp(TrainingSet train){
        setInputs(train);
        List<Double> out = getOutputs();
        //for(Double d: out) System.out.println("Y = "+d);
        return out;
    }
    /**
     * This adjusts the weightings of each neuron in the network, so that the produced
     * output in each will be closer to a desired value
     * @param train 
     */
    public void backwardProp(TrainingSet train){
        currentOut = forwardProp(train);
        System.out.println("Output array size "+currentOut.size());
        for(int i =0; i< currentOut.size(); i++) System.out.println(NEURONS.get(i).toString()+" | Out: "+currentOut.get(i));
        
        List<Double> deltas = new ArrayList<Double>();
       
        System.out.println("Expected output sizes "+ train.EXPECTED_OUTPUT.size()+"  Current out size" + this.currentOut.size());
        for(int i =0; i< currentOut.size(); i++) deltas.add(train.EXPECTED_OUTPUT.get(i)- currentOut.get(i));
       
         for(int i =0; i< deltas.size(); i++){
             this.NEURONS.get(i).ajustWeights(deltas.get(i));
         }
           
        
            //currentOutput = currentOutput - train
    }
    
    /**
     * This gets the outputs of all of the neurons
     * @return 
     */
    private List<Double> getOutputs(){
        List<Double> out = new ArrayList<Double>();
        for(Neuron n: NEURONS) out.add(n.getOuput());
        return out;
    }
    
    private void setInputs(TrainingSet inp){
        //System.out.println("Inputs sizes"+ inp.TRAINING_INPUTS.size());
        for(Neuron n: NEURONS) for(int i=0; i< n.inputs.size();i++){
            n.inputs.get(i).inp = inp.TRAINING_INPUTS.get(i);
        }
        this.EXPECTED = inp.EXPECTED_OUTPUT;
        
    }
    
  //  public 
    
    
    public void generateRandomNeurons(int amount,int inputsPerNeuron){
        for(int i =0; i< amount; i++){ 
            NEURONS.add(new Neuron("Ran"+i, inputsPerNeuron));
        }
    }
    /**
     * Adds a neuron to the network
     * @param n  Neuron to add
     */
    public void add(Neuron n){
        NEURONS.add(n);
    }
    public void displayContents(){
        int count =1;
        for(Neuron n : NEURONS){
            System.out.println("-- Neuron "+count+" --");
            n.displayContents();
            count++;
    }
      
    }
    
    public static void main(String args[]){
        Layer l = new Layer();
        l.generateRandomNeurons(2, 4);
        
        JFrame f = new JFrame("Input");
        
        f.setResizable(false);
        BinaryGrid grid = new BinaryGrid(2,2);
        f.add(grid);
         f.setSize(500, 500);
        f.setVisible(true);
        
        
        try{Thread.sleep(5000);}catch(Exception e){}
        
        
        for(Double b: grid.getData());
       TrainingSet th = new TrainingSet(grid.getData(), 1,26);
        l.forwardProp(th);
        
        l.displayContents();
        
    }
    
}

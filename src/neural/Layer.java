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
 *
 * @author mathew
 */
public class Layer implements Serializable{
    public List<Neuron> NEURONS;
    public List<Double> currentOut;
    public List<Double> EXPECTED;
    public Layer(){
        NEURONS = new ArrayList<Neuron>();
    }
    public Layer(List<Neuron> neurons){
        NEURONS = neurons;
    }
    
    public List<Double> forwardProp(TrainingSet train){
        setInputs(train);
        List<Double> out = getOutputs();
        for(Double d: out) System.out.println("Y = "+d);
        return out;
    }
    
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
    
    
    private List<Double> getOutputs(){
        List<Double> out = new ArrayList<Double>();
        for(Neuron n: NEURONS) out.add(n.getOuput());
        return out;
    }
    
    private void setInputs(TrainingSet inp){
        System.out.println("Inputs sizes"+ inp.TRAINING_INPUTS.size());
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neural;

import Tools.MathFunc;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author mathew
 */
public class Neuron implements Serializable{
    public List<Input> inputs;
    public double biasWeight =1;
    double EXPECTED_VALUE;
    public double LEARNING_RATIO = 0.1;
    public String NAME = "";
    public Neuron(String name){
        inputs = new ArrayList<Input>();
        NAME = name;
    }
    public Neuron(String name,int inpNumber){
        NAME = name;
         inputs = new ArrayList<Input>();
        for(int i =0; i<inpNumber; i++){
            inputs.add(new Input(0));
        }
    }
    @Override
    public String toString(){
        return NAME;
    }
    public void setInputValues(List<Double> inpVals){
        System.out.println("Changing all "+inputs.size());
        for(int i =0; i< inputs.size(); i++){
            inputs.get(i).inp = inpVals.get(i);
        } 
    }
    
    public void setLearningRatio(double ratio){
        LEARNING_RATIO = ratio;
    }
    
    public Neuron(TrainingSet set){
         inputs = new ArrayList<Input>();
        for(double inp:set.TRAINING_INPUTS) inputs.add(new Input(inp,0));
        
    }
    public void generateRandomWeights(){
        biasWeight = Math.random();
        for(Input in: inputs) {
            int mult =-1;
            if(new Random().nextBoolean()) mult = 1;
            in.weight=(Math.random()*mult);
        }
    }
    
    public double getOuput(){
      return MathFunc.sigmoidValue(getCalculatedSum()+this.biasWeight);
    }
    public void displayContents(){
     for(Input in: inputs) System.out.println("[I: "+in.inp+", W: "+in.weight+"]");   
    }
    
    public void ajustWeights(double delta){
        System.out.println("delta "+delta+"\nLRatio"+this.LEARNING_RATIO+"\n");
        for(Input i: this.inputs){
               double before = i.weight;
                i.weight += delta *this.LEARNING_RATIO*i.inp;
                
        }
        this.biasWeight += delta*this.LEARNING_RATIO;
    }
    
    
    public double getCalculatedSum(){
        double ret = 0;
        
        for(int i =0; inputs.toArray().length> i; i++) ret += (inputs.get(i).inp*inputs.get(i).weight);
        
        return ret;
        
    }
}

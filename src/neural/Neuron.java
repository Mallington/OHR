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
 * Each neuron can be used as a particular classification to detect certain characters, each input
 * has a significant outcome on whether this particular neuron detects what it is looking for, when
 * it detects what it is trained to detect, the output of the neuron will be high.
 * @author mathew
 */
public class Neuron implements Serializable{
    /**
     * Contains a list of all of the inputs for this particular neuron, each input signifies a pixel
     * from an image of the character trying to be detected.
     */
    public List<Input> inputs;
    /**
     * This bias weight is used to shift the output of the neuron along the x-axis of the logistical 
     * sigmoid function included to calculate the out put of a Neuron
     */
    public double biasWeight =1;
    /**
     * This is used in training, to calculate the difference between the output that was presented,
     * out the output that it should actually be, this used to calculate delta
     */
    double EXPECTED_VALUE;
    /**
     * This ratio is multiplied by delta, and is used to control the rate at which a neuron learns 
     * from a particular dataset by shifting its weightings is a particular direction
     */
    public double LEARNING_RATIO = 0.1;
    /**
     * This is used to label each neuron for referencing what classification it is trying to detect,
     * for example it could be named "A" or "a"
     */
    public String NAME = "";
    public Neuron(String name){
        inputs = new ArrayList<Input>();
        NAME = name;
    }
    /**
     * Upon call it generates the specified number of inputs that can be trained and used to gain an output
     * @param name Neuron Name
     * @param inpNumber Number of inputs
     */
    public Neuron(String name,int inpNumber){
        NAME = name;
         inputs = new ArrayList<Input>();
        for(int i =0; i<inpNumber; i++){
            inputs.add(new Input(0));
        }
    }
    /**
     * Overrides the toString() method so that when a neuron is printed, it contains its name.
     * @return 
     */
    @Override
    public String toString(){
        return NAME;
    }
    /**
     * Sets the input values of the neuron
     * @param inpVals Input Values to set
     */
    public void setInputValues(List<Double> inpVals){
        System.out.println("Changing all "+inputs.size());
        for(int i =0; i< inputs.size(); i++){
            inputs.get(i).inp = inpVals.get(i);
        } 
    }
    /**
     * Sets the learning ratio
     * @param ratio  Learning Ratio to be set at
     */
    public void setLearningRatio(double ratio){  
        LEARNING_RATIO = ratio;
    }
    /**
     * Creates a neuron with specification to the Training set supplied
     * @param set 
     */
    public Neuron(TrainingSet set){
         inputs = new ArrayList<Input>();
        for(double inp:set.TRAINING_INPUTS) inputs.add(new Input(inp,0));
        
    }
    /**
     * Randomises the weights so that there is no clear distribution of weightings through out,
     * this is useful when training a whole layer of neurons
     */
    public void generateRandomWeights(){
        biasWeight = Math.random();
        for(Input in: inputs) {
            int mult =-1;
            if(new Random().nextBoolean()) mult = 1;
            in.weight=(Math.random()*mult);
        }
    }
    /**
     * This gets the calculate output of the neuron, this is the SigmoidFunc(delta(w*i)+ biasWeight)
     * @return 
     */
    public double getOuput(){
      return MathFunc.sigmoidValue(getCalculatedSum()+this.biasWeight);
    }
    /**
     * Prints out the contents of the weightings and inputs for debugging purposes
     */
    public void displayContents(){
     for(Input in: inputs) System.out.println("[I: "+in.inp+", W: "+in.weight+"]");   
    }
    /**
     * Delta (the difference between the actual output and the desired one ) is used as a sort of 
     * vector/ direction for the weightings to be moved in, in order to produce the desired output
     * for the neuron, this is done a multiple of the learning ratio so that the vector magnitude can
     * be controlled, It is then multiplied by the output, if the output is 0, no changes will be made
     * because in this state, the input has no significance because nothing was inputted here, when at
     * 1, the input needs to be changed.
     * @param delta 
     */
    public void ajustWeights(double delta){
        System.out.println("delta "+delta+"\nLRatio"+this.LEARNING_RATIO+"\n");
        for(Input i: this.inputs){
               double before = i.weight;
               //Adjusts the individual weightings
                i.weight += delta *this.LEARNING_RATIO*i.inp;
                
        }
        //Adjusts the overall bias weight
        this.biasWeight += delta*this.LEARNING_RATIO;
    }
    
   /**
    * 
    * @return Sum of weight*input
    */
    public double getCalculatedSum(){
        double ret = 0;
        
        for(int i =0; inputs.toArray().length> i; i++) ret += (inputs.get(i).inp*inputs.get(i).weight);
        
        return ret;
        
    }
}

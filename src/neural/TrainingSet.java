/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neural;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mathew
 */
public class TrainingSet {
    public List<Double> TRAINING_INPUTS;
    public List<Double> EXPECTED_OUTPUT;
    
    public TrainingSet(List<Double> toTrain ,int index, int size){
        
        EXPECTED_OUTPUT = new ArrayList<Double>();
        for(int i =0; i< size; i++){
            if(i ==index) EXPECTED_OUTPUT.add(1.0);
            else  EXPECTED_OUTPUT.add(0.0);
        }
        TRAINING_INPUTS = toTrain;
    }
}

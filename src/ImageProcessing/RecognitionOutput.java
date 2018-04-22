/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import MassCharacterRecognition.FormRecognition;
import java.util.ArrayList;
import java.util.List;

/**
 *The purpose of this class is to create a common object which the FormRecongition class returns
 * to the instigating class, allowing the instigating class to get a 'Real-time' update on the
 * pixel formations detected and the progress.
 * @author mathew
 */
public class RecognitionOutput {
    public RecognitionOutput(FormRecognition fr){
        FR = fr;
    }
    
    private FormRecognition FR = null;
    double PROGRESS = 0.0;
     /**
      * This is the most up to date list of the chars detected at the time of call
      **/
    public List<String> CHARS = new ArrayList<String>();
   
    /**Contains additional chars such as carrage returns etc.
     * */
     
    public List<AdditionalChar> FORMATTING = new ArrayList<AdditionalChar>();
    /**
     * All the detected formations
     */
    public List<PixelFormation> FORMATIONS;
    
    public double ACC_PROB =0.0;
    /**
     * States the overall certainty of the predictions
     */
    public double TOTAL_PROB=0;
    public int THRESHOLD = 127;
    /**
     * Time period before a another value is published
     */
    public int buffer = 300;
    /**
     * This is the system time for the next update
     */
    private long nextUpdate =0;
    
    public double getProgress(){
        return PROGRESS;
    }
    /**
     * Gets the text prediction for all of the charcters
     * @return 
     */
    public String getResultantString(){
        String append = "";
        for(int i =0; i< CHARS.size(); i++){
            
            append+= CHARS.get(i)+getAdditional(i);
           
        }
        return append;
    }
    /**
     * Adds any additional text referenced with a particular char such as '\n'
     * @param index
     * @return 
     */
    private String getAdditional(int index){
        for(AdditionalChar a: FORMATTING) if(a.INDEX == index) return a.ADDITION+"";
        
        return "";
    }
    /**
     * Requests a new update, if the buffer has completed, it will update
     */
    private void  requestUpdate(){
        if(System.currentTimeMillis()> nextUpdate || PROGRESS ==1.0){
            FR.update(this);
            nextUpdate = System.currentTimeMillis()+buffer;
        }
    }
    /**
     * Updates the progress and requests an update
     * @param prog 
     */
    public void updateProgress(double prog){
        PROGRESS = prog;
        requestUpdate();
       
    }
    /**
     * Gets the probability of the overall prediction
     * @return 
     */
    public double getProbability(){
    return ACC_PROB/TOTAL_PROB;
    }
}

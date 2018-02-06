/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mathew
 */
public class RecognitionOutput {
    public RecognitionOutput(FormRecognition fr){
        FR = fr;
    }
    
    private FormRecognition FR = null;
    double PROGRESS = 0.0;
    public List<String> CHARS = new ArrayList<String>();
    public List<AdditionalChar> FORMATTING = new ArrayList<AdditionalChar>();
    public List<PixelFormation> FORMATIONS;
    public double ACC_PROB =0.0;
    public double TOTAL_PROB=0;
    public int THRESHOLD = 127;
    public int buffer = 300;
    
    private long nextUpdate =0;
    
    public double getProgress(){
        return PROGRESS;
    }
    public String getResultantString(){
        String append = "";
        for(int i =0; i< CHARS.size(); i++){
            
            append+= getAdditional(i)+CHARS.get(i);
        }
        return append;
    }
    
    private String getAdditional(int index){
        for(AdditionalChar a: FORMATTING) if(a.INDEX == index) return a.ADDITION+"";
        
        return "";
    }
    private void  requestUpdate(){
        if(System.currentTimeMillis()> nextUpdate || PROGRESS ==1.0){
            FR.update(this);
            nextUpdate = System.currentTimeMillis()+buffer;
        }
    }
    public void updateProgress(double prog){
        PROGRESS = prog;
        requestUpdate();
       
    }
    public double getProbability(){
    return ACC_PROB/TOTAL_PROB;
    }
}

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
    public List<String> CHARS = new ArrayList<String>();
    public List<PixelFormation> formations;
    public double ACC_PROB =0.0;
    public double TOTAL_PROB=0;
    
    public double getProbability(){
    return ACC_PROB/TOTAL_PROB;
    }
}

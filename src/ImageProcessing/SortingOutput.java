/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import java.util.ArrayList;
import java.util.List;

/**
 * Similar to the Recognition Output, all of the pixel formations are published to this as they are being discovered, so that the array list in this object can
 * be called in real time and an update of what has been found can be displayed on a UI
 * @author mathew
 */
public class SortingOutput {
    public SortingOutput(List<AdditionalChar> formatting,List<PixelFormation> formations){
        FORMATTING = formatting;
        FORMATIONS = formations;
    }
    /**
     * Additional chars such as '\n'
     */
    public List<AdditionalChar> FORMATTING;
    /**
     * All the detected pixel masses
     */
    public List<PixelFormation> FORMATIONS;
}

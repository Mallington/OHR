/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

/**
 * This controller is responsible for controlling the output of the Output bar at the
 * bottom of the main window
 * @author mathew
 */
public class OutputController {
    /**
     * Text Area instance used for publishing text to
     */
    private TextArea TEXT;
    /**
     * Contains an array list of all of the published items
     */
    private List<String> CONTENTS;
   /**
    * Creates a  new controller passing the Text Area instance to it
    * @param text 
    */
    public OutputController(TextArea text){
      TEXT = text;
      CONTENTS = new ArrayList<String>();
      TEXT.setEditable(false);
      
    }
    
  /**
   * Allows for easy printing to the conasole
   * @param text 
   */
    public void print(String text){
       CONTENTS.add(text);
       update();
        System.out.println(text);
    }
    /**
     * Returns a complete list of all that has been published to the console
     * @return 
     */
    public List<String> getOutputList(){
        return CONTENTS;
    }
   
   /**
    * Used for updating the output console against the Array List - CONTENTS
    */ 
    private void update(){
        String st = "";
        for(String s:CONTENTS)  st+=s+"\n";
        TEXT.setText(st);
        TEXT.setScrollTop(Double.MAX_VALUE);
                
    }
}

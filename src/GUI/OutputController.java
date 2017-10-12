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
 *
 * @author mathew
 */
public class OutputController {
    private TextArea TEXT;
    private List<String> CONTENTS;
   
    public OutputController(TextArea text){
      TEXT = text;
      CONTENTS = new ArrayList<String>();
      TEXT.setEditable(false);
      
    }
    
  
    public void print(String text){
       CONTENTS.add(text);
       update();
        System.out.println(text);
    }
    public List<String> getOutputList(){
        return CONTENTS;
    }
   
    
    private void update(){
        String st = "";
        for(String s:CONTENTS)  st+=s+"\n";
        TEXT.setText(st);
        TEXT.setScrollTop(Double.MAX_VALUE);
                
    }
}

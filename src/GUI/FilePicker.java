/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.File;
import java.lang.reflect.Array;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author mathew
 */
public class FilePicker {
    public static int SAVE =0;
    public static int OPEN =1;
    

    private FileChooser PICKER;
    private String FILE_TYPE;
    public FilePicker(String fileType, String descriptor, String defaultName){
        PICKER = new FileChooser();
        PICKER.getExtensionFilters().add(new ExtensionFilter(descriptor+" ("+fileType+")","*"+fileType));
        PICKER.setInitialFileName(defaultName+fileType);
        FILE_TYPE = fileType;
    }
    public FilePicker(String descriptor,List<String> fileTypes){
        PICKER = new FileChooser();
        for(String fileType : fileTypes) PICKER.getExtensionFilters().add(new ExtensionFilter(descriptor+" ("+fileType+")","*"+fileType));
      
    }
    
    public File getFile(Stage s, int option){
        
        File f= null;
        
        if(option == SAVE){
            f= PICKER.showSaveDialog(s);
            if(!f.getName().contains(FILE_TYPE)){
            f = new File(f.getAbsoluteFile()+FILE_TYPE);
        }
        }
        else if(option == OPEN ) f= PICKER.showOpenDialog(s);
        else {
            f= null;
            System.out.println("Not a valid option!");
        }
        
            
        
        return f;
        
    }
    
    public static void main(String[] args){
        FilePicker fp = new FilePicker(".nns","neural net struct","Unitled");
    }
}
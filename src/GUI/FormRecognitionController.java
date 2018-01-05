/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import InterfaceManagement.TabAttributes;
import InterfaceManagement.ControllerInterface;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;

/**
 * FXML Controller class
 *
 * @author mathew
 */
public class FormRecognitionController extends TabAttributes implements Initializable{

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setFileType(".fs");
        this.setFileDes("Form Structure");
        this.setMenuItems(new ArrayList<Menu>(genMenus())); // Need to set menus
        
    }

   public List<Menu> genMenus(){
        
        
        Menu imp = new Menu("Import");
          MenuItem fScan = new MenuItem("Form Scan");
          MenuItem nStruct= new MenuItem("Network Structure");
          
          imp.getItems().addAll(fScan, nStruct);
        Menu runM = new Menu("Run");
          MenuItem runJ = new MenuItem("Run");
          MenuItem param= new MenuItem("Modify Parameters");
          runM.getItems().addAll(runJ, param);
          return Arrays.asList(imp, runM);
    }

    

    public void setGUI(Object file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }




    

    
}

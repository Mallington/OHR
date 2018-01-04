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
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
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
        this.setMenuItems(null); // Need to set menus
    }

   

    

    public void setGUI(Object file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }




    

    
}

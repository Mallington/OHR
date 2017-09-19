/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author mathew
 */
public class MainWindowController implements Initializable {

    @FXML
    private Tab TAB = new Tab();
    
   @FXML
    private Text TEX = new Text();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     TEX.setText("Hello");

   
    }  
    
    @FXML
    public void test() {
        System.out.println("Settign");
        TEX.setText("Hello");
        
    }
    
    
    
}

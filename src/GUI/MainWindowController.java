/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import InterfaceManagement.ControllerInterface;
import InterfaceManagement.Session;
import InterfaceManagement.TabController;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/**
 *
 * @author mathew
 */
public class MainWindowController implements Initializable{

    private Session SESSION = new Session();
    @FXML
    Text TEXT = new Text();
    @FXML
    TabPane TAB_VIEW = new TabPane();
    @FXML
    TextArea OUTPUT_TEXT = new TextArea();
    @FXML

    OutputController OUTPUT;

    public void newNeuralInterface() throws IOException, InterruptedException {
       int tabID = SESSION.addTab(TabController.createFromFXMLandLoadTab(TAB_VIEW, "NeuralNetInterface.fxml", OUTPUT));
        SESSION.getTabList().get(tabID).getController().setText("Untitled.txt");
        // SESSION.getTabList().get(tabID).getController().getTab().se
        
    }
    
    public void openNeuralNetwork(){
        
    }
    
    public void save(){
        try{
         SESSION.getTabList().get(SESSION.getSelectedID()).getController().save();
        }
        catch(Exception e){
            OUTPUT.print("That failed, are you sure that you have opened a window?");
        }
    }
    
    public void saveAs(){
      
       SESSION.getTabList().get(SESSION.getSelectedID()).getController().saveAsNew();
       
    }
   

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      OUTPUT = new OutputController(OUTPUT_TEXT);
      TAB_VIEW.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
    }

}

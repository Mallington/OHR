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
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    MenuBar MENU_BAR = new MenuBar();
    
    OutputController OUTPUT;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      OUTPUT = new OutputController(OUTPUT_TEXT);
      TAB_VIEW.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
      addSelectionModel(TAB_VIEW);
        try {
            int added = SESSION.addTab(TabController.createFromFXMLandLoadTab(TAB_VIEW, "WelcomeScreen.fxml", OUTPUT, this.MENU_BAR));
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addSelectionModel(TabPane pane){
        pane.getSelectionModel().selectedItemProperty().addListener(
    new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                
                if(oldValue != null )Platform.runLater( ()-> SESSION.getControllerFromTab(oldValue).deselectTab());
                if(newValue != null )Platform.runLater( ()-> SESSION.getControllerFromTab(newValue).selectTab());
            }
     
    }
);
        System.out.println("Added Change Listeners");
    }
    
    public void newNeuralInterface() throws IOException, InterruptedException {
       int tabID = SESSION.addTab(TabController.createFromFXMLandLoadTab(TAB_VIEW, "NeuralNetInterface.fxml", OUTPUT, this.MENU_BAR));
        SESSION.getTabList().get(tabID).getController().setText("Untitled.nns");
        // SESSION.getTabList().get(tabID).getController().getTab().se
        
    }
    public void newFormInterface() throws IOException{
         int tabID = SESSION.addTab(TabController.createFromFXMLandLoadTab(TAB_VIEW, "FormRecognition.fxml", OUTPUT, this.MENU_BAR));
         SESSION.getTabList().get(tabID).getController().setText("Untitled.ffs");
    }
    
    
    
    public void open(){
        
        try{
        FilePicker pick = new FilePicker("File ", Arrays.asList(".nns",".fs",".csv"));
        File picked = pick.getFile((Stage)this.TAB_VIEW.getScene().getWindow(), FilePicker.OPEN);
       
        if(picked.getName().contains(".nns")) {
            int added = SESSION.addTab(TabController.createFromFXMLandLoadTab(TAB_VIEW, "NeuralNetInterface.fxml", OUTPUT, this.MENU_BAR));
            SESSION.getTabList().get(added).getController().load(picked);
        }
        
        }
        catch(Exception e){
            OUTPUT.print("That failed, are you sure that you have opened a window?");
        }
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
      try{
       SESSION.getTabList().get(SESSION.getSelectedID()).getController().saveAsNew();
      }
      catch(Exception e){
          OUTPUT.print("That failed, are you sure that you have opened a window?");
      }
       
    }
}

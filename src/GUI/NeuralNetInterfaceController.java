/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import InterfaceManagement.ControllerInterface;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingNode;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;

/**
 * FXML Controller class
 *
 * @author mathew
 */
public class NeuralNetInterfaceController implements Initializable, ControllerInterface{
    //Swing Nodes
    private BinaryGrid GRID = new BinaryGrid(30,30);
    private String NAME = "UNTITLED";
    private Tab NEURAL_TAB;
    
    //FXML Nodes
    @FXML
    public Button CLOSE = new Button();
    
    @FXML
    public SwingNode SWING_NODE = new SwingNode();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CLOSE.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                closeTab();
            }
        });
        System.out.println("Listeners added");
        SWING_NODE.setContent(GRID);
        System.out.println("Added content");
    }    
    
    
    //Actions
    public void clear(){
        GRID.clear();
    }
    
    public void evaluate(){
        
    }
     public void train(){
         
     }

    @Override
    public void closeTab() {
        System.out.println("Title: "+this.NAME);
        System.out.println("Serial: "+this.toString());
        if(this.NEURAL_TAB == null) System.out.println("Go to bed!");
        System.out.println(NEURAL_TAB.getText());
       this.NEURAL_TAB.getTabPane().getTabs().remove(NEURAL_TAB);
    }

    @Override
    public void showTab(boolean visible) {
        
    }

    @Override
    public void setText(String title) {
        this.NEURAL_TAB.setText(title);
        this.NAME = title;
    }

    @Override
    public void selectTab() {
       
    }

    @Override
    public Tab getTab() {
        return this.NEURAL_TAB;
    }

    @Override
    public void setTab(Tab t) {
       this.NEURAL_TAB = t;
    }

    @Override
    public void loadIntoTab(File res) {
        System.out.println("Loading "+res.getPath());
    }
    
}

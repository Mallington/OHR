/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Components.DrawingGrid;
import InterfaceManagement.ControllerInterface;
import Util.Save;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingNode;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import neural.Layer;

/**
 * FXML Controller class
 *
 * @author mathew
 */
public class NeuralNetInterfaceController implements Initializable, ControllerInterface {

    private String NAME = "UNTITLED.nns";
    private Tab NEURAL_TAB;
    OutputController OUT;
    private Layer NEURAL_LAYER;

    private DrawingGrid DGRID;

    public boolean SAVED = false;

    public boolean NEW_DOCUMENT = true;
    
    private File ORIGINAL_DIRECTORY;

    //FXML Nodes
    @FXML
    public Button CLOSE = new Button();

    @FXML
    public Canvas INPUT_PAD = new Canvas();

    @FXML
    public ChoiceBox CHARECTAR_SELECT = new ChoiceBox();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        NEURAL_LAYER = new Layer();
        NEURAL_LAYER.CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        //  CHARECTAR_SELECT.set
        NEURAL_LAYER.generateRandomNeurons(26, 900);

        DGRID = new DrawingGrid(30, 30, INPUT_PAD);

    }

    //Actions
    public void clear() {
        DGRID.clear();
        OUT.print("Clearing");
    }

    public void evaluate() {

    }

    public void train() {

    }
    
    public void saveAsNew(){
        this.NEW_DOCUMENT = true;
        this.save();
    }

    @Override
    public void closeTab() {
        if(!SAVED){
        save();}
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

    public void setContextMenu() {
        ContextMenu m = new ContextMenu();
        // MenuItem mi = new MenuItems("Close");
        // m.getItems()
        //this.NEURAL_TAB.
    }

    @Override
    public Tab getTab() {
        return this.NEURAL_TAB;
    }

    @Override
    public void setTab(Tab t) {
        this.NEURAL_TAB = t;
        this.NEURAL_TAB.setClosable(true);
        this.NEURAL_TAB.setOnCloseRequest(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                closeTab();
            }
        });
        
                /*
        Resource r = new Resource("NeuralTab.fxml");

        try {
            t.setGraphic(r.getNode());
        } catch (IOException ex) {
            System.out.println("FAILED TO SET GRAPHICS");
        }*/
    }

    @Override
    public void loadIntoTab(File res) {
        setText(res.getName());
        this.setText(res.getName());
        OUT.print("Loading " + res.getPath());
    }

    @Override
    public void setOutputController(OutputController out) {
        OUT = out;
        OUT.print("Set output");
    }

    public void setModified() {
        SAVED = false;
    }

    @Override
    public void saveAs(File f) {
        this.ORIGINAL_DIRECTORY = f;
         saveFile(this.ORIGINAL_DIRECTORY);
        this.NEW_DOCUMENT = false;
        this.OUT.print("New directory is now "+ORIGINAL_DIRECTORY.getAbsolutePath());
        hasSaved();
        this.getTab().setText(this.ORIGINAL_DIRECTORY.getName());
    }

    @Override
    public boolean hasSaved() {
        
        
        return SAVED;
    }
    
    private void saveFile(File f){
         try{
          new Save(f).write(this.NEURAL_LAYER);
         this.SAVED = true;
          this.OUT.print("Saved");
            }
            catch(Exception e){
                this.OUT.print("Failed to save");
            }
    }

    @Override
    public void save() {
        if (NEW_DOCUMENT) {
            this.OUT.print("Ah, it seems you have not saved it before.");
           FilePicker fp = new FilePicker(".nns","neural net struct","Unitled");
       // fp.getFile(this.getTab().getTabPane().getScene().get)
      Stage s =  (Stage)this.CLOSE.getScene().getWindow();
    
      try{
        File f = fp.getFile(s, FilePicker.SAVE); 
       saveAs(f);
      } catch(Exception e){
          this.OUT.print("You backed out! Are you happy with your self?");
      }
       
        
        } else {
           saveFile(this.ORIGINAL_DIRECTORY);
        }
        
    }

}

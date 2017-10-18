/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Components.DrawingGrid;
import InterfaceManagement.ControllerInterface;
import Util.Load;
import Util.Save;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import neural.TrainingSet;

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
        NEURAL_LAYER.generateRandomNeurons(26, 900);
        setGUI (this.NEURAL_LAYER);

    }
    
    
    private void setGUI(Layer l){
         ObservableList<String> list = FXCollections.observableArrayList();
        for(char c : l.CHAR_SET.toCharArray()) list.add("Char: "+c);
        CHARECTAR_SELECT.setItems(list);
        CHARECTAR_SELECT.getSelectionModel().selectFirst();
        DGRID = new DrawingGrid(30, 30, INPUT_PAD);
    
}
    
    @Override
    public void load(File f){
     this.ORIGINAL_DIRECTORY = f;
        try {
            Object in = new Load(this.ORIGINAL_DIRECTORY).load();
            if(in.getClass().equals(this.NEURAL_LAYER.getClass())){
                this.NEURAL_LAYER =  (Layer)in;
                OUT.print("Loaded "+this.ORIGINAL_DIRECTORY.getName());
                OUT.print("Charectar set: "+this.NEURAL_LAYER.CHAR_SET);
                this.setText(this.ORIGINAL_DIRECTORY.getName());
                this.NEW_DOCUMENT = false;
                this.SAVED = true;
                this.setGUI(NEURAL_LAYER);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NeuralNetInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NeuralNetInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }

    //Actions
    public void clear() {
        DGRID.clear();
        OUT.print("Clearing");
    }

    public void evaluate() {
             this.OUT.print("Evaluating");
                List<Double> out = this.NEURAL_LAYER.forwardProp(new TrainingSet(this.DGRID.getOutput(), 0, this.NEURAL_LAYER.NEURONS.size()));
              double biggest = out.get(0);
              int neuronPos =0;
               for(int i =1; i< out.size(); i++){
                   if(out.get(i)> biggest) {
                       biggest = out.get(i);
                       neuronPos = i;
                   }
               }
               this.OUT.print("Recognised as "+this.NEURAL_LAYER.CHAR_SET.substring(neuronPos, neuronPos+1));
   
    }

    public void train() {
        int selection =0;
        for(int i =0; i < this.NEURAL_LAYER.CHAR_SET.length(); i++){
            if(this.NEURAL_LAYER.CHAR_SET.subSequence(i, i+1) == this.CHARECTAR_SELECT.getValue()){
                selection = i;
            }
        }
        OUT.print("Selected "+this.NEURAL_LAYER.CHAR_SET.subSequence(selection, selection+1));
      this.NEURAL_LAYER.backwardProp(new TrainingSet(this.DGRID.getOutput(), selection, this.NEURAL_LAYER.NEURONS.size()));
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
            this.OUT.print("Saving as new file, good and new!");
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

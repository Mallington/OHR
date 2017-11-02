/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Components.DrawingGrid;
import ImageProcessing.ImageTools;
import InterfaceManagement.ControllerInterface;
import InterfaceManagement.SubControllerInterface;
import Util.Load;
import Util.Save;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import neural.Input;
import neural.Layer;
import neural.Neuron;
import neural.TrainingSet;
// meaning of everything
/**
 * FXML Controller class
 *
 * @author mathew
 */
public class NeuralNetInterfaceController implements Initializable, ControllerInterface {
    
    
    //Tim
    @FXML
    BorderPane BORDER = new BorderPane();
    
    private String NAME = "UNTITLED.nns";
    private Tab NEURAL_TAB;
    OutputController OUT;
    private Layer NEURAL_LAYER;
    private Window WINDOW;
    

    private DrawingGrid DGRID;

    public boolean SAVED = false;

    public boolean NEW_DOCUMENT = true;
    
    private File ORIGINAL_DIRECTORY;
    
    
    //FXML Nodes
    @FXML
    public ImageView IMAGE_VIEW = new ImageView();
    
    @FXML
    public Text OUT_LETTER = new Text();
    
    @FXML
    public Button CLOSE = new Button();

    @FXML
    public Canvas INPUT_PAD = new Canvas();

    @FXML
    public ChoiceBox CHARECTAR_SELECT = new ChoiceBox();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        try {
              URL imageR = getClass().getResource("UpperCase.jpg");
            
             Image image = ImageTools.convertBuffered(ImageIO.read(imageR));
            IMAGE_VIEW.setImage(image);
           
        } catch (IOException ex) {
            Logger.getLogger(NeuralNetInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         Platform.runLater(() -> WINDOW = this.INPUT_PAD.getScene().getWindow());
        NEURAL_LAYER = new Layer();
       
        
        NEURAL_LAYER.generateRandomNeurons(26, 900);
        NEURAL_LAYER.setCharSet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        
        setGUI (this.NEURAL_LAYER);
        
        
        
        //Tim
        //Button b = new Button();
      //  b.setText("Tim Sucks");
        BORDER.getChildren().add(new Button("tim"));
        BORDER.setTop(CLOSE);
        //BORDER.
    }
    
    
    private void setGUI(Layer l){
         ObservableList<String> list = FXCollections.observableArrayList();
        for(Neuron n: this.NEURAL_LAYER.NEURONS) list.add(n.NAME);
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
               
              OUT_LETTER.setText(this.NEURAL_LAYER.NEURONS.get(neuronPos).NAME);
              this.OUT.print("Recognised as "+this.NEURAL_LAYER.NEURONS.get(neuronPos).NAME);
   
    }

    public void train() {
        // NEURAL_LAYER.displayContents();
       int selection =  this.getChoiceBox();
        OUT.print("Selected "+this.NEURAL_LAYER.NEURONS.get(selection));
      this.NEURAL_LAYER.backwardProp(new TrainingSet(this.DGRID.getOutput(), selection, this.NEURAL_LAYER.NEURONS.size()));
      this.setModified();
    }
    // Here is a message
    public void saveAsNew(){
        this.NEW_DOCUMENT = true;
        this.save();
    }
    
    private int getChoiceBox(){
     return this.CHARECTAR_SELECT.getSelectionModel().getSelectedIndex();
        
        
       
    }
    public void loadImage() throws InterruptedException {
        System.out.println("Loading Image");
        SubWindowLoader wind = new SubWindowLoader("CropWindow.fxml", WINDOW);
        wind.show();
        Runnable r = new Runnable(){public void run(){
        
        
        Image ret =(Image)wind.getReturn();
        Platform.runLater(()->IMAGE_VIEW.setImage(ret));
            System.out.println("Set Image");
         
           }};
        new Thread(r).start();
       // Platform.runLater(r);
 
       
       
       
       
      
       
    }

    @Override
    public void closeTab() {
      
        if(!SAVED ){
        save();}
       
        NEURAL_TAB.getTabPane().getTabs().remove(NEURAL_TAB);
       
        
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

    private boolean saveMenu(){
        String[] buttons = { "Yes", "No"};    
int returnValue = JOptionPane.showOptionDialog(null, "Oh dear", "Would you like to save", JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[0]);
        if(returnValue == 0) return true;
        else return false;
    }
    
    
    @Override
    public void save() {
        if (NEW_DOCUMENT) {
            this.OUT.print("Saving as new file, good and new!");
           FilePicker fp = new FilePicker(".nns","neural net struct","Unitled");
       // fp.getFile(this.getTab().getTabPane().getScene().get)
      Stage s =  (Stage)WINDOW;
    
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
    
    public void peek(){
        OUT.print("Peeking");
        List<Double> toSet = new ArrayList<Double>();
        for(Input i: this.NEURAL_LAYER.NEURONS.get(this.getChoiceBox()).inputs) toSet.add(i.getWeight());
         
        double largestVal =toSet.get(0);
        double lowestVal = toSet.get(0);
        for(double d : toSet) if (d> largestVal) largestVal = d;
        for(double d : toSet) if (d< lowestVal) lowestVal = d;
        
        for(int i =0; i< toSet.size(); i++) {
            
             if(toSet.get(i)>0){
                 toSet.set(i,  (toSet.get(i)+Math.abs(lowestVal))/(largestVal-lowestVal));
             }
             else{
                 toSet.set(i, (Math.abs(lowestVal)-Math.abs(toSet.get(i)))/(largestVal-lowestVal));
             }
            
             
           
        }
      //  for(Double d: toSet) System.out.println("New "+d);
        
        
       this.DGRID.setContents(toSet);
       
    }
    
    public void endPeek(){
       this.DGRID.clear();
    }

}

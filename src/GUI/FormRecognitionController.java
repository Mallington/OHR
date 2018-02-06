/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Components.FormView;
import ImageProcessing.FormRecognition;
import ImageProcessing.ImageTools;
import ImageProcessing.RecognitionOutput;
import InterfaceManagement.TabAttributes;
import InterfaceManagement.ControllerInterface;
import Util.Load;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;
import neural.Layer;

/**
 * FXML Controller class
 *
 * @author mathew
 */
public class FormRecognitionController extends TabAttributes<Layer> implements Initializable{
    @FXML
    ImageView FORM_VIEW = new ImageView();
    @FXML
    ProgressIndicator PROGRESS_IND = new ProgressIndicator();
    @FXML
    Canvas MAIN_VIEW = new Canvas();
    @FXML
    Text SCALE_LABEL = new Text();
    @FXML
    TextField THRESH_BOX = new TextField();
    @FXML
    Slider SCALE = new Slider();
    @FXML
    Slider THRESH_SLIDER = new Slider();
    @FXML
    Text PROBABILITY = new Text();
    @FXML
    Text PROGRESS = new Text();
    
    FormView VIEW_CONTROLLER;
    private double DEFAULT_THRESH = 125;
   
    private Image FORM = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setFileType(".nns");
        this.setFileDes("Neural Net Struct");
        this.setMenuItems(new ArrayList<Menu>(genMenus())); // Need to set menus
        
        
        SCALE.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object val) {
               
              
       SCALE_LABEL.setText((int)(double)val+"%");
       VIEW_CONTROLLER.setScale((double)((int)(double)val)/100.0);
            }});
        THRESH_SLIDER.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object val) {
               
              
       THRESH_BOX.setText((int)(double)val+"");
       setImageView(FORM,(double)val );
            }});
        
        
        
         URL imageR = getClass().getResource("UpperCase.jpg");
            
        try {
            
            FORM = ImageTools.convertBuffered(ImageIO.read(imageR));
            VIEW_CONTROLLER = new FormView(FORM,MAIN_VIEW, this);
            this.setImageView(FORM, DEFAULT_THRESH);
            
        } catch (IOException ex) {
            System.out.println("Could not find image");
        }
        
    }
    
    public void setImageView(Image img, double thresh){
        BufferedImage bimg = ImageTools.toGreyScale(ImageTools.convertImgToBuf(img), true, (int)thresh);
        FORM_VIEW.setImage(ImageTools.convertBuffered(bimg));
    }
   

   public List<Menu> genMenus(){
        
        
        Menu imp = new Menu("Import");
          MenuItem fScan = new MenuItem("Form Scan");
          fScan.setOnAction(event->{
            try {
                importScan();
            } catch (IOException ex) {
                OUT.print("Failed to import scan");
            }
        });
          MenuItem nStruct= new MenuItem("Network Structure");
          nStruct.setOnAction(event->{
            try {
                importNetwork();
            } catch (IOException ex) {
                Logger.getLogger(FormRecognitionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
          imp.getItems().addAll(fScan, nStruct);
        Menu runM = new Menu("Run");
          MenuItem runJ = new MenuItem("Run");
          runJ.setOnAction(event->run());
          MenuItem param= new MenuItem("Modify Parameters");
          param.setOnAction(event->modifyParams());
          runM.getItems().addAll(runJ, param);
          return Arrays.asList(imp, runM);
    }
   
   public void recalculateThreshold(){
       try{
       int val = Integer.parseInt(this.THRESH_BOX.getText());
       if(val >=0 && val<=255 ){
       THRESH_SLIDER.setValue(val);
       this.setImageView(FORM, val);
       }
       else{
           THRESH_BOX.setText((int)THRESH_SLIDER.getValue()+"");
       } } catch(Exception e){THRESH_BOX.setText((int)THRESH_SLIDER.getValue()+"");}
   }

    public void importScan() throws MalformedURLException, IOException{
       this.OUT.print("Importing Scan...");
       FilePicker pick = new FilePicker("Image ", Arrays.asList(".jpg",".png"));
         File picked = pick.getFile(null, FilePicker.OPEN);
         FORM = ImageTools.convertBuffered(ImageIO.read(picked.toURI().toURL()));
          setImageView(FORM, DEFAULT_THRESH);
          VIEW_CONTROLLER.loadImage(FORM);
        
    }
    
    public void importNetwork() throws MalformedURLException, IOException{
        this.OUT.print("Importing Network...");
       FilePicker pick = new FilePicker("Neural Net Struct ", Arrays.asList(".nns"));
         File picked = pick.getFile(null, FilePicker.OPEN);
         this.ORIGINAL_DIRECTORY = picked;
          this.NEW_DOCUMENT = false;
          this.FILE = (Layer) new Load(picked).load();
          this.SAVED = true;
          
    }
    
    public void run(){
        this.OUT.print("Performing Letter Recognition");
        FormRecognition FR = new FormRecognition(this.FORM, FILE, (int)this.THRESH_SLIDER.getValue()) {
            @Override
            public void update(RecognitionOutput out) {
                Platform.runLater(()->{
                 
                PROBABILITY.setText((int)(out.getProbability()*100)+"");
                PROGRESS.setText((int)(out.getProgress()*100)+"");
                VIEW_CONTROLLER.displayJobOutput(out,  out.THRESHOLD);
                PROGRESS_IND.setProgress(out.getProgress());
                });
            }

            @Override
            public void complete(RecognitionOutput out) {
                OUT.print("Output: "+out.getResultantString());
            }
        };
        RecognitionOutput out = FR.startJob();
        //OUT.print("Output: "+out.getResultantString());
       // this.PROBABILITY.setText((int)(out.getProbability()*100.0)+"");
        //this.VIEW_CONTROLLER.displayJobOutput(out,  (int)this.THRESH_SLIDER.getValue());
    }
    
    public void modifyParams(){
        this.OUT.print("Opening Parameters window");
    }

    

    @Override
    public void setGUI(Layer file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }




    

    
}

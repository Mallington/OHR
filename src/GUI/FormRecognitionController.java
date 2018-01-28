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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
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
public class FormRecognitionController extends TabAttributes implements Initializable{
    @FXML
    ImageView FORM_VIEW = new ImageView();

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
    FormView VIEW_CONTROLLER;
    private double DEFAULT_THRESH = 125;
    private Layer NNS = null;
    private Image FORM = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setFileType(".fs");
        this.setFileDes("Form Structure");
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
            VIEW_CONTROLLER = new FormView(FORM,MAIN_VIEW);
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
          NNS = (Layer) new Load(picked).load();
          
    }
    
    public void run(){
        this.OUT.print("Performing Letter Recognition");
        FormRecognition FR = new FormRecognition(this.FORM, this.NNS, (int)this.THRESH_SLIDER.getValue());
        RecognitionOutput out = FR.startJob();
        String append = "";
        for(String s: out.CHARS) append+=s;
        System.out.println(append);
        OUT.print("Output: "+append);
        this.VIEW_CONTROLLER.displayJobOutput(out);
    }
    
    public void modifyParams(){
        this.OUT.print("Opening Parameters window");
    }

    public void setGUI(Object file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }




    

    
}

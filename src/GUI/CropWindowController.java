/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Components.CropPanel;
import ImageProcessing.ImageTools;
import InterfaceManagement.ControllerInterface;
import InterfaceManagement.SubControllerInterface;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author mathew
 */
public class CropWindowController implements Initializable, SubControllerInterface{

    
    
    
    
    private OutputController OUT;
    /**
     * Canvas used for drawing the crop panel, used by the CropPanel class
     * @see CropPanel
     */
    @FXML
    private Canvas CROP_CANVAS = new Canvas();
    
    @FXML
    private Slider SCALE = new Slider();
    
    @FXML
    private Slider THRESHOLD = new Slider();
    
    @FXML
    private  CheckBox BINARISE = new CheckBox();
    
     @FXML
    private  CheckBox DETECT = new CheckBox();
    
    private CropPanel CROP_PANEL;
    /**
     * Contains the window instance that the FXML is being held in
     */
    private Window WINDOW;
    
    private Popup POPUP = null;
    /**
     * Contains the final output image to the instigating class
     */
    private Image RETURN = null; 
    
    
    /**
     * Because the controller implements the use of initializable, when the FXML file is loaded,
     * this method is called in its corresponding controller, In this implementation the CROP_PANEL 
     * is instantiated by passing in the CROP_CANVAS, This creates a separate controller responsible,
     * for drawing the crop panel and governing mouse inputs. It also adds
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Init");
    Platform.runLater(() -> WINDOW = CROP_CANVAS.getScene().getWindow());
        
        try {
             URL imageR = getClass().getResource("UpperCase.jpg");
            Image image = ImageTools.convertBuffered(ImageIO.read(imageR));
            CROP_PANEL = new CropPanel(image,this.CROP_CANVAS,30,30);
        } catch (IOException ex) {
            CROP_PANEL = new CropPanel(null,this.CROP_CANVAS, 30,30);
        }
        
        
       
        SCALE.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               CROP_PANEL.setScale((double) newValue);
              
            }
            
            
        
        });
       
        THRESHOLD.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
              
               CROP_PANEL.modifyImg((int)(double)newValue, BINARISE.isSelected(),false);
            } });
        
       
        System.out.println("Done.");
        
    }   
    
     /**
      * This adjusts the settings render parameters that the CROP_PANEL uses to draw the image
      */
    public void render(){
        CROP_PANEL.modifyImg((int)THRESHOLD.getValue(), BINARISE.isSelected(), DETECT.isSelected());
    }
    
    /**
     * This opens a file window allowing the user to pick and load a .jpg or .png.
     * @throws MalformedURLException 
     */
    public void open() throws MalformedURLException{
         FilePicker pick = new FilePicker("Image ", Arrays.asList(".jpg",".png"));
         File picked = pick.getFile(null, FilePicker.OPEN);
         this.load(picked.toURI().toURL());
    }
    
    
    /*
    This obtains the image inside the crop area and stores it in RETURN for the parent class to use.
    */
    public void submit(){
      
          
        RETURN = CROP_PANEL.getImage();
        
        
    }
    

   /**
    * This closes the window
    */
   public void cancel(){
       System.out.println("Cancelling");
       if(POPUP !=null) this.POPUP.hide();
       
       else System.out.println("Need to set popup inst.");
   }
   
  
    @Override
    public void load(URL url) {
        try {
            Image image = ImageTools.convertBuffered(ImageIO.read(url));
            this.CROP_PANEL.loadImage(image);
        } catch (IOException ex) {
            System.out.println("Could not load image");
        }
    }
   
    @Override
    public void setBackColour(Paint p) {
        System.out.println("Setting colour");
       this.CROP_PANEL.setBackgroundColour(p);
    }
   
    @Override
    public void disclosePopup(Popup popInst) {
        POPUP = popInst;
    }
    
    @Override
    public void flushReturn(){
        RETURN =null;
    }
    
    @Override
    public Object getReturn() {
        
        return RETURN;
    }

    @Override
    public void dispose() {
        
    }

   

   

    
    
}

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
import java.net.URL;
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
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Popup;
import javafx.stage.Window;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author mathew
 */
public class CropWindowController implements Initializable, SubControllerInterface{

    /**
     * Initializes the controller class.
     */
    
    
    
    private OutputController OUT;
    @FXML
    private Canvas CROP_CANVAS = new Canvas();
    
    @FXML
    private Slider SCALE = new Slider();
    
    
    private CropPanel CROP_PANEL;
   
    private Window WINDOW;
    
    private Popup POPUP = null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Init");
    Platform.runLater(() -> WINDOW = CROP_CANVAS.getScene().getWindow());
        
        try {
             URL imageR = getClass().getResource("UpperCase.jpg");
            Image image = ImageTools.convertBuffered(ImageIO.read(imageR));
            CROP_PANEL = new CropPanel(image,this.CROP_CANVAS);
        } catch (IOException ex) {
            CROP_PANEL = new CropPanel(null,this.CROP_CANVAS);
        }
        
        
       
        SCALE.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               CROP_PANEL.setScale((double) newValue);
                System.out.println("Scale: "+(double)newValue);
            }
        });
        
       
        System.out.println("Done.");
        
    }   
    
    
    
    
    

   
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

   

   

    
    
}

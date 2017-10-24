/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import InterfaceManagement.SubControllerInterface;
import java.awt.Insets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author mathew
 */
public class SubWindowLoader extends Application {
    private static SubControllerInterface CONTROLLER;
    private static String TITLE = "Sub Window";
    private static String FXML = null;
    
   
    public static SubControllerInterface createGUI(String fxml, String title, String[] args) {
        FXML = fxml;
        TITLE = title;
        launch(args);
        return CONTROLLER;
    }

    @Override
    public void start(Stage stage) throws Exception {
       
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle(TITLE);
        if(FXML == null) System.out.println("NULL!");
       Resource<SubControllerInterface> r = new Resource<SubControllerInterface>(this.FXML);
       System.out.println("Test");
      
       
       
       // FXMLLoader loader = new FXMLLoader(getClass().getResource("CropWindow.fxml"));
      // SubControllerInterface controller = loader.<SubControllerInterface>getController();

        Scene scene = new Scene((Parent) r.getNode());

        stage.setScene(scene);
        stage.show(); 
        CONTROLLER = r.loadController();
        
       
    }
    
    
    
    public static void main(String args[]){
    
/*
   
        
        
        SubWindowLoader sub = new SubWindowLoader();
        System.out.println("1");
        new Thread(new Runnable(){public void run(){
        createGUI("CropWindow.fxml", "Select Image", args);  }}).start();
        System.out.println("Number 2");
        createGUI("CropWindow.fxml", "Select Image 2", args);*/
    }
}

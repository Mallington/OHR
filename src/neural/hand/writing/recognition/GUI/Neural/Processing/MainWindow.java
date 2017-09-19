/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neural.hand.writing.recognition.GUI.Neural.Processing;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author mathew
 */
public class MainWindow extends Application {
    
    public MainWindow(String args[]){
           System.out.println( getClass().getResource("").getPath());
        Application.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
      //  Parent root = FXMLLoader.load(this.getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(createNewScene("MainWindow.fxml"));
        primaryStage.show();
    }

   public static void main(String args[]){
       MainWindow m = new MainWindow(args);
       
   }
      public Scene createNewScene(String location) throws IOException{
        
        System.out.println( getClass().getResource(location).getPath());
        Parent root = FXMLLoader.load(getClass().getResource(location));
       
        
        Scene scene = new Scene(root, 600, 400);

        return scene;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author mathew
 */
public class MainWindow extends Application {

    /**
     * Used to make launch(args) assessable to other classes
     *
     * @param args Command line parameters
     */
    public static void startGUI(String[] args) {
        launch(args);
    }

    @Override

    /**
     * This imports the MainWindow.fxml and adds it a scene to the JavaFX stage
     */
    public void start(Stage stage) throws Exception {

        //For windows platforms, it sets the icon of the window
        stage.getIcons().add(new Image(MainWindow.class.getResourceAsStream("Hand.png")));

        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

}

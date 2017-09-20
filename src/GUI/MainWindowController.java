/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.text.Text;

/**
 *
 * @author mathew
 */
public class MainWindowController {
    @FXML Text TEXT = new Text();
    @FXML TabPane TAB_VIEW = new TabPane();
    
    public void test() throws IOException{
        System.out.println("Hello");
        TEXT.setText("Hello");
       
        Tab toAdd = new Tab("Yes!!");
         toAdd.setContent((Node) FXMLLoader.load(getClass().getResource("NeuralNetInterface.fxml")));
        TAB_VIEW.getTabs().add(toAdd);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import InterfaceManagement.ControllerInterface;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingNode;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;

/**
 * FXML Controller class
 *
 * @author mathew
 */
public class NeuralNetInterfaceController implements Initializable, ControllerInterface {

    //Swing Nodes
    private BinaryGrid GRID = new BinaryGrid(50, 50);
    private String NAME = "UNTITLED";
    private Tab NEURAL_TAB;

    //FXML Nodes
    @FXML
    public Button CLOSE = new Button();

    @FXML
    public SwingNode SWING_NODE = new SwingNode();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Listeners added");
        SWING_NODE.setContent(GRID);
        System.out.println("Added content");
    }

    //Actions
    public void clear() {
        GRID.clear();
    }

    public void evaluate() {

    }

    public void train() {

    }

    @Override
    public void closeTab() {
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

    public void setContectMenu() {
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
        Resource r = new Resource("NeuralTab.fxml");

        try {
            t.setGraphic(r.getNode());
        } catch (IOException ex) {
            System.out.println("FAILED TO SET GRAPHICS");
        }
    }

    @Override
    public void loadIntoTab(File res) {
        setText(res.getName());
        System.out.println("Loading " + res.getPath());
    }

}

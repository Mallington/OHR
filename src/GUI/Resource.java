/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import InterfaceManagement.ControllerInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Popup;

/**
 * This class provides a way of loading a FXML page and getting its attached controller so that the
 * instigating class has a way of interacting with the GUI it has just loaded
 * @author mathew
 */
public class Resource <ControllerType>{

    private FXMLLoader LOADER;
    private String RESOURCE;
/**
 * Upon initialisation it stores the resource and creates a new FXML loader
 * @param res FXML page to be loaded
 */
    public Resource(String res) {

        System.out.println("Path to resource " + getClass().getResource(res).getPath());
        LOADER = new FXMLLoader();
        // LOADER.setRoot(null);
        // LOADER.setLocation(getClass().getResource(res));
        RESOURCE = res;
    }
    /**
     * Loads the resource file an outputs as a node to be loaded into a Stage/ Window
     * @return Node to be loaded
     * @throws IOException 
     */
    public Node getNode() throws IOException {

        return LOADER.load(getClass().getResource(RESOURCE).openStream());
    }
    /**
     * Returns the controller instance attached to the loaded noad
     * @return
     * @throws IOException 
     */
    public ControllerType loadController() throws IOException {

        ControllerType con = LOADER.getController();

        return con;
    }
    
    public static void main(String args[]) throws IOException{
        Popup p = new Popup();
        Resource r = new Resource("CropWindow.fxml");
        p.getContent().add(r.getNode());
        
        
    }
    
  
    
}

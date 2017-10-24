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
 *
 * @author mathew
 */
public class Resource <ControllerType>{

    private FXMLLoader LOADER;
    private String RESOURCE;

    public Resource(String res) {

        System.out.println("Path to resource " + getClass().getResource(res).getPath());
        LOADER = new FXMLLoader();
        // LOADER.setRoot(null);
        // LOADER.setLocation(getClass().getResource(res));
        RESOURCE = res;
    }

    public ControllerType loadController() throws IOException {

        ControllerType con = LOADER.getController();

        return con;
    }

    public Node getNode() throws IOException {

        return LOADER.load(getClass().getResource(RESOURCE).openStream());
    }
    
    public static void main(String args[]) throws IOException{
        Popup p = new Popup();
        Resource r = new Resource("CropWindow.fxml");
        p.getContent().add(r.getNode());
        
        
    }
    
  
    
}

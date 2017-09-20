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

/**
 *
 * @author mathew
 */
public class Resource {
    
    private FXMLLoader LOADER;
    private String RESOURCE;
    public Resource(String res){
        
        System.out.println("Path to resource "+getClass().getResource(res).getPath());
        LOADER = new FXMLLoader();
           // LOADER.setRoot(null);
           // LOADER.setLocation(getClass().getResource(res));
        RESOURCE = res;
    }
    
    public ControllerInterface loadController() throws IOException{
//        if(LOADER.load() == null) System.out.println("No FXML!");
        ControllerInterface con =  LOADER.getController();
        if(con == null) System.out.println("Failed to load!");
        return con;
    }
    public Node getNode() throws IOException{
        //return (Node) FXMLLoader.load(getClass().getResource(RESOURCE));
       
        System.out.println("Trying!");
        
        return LOADER.load(getClass().getResource(RESOURCE).openStream());
    }
}

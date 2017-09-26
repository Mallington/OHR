/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceManagement;

import GUI.NeuralNetInterfaceController;
import GUI.Resource;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author mathew


/**
 *
 * @author mathew
 * @param <C>
 */
 
public class TabController<ControllerType extends ControllerInterface>{
    private ControllerType CONTROLLER_INSTANCE;
    private String NAME = "";
    private TabController(ControllerType controller){
        
        System.out.println("Created Controller Type: "+controller.getClass().toGenericString());
        CONTROLLER_INSTANCE = controller;
}
   
    public static TabController createFromFXMLandLoadTab(TabPane tabs, String res) throws IOException{
        ControllerInterface con = getController(tabs,res);
        
        
        return new TabController(con);
    }
   
   private  static Object getController(Node node) {
    Object controller = null;
    do {
        controller = node.getProperties().get("foo");
        node = node.getParent();
    } while (controller == null && node != null);
    return controller;
}
    private static  ControllerInterface getController(TabPane tabs, String FXML) throws IOException{
        Resource res = new Resource(FXML);
        Tab t = new Tab();
        Node n = res.getNode();
        
        t.setContent(n);
        
        
        
        //NEED TO FIX HERE, accidently loading SEPERATE controller instances, messing up everything!
        ControllerInterface cont = (ControllerInterface)  res.loadController();
        System.out.println("Loaded Controeller");
        cont.setTab(t);
        if(cont == null) System.out.println("No Resource");
        
        tabs.getTabs().add(cont.getTab());
        return cont;
    }
    
    public void rename(String newName){
        NAME = newName;
        CONTROLLER_INSTANCE.setText(NAME);
    }
    
    public void dispose(){
        CONTROLLER_INSTANCE.closeTab();
        
    }
    public void loadFileIntoTab(File res){
        CONTROLLER_INSTANCE.loadIntoTab(res);
    }
    
    public static void main(String args[]){
        
    }
    
}

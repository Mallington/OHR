/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceManagement;

import GUI.NeuralNetInterfaceController;
import Util.Save;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Tab;

/**
 * This class keeps track of all of the active TabControllers in the current session, allowing for easy access to all of them
 * @author mathew
 */
public class Session {
    /**
     * All of the active tabs in the session
     */
    private List<TabController> TABS;
    
    public Session() {
        TABS = new ArrayList<TabController>();

    }
    /**
     * Used for loading a previous session, could be used for session recovery
     * @param s 
     */
    public Session(Session s) {
        this.TABS = s.TABS;
    }

    /**
     * Allows a tab to be added
     * @param t
     * @return 
     */
    public int addTab(TabController t) {
        t.getController().getTab().setId(TABS.size()+"");
        TABS.add(t);
        return TABS.size() - 1;
    }
    /**
     * Captures the Controller Interface for a particular tab
     * @param tab
     * @return 
     */
    public ControllerInterface getControllerFromTab(Tab tab){
        ControllerInterface inter = null;
        for(int i =0; i< TABS.size(); i++) if((inter =TABS.get(i).getController()).getTab().getId().equals(tab.getId())) return inter;
        return inter;
    }
    
    /**
     * Gets the ID of the tab that is current selected in the Main Window
     * @return 
     */
    public int getSelectedID(){
        for(int i =0; i< TABS.size(); i++) if(TABS.get(i).getController().getTab().isSelected()) return i;
        return -1;
    }
    /**
     * Gets the complete ArrayList of all of the tabs with their attached controllers for interfacing
     * @return 
     */
    public List<TabController> getTabList() {
        return this.TABS;
    }

   
    /**
     * Remove a particular tab at index - id
     * @param id ID of tab to be removed
     */
    public void removeTab(int id) {
        TABS.get(id).dispose();
        TABS.remove(id);
    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        /*
        Session s = new Session();
        TabController<NeuralNetInterfaceController> t= new TabController<NeuralNetInterfaceController>(new NeuralNetInterfaceController());
        s.TABS.add(t);*/

    }
}

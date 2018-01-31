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
 *
 * @author mathew
 */
public class Session {
    
    private List<TabController> TABS;
    
    public Session() {
        TABS = new ArrayList<TabController>();

    }

    public Session(Session s) {
        this.TABS = s.TABS;
    }

    /*
     *
     * 
     */
    public int addTab(TabController t) {
        t.getController().getTab().setId(TABS.size()+"");
        TABS.add(t);
        return TABS.size() - 1;
    }
    
    public ControllerInterface getControllerFromTab(Tab tab){
        ControllerInterface inter = null;
        for(int i =0; i< TABS.size(); i++) if((inter =TABS.get(i).getController()).getTab().getId().equals(tab.getId())) return inter;
        return inter;
    }
    
    
    public int getSelectedID(){
        for(int i =0; i< TABS.size(); i++) if(TABS.get(i).getController().getTab().isSelected()) return i;
        return -1;
    }
    public List<TabController> getTabList() {
        return this.TABS;
    }

   

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

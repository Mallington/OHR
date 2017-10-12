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

    /**
     *
     * @param t
     * @return Returns the value of the tab in the array
     */
    public int addTab(TabController t) {
        TABS.add(t);
        return TABS.size() - 1;
    }
    
    
    public int getSelectedID(){
        for(int i =0; i< TABS.size(); i++) if(TABS.get(i).getController().getTab().isSelected()) return i;
        return -1;
    }
    public List<TabController> getTabList() {
        return this.TABS;
    }

    public void loadFileInTab(File res, int tabControllerID) {
        TABS.get(tabControllerID).loadFileIntoTab(res);
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

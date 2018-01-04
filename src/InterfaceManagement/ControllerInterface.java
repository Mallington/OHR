/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceManagement;

import GUI.OutputController;
import java.io.File;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;

/**
 *
 * @author mathew
 */
public interface ControllerInterface {

    public void closeTab();

     public void setMenuBar(MenuBar menuBar);

    public void setText(String title);

    public void selectTab();
    
    public void deselectTab();

    public Tab getTab();

    public void setTab(Tab t);

    public void load(File f);
    
    public void setOutputController(OutputController out);
    
    public void save();
    
    public void saveAs(File f);
    
    public boolean hasSaved();
    
    public void saveAsNew();
    
}

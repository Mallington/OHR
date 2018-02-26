/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceManagement;

import java.io.File;
import java.net.URL;
import javafx.scene.paint.Paint;
import javafx.stage.Popup;
import javafx.stage.Window;

/**
 *
 * @author mathew
 */
public interface SubControllerInterface {
      /**
     * Allows parent to load an object which is then processed by the controller.
     * @param url The location of the file to be loaded.
     */
    public void load(URL url);
     /**
     * Allows parent to the background of the UI attached to the controller implementing it.
     * @param p 
     */
    public void setBackColour(Paint p);
     /**
     * Used by the SubWindowLoader or parent class to disclose the GUIs containing window/ pop up instance
     * @param popInst Containing window
     */
    /**
     * Discloses the pop up instance to the controller, so that it may have control over it containing window functions.
     * @param popInst Pop Up instance containing the GUI
     */
    public void disclosePopup(Popup popInst);
    /**
     * 
     * @return Allows parent class to check if there is data to return, so that it can dispose of the window or keep it open.
     */
    public Object getReturn();
    /**
     * The implementing class will nullify its current return variable.
     */
    public void flushReturn();
    /*
    Allows the implementing class to run an shutdown procedures so that the window can be safely closed without data loss.
    */
    public void dispose();
}

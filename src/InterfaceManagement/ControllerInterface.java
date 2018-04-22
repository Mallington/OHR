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
 * For different tab types there are different controller instances attached to
 * them, each one inherently implements the ControllerInterface so that any
 * functions can be called through this interface without ever first specifying
 * what type of controller it is in the first place
 *
 * @author mathew
 */
public interface ControllerInterface {

    /**
     * Closes the tab that the controller is attached to, allows a shutdown hook
     * to be added for functions such as notifying the user to Save the file
     */
    public void closeTab();

    /**
     * This passes the MenuBar instance to the controller
     *
     * @param menuBar
     */
    public void setMenuBar(MenuBar menuBar);

    /**
     * This sets the title of the Tab
     *
     * @param title
     */
    public void setText(String title);

    /**
     * Activates the methods associated with showing a Tab as well as setting
     * the Tab to visible
     */
    public void selectTab();

    /**
     * Deselects the ta
     */
    public void deselectTab();

    /**
     * Returns the tab instance associated with the controller
     *
     * @return
     */
    public Tab getTab();

    /**
     * Passes the Tab instance to the controller so an association between the
     * two can be made
     *
     * @param t
     */
    public void setTab(Tab t);

    /**
     * Loads a file into the tab
     *
     * @param f
     */
    public void load(File f);

    /**
     * Passes the output controller so that the controller can print to the
     * Output Box
     *
     * @param out
     */
    public void setOutputController(OutputController out);

    /**
     * Saves any progress
     */
    public void save();

    /**
     * Saves current style in a new directory
     *
     * @param f
     */
    public void saveAs(File f);

    /**
     * Returns true if current progress has been saved
     *
     * @return
     */
    public boolean hasSaved();

    public void saveAsNew();

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceManagement;

import GUI.NeuralNetInterfaceController;
import GUI.OutputController;
import GUI.Resource;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * The purpose of this class is to manage each Tab within the main window, each
 * tab is linked to a controller which is contained in this class. It also
 * allows the containing class to access various functions within the FXML
 * controller itself, allowing tabs to be modified while open, or closed on
 * demand
 *
 * @author mathew
 *
 */
public class TabController<ControllerType extends ControllerInterface> {

    /**
     * This is the Interface is used to interact with the controller.
     */
    private ControllerType CONTROLLER_INSTANCE;
    /**
     * Contains the name of the tab
     */
    private String NAME = "";

    /**
     * Used by static method to create new instance
     *
     * @param controller
     */
    private TabController(ControllerType controller) {

        System.out.println("Created Controller Type: " + controller.getClass().toGenericString());
        CONTROLLER_INSTANCE = controller;
    }

    /**
     * Used to obtain access to the Tab controller and use its functions
     *
     * @return Controller Interface
     */
    public ControllerInterface getController() {
        return CONTROLLER_INSTANCE;

    }

    /**
     * Used to create a new tab by passing all of the relevant instances
     * required for a Controller Interface to operate.
     *
     * @param tabs TabPane for the FXML to be contained in
     * @param res This is the resource name of the FXML to be loaded. eg.
     * WelcomeScreen.fxml
     * @param out This is the controller instance the controls the printing of
     * the Output in the main window
     * @param menu This is the tool bar instance used for adding and removing
     * menu items
     * @return Returns a new Tab Controller instance
     * @throws IOException
     */
    public static TabController createFromFXMLandLoadTab(TabPane tabs, String res, OutputController out, MenuBar menu) throws IOException {
        ControllerInterface con = getController(tabs, res, out);
        con.setMenuBar(menu);
        return new TabController(con);
    }

    /**
     * This gets the controller from a loaded FXML node
     *
     * @param node
     * @return
     */
    private static Object getController(Node node) {
        Object controller = null;
        do {
            controller = node.getProperties().get("foo");
            node = node.getParent();
        } while (controller == null && node != null);
        return controller;
    }

    /**
     * Creates a new Tab by loading the FXML file and obtains the controller for
     * interfacinng
     *
     * @param tabs TabPane for the FXML to be contained i
     * @param FXML his is the resource name of the FXML to be loaded. eg.
     * WelcomeScreen.fxml
     * @param out his is the controller instance the controls the printing of
     * the Output in the main window
     * @return Returns the controller instance of the tab
     * @throws IOException
     */
    private static ControllerInterface getController(TabPane tabs, String FXML, OutputController out) throws IOException {
        Resource res = new Resource(FXML);
        Tab t = new Tab();
        Node n = res.getNode();

        t.setContent(n);

        ControllerInterface cont = (ControllerInterface) res.loadController();
        System.out.println("Loaded Controeller");
        cont.setTab(t);
        cont.setOutputController(out);
        if (cont == null) {
            System.out.println("No Resource");
        }

        tabs.getTabs().add(cont.getTab());
        return cont;
    }

    /**
     * Renames the tab, changing the text in the tab bar
     *
     * @param newName New name
     */
    public void rename(String newName) {
        NAME = newName;
        CONTROLLER_INSTANCE.setText(NAME);
    }

    /**
     * Closes the tab
     */
    public void dispose() {
        CONTROLLER_INSTANCE.closeTab();

    }

    public static void main(String args[]) {

    }

}

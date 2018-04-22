/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import InterfaceManagement.ControllerInterface;
import InterfaceManagement.Session;
import InterfaceManagement.TabController;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class is used to initialise the main tab view and holds the session
 * object which governs the creation and deletion of tabs.
 *
 * @author mathew
 */
public class MainWindowController implements Initializable {

    private Session SESSION = new Session();

    @FXML
    Text TEXT = new Text();
    @FXML
    TabPane TAB_VIEW = new TabPane();
    @FXML
    TextArea OUTPUT_TEXT = new TextArea();
    @FXML
    MenuBar MENU_BAR = new MenuBar();
    /**
     * Used for main output for all of the tabs
     */
    OutputController OUTPUT;

    /**
     * Initialises the OutputController class with the TextArea and sets the tab
     * listeners so that a TabController context knows it has been selected.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        OUTPUT = new OutputController(OUTPUT_TEXT);
        TAB_VIEW.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        addSelectionModel(TAB_VIEW);
        try {
            int added = SESSION.addTab(TabController.createFromFXMLandLoadTab(TAB_VIEW, "WelcomeScreen.fxml", OUTPUT, this.MENU_BAR));
            SESSION.getTabList().get(added).getController().setText("Welcome");
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This adds a listener which identifies which individual tab has been
     * selected or deselected and retrieves the relevant TabController interface
     *
     * @param pane
     */
    private void addSelectionModel(TabPane pane) {
        pane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {

                        if (oldValue != null) {
                            Platform.runLater(() -> SESSION.getControllerFromTab(oldValue).deselectTab());
                        }
                        if (newValue != null) {
                            Platform.runLater(() -> SESSION.getControllerFromTab(newValue).selectTab());
                        }
                    }

                }
        );
        System.out.println("Added Change Listeners");
    }

    /**
     * Creates and adds a new Neural Tab which allows the user to edit, train
     * and create a neural network
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void newNeuralInterface() throws IOException, InterruptedException {
        int tabID = SESSION.addTab(TabController.createFromFXMLandLoadTab(TAB_VIEW, "NeuralNetInterface.fxml", OUTPUT, this.MENU_BAR));
        SESSION.getTabList().get(tabID).getController().setText("Untitled.nns");
        // SESSION.getTabList().get(tabID).getController().getTab().se

    }

    /**
     * Creates and adds a new Form Recogniser tab which allows the user to use
     * pre-trained neural networks and pictures of scans to extract and convert
     * the characters into ASCII form.
     *
     * @throws IOException
     */
    public void newFormInterface() throws IOException {
        int tabID = SESSION.addTab(TabController.createFromFXMLandLoadTab(TAB_VIEW, "FormRecognition.fxml", OUTPUT, this.MENU_BAR));
        SESSION.getTabList().get(tabID).getController().setText("Form Scan");
    }

    /**
     * Implemented by a listeners stated in the MainWindow.FXML, allows the user
     * to open a new neural network file.
     */
    public void open() {

        try {
            FilePicker pick = new FilePicker("File ", Arrays.asList(".nns"));
            File picked = pick.getFile((Stage) this.TAB_VIEW.getScene().getWindow(), FilePicker.OPEN);

            if (picked.getName().contains(".nns")) {
                int added = SESSION.addTab(TabController.createFromFXMLandLoadTab(TAB_VIEW, "NeuralNetInterface.fxml", OUTPUT, this.MENU_BAR));
                SESSION.getTabList().get(added).getController().load(picked);
            }

        } catch (Exception e) {
            OUTPUT.print("That failed, are you sure that you have opened a window?");
        }
    }

    public void about() throws IOException {
        int added = SESSION.addTab(TabController.createFromFXMLandLoadTab(TAB_VIEW, "WelcomeScreen.fxml", OUTPUT, this.MENU_BAR));
        SESSION.getTabList().get(added).getController().setText("About & Help");
    }

    /**
     * Identifies the current tab which is open and uses the abstract method
     * save() to save what is in that current tab.
     */
    public void save() {
        try {
            SESSION.getTabList().get(SESSION.getSelectedID()).getController().save();
        } catch (Exception e) {
            OUTPUT.print("That failed, are you sure that you have opened a window?");
        }
    }

    /**
     * Saves the content in the current tab as a new file
     */
    public void saveAs() {
        try {
            SESSION.getTabList().get(SESSION.getSelectedID()).getController().saveAsNew();
        } catch (Exception e) {
            OUTPUT.print("That failed, are you sure that you have opened a window?");
        }

    }
}

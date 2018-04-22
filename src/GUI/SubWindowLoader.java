/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import InterfaceManagement.SubControllerInterface;
import java.awt.Insets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * This is used for loading popup menus such as the crop menu in the neural
 * network tab
 *
 * @author mathew
 */
public class SubWindowLoader {

    private Window WINDOW;
    private SubControllerInterface CONTROLLER;
    private Popup POPUP;

    /**
     * Upon initialisation it loads the GUI from the FXML file specified and
     * loads the page into a popup attatched to the windInst
     *
     * @param FXML
     * @param windInst
     */
    public SubWindowLoader(String FXML, Window windInst) {
        WINDOW = windInst;
        POPUP = new Popup();

        try {
            Resource<SubControllerInterface> r = new Resource<SubControllerInterface>(FXML);
            POPUP.getContent().add(r.getNode());
            centerPopup();
            CONTROLLER = r.loadController();
            CONTROLLER.disclosePopup(POPUP);
        } catch (Exception e) {
            System.out.println("Fail");
        }

    }

    /**
     * Gains access to the controller so data can flow back and forth from the
     * instigating class to the controller class
     *
     * @return Returns the controller instance
     */
    public SubControllerInterface getController() {
        return this.CONTROLLER;
    }

    private void centerPopup() {
        POPUP.setAnchorX((WINDOW.getX() + WINDOW.getWidth() / 2) - POPUP.getWidth() / 2);
        POPUP.setAnchorY((WINDOW.getY() + WINDOW.getHeight() / 2) - POPUP.getHeight() / 2);
    }

    /**
     * Sets the opacity of the main window to 50% and sets the pop up to visible
     * to put focus on the pop up
     */
    public void show() {
        CONTROLLER.flushReturn();
        WINDOW.setOpacity(0.5);
        POPUP.setOnHiding(new EventHandler() {
            @Override
            public void handle(Event event) {
                POPUP.getOwnerWindow().setOpacity(1.0);
            }
        });
        POPUP.show(WINDOW);
    }

    /**
     * When the popup's task has been completed, such as loading an image, the
     * corresponding object will be returned to the instigating class
     *
     * @return
     */
    public Object getReturn() {
        Object ret;

        while ((ret = CONTROLLER.getReturn()) == null && POPUP.isShowing());

        Platform.runLater(() -> POPUP.hide());
        if (ret == null) {
            System.out.println("Something went wrong or window closed.");
        }
        return ret;
    }

}

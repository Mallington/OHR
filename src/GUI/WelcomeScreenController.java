/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import ImageProcessing.ImageTools;
import InterfaceManagement.TabAttributes;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;

/**
 * FXML Controller class This tab is used for greeting the user and giving
 * information on how to start using the program
 *
 * @author mathew
 */
public class WelcomeScreenController extends TabAttributes implements Initializable {

    /**
     * Allington Industries Logo, no it's not a proper company. Yet.
     */
    @FXML
    public ImageView LOGO = new ImageView();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            URL imageR = getClass().getResource("Logo.png");

            Image image = ImageTools.convertBuffered(ImageIO.read(imageR));
            LOGO.setImage(image);

        } catch (IOException ex) {
            OUT.print("Failed to load logo");
        }
    }

    @Override
    public void setGUI(Object file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

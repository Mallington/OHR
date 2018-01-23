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
    public void load(URL url);
    public void setBackColour(Paint p);
    public void disclosePopup(Popup popInst);
    public Object getReturn();
    public void flushReturn();
    public void dispose();
}

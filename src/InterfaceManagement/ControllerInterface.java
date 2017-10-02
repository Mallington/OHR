/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceManagement;

import java.io.File;
import javafx.scene.control.Tab;

/**
 *
 * @author mathew
 */
public interface ControllerInterface {

    public void closeTab();

    public void showTab(boolean visible);

    public void setText(String title);

    public void selectTab();

    public Tab getTab();

    public void setTab(Tab t);

    public void loadIntoTab(File res);
}

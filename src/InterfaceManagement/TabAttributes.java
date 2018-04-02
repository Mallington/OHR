/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceManagement;

import GUI.FilePicker;
import GUI.NeuralNetInterfaceController;
import GUI.OutputController;
import InterfaceManagement.ControllerInterface;
import Util.Load;
import Util.Save;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import neural.Layer;

/**
 *
 * @author mathew
 */
public abstract class TabAttributes<FileType> implements ControllerInterface {

    public FileType FILE;
    public Tab TAB_INSTANCE;
    public String NAME = "UNTITLED";
    public boolean SAVED = false;
    public OutputController OUT;
    private String FILE_TYPE = ".NONE";
    private String FILE_DES = "NON FILE";
    boolean FILE_EXT_SET = false;

    public boolean NEW_DOCUMENT = true;

    public File ORIGINAL_DIRECTORY;
    
    private ArrayList<Menu> MENUS = null;
    private MenuBar MENU_BAR = null;

    abstract public void setGUI(FileType file);

    public void selectTab(){
        this.OUT.print("Selecting "+this.NAME);
        TAB_INSTANCE.setText(NAME+" ✓ ");
        if(MENU_BAR != null && MENUS !=null) for(Menu men : MENUS){
            System.out.println("Add Menu");
            MENU_BAR.getMenus().add(men);
           
        }
        else{
            System.out.println("Null!");
        }
    }
    public void deselectTab(){
        this.OUT.print("Deselecting "+this.NAME);
        TAB_INSTANCE.setText(NAME);
        if(MENU_BAR != null && MENUS !=null) for(Menu men : MENUS) {
            System.out.println("Rem Menu");
            MENU_BAR.getMenus().remove(men);
           
        }
    } 
    
    private void reloadStage(){
        ((Stage)(TAB_INSTANCE.getTabPane().getScene().getWindow())).hide();
        ((Stage)(TAB_INSTANCE.getTabPane().getScene().getWindow())).hide();((Stage)(TAB_INSTANCE.getTabPane().getScene().getWindow())).show();

        
       
        
    }
    
    public void setMenuBar(MenuBar menuBar){
        MENU_BAR = menuBar;
    }
    
    public void setMenuItems(ArrayList<Menu> menus){
        MENUS =  menus;
    }
    
    public void setModified() {
        SAVED = false;
    }

    private boolean saveMenu() {
        String[] buttons = {"Yes", "No"};
        int returnValue = JOptionPane.showOptionDialog(null, "Oh dear", "Would you like to save", JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[0]);
        if (returnValue == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    
    public void save() {
        if (NEW_DOCUMENT) {
            this.OUT.print("Saving as new file, good and new!");
            FilePicker fp = new FilePicker(FILE_TYPE, FILE_DES, "Unitled");
            // fp.getFile(this.getTab().getTabPane().getScene().get)
            Stage s = (Stage) TAB_INSTANCE.getTabPane().getScene().getWindow();

            try {
                File f = fp.getFile(s, FilePicker.SAVE);
                saveAs(f);
            } catch (Exception e) {
                this.OUT.print("You backed out! Are you happy with your self?");
            }

        } else {

            this.saveFile(this.ORIGINAL_DIRECTORY);
        }

    }

    public void load(File f) {
        this.ORIGINAL_DIRECTORY = f;
        try {
            Object in = new Load(this.ORIGINAL_DIRECTORY).load();
            if (in.getClass().equals(this.FILE.getClass())) {
                this.FILE = (FileType) in;
                OUT.print("Loaded " + this.ORIGINAL_DIRECTORY.getName());

                this.setText(this.ORIGINAL_DIRECTORY.getName());
                this.NEW_DOCUMENT = false;
                this.SAVED = true;
                this.setGUI(FILE);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NeuralNetInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NeuralNetInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setFileType(String type) {
        FILE_TYPE = type;
        FILE_EXT_SET = true;
    }

    public void setFileDes(String des) {
        FILE_DES = des;
    }

    public void setTab(Tab t) {
        this.TAB_INSTANCE = t;
        this.TAB_INSTANCE.setClosable(true);
        this.TAB_INSTANCE.setOnCloseRequest(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                closeTab();
            }
        });
    }

    public boolean hasSaved() {

        return SAVED;
    }

    public void setText(String title) {
        this.TAB_INSTANCE.setText(title);
        this.NAME = title;
    }

    public void saveAsNew() {
        this.NEW_DOCUMENT = true;
        this.save();
    }

    @Override
    public void saveAs(File f) {
        this.ORIGINAL_DIRECTORY = f;
        saveFile(this.ORIGINAL_DIRECTORY);
        this.NEW_DOCUMENT = false;
        this.OUT.print("New directory is now " + ORIGINAL_DIRECTORY.getAbsolutePath());
        hasSaved();
        this.getTab().setText(this.ORIGINAL_DIRECTORY.getName());
    }

    public void saveFile(File f) {
        try {
            new Save(f).write(this.FILE);
            this.SAVED = true;
            this.OUT.print("Saved");
        } catch (Exception e) {
            this.OUT.print("Failed to save");
        }
    }

    public Tab getTab() {
        return this.TAB_INSTANCE;
    }

    public void closeTab() {

        if (!SAVED && FILE_EXT_SET) {
            save();
        }

        TAB_INSTANCE.getTabPane().getTabs().remove(TAB_INSTANCE);

    }

    public void setOutputController(OutputController out) {
        OUT = out;
        OUT.print("Set output");
    }

}

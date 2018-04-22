/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.File;
import java.lang.reflect.Array;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author mathew
 */
public class FilePicker {

    /**
     * Value to be specified function for the save function
     */
    public static int SAVE = 0;
    /**
     * Value to be specified function for the open function
     */
    public static int OPEN = 1;

    private FileChooser PICKER;
    /**
     * Allows for custom file types to be used
     */
    private String FILE_TYPE;

    /**
     *
     * @param fileType eg .nns, .fs, .png
     * @param descriptor This is the syntax used to describe the file type for
     * example .txt would be Text File
     * @param defaultName For example untitled
     */
    public FilePicker(String fileType, String descriptor, String defaultName) {
        PICKER = new FileChooser();
        PICKER.getExtensionFilters().add(new ExtensionFilter(descriptor + " (" + fileType + ")", "*" + fileType));
        PICKER.setInitialFileName(defaultName + fileType);
        FILE_TYPE = fileType;
    }

    /**
     * Alternative instantiation without the default filename
     */
    public FilePicker(String descriptor, List<String> fileTypes) {
        PICKER = new FileChooser();
        for (String fileType : fileTypes) {
            PICKER.getExtensionFilters().add(new ExtensionFilter(descriptor + " (" + fileType + ")", "*" + fileType));
        }

    }

    /**
     *
     * @param s The current GUI it is being opened from
     * @param option Specifies the either LOAD or SAVE on the file picker UI
     * @return Returns the file
     */
    public File getFile(Stage s, int option) {

        File f = null;

        if (option == SAVE) {
            f = PICKER.showSaveDialog(s);
            if (!f.getName().contains(FILE_TYPE)) {
                f = new File(f.getAbsoluteFile() + FILE_TYPE);
            }
        } else if (option == OPEN) {
            f = PICKER.showOpenDialog(s);
        } else {
            f = null;
            System.out.println("Not a valid option!");
        }

        return f;

    }

    public static void main(String[] args) {
        FilePicker fp = new FilePicker(".nns", "neural net struct", "Unitled");
    }
}

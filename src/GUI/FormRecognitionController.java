/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Components.FormView;
import MassCharacterRecognition.FormRecognition;
import ImageProcessing.ImageTools;
import ImageProcessing.RecognitionOutput;
import InterfaceManagement.TabAttributes;
import InterfaceManagement.ControllerInterface;
import Util.FileTools;
import Util.Load;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import neural.Layer;
import neural.TrainingSet;

/**
 * FXML Controller class
 *
 * @author mathew
 */
public class FormRecognitionController extends TabAttributes<Layer> implements Initializable {

    @FXML
    ImageView FORM_VIEW = new ImageView();
    @FXML
    TextField OUTPUT_DIR = new TextField();
    @FXML
    ProgressIndicator PROGRESS_IND = new ProgressIndicator();
    @FXML
    Canvas MAIN_VIEW = new Canvas();
    @FXML
    Text SCALE_LABEL = new Text();
    @FXML
    TextField THRESH_BOX = new TextField();
     @FXML
    TextField THRESH_BOX_MAX = new TextField();
    @FXML
    Slider SCALE = new Slider();
    @FXML
    Slider THRESH_SLIDER = new Slider();
    @FXML
    Slider THRESH_SLIDER_MAX = new Slider();
    @FXML
    Text PROBABILITY = new Text();
    @FXML
    Text PROGRESS = new Text();

    boolean PROCESSING_JOB = false;

    FormView VIEW_CONTROLLER;
    private double DEFAULT_THRESH = 125;

    private Image FORM = null;
    private String trainingData = null;
    RecognitionOutput CHARS = null;

    /**
     * As per all of the ControllerInterface types, they add all of the relevant
     * menus asscoiated with that tab.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setFileType(".nns");
        this.setFileDes("Neural Net Struct");
        this.setMenuItems(new ArrayList<Menu>(genMenus())); // Need to set menus

        // Adds the listener for scaling the main imageview
        SCALE.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object val) {

                SCALE_LABEL.setText((int) (double) val + "%");
                VIEW_CONTROLLER.setScale((double) ((int) (double) val) / 100.0);
            }
        });
        THRESH_SLIDER.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object val) {
     
                THRESH_BOX.setText((int) (double) val + "");
                setImageView(FORM, (double) val,THRESH_SLIDER_MAX.getValue());
            }
        });
        THRESH_SLIDER_MAX.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object val) {
          
                THRESH_BOX_MAX.setText((int) (double) val + "");
                setImageView(FORM, THRESH_SLIDER.getValue(),(double) val);
               VIEW_CONTROLLER.setThreshold((int)(double)val);
            }
        });

        // Sets a default image
        URL imageR = getClass().getResource("UpperCase.jpg");

        try {

            FORM = ImageTools.convertBuffered(ImageIO.read(imageR));
            VIEW_CONTROLLER = new FormView(FORM, MAIN_VIEW, this);
            this.setImageView(FORM,0 ,255);

        } catch (IOException ex) {
            System.out.println("Could not find image");
        }

    }

    /**
     * Used for previewing the image after binary threshold process
     *
     * @param img Image to show
     * @param min That point at which each pixel in turned black or white
     */
    public void setImageView(Image img, double min, double max) {
        BufferedImage bimg = ImageTools.toGreyScale(ImageTools.convertImgToBuf(img), true, (int) min, (int)max);
        FORM_VIEW.setImage(ImageTools.convertBuffered(bimg));
    }

    public void importTrainingData() throws IOException {
        try{
        this.OUT.print("Importing Training Data...");
        FilePicker pick = new FilePicker("Text ", Arrays.asList(".txt", ".*"));
        File picked = pick.getFile(null, FilePicker.OPEN);
        System.out.println(picked.getAbsoluteFile());
        this.trainingData = FileTools.read(picked.getAbsolutePath());
        OUT.print("Training data imported: " + this.trainingData);
        }
        catch(Exception e){
            OUT.print("File Import Failed");
        }

    }

    public void massTrain() {
        OUT.print("Training data with: " + this.trainingData);
        OUT.print("Threshold: "+(int) this.THRESH_SLIDER_MAX.getValue());
        if (this.trainingData != null && CHARS != null && this.FILE != null) {
            if (trainingData.length() == CHARS.FORMATIONS.size()) {
                int PF = 0;
                for (char c : trainingData.toCharArray()) {
                    OUT.print("Training " + c + " as formation number " + PF);
                    TrainingSet.train(this.FILE, CHARS.FORMATIONS.get(PF), ImageTools.convertImgToBuf(FORM), c, (int) this.THRESH_SLIDER_MAX.getValue());
                    PF++;
                }

            } else {
                OUT.print("Length mismatch, training data: " + trainingData.length() + ", " + CHARS.FORMATIONS.size() + "\nYou see the problem?");
            }
        } else {

            new Thread(() -> {
                String msg = "Please complete following to mass train a network:\n";
                if (this.FILE == null) {
                    msg += "Import Neural Network\n";
                }
                if (this.trainingData == null) {
                    msg += "Import Training Data\n";
                }
                if (this.CHARS == null) {
                    msg += "Update char locations";
                }
                JOptionPane.showMessageDialog(null, msg);
            }).start();
        }
    }

    /**
     * Sets all of the relevant menus for this tab type
     *
     * @return Returns the menus to be added
     */
    public List<Menu> genMenus() {

        Menu imp = new Menu("Import");
        MenuItem fScan = new MenuItem("Form Scan");
        fScan.setOnAction(event -> {
            try {
                importScan();
            } catch (IOException ex) {
                OUT.print("Failed to import scan");
            }
        });
        MenuItem nStruct = new MenuItem("Network Structure");
        nStruct.setOnAction(event -> {
            try {
                importNetwork();
            } catch (IOException ex) {
                Logger.getLogger(FormRecognitionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        imp.getItems().addAll(fScan, nStruct);
        Menu runM = new Menu("Run");
        MenuItem runJ = new MenuItem("Run");
        runJ.setOnAction(event -> run());
       
        runM.getItems().addAll(runJ);
        return Arrays.asList(imp, runM);
    }

    /**
     * When an action listener stated in the FXML is activated, the binary
     * threshold will be updated on screen
     *
     */
    public void recalculateThreshold() {
        try {
            int val = Integer.parseInt(this.THRESH_BOX.getText());
            if (val >= 0 && val <= 255) {
                THRESH_SLIDER.setValue(val);
                this.setImageView(FORM, val, this.THRESH_SLIDER_MAX.getValue());
            } else {
                THRESH_BOX.setText((int) THRESH_SLIDER.getValue() + "");
            }
           
        } catch (Exception e) {
            THRESH_BOX.setText((int) THRESH_SLIDER.getValue() + "");
           
        }
    }
    public void recalculateThresholdMAX() {
        try {
            int val = Integer.parseInt(this.THRESH_BOX_MAX.getText());
            if (val >= 0 && val <= 255) {
                THRESH_SLIDER_MAX.setValue(val);
                this.setImageView(FORM,THRESH_SLIDER.getValue(), val);
            } else {
                THRESH_BOX_MAX.setText((int) THRESH_SLIDER_MAX.getValue() + "");
            }
           
        } catch (Exception e) {
            THRESH_BOX_MAX.setText((int) THRESH_SLIDER_MAX.getValue() + "");
           
        }
    }

    /**
     * Using the filepicker class, it loads a new image to be recognised
     *
     */
    public void changeOutput() {
        FilePicker fp = new FilePicker(".txt", "", "Unitled");
        File picked = fp.getFile(null, FilePicker.SAVE);
        OUTPUT_DIR.setText(picked.getAbsolutePath());
    }
/**
 * Prompts the user with a finder/ explorer window and the user chooses either a png or jpg
 * that is loaded into the GUI
 * @throws MalformedURLException
 * @throws IOException 
 */
    public void importScan() throws MalformedURLException, IOException {
        try{
        this.OUT.print("Importing Scan...");
        FilePicker pick = new FilePicker("Image ", Arrays.asList(".jpg", ".png"));
        File picked = pick.getFile(null, FilePicker.OPEN);
        FORM = ImageTools.convertBuffered(ImageIO.read(picked.toURI().toURL()));
        setImageView(FORM, DEFAULT_THRESH, 255);
        VIEW_CONTROLLER.loadImage(FORM);
        this.CHARS = null;
 }
        catch(Exception e){
            OUT.print("File Import Failed");
        }
    }

    /**
     * Using the filepicker class it loads a neural network structure file which
     * contains the weightings used to evaluate the characters
     *
     */
    public void importNetwork() throws MalformedURLException, IOException {
        this.OUT.print("Importing Network...");
        FilePicker pick = new FilePicker("Neural Net Struct ", Arrays.asList(".nns"));
        File picked = pick.getFile(null, FilePicker.OPEN);
        this.ORIGINAL_DIRECTORY = picked;
        this.NEW_DOCUMENT = false;
        this.FILE = (Layer) new Load(picked).load();
        this.SAVED = true;

    }

    /**
     * Executes the main function of the tab, this executes n steps: 1)
     * Binarises the image using the thresholding parameters provided by the
     * user 2) The checks for individual pixel masses (pixel formations that are
     * separate from one another) 3) Converts each pixel mass and converts it to
     * a 30x30 pixel array 4) It then runs each 30x30 pixel array through the
     * neural network (.nns) provided 5) It then determines the character by
     * taking the highest output out of each neuron 6) It does this for all the
     * pixel formations building up a map of all of the charactars 7) It then
     * executes a sorting process that recognises the individual lines a output
     * the chars in order
     */
    public void run() {

        if (this.FILE == null) {

            new Thread(() -> JOptionPane.showMessageDialog(null, "Please import a neural network layer")).start();
            this.OUT.print("No network layer loaded");
        } else {

            if (!this.PROCESSING_JOB) {
                PROCESSING_JOB = true;
                this.OUT.print("Performing Letter Recognition");
                FormRecognition FR = new FormRecognition(this.FORM, FILE, (int) this.THRESH_SLIDER_MAX.getValue()) {
                    @Override
                    public void update(RecognitionOutput out) {
                        Platform.runLater(() -> {

                            PROBABILITY.setText((int) (out.getProbability() * 100) + "");
                            PROGRESS.setText((int) (out.getProgress() * 100) + "");
                            VIEW_CONTROLLER.displayJobOutput(out, out.THRESHOLD);
                            PROGRESS_IND.setProgress(out.getProgress());
                        });
                    }

                    @Override
                    public void complete(RecognitionOutput out) {
                        OUT.print("Output: " + out.getResultantString());
                        writeToOut(out.getResultantString());
                        CHARS = out;
                        PROCESSING_JOB = false;
                    }

                    @Override
                    public void error() {
                         PROCESSING_JOB = false;
                        JOptionPane.showMessageDialog(null, "Processing Failed\nPlease check your parameters:\nEnsure the binary threshold is ajusted to reduce noise and to enhance charactar thickness\nAlso make sure you have loaded a neural network");
                        
                    }
                };

                FR.startJob();

            } else {
                new Thread(() -> JOptionPane.showMessageDialog(null, "Job in progress, please wait until finished")).start();
            }

        }

        //OUT.print("Output: "+out.getResultantString());
        // this.PROBABILITY.setText((int)(out.getProbability()*100.0)+"");
        //this.VIEW_CONTROLLER.displayJobOutput(out,  (int)this.THRESH_SLIDER.getValue());
    }
/**
 * This is used to output the results of a scan into a text file for post processing
 * @param s 
 */
    private void writeToOut(String s) {
        try {
            FileTools.write(this.OUTPUT_DIR.getText(), s);
        } catch (Exception e) {

            OUT.print("Failed to output. Have you checked the output directory?");
        }
    }


    

    /**
     * This method is not used in this particular context
     * @param file 
     */
    @Override
    public void setGUI(Layer file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

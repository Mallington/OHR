/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neural.hand.writing.recognition;

import ImageProcessing.ImageTools;
import ImageProcessing.RecognitionOutput;
import MassCharacterRecognition.FormRecognition;
import Util.FileTools;
import Util.Load;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.image.Image;
import static javafx.scene.input.DataFormat.URL;
import javax.imageio.ImageIO;
import neural.Layer;
import neural.Neural;

/**
 * The purpose of this class is to facilitate the use of command line parameters
 * to execute jobs, this means that the program can be utilised by other systems
 * by simply inputting parameters to process and then reading the output via
 * Shell Commands
 *
 * @author mathew
 */
public class Commandline {

    private static int prog = 10;

    /**
     * This is the first point of entry if any commandline parameters are
     * detected by the NeuralHandWritingRecongition class
     *
     * @param args Parameters to be processed
     */
    public static void runCommandLineJob(String[] args) {
        switch (args[0].toLowerCase()) {
            case "processform":
                if (args.length == 5) {
                    processForm(args[1], args[2], Integer.parseInt(args[3]), args[4]);
                }
                break;
            case "help":
                System.out.println("Commands:\n"
                        + "processform <Scanned image directory (.png,.jpg)> <Neural Network Directory (.nns)> <Canny Threshold (Integer)> <Output text File>");
                break;

            default:
                System.out.println("Invalid parameter: " + args[0] + "\n For commands to use, type help.");

                break;
        }
    }

    /**
     * If the commands "processform" is executed, this command is called.
     *
     * @param imageDir
     * @param neuralDir
     * @param threshold
     * @param outputDir
     */
    public static void processForm(String imageDir, String neuralDir, int threshold, String outputDir) {
        try {
            System.out.println("Image: " + imageDir);
            System.out.println("Neural Layer: " + neuralDir);
            System.out.println("Threshold: " + threshold);
            Layer network = (Layer) new Load(new File(neuralDir)).load();
            Image img = ImageTools.convertBuffered(ImageIO.read(new File(imageDir).toURI().toURL()));

            FormRecognition FR = new FormRecognition(img, network, threshold) {
                @Override
                public void update(RecognitionOutput out) {

                    if (out.getProgress() * 100 > prog) {
                        System.out.println("Progress: " + out.getProgress() * 100 + "%");
                        prog += 10;
                    }

                }

                @Override
                public void complete(RecognitionOutput out) {
                    try {
                        FileTools.write(outputDir, out.getResultantString() + "\n");
                    } catch (IOException ex) {
                        System.out.println("Error outputting file");
                    }
                    System.out.println("Processed with " + out.TOTAL_PROB + "% probability:\n" + out.getResultantString());

                }

                @Override
                public void error() {
                    System.out.println("Hmm, there was a problem processing this file!");
                }
            };

            RecognitionOutput out = FR.startJob();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }

}

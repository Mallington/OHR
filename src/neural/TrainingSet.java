/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neural;

import ImageProcessing.ImageTools;
import ImageProcessing.PixelFormation;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Rectangle;

/**
 * This class is used to store training inputs and expected outputs for
 * adjusting the neuron weightings to a desired value
 *
 * @author mathew
 */
public class TrainingSet {

    /**
     * Used for training a layer against a particular pixel formation contained
     * in an image
     *
     * @param l The layer to perform back propagation on
     * @param pf Pixel Formation class contains the bounds of the character to
     * be cropped out of the image
     * @param img The image containing the character to be used for training
     * @param toTrainAs The character to be trained as: this will refer to a
     * particular neuron in the layer
     * @param thresh This is the threshold (0-255) at which a pixel is turned
     * back or white
     */
    public static void train(Layer l, PixelFormation pf, BufferedImage img, char toTrainAs, int thresh, double learningRatio) {
        int index = -1;
        for (int i = 0; i < l.NEURONS.size(); i++) {
            l.NEURONS.get(i).setLearningRatio(learningRatio);
            if (l.NEURONS.get(i).NAME.equals(toTrainAs + "")) {
                index = i;
            }
        }
        if (index != -1) {
            Rectangle b = pf.getBounds();
            BufferedImage c = ImageTools.cropImage(img, (int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight());
            c = ImageTools.toGreyScale(c, true, thresh);
            List<Double> bin = ImageTools.imageToBinaryGrid(c, 30, 30, 127).getList();
            l.backwardProp(new TrainingSet(bin, index, l.NEURONS.size()));
        } else {
            System.out.println("Error: Could not find character: " + toTrainAs);
        }
    }
    /**
     * Training input
     */
    public List<Double> TRAINING_INPUTS;
    /**
     * Expected output
     */
    public List<Double> EXPECTED_OUTPUT;

    /**
     * This creates a new training set based on the particular neuron that its
     * aiming to have a high output
     *
     * @param toTrain Inputs to be trained against
     * @param index The index at which the neuron is expected to have a high
     * output or recongition state
     * @param size This signifies the size of the layer (amount of neurons
     * )being trained
     *
     */
    public TrainingSet(List<Double> toTrain, int index, int size) {

        EXPECTED_OUTPUT = new ArrayList<Double>();
        for (int i = 0; i < size; i++) {
            if (i == index) {
                EXPECTED_OUTPUT.add(1.0);
            } else {
                EXPECTED_OUTPUT.add(0.0);
            }
        }
        TRAINING_INPUTS = toTrain;
    }
}

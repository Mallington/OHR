/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MassCharacterRecognition;

import GUI.Storage.Grid;
import ImageProcessing.AdditionalChar;
import ImageProcessing.ImageTools;
import ImageProcessing.PixelFormation;
import ImageProcessing.RecognitionOutput;
import ImageProcessing.SortingOutput;
import java.awt.image.BufferedImage;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javax.swing.JOptionPane;
import neural.Layer;
import neural.TrainingSet;

/**
 * The function of this class is to detect the character locations, binarise each one, run it 
 * through the neural network to evaluate what each char is, work out how many lines of text
 * there is on the page and output the detected chars  using the Recognition output class.
 * @author mathew
 */
public abstract class FormRecognition {
    /**
     * Image where chars are being detected
     */
    private Image FORM;
    
    /**
     * The neural weightings used to evaluate what each char is
     */
    private Layer NEURAL;
    
    /**
     * The class containing the most up to date outputs such as char locations and order for 
     * real-time viewing
     */
    private RecognitionOutput OUT;
    private int BINARY;

    public FormRecognition(Image form, Layer neural, int binaryThresh) {
        FORM = form;
        NEURAL = neural;
        BINARY = binaryThresh;

        // Platform.runLater(()->{
        OUT = new RecognitionOutput(this);
        OUT.THRESHOLD = BINARY;
        //  });

    }
    
   
    
    /**
     * Abstract method to be executed upon an error with the recognition parameters
     */
    public abstract void error();
    /**
     * This abstract method is specified by the instigator class so that when progress
     * is updated, this method executes specialised code tailored to the context the 
     * class was instigated in, for example this could be used to update a GUI or simply
     * output into a console.
     * @param out Most up to date data at the point of call
     */
    public abstract void update(RecognitionOutput out);
    /**
     * Notifies the instigating class that processing is complete
     * @param out 
     */
    public abstract void complete(RecognitionOutput out);
    /**
     * When executed, the processing starts on a separate thread so that the instigating thread
     * does not freeze up, to create a real-time update on processing this methods uses the abstract
     * classes specified by the instigating class so that progress is constantly updated
     * @return 
     */
    public RecognitionOutput startJob() {
        new Thread(() -> {
            //Converts the image to the required format used by the ImageTools class
            BufferedImage bi = ImageTools.convertImgToBuf(FORM);
            try {
            //Binarises the so that enclosed pixel formations are able to be detcted
                bi = ImageTools.toGreyScale(bi, true, BINARY);
            // Uses a recursive algorithm to find the bounds of each enclosed pixel formation
                List<PixelFormation> pf = ImageTools.findEnclosedPixels(bi, OUT, 0.5);
                if (pf == null) {
                    error();
                }
            //When chars are detected there aren't necessarily in reading order, so this algorithm
            //detects the individual lines each char is on and order the lines
                LineSorter ls = new LineSorter(pf);
                SortingOutput sOut = ls.sortLeftToRight(); // outputs sortedchars
                OUT.FORMATIONS = sOut.FORMATIONS;
              

                double inc = 0.5 / (double) OUT.FORMATIONS.size(); //Sets the increments for the progress updater
                
                int additionalIndex =0;
                
                //This next part will use the detected bounds of each pixel formation will recognise the character in each bound
                for (int i = 0; i < OUT.FORMATIONS.size(); i++) {
                    try {
                        String out = this.evaluateCharacter(bi, OUT.FORMATIONS.get(i), NEURAL); // Detects char
                        OUT.updateProgress(OUT.getProgress() + inc); // Updates progress

                        OUT.CHARS.add(out); //adds new char to the list
                        
                        //Then adds all the additional characters such as '\n' required for new lines
                        for(AdditionalChar c : sOut.FORMATTING){
                            if(c.INDEX == additionalIndex){
                                OUT.FORMATTING.add(new AdditionalChar(c.ADDITION,i));
                                 System.out.println("(Adding formatting) To Index:" +i+"from "+c.INDEX);
                            }
                           
                        }
                        
                    } catch (Exception e) {
                        System.out.println("Failed to read char: ["+OUT.FORMATIONS.get(i).getBounds().getX()+", "+OUT.FORMATIONS.get(i).getBounds().getY()+"]");
                        OUT.FORMATIONS.remove(i);
                        
                        i--;
                        
                    }
                    additionalIndex ++;
                }
                OUT.updateProgress(1.0);

                complete(OUT); // Notifies the instigating class that processing is complete
                System.out.println("Done.");

            } catch (Exception e) {
                error();
                
            }
        }).start();
        return OUT;
    }
/**
 * This methods crops the image according to the pixel bound, converts it to a 30x30 binary image and inputs it into the neural netork
 * @param img image to be cropped
 * @param p Pixel bound used for cropping
 * @param neural Neural network weightings used for evaluating each character
 * @return Returns the highest probability character for that formation
 */
    public String evaluateCharacter(BufferedImage img, PixelFormation p, Layer neural) {
        img = ImageTools.cropImage(img, (int) p.getBounds().getX(), (int) p.getBounds().getY(), (int) p.getBounds().getWidth(), (int) p.getBounds().getHeight());
        Grid<Double> binary = ImageTools.imageToBinaryGrid(img, 30, 30, BINARY);
        List<Double> out = neural.forwardProp(new TrainingSet(binary.getList(), 0, neural.NEURONS.size()));
        double biggest = out.get(0);
        int neuronPos = 0;
        double acc = biggest;
        for (int i = 1; i < out.size(); i++) {
            acc += out.get(i);
            if (out.get(i) > biggest) {
                biggest = out.get(i);
                neuronPos = i;
            }
        }
        OUT.ACC_PROB += (biggest / acc);
        OUT.TOTAL_PROB++;
        return neural.NEURONS.get(neuronPos).NAME;
    }
}

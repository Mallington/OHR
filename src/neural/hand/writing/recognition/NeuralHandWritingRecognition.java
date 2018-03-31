/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neural.hand.writing.recognition;

import GUI.MainWindow;
import java.io.IOException;

/**
 *
 * @author mathew
 */
public class NeuralHandWritingRecognition {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        if(args.length>0) Commandline.runCommandLineJob(args);
        else MainWindow.startGUI(args); // this is where the big bang was created
        
        
    }
    
}

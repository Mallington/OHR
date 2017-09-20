/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mathew
 */
public class Save {
     private File FILE;
  
    public Save(File loc) throws FileNotFoundException{
       FILE = loc;
    }
    public boolean write(Object toWrite) throws IOException{
        try {
            Files.write(FILE.toPath(), Util.ObjectConverter.serialize(toWrite));
          
            return true;
        } catch (Exception e) {
            
            System.out.println("Failed to write object");
            e.printStackTrace();
            return false;
        }
        
    }
}

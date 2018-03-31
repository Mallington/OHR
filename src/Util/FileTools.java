/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mathew
 */
public class FileTools {
    public static void write(String dir, String toWrite) throws IOException{
        FileWriter fw = new FileWriter(dir, true);
            fw.write(toWrite+"\n");
            fw.close();
    }
    public List<String> separateCommaValues(String contents) {
        String append = "";
        List<String> ret = new ArrayList<String>();
        for (int j = 0; j < contents.length(); j++) {
            char c;
            if ((c = contents.charAt(j)) == ',') {
                ret.add(append);
                append = "";
            } else {
                append += c;
            }

        }
        return ret;
    }
    public static String read(String dir) throws FileNotFoundException, IOException{
        FileInputStream inputStream = new FileInputStream(dir);
        String append = "";
        while(inputStream.available()>0) append += (char)inputStream.read();
        inputStream.close();
        return append;
    }
}

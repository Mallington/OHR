/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mathew
 */
public class Load {

    private File FILE;

    public Load(File loc) throws FileNotFoundException {
        FILE = loc;

    }

    public Object load() throws IOException {
        try {

            byte[] array = Files.readAllBytes(FILE.toPath());
            System.out.println("Arr " + array.length);

            return Util.ObjectConverter.deserialize(array);
        } catch (Exception e) {

            return null;
        }

    }

}

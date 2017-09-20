/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 *
 * @author mathew
 */
public class ObjectConverter {

    public static byte[] serialize(Object b) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(b);
            out.flush();
            byte[] ret = bos.toByteArray();
            bos.close();
            return ret;

        } catch (Exception e) {
            System.out.println("Failed to serialise object");
            e.printStackTrace();
            bos.close();
            return null;
        }

    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            bis.close();
            return o;
        } catch (Exception e) {
            System.out.println("Failed to deserialise object");
            e.printStackTrace();
            bis.close();
            return null;
        }

    }

}

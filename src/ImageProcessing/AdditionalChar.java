/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

/**
 *This class is used during post form processing to add in extra invisible characters detected such as carriage returns.
 * @author mathew
 */
public class AdditionalChar {
    /**
     * The Char to be added after the Char at INDEX
     */
    public char ADDITION;
    /**
     * The location of the char to be added on to.
     */
    public int INDEX;
    public  AdditionalChar(char addition, int index){
        ADDITION = addition;
        INDEX = index;
    }
}

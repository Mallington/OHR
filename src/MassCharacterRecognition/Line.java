/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MassCharacterRecognition;

/**
 *
 * @author mathew This is used by the line sorter class to keep track of the
 * start and end of lines
 */
public class Line {

    public Line(int s, int e) {
        start = s;
        end = e;
    }
    int start;
    int end;
}

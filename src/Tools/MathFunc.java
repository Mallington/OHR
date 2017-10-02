/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

/**
 *
 * @author mathew
 */
public class MathFunc {

    public static double sigmoidValue(double arg) {
        return (1 / (1 + Math.exp(-arg)));
    }
}

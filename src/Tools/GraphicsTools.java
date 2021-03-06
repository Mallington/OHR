/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import ImageProcessing.PixelFormation;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * These static methods are used for a variety of Graphical drawing based
 * functions
 *
 * @author mathew
 */
public class GraphicsTools {

    /**
     * Draws a rectangle onto the canvas
     *
     * @param g
     * @param rect
     */
    public static void drawRect(GraphicsContext g, Rectangle rect) {
        g.beginPath();
        g.lineTo(rect.getX(), rect.getY());
        g.lineTo(rect.getX() + rect.getWidth(), rect.getY());
        g.lineTo(rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight());
        g.lineTo(rect.getX(), rect.getY() + rect.getHeight());
        g.lineTo(rect.getX(), rect.getY());
        g.stroke();
        g.fill();
    }

    /**
     * Checks to see if a certain point intersects a bounding rectangle
     *
     * @param xp
     * @param yp
     * @param xi
     * @param yi
     * @param width
     * @param height
     * @return
     */
    public static boolean pointIntersects(double xp, double yp, double xi, double yi, double width, double height) {
        if ((xp > xi) && (yp > yi) && (xp < xi + width) && (yp < yi + height)) {
            return true;
        }

        return false;
    }

    /**
     * Used in the CropPanel class, it used is used to a fit a pre-proportioned
     * rectangle to a certain bound, centering the bound within the proportioned
     * rectangle
     *
     * @param p
     * @return
     */
    public Rectangle squareFitFormation(PixelFormation p) {
        Rectangle b = p.getBounds();
        Rectangle r;
        double xOff = 0;
        double yOff = 0;

        double x = 0;
        if (b.getWidth() > b.getHeight()) {
            x = b.getWidth();
            yOff = (x - b.getHeight()) / 2.0;
        } else {
            x = b.getHeight();
            xOff = (x - b.getWidth()) / 2.0;
        }
        return new Rectangle(b.getX() - xOff, b.getY() - yOff, x, x);
    }

    /**
     * Draws a rectangle around a pre-defined pixel formation
     *
     * @param g
     * @param formation
     * @param X_OFF
     * @param Y_OFF
     * @param SCALE
     * @param LABELS
     */
    public static void drawFormationBounds(GraphicsContext g, List<PixelFormation> formation, double X_OFF, double Y_OFF, double SCALE, List<String> LABELS) {

        int i = 0;
        if (formation != null) {
            try {
                for (PixelFormation f : formation) {

                    Rectangle r = f.getBounds();
                    drawRect(g, new Rectangle((X_OFF + r.getX()) * SCALE, (Y_OFF + r.getY()) * SCALE, r.getWidth() * SCALE, r.getHeight() * SCALE));
                    String label = "";

                    try {
                        label = LABELS.get(i);
                    } catch (Exception e) {
                    }

                    g.strokeText("(" + label + ") F: " + i++, (r.getX() + X_OFF) * SCALE, (r.getY() + Y_OFF) * SCALE);
                }
            } catch (Exception e) {
            }
        }

    }

    /**
     * Used for debugging,to make sure the recursive pixel mass detection
     * algorithm found all of the pixels
     *
     * @param g
     * @param pF
     */
    public static void drawPixelFormations(GraphicsContext g, List<PixelFormation> pF) {
        for (PixelFormation p : pF) {
            for (java.awt.Point po : p.getPoints()) {
                g.getPixelWriter().setColor(po.x, po.y, Color.BLUE);
            }
        }

    }
}

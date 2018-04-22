/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Components;

import Tools.GraphicsTools;
import GUI.Storage.Point;
import ImageProcessing.PixelFormation;
import static Tools.GraphicsTools.pointIntersects;
import java.util.List;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * The purpose of this polymorphic class is to create a drawable image view
 * where an image such as a form scan can be imported and it can be moved and
 * manipulated while providing an interface for for other more specialised
 * classes such as the Form view and Crop Panel to implement their own functions
 * into the pre-existing architecture that the Image View class provides.
 *
 * @author mathew
 */
public abstract class ImageView extends GraphicsTools {

    protected Canvas CANVAS;
    protected double X_OFF = 0;
    protected double Y_OFF = 0;
    protected double SCALE = 1.0;
    protected Image ORIGINAL;
    protected Image DOCUMENT;
    protected Point prev = null;
    protected Paint BACK_COLOUR = Paint.valueOf("WHITE");

    /**
     * Adds the canvas and the image to be loaded and then after the child class
     * extending has initialised its own object the listeners are added and the
     * canvas is rendered.
     *
     * @param i Image to be loaded into the canvas
     * @param canv Canvas to be drawn on
     */
    public ImageView(Image i, Canvas canv) {
        CANVAS = canv;
        ORIGINAL = i;
        DOCUMENT = ORIGINAL;

        Platform.runLater(() -> {
            addListeners();
            render();

        });
    }

    /**
     * The classes extending this particular class will need to be able to draw
     * the bounds around recognised characters and then when the user clicks on
     * one, identify which one has been clicked
     *
     * @param m
     * @param formations
     * @return
     */
    public PixelFormation findFormIntercept(MouseEvent m, List<PixelFormation> formations) {
        if (formations == null) {
            return null;
        }
        for (PixelFormation p : formations) {
            Rectangle b = p.getBounds();
            if (pointIntersects(m.getX() / SCALE - X_OFF, m.getY() / SCALE - Y_OFF, b.getX(), b.getY(), b.getWidth(), b.getHeight())) {
                return p;
            }
        }
        return null;

    }

    /**
     * Simply changes the image and then re-renders the canvas.
     *
     * @param img Image to be loaded into the canvas
     */
    public void loadImage(Image img) {
        this.DOCUMENT = img;
        this.ORIGINAL = img;
        render();
    }

    /**
     * For future customisation such as a change of texture pack, I have left
     * this in for future development
     *
     * @param c Colour to change the background to
     */
    public void setBackgroundColour(Paint c) {
        BACK_COLOUR = c;
        render();
    }

    /**
     * Sets the scale of the image to be drawn in the canvas
     *
     * @param scale
     */
    public void setScale(double scale) {
        SCALE = scale;
        render();
    }

    /**
     * This adds the listeners responsible for allowing the using to drag and
     * move the image, as well as completing extra functions upon a particular
     * actions which is specified by the child class extending this one.
     */
    void addListeners() {
        CANVAS.setOnMouseClicked(m -> clicked(m));

        CANVAS.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                event -> {
                    if (!(prev == null)) {
                        boolean moveImage = dragged(event);
                        if (moveImage) {
                            X_OFF += (event.getX() - prev.getX()) / SCALE;
                            Y_OFF += (event.getY() - prev.getY()) / SCALE;
                        }
                    }
                    prev = new Point(event.getX(), event.getY());
                    render();
                });

        CANVAS.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        prev = null;
                        released(event);
                    }
                });

        CANVAS.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> pressed(event));

        CANVAS.setOnScroll(event -> {
            ScrollEvent s = (ScrollEvent) event;
            X_OFF += s.getDeltaX() / SCALE;
            Y_OFF += s.getDeltaY() / SCALE;
            render();
        });

    }

    /**
     * This renders the entire canvas including the image specified to the scale
     * and offset, it also draws any additional objects such as pixel formations
     * bound specified by the child class through the abstract method tick().
     */
    public void render() {
        GraphicsContext g = CANVAS.getGraphicsContext2D();
        g.setFill(this.BACK_COLOUR);
        g.fillRect(0, 0, CANVAS.getWidth(), CANVAS.getHeight());
        drawImage(g);
        tick(g);
    }

    /**
     * Used to draw the image onto the canvas
     *
     * @param g
     */
    public void drawImage(GraphicsContext g) {

        g.scale(SCALE, SCALE);
        g.drawImage(DOCUMENT, this.X_OFF, this.Y_OFF);
        g.scale(1.0 / SCALE, 1.0 / SCALE); //reverts scale back to original
    }

    /**
     * For the child class to add an extra layer to the graphics being drawn
     *
     * @param g Graphics to be drawn
     */
    abstract void tick(GraphicsContext g);

    /**
     * Ran by the ImageView in the listeners when the user clicks on the canvas
     *
     * @param m
     */
    abstract void clicked(MouseEvent m);

    /**
     * Ran by the ImageView in the listeners when the user scrolls on the canvas
     *
     * @param event
     */
    abstract void scroll(Event event);

    /**
     * Ran by the ImageView in the listeners when the user presses on the canvas
     *
     * @param event
     */
    abstract void pressed(MouseEvent event);

    /**
     * Ran by the ImageView in the listeners when the user releases a click on
     * the canvas
     *
     * @param event
     */
    abstract void released(MouseEvent event);

    /**
     * Ran by the ImageView in the listeners when the user drags on the canvas
     *
     * @param event
     */
    abstract boolean dragged(MouseEvent event);
}

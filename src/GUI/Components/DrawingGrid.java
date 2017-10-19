/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Components;

import GUI.Storage.Grid;
import Tools.MathFunc;
import java.awt.Rectangle;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 *
 * @author 160471
 */
public class DrawingGrid {

    private Canvas CANVAS;
    private boolean GEN_FRAME = true;
    private boolean LISTEN = false;
    private Grid<Double> GRID;
    private int G_WIDTH;
    private int G_HEIGHT;
    private Rectangle[][] BOUNDS;
    private double PAINT_VALUE = 1.0;

    public DrawingGrid(int x, int y, Canvas canv) {
        CANVAS = canv;
        GRID = new Grid<Double>(x, y, 0.0);
        G_WIDTH = x;
        G_HEIGHT = y;
        BOUNDS = new Rectangle[x][y];
        addListeners();
        tick();

        //runGraphics();
    }
    public void setContents(List<Double> contents){
        if(contents.size() == this.getOutput().size()){
        this.GRID.setList(contents);
        tick();
        }
        else{
            System.out.println("Array mismatch!");
    }
    }
    public List<Double> getOutput() {
        return GRID.getList();
    }

    public void clear() {
        for (int x = 0; x < G_WIDTH; x++) {
            for (int y = 0; y < G_HEIGHT; y++) {
                GRID.set(x, y, 0.0);
            }
        }
        tick();
    }

    private void runListener() {
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
        Runnable t = () -> listen();
        timer.scheduleAtFixedRate(t, 0, 33, TimeUnit.MILLISECONDS);
    }

    private void listen() {
        if (LISTEN) {
            //CANVAS.getScene().get
        }
    }

    private void calculateBounds() {
        double rectWidth = (double) CANVAS.getWidth() / (double) G_WIDTH;
        double rectHeight = (double) CANVAS.getHeight() / (double) G_HEIGHT;

        for (int x = 0; x < G_WIDTH; x++) {
            for (int y = 0; y < G_HEIGHT; y++) {
                double across = x * rectWidth;
                double down = y * rectHeight;
                BOUNDS[x][y] = new Rectangle((int) across, (int) down, (int) rectWidth, (int) rectHeight);
            }
        }

    }

    private void setByBounds(int xC, int yC) {
       
        for (int x = 0; x < G_WIDTH; x++) {
            for (int y = 0; y < G_HEIGHT; y++) {

                Rectangle r = BOUNDS[x][y];
                if (xC >= r.x && yC >= r.y && xC < (r.x + r.width) && yC < (r.y + r.height)) {
                    GRID.set(x, y, PAINT_VALUE);
                 
                    GRID.get(x, y);
                }

                //}
            }
        }
    }

    private void addListeners() {
        CANVAS.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
               
                setByBounds((int) event.getX(), (int) event.getY());
                tick();
            }
        });

       
    }

    private void tick() {
        if (GEN_FRAME) {

            GraphicsContext g = CANVAS.getGraphicsContext2D();
            g.setFill(Color.WHITE);

            g.fillRect(0, 0, CANVAS.getWidth(), CANVAS.getHeight());

            calculateBounds();
            drawGrid(g);

        }
    }

    private void drawGrid(GraphicsContext g) {
        for (int x = 0; x < G_WIDTH; x++) {
            for (int y = 0; y < G_HEIGHT; y++) {

              
                if(this.GRID.get(x, y)>1) System.out.println("Above 1 wut! "+this.GRID.get(x, y));
                int col = 255-(int)((this.GRID.get(x, y)*255.0));
                if(col>255) System.out.println("Above 255: "+col+"guilty num: "+this.GRID.get(x, y)+", x "+x+", y "+y);
                g.setFill(Color.rgb(col, col, col));

                g.fillRect(BOUNDS[x][y].getX(), BOUNDS[x][y].getY(), BOUNDS[x][y].getWidth(), BOUNDS[x][y].getHeight());

            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


/**
 *
 * @author mathew
 */
public class BinaryGrid extends JPanel {

    //temp
    static boolean good = true;
    
    
    boolean[][] GRID_CONTENTS;
    Rectangle[][] BOUNDS;
    int G_WIDTH;
    int G_HEIGHT;
    boolean mouseDown = false;
    Point previousLocation;
    Point mouseLocation;
    int BRUSH_RAD = 5;

    public BinaryGrid(int width, int height) {
        GRID_CONTENTS = new boolean[width][height];
        BOUNDS = new Rectangle[width][height];
        G_WIDTH = width;
        G_HEIGHT = height;
        //checkerGrid();
        startGraphics();
        addListeners();

    }

    private void tick() {
        repaint();

    }

    private void updateMouseLocation() {
        previousLocation = mouseLocation;
        mouseLocation = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mouseLocation, this);

    }

    private void handleMouse() {
        updateMouseLocation();
        if (mouseDown) {
            markByBound(mouseLocation.x, mouseLocation.y);
            //markWithoutGaps(previousLocation, mouseLocation, (G_WIDTH+G_HEIGHT)/20);
        }

    }

    private void markWithoutGaps(Point start, Point end, int def) {
        int deltaX = end.x - start.x;
        int deltaY = end.y - start.y;

        for (int i = 1; i <= def; i++) {
            markByBound(start.x + (deltaX) / i, start.y + (deltaY) / i);
        }

    }

    private void addListeners() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent c) {
                mouseDown = true;

            }

            public void mouseReleased(MouseEvent c) {
                mouseDown = false;
            }

        });

    }

    private void calculateBounds() {
        double rectWidth = (double) this.getWidth() / (double) G_WIDTH;
        double rectHeight = (double) this.getHeight() / (double) G_HEIGHT;

        for (int x = 0; x < G_WIDTH; x++) {
            for (int y = 0; y < G_HEIGHT; y++) {
                double across = x * rectWidth;
                double down = y * rectHeight;
                BOUNDS[x][y] = new Rectangle((int) across, (int) down, (int) rectWidth, (int) rectHeight);
            }
        }

    }

    private void checkerGrid() {
        boolean currentBool = true;
        for (int x = 0; x < G_WIDTH; x++) {
            for (int y = 0; y < G_HEIGHT; y++) {
                GRID_CONTENTS[x][y] = currentBool;

                currentBool = !currentBool;
            }
            if (G_HEIGHT % 2 == 0) {
                currentBool = !currentBool;
            }

        }

    }

    private void startGraphics() {
        Runnable toSched = () -> tick();
        Runnable mouse = () -> handleMouse();
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(toSched, 0, 33, TimeUnit.MILLISECONDS);
        timer.scheduleAtFixedRate(mouse, 0, 1000, TimeUnit.MICROSECONDS);

    }

    private void drawBrush(Graphics2D d, int x, int y) {

        d.setStroke(new BasicStroke(1));
        d.setColor(Color.gray);
        d.drawOval(x - BRUSH_RAD, y - BRUSH_RAD, BRUSH_RAD * 2, BRUSH_RAD * 2);
    }

    public void paint(Graphics g) {

        Graphics2D d = (Graphics2D) g;

        d.setColor(Color.white);
        d.fillRect(0, 0, this.getWidth(), this.getHeight());
        d.setColor(Color.black);
        d.drawRect(0, 0, this.getWidth(), this.getHeight());
        calculateBounds();
        
        for (int x = 0; x < G_WIDTH; x++) {
            for (int y = 0; y < G_HEIGHT; y++) {
                Color fill = Color.BLACK;
                if (!GRID_CONTENTS[x][y]) {
                    fill = Color.WHITE;
                }
                d.setColor(fill);
                Rectangle r = BOUNDS[x][y];
                d.fillRect(r.x, r.y, r.width, r.height);
            }
        }

        drawBrush(d, mouseLocation.x, mouseLocation.y);

    }

    public void markByBound(int xC, int yC) {
        
        for (int x = 0; x < G_WIDTH; x++) {
            for (int y = 0; y < G_HEIGHT; y++) {
                Rectangle r = BOUNDS[x][y];
                if (xC >= r.x && yC >= r.y && xC < (r.x + r.width) && yC < (r.y + r.height)) {

                    GRID_CONTENTS[x][y] = true;
                }
            }
        }
    }

    public List<Double> getData() {
        List<Double> data = new ArrayList<Double>();
        for (int y = 0; y < G_HEIGHT; y++) {
            for (int x = 0; x < G_WIDTH; x++) {
                if (GRID_CONTENTS[x][y]) {
                    data.add(1.0);
                } else {
                    data.add(0.0);
                }
            }
        }
        return data;
    }

    public void clear() {
        GRID_CONTENTS = new boolean[G_WIDTH][G_HEIGHT];
    }

  

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import java.util.*;

/**
 *
 * @author mathew
 */
public class ProcessorStack {

    private Runnable[] STACK;
    private int PROCESSOR_DELAY = 1;
    private int currentJob;
    private int end;

    public ProcessorStack(int stackLimit) {
        STACK = new Runnable[stackLimit];
        currentJob = 0;
        end = 1;
        startProcessing();

    }

    private void startProcessing() {
        new Thread(() -> process()).start();
    }

    public void add(Runnable r) {
        if (end != currentJob) {
            STACK[end - 1] = r;
            end++;
            if (end == STACK.length) {
                end = 1;
            }
            System.out.println("Adding, END: " + end);
        } else {
            System.out.println("Full");
        }

    }

    private void incrementJob() {
        currentJob++;

        if (currentJob == STACK.length) {
            currentJob = 0;
        }
        if (currentJob == end) {
            System.out.println("Stack Full");
        }
    }

    private void pop() {
        if (currentJob != end) {
            System.out.println(currentJob);
            STACK[currentJob].run();
            incrementJob();
        } else {
            System.out.println("END");
        }
    }

    private void process() {
        while (true) {
            try {
                Thread.sleep(PROCESSOR_DELAY);
            } catch (Exception e) {
            };
            pop();
        }
    }

    public static void main(String args[]) throws InterruptedException {
        ProcessorStack k = new ProcessorStack(5);
        for (int i = 0; i < 5; i++) {
            k.add(() -> System.out.println("Run"));
        }
        Thread.sleep(10000);
    }

}

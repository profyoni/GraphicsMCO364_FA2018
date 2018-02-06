package edu.touro.mco364;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class Main{
    final static int MAX_ITERATIONS = 10_000_000;
    static long total;

    public synchronized static void incTotal(){ // accessable by one thread at a time
        total += 1;
    }

    public static void main(String[] args) {
        //new PaintApp();
        Object key = new Object();
        Object k2 = key;

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Started 1");
                for (int i=0; i< MAX_ITERATIONS; i++)
                {
                    //incTotal();
                    synchronized (k2){
                        total += 1;
                    }

                }
                System.out.println("Ended 1");
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Started 2");
                for (int i=0; i< MAX_ITERATIONS; i++)
                {
                    //incTotal();
                    synchronized ( key){
                    total += 1;
                }
                }
                System.out.println("Ended 2");
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Started 3");
                for (int i=0; i< MAX_ITERATIONS; i++)
                {
                    //incTotal();
                    synchronized (key){
                        total += 1;
                    }
                }
                System.out.println("Ended 3");
            }
        });
        t1.start();
        t2.start();
        t3.start();

        Scanner kb = new Scanner(System.in);
        kb.next();

        System.out.println(total);
    }
}

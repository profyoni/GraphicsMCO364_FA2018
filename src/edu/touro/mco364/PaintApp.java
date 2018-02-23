package edu.touro.mco364;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Pong extends JFrame implements ActionListener{
    Pong()
    {
        setSize(500,500);
        JButton button = new JButton("Press");
        add(button, BorderLayout.NORTH);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        button.addActionListener(this );
    }

    int x = 100, y= 200;
    Timer timer ;
    @Override
    public void actionPerformed(ActionEvent e) {
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Graphics g = getGraphics();
                x+=5;
                y+=7;
                g.fillOval(x,y,30,30);
            }
        });
        timer.start();
    }
}


public class PaintApp extends JFrame {

    class CanvasPanel extends JPanel {

        public CanvasPanel() {
            setBackground(new Color(200, 19, 255));
        }

        @Override
        public void paint(Graphics g) { // auto called when needed
            super.paint(g);

            Graphics2D g2 = (Graphics2D) g;
            if (drawnHistory.size() <= 1) {
                return;
            }
            Point prev = null;
            g2.setStroke(new BasicStroke(5));
            for (Point p : drawnHistory) {
                if (prev != null) {
                    g2.drawLine(prev.x, prev.y, p.x, p.y);
                }
                prev = p;
            }
        }

    }

    private ExecutorService threadPool = Executors.newFixedThreadPool(100);

    private StringBuffer buffer = new StringBuffer();
    int z = 5;
    javax.swing.Timer timer = new javax.swing.Timer(100,
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    synchronized (buffer) {
                        outputArea.setText("");
                        outputArea.append(buffer.toString());
                        buffer.delete(0, buffer.length());
                    }
//
                }
            });
    JTextArea outputArea;
    public PaintApp() {
        setTitle("Amazing Paint App!");

        setSize(600, 800);
        JPanel canvas = new CanvasPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(BorderLayout.CENTER, canvas);

        JButton button = new JButton("Press Me");

        outputArea = new JTextArea(100, 15);
        timer.start();

        JScrollPane scroller = new JScrollPane(outputArea);
        add(BorderLayout.EAST, scroller);

        add(BorderLayout.NORTH, button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                threadPool.submit(new Runnable() {
                                      @Override
                                      public void run() {
                                          for (int i = 0; i < 1000000; i++) {
                                              String counter = Thread.currentThread().getName() + ":" + i;
                                              System.out.println(counter);
                                              System.out.println(z);

                                              buffer.append(counter + '\n');

//                                              SwingUtilities.invokeLater(
//                                                      new Runnable() {
//                                                          @Override
//                                                          public void run() {
//                                                              outputArea.append(counter + '\n');
//                                                          }
//                                                      }
//                                              );

                                              try {
                                                  Thread.sleep(0);
                                              } catch (InterruptedException e1) {
                                                  e1.printStackTrace();
                                              }
                                          }
                                      }
                                  }
                );
            }
        });
        z++;
        JLabel statusBar = new JLabel("Status: ");
        add(BorderLayout.SOUTH, statusBar);

        canvas.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent me) {
            }

            @Override
            public void mouseMoved(MouseEvent me) {
                statusBar.setText(String.format("[%d, %d]", me.getX(), me.getY()));
                if (prev != null) {
                    Graphics2D g2 = (Graphics2D) canvas.getGraphics();
                    g2.setStroke(new BasicStroke(5));
                    g2.drawLine(me.getX(), me.getY(), prev.x, prev.y);
                }
                prev = me.getPoint();
                drawnHistory.add(prev);
//                canvas.getGraphics().fillOval(me.getX() , me.getY(), 20, 20);
            }
        });

        setSize(800, 600);
        setVisible(true);



    }

    LinkedList<Point> drawnHistory = new LinkedList<>();
    Point prev;
}

package edu.touro.mco364;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

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
    public PaintApp() {
        setTitle("Amazing Paint App!");

        setSize(600, 800);
        JPanel canvas = new CanvasPanel();

        add(BorderLayout.CENTER, canvas);

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

        setSize(800,600);
        setVisible(true);

    }
    LinkedList<Point> drawnHistory = new LinkedList<>();
    Point prev;
}
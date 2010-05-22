package tabuvrp.gui;

import java.awt.*;
import tabuvrp.core.*;


public class SolutionDisplay extends Canvas {

    private Solution sol;
    private Graph graph;
    private final double minX, maxX, xCorr, mX;
    private final double minY, maxY, yCorr, mY;

//    public SolutionDisplay() {
//        this.graph = null;
//        minX = 0;
//        maxX = 0;
//        minY = 0;
//        maxY = 0;
//        sol = null;
//    }
    public SolutionDisplay(Graph graph, Solution solution) {
        super();
        this.graph = graph;
        minX = graph.getMinX();
        maxX = graph.getMaxX();
        xCorr = (maxX - minX) * 0.05;
        mX = minX - xCorr;
        minY = graph.getMinY();
        maxY = graph.getMaxY();
        yCorr = (maxY - minY) * 0.05;
        mY = minY - yCorr;
        sol = solution;
    }

    @Override
    public void paint(Graphics graphics) {
//        graphics.drawOval(getWidth() / 2 , getHeight() / 2, getWidth() / 2 , getHeight() / 2);
//        System.out.println(getHeight() + " " + getWidth() + "");
        Graphics2D g =(Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        Dimension d = getSize();
        double xFact = d.width  / (maxX - minX + xCorr * 2);
        double yFact = d.height / (maxY - minY + yCorr * 2);
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < graph.getNodeCount(); ++i) {
            Node n = graph.getNode(i);
            g.fillOval((int) Math.round((n.getX() - mX) * xFact),
                       (int) Math.round((n.getY() - mY) * yFact),
                       8, 8);
            g.drawString(String.valueOf(i),
                         (int) Math.round((n.getX() - mX) * xFact),
                         (int) Math.round((n.getY() - mY) * yFact));
        }
        g.setColor(Color.green);
        Integer[][] solView = sol.getSolView();
        for (Integer[] route : solView) {
            for (int n = 1; n < route.length; ++n) {
                if (n % 2 == 0) {
                    g.setColor(Color.blue);
                } else {
                    g.setColor(Color.red);
                }
                Node n1 = graph.getNode(route[n - 1]);
                Node n2 = graph.getNode(route[n]);
                g.drawLine((int) Math.round((n1.getX() - mX) * xFact),
                           (int) Math.round((n1.getY() - mY) * yFact),
                           (int) Math.round((n2.getX() - mX) * xFact),
                           (int) Math.round((n2.getY() - mY) * yFact));
                g.drawString(String.valueOf(Math.round(graph.getEdge(route[n - 1], route[n]).getCost())),
                             Math.round(((n2.getX() + n1.getX()) / 2 - mX) * xFact),
                             Math.round(((n2.getY() + n1.getY()) / 2 - mY) * yFact) );
            }
        }
    }
    
    public void setSolution(Solution solution) {
        sol = solution;
        this.repaint();
    }
}

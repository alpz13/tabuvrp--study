package tabuvrp.gui;

import java.awt.*;
import javax.swing.*;
import tabuvrp.core.*;


public class SolutionDisplay extends Canvas {

    private Solution sol;
    private Graph graph;

    public SolutionDisplay(Graph graph, Solution solution) {
        super();
        this.graph = graph;
        sol = solution;
    }

    @Override
    public void paint(Graphics graphics) {
        Graphics2D g =(Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        Dimension d = this.getSize();
        double xFact = d.width / (graph.getMaxX() - graph.getMinX());
        double yFact = d.width / (graph.getMaxY() - graph.getMinY());
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < graph.getNodeCount(); ++i) {
            Node n = graph.getNode(i);
            g.fillOval((int) Math.round((n.getX() - graph.getMinX()) * xFact),
                       (int) Math.round((n.getY() - graph.getMinY()) * yFact),
                       8, 8);
            g.drawString("[" + i + "]",
                         (int) Math.round((n.getX() - graph.getMinX()) * xFact),
                         (int) Math.round((n.getY() - graph.getMinY()) * yFact));
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
                g.drawLine((int) Math.round((graph.getNode(route[n - 1]).getX() - graph.getMinX()) * xFact),
                           (int) Math.round((graph.getNode(route[n - 1]).getY() - graph.getMinY()) * yFact),
                           (int) Math.round((graph.getNode(route[n]).getX() - graph.getMinX()) * xFact),
                           (int) Math.round((graph.getNode(route[n]).getY() - graph.getMinY()) * yFact));
            }
        }
    }
    
    public void setSolution(Solution solution) {
        sol = solution;
        this.repaint();
    }
}

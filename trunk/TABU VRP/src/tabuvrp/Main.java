package tabuvrp;

import tabuvrp.vrp.*;
import tabuvrp.gui.TabuVRP;


public class Main {

    public static void main(String[] args) {

        VRP graph = null;

        /* GRAPH INIT */
        try {
            graph = VRPFactory.newVRPFromFile(args[0]);
        } catch (Exception exc) {
            exc.printStackTrace();
            System.exit(1);
        }

        new TabuVRP(graph).setVisible(true);
    }
}

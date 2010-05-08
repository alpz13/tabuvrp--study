package tabuvrp.vrp;

import java.util.ArrayList;
import tabuvrp.graph.*;


public class Solution {


    protected final I_GraphView<Node, Edge> graph;

    protected final ArrayList<Path> paths;


    public Solution(I_GraphView<Node, Edge> graph) {
        this.graph = graph;
        this.paths = new ArrayList<Path>();
    }


    public int getCost() {
        int cost = 0;
        for (Path p : paths) {
            cost += p.getCost();
        }
        return cost;
    }


    public boolean isFeasible() {
        for (Path p : paths) {
            if (!p.isFeasible()) {
                return false;
            }
        }
        return true;
    }


}

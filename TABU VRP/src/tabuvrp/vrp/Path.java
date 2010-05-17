package tabuvrp.vrp;

import tabuvrp.core.Graph;
import tabuvrp.core.Node;
import java.util.ArrayList;


public class Path {

    protected final Graph graph;
    protected final ArrayList<Integer> steps;
    protected int cost;
    protected int demandBalance;

    public Path(Graph graph) {
        this.graph = graph;
        steps = new ArrayList<Integer>();
        cost = 0;
        demandBalance = graph.getNode(0).getDemand();
    }

    public boolean isEmpty() {
        return steps.isEmpty();
    }

    public void insert(int position, Integer nodeIndex) {
        cost += deltaCostForInsert(position, nodeIndex);
        demandBalance += deltaDemandBalanceForInsert(nodeIndex);
        steps.add(position, nodeIndex);
    }

    public void remove(int position) {
        cost += deltaCostForRemove(position);
        demandBalance += deltaDemandBalanceForRemove(position);
        steps.remove(position);
    }

    public int deltaCostForInsert(int position, Integer nodeIndex) {
        if (steps.isEmpty()) {
            return    graph.getEdge(0, nodeIndex).getCost()
                    + graph.getEdge(nodeIndex, 0).getCost();
        } else {
            Integer start = (position == 0) ? 0 : steps.get(position - 1);
            Integer end = (position == steps.size()) ? 0 : steps.get(position);
            return    graph.getEdge(start, nodeIndex).getCost()
                    + graph.getEdge(nodeIndex, end).getCost()
                    - graph.getEdge(start, end).getCost();
        }
    }

    public int deltaDemandBalanceForInsert(Integer nodeIndex) {
        return graph.getNode(nodeIndex).getDemand();
    }

    public int deltaCostForRemove(int position) {
        if (steps.size() <= 1) {
            return -cost;
        }
        Integer start = (position == 0) ? 0 : steps.get(position - 1);
        Integer mid = steps.get(position);
        Integer end = (position == steps.size() - 1) ? 0 : steps.get(position);
        return    graph.getEdge(start, end).getCost()
                - graph.getEdge(start, mid).getCost()
                - graph.getEdge(mid, end).getCost();
    }

    public int deltaDemandBalanceForRemove(int position) {
        Node node = graph.getNode(steps.get(position));
        return node.getDemand();
    }

    public int getCost() {
        return cost;
    }

    public int getDemandBalance() {
        return demandBalance;
    }

    public boolean isFeasible() {
        return demandBalance <= 0;
    }

    public int getStepCount() {
        return steps.size();
    }

    public int getPositionByNodeIndex(Integer nodeIndex) {
        return steps.indexOf(nodeIndex);
    }

    public Graph getProblem() {
        return graph;
    }

    @Override
    public String toString() {
        // TODO: use builder.
        String s = "Path: ";
        for (Integer i : steps) {
            s += i + " ";
        }
        s += "cost:" + cost + " demand:" + demandBalance;
        return s;
    }
}

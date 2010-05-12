package tabuvrp.vrp;

import java.util.ArrayList;


public class Path {

    protected final Problem graph;
    protected final ArrayList<Integer> steps;
    protected int cost;
    protected int demandBalance;

    public Path(Problem graph) {
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
        steps.add(position, nodeIndex);
        demandBalance += graph.getNode(nodeIndex).getDemand();
    }

    public void remove(int position) {
        demandBalance -= graph.getNode(steps.get(position)).getDemand();
        steps.remove(position);
        cost += deltaCostForRemove(position);
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
        return demandBalance + graph.getNode(nodeIndex).getDemand();
    }

    public int deltaOverDemandForInsert(Integer nodeIndex) {
        int newDem = graph.getNode(nodeIndex).getDemand();
        return   ((demandBalance > 0)? demandBalance : 0)
               + ((newDem > 0)? newDem : 0);
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
        return demandBalance - steps.get(position);
    }

    public int getCost() {
        return cost;
    }

    public int getDemandBalance() {
        return demandBalance;
    }

    public int getOverDemand() {
        return (demandBalance > 0)? demandBalance : 0;
    }

    public boolean isFeasible() {
        return demandBalance <= 0;
    }

    public int getStepCount() {
        return steps.size();
    }

    public int getPositionByNodeIndex(Integer nodeIndex) {
        // TODO: need faster lookup.
        return steps.indexOf(nodeIndex);
    }
}

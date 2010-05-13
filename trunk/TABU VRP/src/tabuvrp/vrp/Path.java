package tabuvrp.vrp;

import java.util.ArrayList;


public class Path {

    protected final Problem graph;
    protected final ArrayList<Integer> steps;
    protected int cost;
    protected int demandBalance;
    protected int overDemand;

    public Path(Problem graph) {
        this.graph = graph;
        steps = new ArrayList<Integer>();
        cost = 0;
        demandBalance = graph.getNode(0).getDemand();
        overDemand = 0;
    }

    public boolean isEmpty() {
        return steps.isEmpty();
    }

    public void insert(int position, Integer nodeIndex) {
        cost += deltaCostForInsert(position, nodeIndex);
        demandBalance += deltaDemandBalanceForInsert(nodeIndex);
        overDemand += deltaOverDemandForInsert(nodeIndex);
        steps.add(position, nodeIndex);
    }

    public void remove(int position) {
        cost += deltaCostForRemove(position);
        demandBalance += deltaDemandBalanceForRemove(position);
        overDemand += deltaOverDemandForRemove(position);
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

    public int deltaOverDemandForInsert(Integer nodeIndex) {
        int newDem = graph.getNode(nodeIndex).getDemand();
        return  (newDem > 0)? newDem : 0;
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

    public int deltaOverDemandForRemove(int position) {
        int nodeDemand = graph.getNode(steps.get(position)).getDemand();
        return (overDemand - nodeDemand < 0)? (- overDemand) : (- nodeDemand);
    }

    public int getCost() {
        return cost;
    }

    public int getDemandBalance() {
        return demandBalance;
    }

    public int getOverDemand() {
        return overDemand;
    }

    public boolean isFeasible() {
        return overDemand == 0;
    }

    public int getStepCount() {
        return steps.size();
    }

    public int getPositionByNodeIndex(Integer nodeIndex) {
        // TODO: need faster lookup.
        return steps.indexOf(nodeIndex);
    }

    public Problem getProblem() {
        return graph;
    }

    @Override
    public String toString() {
        // TODO: use builder.
        String s = "Path: ";
        for (Integer i : steps) {
            s += i + " ";
        }
        s += "c:" + cost + " db:" + demandBalance + " od:" + overDemand;
        return s;
    }
}

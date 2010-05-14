package tabuvrp.core;


public final class Node {

    private final int demand;

    public Node(int demand) {
        this.demand = demand;
    }

    public Node(String s) {
        this.demand = Integer.parseInt(s);
    }

    public final int getDemand() {
        return demand;
    }

    @Override
    public String toString() {
        return "Demand: " + demand;
    }
}


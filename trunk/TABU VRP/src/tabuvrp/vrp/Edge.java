package tabuvrp.vrp;

public final class Edge {

    private final int cost;

    public Edge(int cost) {
        this.cost = cost;
    }

    public Edge(String s) {
        this.cost = Integer.parseInt(s);
    }

    public final int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Cost: " + cost;
    }
}

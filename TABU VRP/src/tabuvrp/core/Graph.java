package tabuvrp.core;


public interface Graph {

    public Node getNode(int n);

    public Edge getEdge(int n1, int n2);

    public int[] getNeighbourhood(int n, int count);

    public int getNodeCount();
}
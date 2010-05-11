package tabuvrp.vrp;


public class VRP implements Problem {


	protected Node[] nodes;

	protected Edge[][] edges;

	protected final int nodeCount;


	public VRP(int n) {
		nodes = new Node[n];
		edges = new Edge[n][n];
		nodeCount = n;
	}


	public int getNodeCount() {
		return nodeCount;
	}


	public Node getNode(int n) {
		return nodes[n];
	}


	public Edge getEdge(int i, int j) {
		return edges[i][j];
	}


	public void setNode(int n, Node node) {
		nodes[n] = node;
	}


	public void setEdge(int i, int j, Edge edge) {
		edges[i][j] = edge;
	}


}

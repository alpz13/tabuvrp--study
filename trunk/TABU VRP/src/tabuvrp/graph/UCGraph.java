/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tabuvrp.graph;

/**
 *
 * @author lmolr
 */
public class UCGraph<N, E> implements I_Graph<N, E> {


	protected N[] nodes;

	protected E[][] edges;

	protected final int nodeCount;


	@SuppressWarnings("unchecked")
	public UCGraph(int n) {
		nodes = (N[]) new Object[n];
		edges = (E[][]) new Object[n][n];
		nodeCount = n;
	}


	public int getNodeCount() {
		return nodeCount;
	}


	public N getNode(int n) {
		return nodes[n];
	}


	public E getEdge(int i, int j) {
		return edges[i][j];
	}


	public void setNode(int n, N node) {
		nodes[n] = node;
	}


	public void setEdge(int i, int j, E edge) {
		edges[i][j] = edge;
	}


}

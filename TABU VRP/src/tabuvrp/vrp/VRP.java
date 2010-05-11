package tabuvrp.vrp;

import java.util.Arrays;
import java.util.Comparator;


public class VRP implements Problem {

    class Util implements Comparator<Integer> {

        final Edge[][] edges;
        final int i;

        Util(Edge[][] edges, int i) {
            this.edges = edges;
            this.i = i;
        }

        public int compare(Integer j1, Integer j2) {
            return Integer.valueOf(edges[i][j1].getCost()).compareTo(edges[i][j2].getCost());
        }
    }


    protected Node[] nodes;

    protected Edge[][] edges;

    protected int[][] neighbourhood;

    protected final int nodeCount;


    public VRP(Node[] nodes, Edge[][] edges, int n) {

        this.nodes = Arrays.copyOf(nodes, n);
        this.edges = new Edge[n][n];
        neighbourhood = new int[n][n];
        Integer[] tmp = new Integer[n];

        for (int i = 0; i < n; ++i) {

            Comparator<Integer> comp = new Util(edges, i);

            for (int j = 0; j < n; ++j) {
                this.edges[i][j] = edges[i][j];
                tmp[j] = j;
            }

            Arrays.sort(tmp, comp);

            for (int j = 0; j < n; ++j) {
                neighbourhood[i][j] = tmp[j];
            }

        }

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

    
    public int[] getNeighbourhood(int n, int count) {
        return Arrays.copyOf(neighbourhood[n], count);
    }
}

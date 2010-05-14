package tabuvrp.vrp;

import tabuvrp.core.Graph;
import tabuvrp.core.Node;
import tabuvrp.core.Edge;
import java.util.Arrays;
import java.util.Comparator;


public class VRP implements Graph {


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


    public VRP(Integer[] nodeValues, Integer[][] edgeValues, int n) {

        this.nodes = new Node[n];

        this.edges = new Edge[n][n];
        neighbourhood = new int[n][n];
        Integer[] tmp = new Integer[n];

        for (int i = 0; i < n; ++i) {

            nodes[i] = new Node(nodeValues[i]);

            Comparator<Integer> comp = new Util(edges, i);

            int j;
            for (j = 0; j < n; ++j) {
                this.edges[i][j] = new Edge(edgeValues[i][j]);
                tmp[j] = j;
            }

            Arrays.sort(tmp, comp);

            j = 0;
            int k = 0;
            while(j < n) {
                if ((tmp[j] != i) && (tmp[j] != 0)) {
                    neighbourhood[i][k] = tmp[j];
                    k += 1;
                }
                j += 1;
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

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

    protected Integer[][] neighbourhood;

    protected final int nodeCount;


    public VRP(Integer[] nodeValues, Integer[][] edgeValues, int n) {

        this.nodes = new Node[n];

        this.edges = new Edge[n][n];
        neighbourhood = new Integer[n][n - 1];

        for (int i = 0; i < n; ++i) {

            nodes[i] = new Node(nodeValues[i]);

            Comparator<Integer> comp = new Util(edges, i);

            int j;
            int k = 0;
            for (j = 0; j < n; ++j) {
                if (i == j) {
                    this.edges[i][j] = new Edge(0);
                }
                else {
                    this.edges[i][j] = new Edge(edgeValues[i][j]);
                    neighbourhood[i][k] = j;
                    k += 1;
                }                
            }
            Arrays.sort(neighbourhood[i], comp);
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

    
    public Integer[][] getNeighbourhood() {
        Integer[][] tmp = new Integer[nodeCount][nodeCount - 1];
        for (int i = 0; i < neighbourhood.length; ++i) {
         tmp[i] = Arrays.copyOf(neighbourhood[i], neighbourhood[i].length);
        }
        return tmp;
    }
}

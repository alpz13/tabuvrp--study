package tabuvrp.core;

import tabuvrp.core.Graph;
import java.util.ArrayList;
import java.util.HashMap;


public class Solution {

    protected final Graph graph;
    protected final ArrayList<Path> paths;
    protected final HashMap<Integer, Path> nodesToPaths;

    public Solution(Graph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("'graph' is null");
        }
        this.graph = graph;
        paths = new ArrayList<Path>(graph.getNodeCount());
        nodesToPaths = new HashMap<Integer, Path>();
    }

    public int getPathSizeByNodeIndex(Integer nodeIndex) {
        return getPathByNodeIndex(nodeIndex).getStepCount();
    }

    public boolean inSamePath(Integer nodeIndex1, Integer nodeIndex2) {
        return getPathByNodeIndex(nodeIndex1)
                == getPathByNodeIndex(nodeIndex2);
    }

    public Path getPathByNodeIndex(Integer index) {
        return nodesToPaths.get(index);
    }

    public void makePath(Integer nodeIndex) {
        if (nodesToPaths.containsKey(nodeIndex)) {
            throw new IllegalArgumentException("a 'path' in 'solution' already contains 'nodeIndex' " + nodeIndex);
        }
        // check for nodeIndex
        graph.getNode(nodeIndex);
        Path p = new Path(graph);
        p.insert(0, nodeIndex);
        nodesToPaths.put(nodeIndex, p);
        paths.add(p);
    }

    public void remove(Integer nodeIndex) {
        Path path = getPathByNodeIndex(nodeIndex);
        path.remove(nodeIndex);
        if (path.isEmpty()) {
            paths.remove(path);
        }
        nodesToPaths.remove(nodeIndex);
    }

    public int deltaCostForRemove(Integer nodeIndex) {
        return getPathByNodeIndex(nodeIndex).deltaCostForRemove(nodeIndex);
    }

    public int deltaDemandBalanceForRemove(Integer nodeIndex) {
        return getPathByNodeIndex(nodeIndex).deltaDemandBalanceForRemove(nodeIndex);
    }

    public void insert(int position, Integer nodeIndex) {
        getPathByNodeIndex(nodeIndex).insert(position, nodeIndex);
    }

    public int deltaCostForInsert(int position, Integer nodeIndex) {
        return getPathByNodeIndex(nodeIndex).deltaCostForInsert(position, nodeIndex);
    }

    public int deltaDemandBalanceForInsert(Integer nodeIndex) {
        return getPathByNodeIndex(nodeIndex).deltaDemandBalanceForInsert(nodeIndex);
    }

    public int getCost() {
        int cost = 0;
        for (Path path : paths) {
            cost += path.getCost();
        }
        return cost;
    }

    public int getDemandBalance() {
        int demandBalance = 0;
        for (Path path : paths) {
            demandBalance += path.getDemandBalance();
        }
        return demandBalance;
    }
    
    public boolean isFeasible() {
        for (Path p : paths) {
            if (!p.isFeasible()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        // TODO: use builder.
        String s = "Solution:\n";
        for (Path p : paths) {
            s += "\t" + p + "\n";
        }
        s += "\n  Cost:" + getCost();
        s += "\n  Demand Balance:" + getDemandBalance();
        s += "\n  " + (isFeasible()? "Feasible\n" : "Not feasible\n");

        return s;
    }
}

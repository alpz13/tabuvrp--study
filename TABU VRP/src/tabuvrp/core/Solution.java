package tabuvrp.core;

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

    protected Solution(Graph graph,
                       ArrayList<Path> paths,
                       HashMap<Integer, Path> nodesToPaths) {
        this.graph = graph;
        this.paths = paths;
        this.nodesToPaths = nodesToPaths;
    }

    public Solution deepCopy() {
        ArrayList<Path> newPaths = new ArrayList<Path>(paths.size());
        for (Path oldPath : paths) {
            newPaths.add(oldPath.deepCopy());
        }
        HashMap<Integer, Path> newNodesToPaths = new HashMap<Integer, Path>();
        for (Integer i : nodesToPaths.keySet()) {
            newNodesToPaths.put(i, newPaths.get(paths.indexOf(nodesToPaths.get(i))));
        }
        return new Solution(graph,
                            newPaths,
                            newNodesToPaths);
    }

    public int getPathSizeByNodeIndex(Integer nodeIndex) {
        return getPathByNodeIndex(nodeIndex).getStepCount();
    }

    public boolean inSamePath(Integer nodeIndex1, Integer nodeIndex2) {
        return getPathByNodeIndex(nodeIndex1)
                == getPathByNodeIndex(nodeIndex2);
    }

    public Path getPathByNodeIndex(Integer index) {
        if (nodesToPaths.get(index) == null) {
            System.out.println("as");
        }
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

    public double deltaCostForRemove(Integer nodeIndex) {
        return getPathByNodeIndex(nodeIndex).deltaCostForRemove(nodeIndex);
    }

    public int deltaDemandBalanceForRemove(Integer nodeIndex) {
        return getPathByNodeIndex(nodeIndex).deltaDemandBalanceForRemove(nodeIndex);
    }

    public void insert(Integer targetNode, int position, Integer nodeIndex) {
        Path path = getPathByNodeIndex(targetNode);
        path.insert(position, nodeIndex);
        nodesToPaths.put(nodeIndex, path);
    }

    public double deltaCostForInsert(Integer targetNode, int position, Integer nodeIndex) {
        return getPathByNodeIndex(targetNode).deltaCostForInsert(position, nodeIndex);
    }

    public int deltaDemandBalanceForInsert(Integer targetNode, Integer nodeIndex) {
        return getPathByNodeIndex(targetNode).deltaDemandBalanceForInsert(nodeIndex);
    }

    public double getCost() {
        double cost = 0;
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

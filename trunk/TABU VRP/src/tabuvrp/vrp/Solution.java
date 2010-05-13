package tabuvrp.vrp;

import java.util.ArrayList;
import java.util.HashMap;

public class Solution {

    protected final Problem problem;
    protected final ArrayList<Path> paths;
    protected final HashMap<Integer, Path> nodesToPaths;
    protected int cost;
    protected int infIndex;

    public Solution(Problem problem) {
        this.problem = problem;
        paths = new ArrayList<Path>(problem.getNodeCount());
        nodesToPaths = new HashMap<Integer, Path>();
        cost = 0;
        infIndex = 0;
    }

    public int getPathSizeByNodeIndex(Integer nodeIndex) {
        return nodesToPaths.get(nodeIndex).getStepCount();
    }

    public boolean inSamePath(Integer nodeIndex1, Integer nodeIndex2) {
        return nodesToPaths.get(nodeIndex1) == nodesToPaths.get(nodeIndex2);
    }

    public boolean isFeasible() {
        return infIndex <= 0;
    }

    public void addPath(Integer nodeIndex) {
        Path p = new Path(problem);
        p.insert(0, nodeIndex);
        nodesToPaths.put(nodeIndex, p);
        cost += p.getCost();
        infIndex += p.getDemandBalance();
        paths.add(p);
    }

    public void move(Integer sourceNode,
                     Integer targetNode,
                     int position) {
        Path sourcePath = nodesToPaths.get(sourceNode);
        Path targetPath = nodesToPaths.get(targetNode);

        cost += deltaCostForMove(sourceNode, targetNode, position);
        infIndex += deltaInfIndexForMove(sourceNode, targetNode, position);

        /* remove source node from source path */
        sourcePath.remove(sourcePath.getPositionByNodeIndex(sourceNode));
        if (sourcePath.isEmpty()) {
            paths.remove(sourcePath);
        }
        nodesToPaths.remove(sourceNode);

        /* insert source node to target path */
        targetPath.insert(position, sourceNode);
        nodesToPaths.put(sourceNode, targetPath);
    }

    public int deltaCostForMove(Integer sourceNode, 
                                Integer targetNode,
                                int position) {
        Path sourcePath = nodesToPaths.get(sourceNode);
        Path targetPath = nodesToPaths.get(targetNode);
        return sourcePath.deltaCostForRemove(sourcePath.getPositionByNodeIndex(sourceNode))
               + targetPath.deltaCostForInsert(position, sourceNode);
    }

    public int deltaInfIndexForMove(Integer sourceNode,
                                         Integer targetNode,
                                         int position) {
        Path sourcePath = nodesToPaths.get(sourceNode);
        Path targetPath = nodesToPaths.get(targetNode);
        return sourcePath.deltaDemandBalanceForRemove(sourcePath.getPositionByNodeIndex(sourceNode))
                + targetPath.deltaDemandBalanceForInsert(sourceNode);
    }

    public int getCost() {
        return cost;
    }

    public int getInfIndex() {
        return infIndex;
    }


    @Override
    public String toString() {
        // TODO: use builder.
        String s = "Solution:\n";
        for (Path p : paths) {
            s += "\t" + p + "\n";
        }
        s += isFeasible()? "  Feasible" : "  Not feasible";
        s += "\n  Cost:" + cost;
        s += "\n  Infeasibility index:" + infIndex + "\n";
        return s;
    }
}

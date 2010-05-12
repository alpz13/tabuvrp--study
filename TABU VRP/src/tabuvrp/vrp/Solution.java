package tabuvrp.vrp;

import java.util.ArrayList;
import java.util.HashMap;

public class Solution {

    protected final Problem problem;
    protected final ArrayList<Path> paths;
    protected final HashMap<Integer, Path> nodesToPaths;
    protected int cost;
    protected int demandBalance;
    protected int overDemand;

    public Solution(Problem problem) {
        this.problem = problem;
        paths = new ArrayList<Path>(problem.getNodeCount());
        nodesToPaths = new HashMap<Integer, Path>();
        cost = 0;
        demandBalance = 0;
        overDemand = 0;
    }

    public int getPathSizeByNodeIndex(Integer nodeIndex) {
        return nodesToPaths.get(nodeIndex).getStepCount();
    }

    public boolean inSamePath(Integer nodeIndex1, Integer nodeIndex2) {
        return nodesToPaths.get(nodeIndex1) == nodesToPaths.get(nodeIndex2);
    }

    public boolean isFeasible() {
        return demandBalance <= 0;
    }

    public void addPath(Integer nodeIndex) {
        Path p = new Path(problem);
        p.insert(0, nodeIndex);
        nodesToPaths.put(nodeIndex, p);
        cost += p.getCost();
        demandBalance += p.getDemandBalance();
        overDemand += (p.getDemandBalance() > 0)? p.getDemandBalance() : 0;
        paths.add(p);
    }

    public void move(Integer sourceNode,
                     Integer targetNode,
                     int position) {
        Path sourcePath = nodesToPaths.get(sourceNode);
        Path targetPath = nodesToPaths.get(targetNode);
        cost += deltaCostForMove(sourceNode, targetNode, position);
        demandBalance += deltaDemandBalanceForMove(sourceNode, targetNode, position);
        overDemand += deltaOverDemandForMove(sourceNode, targetNode, position);

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

    public int deltaDemandBalanceForMove(Integer sourceNode, 
                                         Integer targetNode,
                                         int position) {
        Path sourcePath = nodesToPaths.get(sourceNode);
        Path targetPath = nodesToPaths.get(targetNode);
        return sourcePath.deltaDemandBalanceForRemove(sourcePath.getPositionByNodeIndex(sourceNode))
                + targetPath.deltaDemandBalanceForInsert(sourceNode);
    }

    public int deltaOverDemandForMove(Integer sourceNode,
                                      Integer targetNode,
                                      int position) {
        Path sourcePath = nodesToPaths.get(sourceNode);
        Path targetPath = nodesToPaths.get(targetNode);
        int dOD1 = sourcePath.deltaDemandBalanceForRemove(sourcePath.getPositionByNodeIndex(sourceNode));
        int dOD2 = targetPath.deltaDemandBalanceForInsert(sourceNode);
        return   ((dOD1 > 0)? dOD1 : 0)
               + ((dOD2 > 0)? dOD2 : 0);
    }

    public int getCost() {
        return cost;
    }

    public int getDemandBalance() {
        return demandBalance;
    }

    public int getOverDemand() {
        return overDemand;
    }

    @Override
    public String toString() {
        // TODO: use builder.
        String s = "Solution:\n";
        for (Path p : paths) {
            s += "\t" + p + "\n";
        }
        s += isFeasible()? "  Feasible" : "  Not feasible";
        s += "\n  Cost:" + getCost();
        s += "\n  Demand balance:" + getDemandBalance() + "\n";
        return s;
    }
}

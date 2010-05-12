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
        sourcePath.remove(sourcePath.getPositionByNodeIndex(sourceNode));
        if (sourcePath.isEmpty()) {
            paths.remove(sourcePath);
        }
        targetPath.insert(position, targetNode);
        cost += deltaCostForMove(sourceNode, targetNode, position);
        demandBalance += deltaDemandBalanceForMove(sourceNode, targetNode, position);
        overDemand += deltaOverDemandForMove(sourceNode, targetNode, position);
        nodesToPaths.remove(sourceNode);
        nodesToPaths.put(sourceNode, targetPath);
    }

    public int deltaCostForMove(Integer sourceNode, 
                                Integer targetNode,
                                int position) {
        Path sourcePath = nodesToPaths.get(sourceNode);
        Path targetPath = nodesToPaths.get(targetNode);
        return sourcePath.deltaCostForRemove(sourcePath.getPositionByNodeIndex(sourceNode))
               + targetPath.deltaCostForInsert(position, targetNode);
    }

    public int deltaDemandBalanceForMove(Integer sourceNode, 
                                         Integer targetNode,
                                         int position) {
        Path sourcePath = nodesToPaths.get(sourceNode);
        Path targetPath = nodesToPaths.get(targetNode);
        return sourcePath.deltaDemandBalanceForRemove(sourcePath.getPositionByNodeIndex(sourceNode))
                + targetPath.deltaDemandBalanceForInsert(targetNode);
    }

    public int deltaOverDemandForMove(Integer sourceNode,
                                      Integer targetNode,
                                      int position) {
        Path sourcePath = nodesToPaths.get(sourceNode);
        Path targetPath = nodesToPaths.get(targetNode);
        int dOD1 = sourcePath.deltaDemandBalanceForRemove(sourcePath.getPositionByNodeIndex(sourceNode));
        int dOD2 = targetPath.deltaDemandBalanceForInsert(targetNode);
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
}

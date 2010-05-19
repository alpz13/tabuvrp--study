package tabuvrp.stages.tabu;

import java.util.Set;
import java.util.HashSet;
import tabuvrp.core.Graph;
import tabuvrp.core.Solution;


public class TabuMoveGenerator {

    protected final Integer[][] genMat;
//    protected final Graph graph;
    protected final Solution solution;
    protected final TabuIndex tabuIndex;

    public TabuMoveGenerator(Graph graph, Solution solution, TabuIndex tabuIndex) {
//        this.graph = graph;
        genMat = graph.getNeighbourhood();
        this.solution = solution;
        this.tabuIndex = tabuIndex;
    }

    public Set<Integer> extract(Integer row, int count) {
        final HashSet<Integer> extSet = new HashSet<Integer>();
        int i = 0;
        while (extSet.size() < count &&
               i < genMat[row].length) {
            if (accepted(row, genMat[row][i])) {
                extSet.add(genMat[row][i]);
            }
            i += 1;
        }
        return extSet;
    }

    public Set<Integer> getRandomNodeIndexes(int count) {
        final HashSet<Integer> indexes = new HashSet<Integer>();
        while (indexes.size() < count &&
               indexes.size() < genMat.length) {
            Integer index = (int) Math.round(Math.random() * (genMat.length - 2)) + 1;
            indexes.add(index);
        }
        return indexes;
    }

    private final boolean accepted(Integer i1, Integer i2) {
        return i2 != 0 &&
               !tabuIndex.isTabu(i1, i2) &&
               !solution.inSamePath(i1, i2);
    }
}

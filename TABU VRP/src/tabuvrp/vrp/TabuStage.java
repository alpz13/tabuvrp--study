package tabuvrp.vrp;

import java.util.ArrayList;

public class TabuStage implements Stage {

    protected final Problem problem;
    protected final TabuStagePolicy params;
    protected final TabuIndex tabuIndex;
    protected final Solution solution;
    protected boolean stopRequired;
    protected double f2;

    public TabuStage(Problem problem,
            TabuStagePolicy params,
            TabuIndex tabuIndex,
            Solution solution) {
        this.problem = problem;
        this.solution = solution;
        this.params = params;
        this.tabuIndex = tabuIndex;
        f2 = solution.getCost() + params.getAlpha() * solution.getOverDemand();
    }

    public void runStage() {
        stopRequired = false;
        while (!stopRequired) {
            doStep();
            params.step();
            tabuIndex.step();
        }
    }

    public void stopStage() {
        stopRequired = true;
    }

    protected void doStep() {

        ArrayList<Integer> W = getRandomNodeIndexes(params.getQ());

        double minDeltaF2 = Double.MAX_VALUE;
        Integer i_best = -1;
        Integer p_best = -1;
        int pos_best = -1;

        for (Integer i : W) {

            for (Integer p : problem.getNeighbourhood(i, params.getP())) {

                if (tabuIndex.isTabu(i, p)) {
                    continue;
                }
                
                int pathSize = solution.getPathSizeByNodeIndex(p);
                for (int pos = 0; pos < pathSize + 1; ++i) {
                    double deltaF2 = deltaF2ForMove(i, p, pos);
                    if (deltaF2 < minDeltaF2) {
                        i_best = i;
                        p_best = p;
                        pos_best = pos;
                        minDeltaF2 = deltaF2;
                    }
                }
            }    
        }
        
        solution.move(i_best, p_best, pos_best);

        if (minDeltaF2 < 0) {
            f2 = minDeltaF2;
        }

        tabuIndex.setTabu(i_best, p_best);

    }


    protected double deltaF2ForMove(Integer sourceNode,
                                    Integer targetNode,
                                    int position) {
        return    solution.getCost()
                + solution.deltaCostForMove(sourceNode, targetNode, position)
                + params.getAlpha() *
                  (   solution.getOverDemand()
                    + solution.deltaOverDemandForMove(sourceNode, targetNode, position)
                  );
    }

    protected ArrayList<Integer> getRandomNodeIndexes(int count) {
        ArrayList<Integer> indexes = new ArrayList<Integer>(count);
        int i = 0;
        while (i < count) {
            int rnd = (int) Math.round(Math.random() * count);
            if (!indexes.contains(rnd) || rnd == 0) {
                indexes.add(rnd);
                i += 1;
            }
        }
        return indexes;
    }

}

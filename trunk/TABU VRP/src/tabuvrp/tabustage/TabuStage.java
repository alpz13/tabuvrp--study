package tabuvrp.tabustage;

import tabuvrp.core.Graph;
import java.util.Set;
import java.util.HashSet;
import tabuvrp.core.Path;
import tabuvrp.core.Solution;
import tabuvrp.core.stage.Stage;
import tabuvrp.core.move.Move10;


public class TabuStage extends Stage {

    protected final HashSet<TabuStageListener> listeners;
    protected final Graph graph;
    protected final TabuStageParams params;
    protected final TabuIndex<Integer, Path> tabuIndex;
    protected final Solution solution;
    protected final TabuMoveGenerator generator;
    protected int steps;
    protected int feasibleSteps;
    protected int infeasibleSteps;

    public TabuStage(Graph problem,
                     TabuStageParams params,
                     Solution solution) {
        this.listeners = new HashSet<TabuStageListener>();
        this.graph = problem;
        this.solution = solution;
        this.params = params;
        this.tabuIndex = new TabuIndex<Integer, Path>(params.MAX_THETA);
        this.generator = new TabuMoveGenerator(problem, solution, tabuIndex, params);
        setBestSolution(solution);
        steps = 0;
        feasibleSteps = 0;
        infeasibleSteps = 0;
    }

    public void addTabuStageListener(TabuStageListener listener) {
        addStageListener(listener);
        listeners.add(listener);
    }

    public void remTabuStageListener(TabuStageListener listener) {
        remStageListener(listener);
        listeners.remove(listener);
    }

    protected boolean doStep() {

        double minF2 = Double.MAX_VALUE;
        Move10 bestMove = null;
        boolean moveFound = false;

        Set<Move10> moves = generator.getMoves();
        for (Move10 move : moves) {
            double tmpF2 = f2ForMove(move);
            if (tmpF2 < minF2) {
                /* new move candidate */
                bestMove = move;
                minF2 = tmpF2;
                moveFound = true;
            }
        }
        if (moveFound) {
            generator.apply(bestMove);
            tabuIndex.setTabu(bestMove.getSourceNode(),
                              bestMove.getTargetPath(),
                              params.getTheta());
            if (solution.isFeasible()) {
                feasibleSteps += 1;
                notifyAll_NewUsefulStep(true);
                if (minF2 < f2ForSolution(bestSolution)) {
                    /* new best solution */
                    setBestSolution(solution);
                }
            }
            else {
                infeasibleSteps += 1;
                notifyAll_NewUsefulStep(true);
            }
            tabuIndex.step();
            params.step();
        }
        steps += 1;
        return steps < params.MAX_STEPS;
    }

    private void notifyAll_NewUsefulStep(boolean feasible) {
        for (TabuStageListener listener : listeners) {
            listener.newUsefulStep(this, feasible);
        }
    }

    protected double f2ForSolution(Solution solution) {
        return objective(solution.getCost(), solution.getInfIndex());
    }

    protected double f2ForMove(Move10 move) {
        return objective(solution.getCost() + move.getDeltaCost(),
                         solution.getInfIndex() + move.getDeltaInfIndex());
    }

    protected double objective(double cost, int infIndex) {
        return cost + params.getAlpha() * infIndex;
    }

    public int getFeasSteps() {
        return feasibleSteps;
    }

    public int getInfeasSteps() {
        return infeasibleSteps;
    }

}

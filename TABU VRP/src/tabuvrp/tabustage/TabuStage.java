package tabuvrp.tabustage;

import tabuvrp.core.Graph;
import java.util.Set;
import tabuvrp.core.Path;
import tabuvrp.core.Solution;
import tabuvrp.core.stage.Stage;
import tabuvrp.core.move.Move10;


public class TabuStage extends Stage {

    protected final Graph graph;
    protected final TabuStageParams params;
    protected final TabuIndex<Integer, Path> tabuIndex;
    protected final Solution solution;
    protected final TabuMoveGenerator generator;
    protected Solution bestSolution;
    protected int steps;

    public TabuStage(Graph problem,
                     TabuStageParams params,
                     Solution solution) {
        this.graph = problem;
        this.solution = solution;
        this.params = params;
        this.tabuIndex = new TabuIndex<Integer, Path>(params.getMaxTheta());
        this.generator = new TabuMoveGenerator(problem, solution, tabuIndex, params);
        this.bestSolution = solution.deepCopy();
        steps = 0;
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
            if (minF2 < f2ForSolution(bestSolution) &&
                solution.isFeasible()) {
                /* new best solution */
                bestSolution = solution.deepCopy();
//                System.err.println("new best solution -> f2: " + f2ForSolution(solution));
//                System.err.println(solution);
            }
            tabuIndex.setTabu(bestMove.getSourceNode(),
                              bestMove.getTargetPath(),
                              params.getTheta());
            tabuIndex.step();
            params.step();
        }
        steps += 1;
        
        return steps <= params.getMaxSteps();
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

    public Solution getBestSolution() {
        return bestSolution.deepCopy();
    }
}
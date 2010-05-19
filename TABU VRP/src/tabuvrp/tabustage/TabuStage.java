package tabuvrp.tabustage;

import tabuvrp.core.Graph;
import java.util.Set;
import tabuvrp.core.Path;
import tabuvrp.core.Solution;
import tabuvrp.core.stage.Stage;
import tabuvrp.core.move.Move10;


public class TabuStage extends Stage {

    protected final Graph problem;
    protected final TabuStageParams params;
    protected final TabuIndex<Integer, Path> tabuIndex;
    protected final Solution solution;
    protected final TabuMoveGenerator generator;
    protected Solution bestSolution;

    public TabuStage(Graph problem,
            TabuStageParams params,
            TabuIndex<Integer, Path> tabuIndex,
            Solution solution) {
        this.problem = problem;
        this.solution = solution;
        this.params = params;
        this.tabuIndex = tabuIndex;
        this.generator = new TabuMoveGenerator(problem, solution, tabuIndex, params);
        this.bestSolution = solution.deepCopy();
        System.err.println("Start f2: " + f2ForSolution(bestSolution));
        System.err.println(solution);
    }

    protected void doStep() {

        double minF2 = Double.MAX_VALUE;
        Move10 bestMove = null;
        boolean moveFound = false;

        Set<Move10> moves = generator.getMoves();
        for (Move10 move : moves) {
            double tmpF2 = f2ForMove(move);
            if (tmpF2 < minF2) {
                // new candidate
                bestMove = move;
                minF2 = tmpF2;
                moveFound = true;
            }
        }
        if (moveFound) {
            System.err.println("move found");
            generator.apply(bestMove);
            if (minF2 < f2ForSolution(bestSolution)) {
                bestSolution = solution.deepCopy();
                System.err.println("new best solution -> f2: " + f2ForSolution(solution));
            }
            tabuIndex.setTabu(bestMove.getSourceNode(),
                              bestMove.getTargetPath(),
                              params.getTheta());
        }
        System.err.println("end of step -> f2: " + f2ForSolution(solution));
        System.err.println(solution);
    }

    protected double f2ForSolution(Solution solution) {
        return objective(solution.getCost(), solution.getDemandBalance());
    }

    protected double f2ForMove(Move10 move) {
        return objective(solution.getCost() + move.getDeltaCost(),
                         solution.getDemandBalance() + move.getDeltaDemandBalance());
    }

    protected double objective(int cost, int demandBalance) {
        return cost + params.getAlpha() * ((demandBalance > 0)? demandBalance : 0);
    }
}

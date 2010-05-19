package tabuvrp.tabustage;

import tabuvrp.core.Graph;
import java.util.Set;
import tabuvrp.core.Solution;
import tabuvrp.core.stage.Stage;
import tabuvrp.core.move.Move10;


public class TabuStage extends Stage {

    protected final Graph problem;
    protected final TabuStageParams params;
    protected final TabuIndex<Integer, Integer> tabuIndex;
    protected final Solution solution;
    protected final TabuMoveGenerator generator;
    

    public TabuStage(Graph problem,
            TabuStageParams params,
            TabuIndex<Integer, Integer> tabuIndex,
            Solution solution) {
        this.problem = problem;
        this.solution = solution;
        this.params = params;
        this.tabuIndex = tabuIndex;
        this.generator = new TabuMoveGenerator(problem, solution, tabuIndex, params);
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
            generator.apply(bestMove);
            
        }
    }


    protected double f2ForMove(Move10 move) {
        return objective(solution.getCost() + move.getDeltaCost(),
                         solution.getDemandBalance()+ move.getDeltaDemandBalance());
    }

    protected double objective(int cost, int demandBalance) {
        return cost + params.getAlpha() * ((demandBalance > 0)? demandBalance : 0);
    }
}

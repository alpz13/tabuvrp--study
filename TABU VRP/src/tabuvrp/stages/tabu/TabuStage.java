package tabuvrp.stages.tabu;

import tabuvrp.core.Graph;
//import java.util.ArrayList;
import java.util.Set;
import tabuvrp.core.Solution;
import tabuvrp.stages.Stage;


public class TabuStage extends Stage {

    protected final Graph problem;
    protected final TabuStageParams params;
    protected final TabuIndex<Integer, Integer> tabuIndex;
    protected final Solution solution;
    protected final TabuMoveGenerator generator;
    protected double f2;

    public TabuStage(Graph problem,
            TabuStageParams params,
            TabuIndex<Integer, Integer> tabuIndex,
            Solution solution) {
        this.problem = problem;
        this.solution = solution;
        this.params = params;
        this.tabuIndex = tabuIndex;
        this.generator = new TabuMoveGenerator(problem, solution, tabuIndex);
        f2 = solution.getCost() + params.getAlpha() * ((solution.getInfIndex() > 0)? solution.getInfIndex() : 0);
        System.err.println("f2: " + f2);
    }

    protected void doStep() {

        Set<Integer> W = generator.getRandomNodeIndexes(params.getQ());

        double minF2 = Double.MAX_VALUE;
        Integer i_best = -1;
        Integer p_best = -1;
        int pos_best = -1;
        boolean move = false;

        for (Integer i : W) {

            Set<Integer> P = generator.extract(i, params.getP());
            
            for (Integer p : P) {

                System.err.println("i:" + i + " p: " + p);
                
                int pathSize = solution.getPathSizeByNodeIndex(p);
                for (int pos = 0; pos < pathSize + 1; ++pos) {
                    System.err.println("\tpos: " + pos + " / " + pathSize);
                    double tmpF2 = f2ForMove(i, p, pos);
                    System.err.print("\t\tf2: " + tmpF2);
                    if (tmpF2 < minF2) {
                        System.err.println(" *");
                        i_best = i;
                        p_best = p;
                        pos_best = pos;
                        minF2 = tmpF2;
                        move = true;
                    }
                    else {
                        System.err.println();
                    }
                }
            }    
        }

        if (move) {
            System.err.println("\nmove " + i_best + " " + p_best + " " + pos_best);
        
            solution.move(i_best, p_best, pos_best);

            if (minF2 < f2) {
                System.err.print("f2: " + f2 + " -> ");
                f2 = minF2;
                System.err.println(f2 + " *");
            }
            else {
                System.err.println("f2: " + f2);
            }

            tabuIndex.setTabu(i_best, p_best, params.getTheta());
        }
        else {
            System.err.println("no moves for this step");
        }
        System.err.println(solution);
    }


    protected double f2ForMove(Integer sourceNode,
                                    Integer targetNode,
                                    int position) {
        int infIndex = solution.getInfIndex() + 
                solution.deltaInfIndexForMove(sourceNode, targetNode, position);

        return    solution.getCost()
                + solution.deltaCostForMove(sourceNode, targetNode, position)
                + params.getAlpha() * ((infIndex > 0)? infIndex : 0) ;
    }


}

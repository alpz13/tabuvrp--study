package tabuvrp.vrp;

import tabuvrp.core.Stage;
import tabuvrp.core.Graph;
import java.util.ArrayList;

public class TabuStage extends Stage {

    protected final Graph problem;
    protected final TabuStagePolicy params;
    protected final TabuIndex<Integer, Integer> tabuIndex;
    protected final Solution solution;
    protected boolean stopRequired;
    protected double f2;

    public TabuStage(Graph problem,
            TabuStagePolicy params,
            TabuIndex<Integer, Integer> tabuIndex,
            Solution solution) {
        super();
        this.problem = problem;
        this.solution = solution;
        this.params = params;
        this.tabuIndex = tabuIndex;
        f2 = solution.getCost() + params.getAlpha() * solution.getInfIndex();
    }

    public void runStage() {
        stopRequired = false;
        System.err.println(">> INIT");
        System.err.println(solution);
        System.err.println("f2: " + f2 + "*");

        while (!stopRequired) {
            System.err.println("\n>> STEP");

            doStep();
            params.step();
            tabuIndex.step();
            
            notifyAll_StepDone();

            System.err.println(solution);
        }
    }

    public void stopStage() {
        stopRequired = true;
    }

    protected void doStep() {

        ArrayList<Integer> W = getRandomNodeIndexes(params.getQ());


        double minF2 = Double.MAX_VALUE;
        Integer i_best = -1;
        Integer p_best = -1;
        int pos_best = -1;
        boolean move = false;

        for (Integer i : W) {

            int[] P = problem.getNeighbourhood(i, params.getP());
            
            for (Integer p : P) {

                System.err.println("\ni:" + i + " p: " + p);

                if (tabuIndex.isTabu(i, p)) {
                    System.err.println("\ttabu: skip");
                    continue;
                }

                if (solution.inSamePath(i, p)) {
                    System.err.println("\tsame path: skip");
                    continue;
                }
                
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
                tabuIndex.setTabu(i_best, p_best, params.getTheta());
        }
        else {
            System.err.println("no moves for this step");
        }

        

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

    protected ArrayList<Integer> getRandomNodeIndexes(int count) {
        ArrayList<Integer> indexes = new ArrayList<Integer>(count);
        int i = 0;
        while (i < count) {
            int rnd = (int) Math.round(Math.random() * count);
            if (!indexes.contains(rnd) && (rnd != 0)) {
                indexes.add(rnd);
                i += 1;
            }
        }
        return indexes;
    }

}

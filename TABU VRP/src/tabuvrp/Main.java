package tabuvrp;

import tabuvrp.vrp.*;
import tabuvrp.core.Solution;
import tabuvrp.tabustage.TabuStage;
import tabuvrp.tabustage.BasicTabuStageParams;


public class Main {

    
    public static void main(String[] args) {

        VRP vrp = null;

        /* GRAPH INIT */
        try {
            vrp = VRPFactory.newVRPFromFile(args[0]);
        }
        catch (Exception exc) {
            exc.printStackTrace();
            System.exit(1);
        }
        int n = vrp.getNodeCount();
        double mt = vrp.getMTilde();

        /* SOLUTION INIT */
        Solution sol = new Solution(vrp);
        for(int i = 1; i < n; ++i) {
            sol.makePath(i);
        }
        System.err.println(">> INIT\n" + sol);


        /* STAGE 0 */
        System.err.println("\n\n>> STAGE 1\n\n");
        BasicTabuStageParams s0_params = new BasicTabuStageParams(sol,
                    1, 10,                                  // alpha, beta
                    Math.min(n, 5),                         // p
                    (int) Math.min(n, Math.round(5 * mt)),  // q
                    5, 10,                                  // min theta, max theta
                    50 * vrp.getNodeCount());

        TabuStage s0 = new TabuStage(
                vrp,
                s0_params,
                sol);

        s0.runStage();

        System.err.println(s0.getBestSolution());
        System.err.println("elapsed time: " + s0.getElaborationTime() / 1E9 + "s");
        System.err.println("Total steps: " + s0.getSteps());
        System.err.println("Useful steps: " + (long) (s0.getFeasSteps() + s0.getInfeasSteps()));
        System.err.println("Steps spent into feasible solutions: " + s0.getFeasSteps());
        System.err.println("Steps spent into not feasible solutions: " + s0.getInfeasSteps());


        /* STAGE 1 */
        System.err.println("\n\n>> STAGE 2\n\n");
        BasicTabuStageParams s1_params = new BasicTabuStageParams(sol,
                    1, 10,
                    Math.min(n, 10),
                    n,
                    5, 10,
                    100);

        TabuStage s1 = new TabuStage(
                vrp,
                s1_params,
                s0.getBestSolution());

        s1.runStage();

        System.err.println(s1.getBestSolution());
        System.err.println("elapsed time: " + s1.getElaborationTime() / 1E9 + "s");
        System.err.println("Total steps: " + s1.getSteps());
        System.err.println("Useful steps: " + (long)(s1.getFeasSteps() + s1.getInfeasSteps()));
        System.err.println("Steps spent into feasible solutions: " + s1.getFeasSteps());
        System.err.println("Steps spent into not feasible solutions: " + s1.getInfeasSteps());
    }
}

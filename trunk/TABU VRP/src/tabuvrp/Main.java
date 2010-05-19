package tabuvrp;

import tabuvrp.vrp.VRP;
import tabuvrp.core.Solution;
import tabuvrp.core.stage.Stage;
import tabuvrp.tabustage.TabuStage;
import tabuvrp.tabustage.BasicTabuStageParams;


public class Main {

    
    public static void main(String[] args) {

        /* GRAPH INIT */
        VRP vrp = new VRP(
                new Integer[]{-13, 1, 2, 3, 4, 5},
                new Integer[][]{
                    new Integer[]{ 0, 01, 02, 03, 04, 05},
                    new Integer[]{10,  0, 12, 13, 14, 15},
                    new Integer[]{20, 12,  0, 23, 24, 25},
                    new Integer[]{30, 13, 23,  0, 34, 35},
                    new Integer[]{40, 14, 24, 34,  0, 45},
                    new Integer[]{50, 15, 25, 35, 45,  0},
                },
                6);
        

        /* SOLUTION INIT */
        Solution sol = new Solution(vrp);
        for(int i = 1; i < vrp.getNodeCount(); ++i) {
            sol.makePath(i);
        }


        /* STAGE 0 */
        BasicTabuStageParams s0_params = new BasicTabuStageParams(sol,
                    1, 10,  // alpha, beta
                    5,      // p
                    2,      // q
                    5, 10,  // min theta, max theta
                    50 * vrp.getNodeCount());

        Stage s0 = new TabuStage(
                vrp,
                s0_params,
                sol);


        /* STAGE 1 */
        BasicTabuStageParams s1_params = new BasicTabuStageParams(sol,
                    1, 10,
                    6,
                    6,
                    5, 10,
                    100);

        Stage s1 = new TabuStage(
                vrp,
                s1_params,
                sol);


        /* RUN */
        s0.runStage();
        s1.runStage();
        
    }
}

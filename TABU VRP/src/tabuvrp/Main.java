package tabuvrp;

import tabuvrp.vrp.*;
import tabuvrp.core.Solution;
import tabuvrp.core.stage.Stage;
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

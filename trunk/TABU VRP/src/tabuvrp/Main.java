package tabuvrp;

import tabuvrp.core.Stage;
import tabuvrp.vrp.*;

public class Main {

    
    public static void main(String[] args) {
        VRP vrp = new VRP(
                new Integer[]{-50, 1, 2, 3, 4, 5},
                new Integer[][]{
                    new Integer[]{ 0, 01, 02, 03, 04, 05},
                    new Integer[]{01,  0, 12, 13, 14, 15},
                    new Integer[]{02, 12,  0, 23, 24, 25},
                    new Integer[]{03, 13, 23,  0, 34, 35},
                    new Integer[]{04, 14, 24, 34,  0, 45},
                    new Integer[]{05, 15, 25, 35, 45,  0},
                },
                6);
        Solution s0sol = new Solution(vrp);

        for(int i = 1; i < vrp.getNodeCount(); ++i) {
            s0sol.addPath(i);
        }

        TabuStagePolicy s0params = new TabuStagePolicy0(s0sol, 0.5, 3, 2, 4, 1);
        TabuIndex<Integer, Integer> s0index = new TabuIndex<Integer, Integer>(s0params.getTheta());
        Stage s0 = new TabuStage(vrp, s0params, s0index, s0sol);

        s0.runStage();
    }
}

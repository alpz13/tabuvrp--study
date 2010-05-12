package tabuvrp;

import tabuvrp.vrp.*;

public class Main {

    
    public static void main(String[] args) {
        VRP vrp = new VRP(
                new Integer[]{-10, 1, 2, 3, 4},
                new Integer[][]{
                    new Integer[]{ 0, 01, 02, 03, 04},
                    new Integer[]{01,  0, 12, 13, 14},
                    new Integer[]{02, 12,  0, 23, 24},
                    new Integer[]{03, 13, 23,  0, 34},
                    new Integer[]{04, 14,  24, 34, 0},
                },
                5);
        Solution s0sol = new Solution(vrp);

        for(int i = 1; i < vrp.getNodeCount(); ++i) {
            s0sol.addPath(i);
        }

        TabuStagePolicy s0params = new TabuStagePolicy0(s0sol, 0.5, 10, 2, 2, 2);
        TabuIndex s0index = new TabuIndex(vrp.getNodeCount(), s0params.getTheta());
        Stage s0 = new TabuStage(vrp, s0params, s0index, s0sol);

        s0.runStage();
    }

}

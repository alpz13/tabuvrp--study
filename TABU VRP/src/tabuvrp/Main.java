package tabuvrp;

import tabuvrp.vrp.*;

public class Main {

    
    public static void main(String[] args) {
        VRP vrp = new VRP(
                new Integer[]{-10,1,2,3},
                new Integer[][]{
                    new Integer[]{0, 1, 2, 3},
                    new Integer[]{1, 0, 3, 4},
                    new Integer[]{2, 3, 0, 1},
                    new Integer[]{3, 4, 1, 0},
                },
                4);
        Solution s0sol = new Solution(vrp);

        for(int i = 1; i < vrp.getNodeCount(); ++i) {
            s0sol.addPath(i);
        }

        TabuStagePolicy s0params = new TabuStagePolicy0(s0sol, 0.5, 10, 3, 2, 1);
        TabuIndex s0index = new TabuIndex(vrp.getNodeCount(), s0params.getTheta());
        Stage s0 = new TabuStage(vrp, s0params, s0index, s0sol);

        s0.runStage();
    }

}

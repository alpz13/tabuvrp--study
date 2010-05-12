package tabuvrp.vrp;

import java.util.HashMap;


public class TabuIndex {

    protected int[][] left;
    protected final int n;
    protected final int theta;

    public TabuIndex(int n, int theta) {
        left = new int[n][n];
        this.n = n;
        this.theta = theta;
    }

    public void step() {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (left[i][j] > 0) left[i][j] -= 1;
            }
        }
    }

    public void setTabu(int i, int j) {
        left[i][j] += theta;
    }

    public boolean isTabu(int i, int j) {
        return left[i][j] > 0;
    }
}

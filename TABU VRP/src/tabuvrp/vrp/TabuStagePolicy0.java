package tabuvrp.vrp;


public final class TabuStagePolicy0 implements TabuStagePolicy {

    protected final Solution solution;
    protected long stepCount;
    protected long noFeasTransStreak;
    protected boolean feasible;
    protected double alpha;
    protected final int BETA;
    protected final int P;
    protected final int Q;
    protected final int THETA;

    public TabuStagePolicy0(Solution solution,
            double initialAlpha,
            int beta,
            int p,
            int q,
            int theta) {
        this.solution = solution;
        alpha = initialAlpha;
        BETA = beta;
        P = p;
        Q = q;
        THETA = theta;
        stepCount = 0;
        noFeasTransStreak = 0;
        feasible = solution.isFeasible();
    }

    public final void step() {
        stepCount += 1;
        if (solution.isFeasible() == feasible) {
            noFeasTransStreak += 1;
        } else {
            noFeasTransStreak = 0;
            feasible = !feasible;
        }
        if (noFeasTransStreak == BETA) {
            alpha = feasible ? alpha / 2 : alpha * 2;
            noFeasTransStreak = 0;
        }
    }

    public final double getAlpha() {
        return alpha;
    }

    public final int getP() {
        return P;
    }

    public final int getQ() {
        return Q;
    }

    public final int getTheta() {
        return THETA;
    }
}

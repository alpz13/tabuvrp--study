package tabuvrp.vrp;


public class TabuStagePolicy0 implements TabuStagePolicy {


    protected final Solution solution;

    protected long stepCount;

    protected long noFeasTransStreak;

    protected boolean feasible;
    
    protected double alpha;

    protected final int BETA;

    protected final int P;

    protected final int Q;


    public TabuStagePolicy0(Solution solution,
                           double initialAlpha,
                           int beta,
                           int p,
                           int q) {
        this.solution = solution;
        alpha = initialAlpha;
        BETA = beta;
        P = p;
        Q = q;
        stepCount = 0;
        noFeasTransStreak = 0;
        feasible = solution.isFeasible();
    }

    
    public void step() {
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


    public double getAlpha() {
        return alpha;
    }


    public int getP() {
        return P;
    }


    public int getQ() {
        return Q;
    }

    
}

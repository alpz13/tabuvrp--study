package tabuvrp.core.move;


public abstract class Move {

    private final double deltaCost;
    private final int deltaDemandBalance;

    public Move(double deltaCost,
                int deltaDemandBalance) {
        this.deltaCost = deltaCost;
        this.deltaDemandBalance = deltaDemandBalance;
    }
    
    public final double getDeltaCost() {
        return deltaCost;
    }

    public final int getDeltaDemandBalance() {
        return deltaDemandBalance;
    }
}

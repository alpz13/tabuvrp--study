package tabuvrp.core.move;


public abstract class Move {

    private final int deltaCost;
    private final int deltaDemandBalance;

    public Move(int deltaCost,
                int deltaDemandBalance) {
        this.deltaCost = deltaCost;
        this.deltaDemandBalance = deltaDemandBalance;
    }
    
    public final int getDeltaCost() {
        return deltaCost;
    }

    public final int getDeltaDemandBalance() {
        return deltaDemandBalance;
    }
}

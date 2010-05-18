package tabuvrp.tmp;


public final class Tuple2<E1, E2> {

    private final E1 first;
    private final E2 second;

    public Tuple2(E1 first, E2 second) {
        this.first = first;
        this.second = second;
    }

    public final E1 first() {
        return first;
    }

    public final E2 second() {
        return second;
    }
}

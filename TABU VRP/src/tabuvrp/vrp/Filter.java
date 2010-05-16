package tabuvrp.vrp;


public interface Filter<E1, E2> {

    public boolean filter(E1 e1, E2 e2);
}

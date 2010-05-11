package tabuvrp.vrp;


public interface TabuStagePolicy {


    public void step();


    public double getAlpha();


    public int getP();


    public int getQ();

    
}

package tabuvrp.stages.tabu;


public interface TabuStageParams {

    public void step();

    public double getAlpha();

    public int getP();

    public int getQ();

    public int getMinTheta();

    public int getMaxTheta();
    
    public int getTheta();
}

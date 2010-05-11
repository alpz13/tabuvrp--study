package tabuvrp.vrp;


public class TabuStage implements Stage {


    protected final Problem problem;

    protected final TabuStagePolicy params;

    protected final Solution solution;


    public TabuStage(Problem problem,
                     TabuStagePolicy params,
                     Solution solution) {
        this.problem = problem;
        this.solution = solution;
        this.params = params;
    }

    
    public void runStage() {
        
    }


}

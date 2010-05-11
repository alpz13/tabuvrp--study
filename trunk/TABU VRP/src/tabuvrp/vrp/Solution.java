package tabuvrp.vrp;

import java.util.ArrayList;


public class Solution {


    protected final ArrayList<Path> paths;


    public Solution() {
        this.paths = new ArrayList<Path>();
    }


    public int getCost() {
        int cost = 0;
        for (Path p : paths) {
            cost += p.getCost();
        }
        return cost;
    }


    public double getOverDemand() {
        double total = 0;
        for (Path p : paths) {
            if (!p.isFeasible()) {
                total += p.getDemandBalance();
            }
        }
        return total;
    }


    public boolean isFeasible() {
        for (Path p : paths) {
            if (!p.isFeasible()) {
                return false;
            }
        }
        return true;
    }


    public ArrayList<Path> getPaths() {
        return paths;
    }


}

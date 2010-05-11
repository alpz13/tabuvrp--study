package tabuvrp.vrp;

import java.util.ArrayList;


public class Path {


	protected final Problem graph;

	protected final ArrayList<Integer> steps;

	protected int cost;

        protected int demandBalance;
        

	public Path(Problem graph) {
		this.graph = graph;
		steps = new ArrayList<Integer>();
		cost = 0;
                demandBalance = graph.getNode(0).getDemand();
	}


	public boolean isEmpty() {
		return steps.isEmpty();
	}


	public void insert(int index, Integer n) {
		steps.add(index, n);
		cost += deltaForInsert(index, n);
                demandBalance += graph.getNode(n).getDemand();
	}


	public int deltaForInsert(int index, Integer n) {
		if (steps.isEmpty()) {
			return graph.getEdge(0, n).getCost() +
				   graph.getEdge(n, 0).getCost();
		}
		else {
			Integer start = (index == 0)? 0 : steps.get(index - 1);
			Integer end = (index == steps.size())? 0 : steps.get(index);
			return graph.getEdge(start, n).getCost() +
				   graph.getEdge(n, end).getCost() -
				   graph.getEdge(start, end).getCost();
		}
	}


	public void append(Integer n) {
		steps.add(n);
		cost += deltaForInsert(steps.size(), n);
                demandBalance += graph.getNode(n).getDemand();
	}


	public void remove(int index) {
                demandBalance -= graph.getNode(steps.get(index)).getDemand();
		steps.remove(index);
		cost += deltaForRemove(index);
	}


	public int deltaForRemove(int index) {
		if (steps.size() <= 1) {
			return -cost;
		}
		Integer start = (index == 0)? 0 : steps.get(index - 1);
		Integer mid = steps.get(index);
		Integer end = (index == steps.size() - 1)? 0 : steps.get(index);
		return graph.getEdge(start, end).getCost() -
			   graph.getEdge(start, mid).getCost() -
			   graph.getEdge(mid, end).getCost();
	}


        public int getCost() {
            return cost;
        }


        public int getDemandBalance() {
            return demandBalance;
        }

        
        public boolean isFeasible() {
            return demandBalance <= 0;
        }
        
}

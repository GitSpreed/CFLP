package algorithm;

import tools.Instance;
import tools.Result;

public class HillClimbing {
	private Instance instance;
	private Instance.Solution solution;
	private int[] minAssign;
	
	public HillClimbing(String filePath) {
		this.instance = new Instance(filePath);
		this.solution = instance.new Solution();
		this.minAssign = new int[instance.getNumOfCustomer()];
		
		for (int i = 0; i < instance.getNumOfCustomer(); i++) {
			int minIndex = -1;
			int minValue = Integer.MAX_VALUE;
			for (int j = 0; j < instance.getNumOfFacility(); j++) {
				if (instance.getAssignCost(j, i) < minValue) {
					minIndex = j;
					minValue = instance.getAssignCost(j, i);
				}
			}
			this.minAssign[i] = minIndex;
		}
	}
	
	public Result run() {
		long startTime = System.currentTimeMillis();
		boolean flag = true;
		int n = 0;
		
		while(flag) {
			flag = false;
			
			Instance.Solution[] mins = getMin();
			int minIndex = -1;
			int minCost = this.solution.getCost();
			
			for (int i = 0; i < mins.length; i++) {
				if (mins[i].isValid() && mins[i].getCost() < minCost) {
					flag = true;
					minCost = mins[i].getCost();
					minIndex = i;
				}
			}
			if (flag) {
				this.solution = mins[minIndex];
			}
			//System.out.println(n++ + ": " + solution.getCost()  + " -> " + solution.toString());
		}
		long endTime = System.currentTimeMillis();
		return new Result(solution.getCost(), endTime - startTime, solution.toString());
	}
	
	private Instance.Solution[] getMin() {
		Instance.Solution[] ret = new Instance.Solution[instance.getNumOfCustomer()];
		
		for (int i = 0; i < instance.getNumOfCustomer(); i++) {
			ret[i] = this.solution.clone();
			ret[i].setDataByPosition(i, minAssign[i]);
		}
		
		return ret;
	}
	
	public static void main(String[] args) {
		HillClimbing algorithm = new HillClimbing("Instances/p1");
		algorithm.run();
	}
	

}

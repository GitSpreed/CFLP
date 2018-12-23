package tools;

public class Result {

	private int cost;
	private long time;
	private String solution;
	
	public Result(int _cost, long _time, String _solution) {
		this.cost = _cost;
		this.time = _time;
		this.solution = _solution;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
	
	
}

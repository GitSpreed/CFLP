package tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Instance {
	private int numOfFacility;
	private int numOfCustomer;
	private Facility[] facilities;
	private int[] demands;
	private int[][] assignCost;
	
	public Instance(String filePath) {
		Scanner s = null;
		try {
			s = new Scanner(new BufferedReader(new FileReader(filePath)));
			
			this.numOfFacility = s.nextInt();
			this.numOfCustomer = s.nextInt();
			
			this.facilities = new Facility[numOfFacility];
			this.demands = new int[numOfCustomer];
			this.assignCost = new int[numOfFacility][numOfCustomer];
			
			for (int i = 0; i < numOfFacility; i++) {
				int capacity = s.nextInt();
				int openCost = s.nextInt();
				facilities[i] = new Facility(capacity, openCost);
			}
			
			for (int i = 0; i < numOfCustomer; i++) {
				demands[i] = (int) s.nextFloat();
			}
			
			for (int i = 0; i < numOfFacility; i++) {
				for (int j = 0; j < numOfCustomer; j++) {
					assignCost[i][j] = (int) s.nextFloat();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getNumOfFacility() {
		return numOfFacility;
	}


	public int getNumOfCustomer() {
		return numOfCustomer;
	}
	
	public int getAssignCost(int facility, int customer) {
		return assignCost[facility][customer];
	}
	
	public int getOpenCost(int facility) {
		return facilities[facility].getOpenCost();
	}
	
	public int[] getAllCapacity() {
		int[] ret = new int[numOfFacility];
		for (int i = 0; i < numOfFacility; i++) {
			ret[i] = facilities[i].getCapacity();
		}
		return ret;
	}
	
	public class Solution {
		private int[] line;
		private int cost;
		private boolean isDirty;
		
		 public Solution() {
			this.line = new int[Instance.this.numOfCustomer];
			this.isDirty = false;
			
			Random rand = new Random();
			int[] capacity = Instance.this.getAllCapacity();
			for (int i = 0; i < Instance.this.numOfCustomer; i++) {
				int randNum;
				do {
					randNum = Math.abs(rand.nextInt()) % Instance.this.numOfFacility;
				} while (Instance.this.demands[i] > capacity[randNum]);
				capacity[randNum] -= Instance.this.demands[i];
				line[i] = randNum;
			}
			
			this.cost = computeCost();
		 }
		 
		 public boolean isValid() {
			 int[] capacities = new int[Instance.this.numOfFacility];
			 
			 for (int i = 0; i < Instance.this.numOfFacility; i++) capacities[i] = 0;
			 for (int i = 0; i < line.length; i++) capacities[line[i]] += Instance.this.demands[i];
			 
			 for (int i = 0; i < Instance.this.numOfFacility; i++) {
				 if (capacities[i] > Instance.this.facilities[i].getCapacity()) 
					 return false;
			 }
			 
			 return true;
		 }
		 
		 public int[] getData() {
			 this.isDirty = true;
			 return line;
		 }
		 
		 public int getDataByPosition(int position) {
			 return line[position];
		 }
		 
		 public void setDataByPosition(int position, int data) {
			 if (data < 0 || data > Instance.this.numOfFacility) {
				 System.out.println("ERROR: The data is invalid.(In function 'setDataByPosition')");
			 } else {
				 this.isDirty = true;
				 this.line[position] = data;
			 }
		 }
		 
		 public int getCost() {
			 if (isDirty) {
				 cost = computeCost();
			 }
			 return cost;
		 }
		 
		 private int computeCost() {
			 this.isDirty = false;
			 int ret = 0;
			 
			 boolean[] isOpen = new boolean[Instance.this.numOfFacility];
			 for (int i = 0; i < Instance.this.numOfFacility; i++) isOpen[i] = false;
			 
			 for (int i = 0; i < Instance.this.numOfCustomer; i++) {
				 isOpen[line[i]] = true;
				 ret += Instance.this.assignCost[line[i]][i];
			 }
			 for (int i = 0; i < Instance.this.numOfFacility; i++) {
				 if (isOpen[i]) ret += Instance.this.facilities[i].getOpenCost();
			 }
			 
			 return ret;
		 }
		 
		 public Solution clone() {
			 Solution ret = new Solution();
			 for (int i = 0; i < Instance.this.numOfCustomer; i++) {
				 ret.line[i] = this.line[i];
			 }
			 return ret;
		 }
		 
		 public String toString() {
			 String ret = "";
			 
			 for (int i = 0; i < Instance.this.numOfCustomer; i++) {
				 ret += this.line[i] + " ";
			 }
			 
			 return ret;
		 }
	}
	
	private class Facility {
		private int capacity;
		private int openCost;
		
		public Facility(int _capacity, int _openCost) {
			this.capacity = _capacity;
			this.openCost = _openCost;
		}

		public int getCapacity() {
			return capacity;
		}

		public void setCapacity(int capacity) {
			this.capacity = capacity;
		}

		public int getOpenCost() {
			return openCost;
		}

		public void setOpenCost(int openCost) {
			this.openCost = openCost;
		}
		
	}
	
	
	public static void main(String[] args) {
		String filePath = "Instances/p1";
		Scanner s = null;
		try {
			s = new Scanner(new BufferedReader(new FileReader(filePath)));
			while(s.hasNext()) {
				System.out.println(s.nextFloat());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

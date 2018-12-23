package algorithm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import tools.Instance;
import tools.Instance.Solution;
import tools.Result;

public class Genetic {
	private static final int totalNumberOfIndividuals = 100;
	private static final int numberOfReserved = 20;
	private static final int numberOfGeneration = 10000;
	private static final float ProbabilityOfMutation = 0.5f;
	
	private Instance instance;
	private Instance.Solution[] population;

	public Genetic(String filePath) {
		this.instance = new Instance(filePath);
		this.population = new Instance.Solution[Genetic.totalNumberOfIndividuals];
		initPopulation();
	}
	
	private void initPopulation() {
		for (int i = 0; i < Genetic.totalNumberOfIndividuals; i++) {
			do {
				population[i] = instance.new Solution();
			} while(!population[i].isValid());
		}
		sortPopulation();
	}
	
	private Instance.Solution geneticMutation(Instance.Solution father) {
		Instance.Solution ret = father.clone();
		int[] data = ret.getData();
		Random rand = new Random();
		
		int n = (int) (Math.random() * Math.random() * data.length);
		while (n-- > 0) {
			int randPosition = Math.abs(rand.nextInt()) % data.length;
			int randValue = Math.abs(rand.nextInt()) % instance.getNumOfFacility();
			data[randPosition] = randValue;
		}
		return ret;
	}
	
	private Instance.Solution geneticCrossover(Instance.Solution father, Instance.Solution mother) {
		Instance.Solution ret = father.clone();
		int[] data = ret.getData();
		Random rand = new Random();
		
		int n = (int) (Math.random() * Math.random() * data.length);
		while (n-- > 0) {
			int randPosition = Math.abs(rand.nextInt()) % data.length;
			data[randPosition] = mother.getDataByPosition(randPosition);
		}
		return ret;
	}
	
	private void sortPopulation() {
		Arrays.sort(population, new Comparator<Instance.Solution>() {

			@Override
			public int compare(Solution arg0, Solution arg1) {
				return arg0.getCost() - arg1.getCost();
			}
			
		});
	}
	
	public Result run() {
		long startTime = System.currentTimeMillis();
		Random rand = new Random();
		for (int generation = 0; generation < Genetic.numberOfGeneration; generation++) {
			for (int i = Genetic.numberOfReserved; i < Genetic.totalNumberOfIndividuals; i++) {
				do {
					if (Math.random() < Genetic.ProbabilityOfMutation) {
						int randNum = Math.abs(rand.nextInt()) % i;
						population[i] = geneticMutation(population[randNum]);
					} else {
						int randNum1 = Math.abs(rand.nextInt()) % i;
						int randNum2 = Math.abs(rand.nextInt()) % i;
						population[i]  = geneticCrossover(population[randNum1], population[randNum2]);
					}
				} while (!population[i].isValid());
				
			}
			sortPopulation();
			//System.out.println(generation + ": " + population[0].getCost() + " -> " + population[0].toString());
		}
		long endTime = System.currentTimeMillis();
		return new Result(population[0].getCost(), endTime - startTime, population[0].getResultStr());
	}
	
	public static void main(String[] args) {
		Genetic algorithm = new Genetic("Instances/p1");
		algorithm.run();
	}
}

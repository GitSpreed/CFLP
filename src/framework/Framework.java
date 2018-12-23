package framework;

import java.io.FileWriter;
import java.io.IOException;

import algorithm.Genetic;
import algorithm.HillClimbing;
import tools.Result;

public class Framework {

	public static void main(String[] args) {
		FileWriter hillResWriter = null, genResWriter = null, tableWriter = null;
		try {
			hillResWriter = new FileWriter("Result/HillClimbing.dat", true);
			genResWriter = new FileWriter("Result/Genetic.dat", true);
			tableWriter = new FileWriter("Result/table.dat", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 70; i < 72; i++) {
			Genetic genetic = new Genetic("Instances/p" + i);
			Result genRes = genetic.run();
			
			HillClimbing hillClimbing = new HillClimbing("Instances/p" + i);
			Result hillRes = hillClimbing.run();
			
			Result temp;
			for (int j = 0; j < 2; j++) {
				Genetic tempGenetic = new Genetic("Instances/p" + i);
				temp = tempGenetic.run();
				if (temp.getCost() < genRes.getCost()) genRes = temp;
				
				HillClimbing tempHillClimbing = new HillClimbing("Instances/p" + i);
				temp = tempHillClimbing.run();
				if (temp.getCost() < hillRes.getCost()) hillRes = temp;
				
			}
			
			try {
				hillResWriter.write("p" + i + ":\n");
				hillResWriter.write(hillRes.getSolution());
				
				genResWriter.write("p" + i + ":\n");
				genResWriter.write(genRes.getSolution());
				
				tableWriter.write("" + hillRes.getCost() + " " + hillRes.getTime() + " " + genRes.getCost() + " " + genRes.getTime() + "\n");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(i);
		}
		try {
			hillResWriter.close();
			genResWriter.close();
			tableWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

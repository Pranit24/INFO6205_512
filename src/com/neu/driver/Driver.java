package com.neu.driver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.neu.helper.Helper;
import com.neu.io.FileIO;
import com.neu.multithread.Multithread;
import com.neu.pojo.Individual;
import com.neu.solver.GeneticAlgo;

public class Driver {

	public static void main(String[] args) {
		GeneticAlgo ga = new GeneticAlgo();
		Integer gen= 1;
		Integer fitness = 0;
		int BOARD_SIZE = FileIO.sudokuSize();
		System.out.println("Original unsolved sudoku");
		Helper.printBoard(new Individual().getSudoku());
		System.out.println("\n\n\n");
		List<Individual> individuals = ga.first_gen();
		final long startTime = System.currentTimeMillis();
		int mutation_onset = 20;
		int same_fitness=0;
		int tmp_fitness = 0;
		
		boolean threading = false;
		
		while(fitness < BOARD_SIZE*BOARD_SIZE-1) {
			System.out.println("GENERATION ="+gen);
			if(threading) {
				List<Individual> bucket1 = new ArrayList<>(individuals.subList(0, 200));
				List<Individual> bucket2 = new ArrayList<>(individuals.subList(200, 400));
				List<Individual> bucket3 = new ArrayList<>(individuals.subList(400, 600));
				ExecutorService pool = Executors.newFixedThreadPool(3);
				Thread t1 = new Thread( () ->  {
					ga.crossover(bucket1, 200);
				});
				Thread t2 = new Thread( () ->  {
					ga.crossover(bucket2, 200);
				});
				Thread t3 = new Thread( () ->  {
					ga.crossover(bucket3, 200);
				});
				pool.execute(t1);
				pool.execute(t2);
				pool.execute(t3);
				pool.shutdown();
				
				List<Individual> newList = new ArrayList<>();
				newList.addAll(bucket1);
				newList.addAll(bucket2);
				newList.addAll(bucket3);
				individuals = newList;
			}
			else {
			ga.crossover(individuals, ga.getPopulationSize()/2);
			}
			individuals = ga.selection(individuals, ga.getPopulationSize()/2);
			fitness = individuals.get(0).getFitness();
			gen++;
			if(tmp_fitness == fitness)
			{
				same_fitness++;
				if(same_fitness==mutation_onset)
				{
					ga.mutationSelect(individuals);
					same_fitness=0;
					System.out.println("Mutation Performed");
				}
			}
			tmp_fitness = fitness;
			System.out.println("FITNESS = "+fitness);
		}
		System.out.println("\n\n\n");
		System.out.println("GENERATION = "+gen);
		final long endTime = System.currentTimeMillis();
		Helper.printBoard(individuals.get(0).getSudoku());
		System.out.println("Total execution time: " + (double)(endTime - startTime)/1000 + " seconds!");
		System.out.println("\n\n\n");
	}

}

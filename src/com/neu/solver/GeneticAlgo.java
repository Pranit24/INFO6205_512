package com.neu.solver;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.neu.helper.Helper;
import com.neu.io.FileIO;
import com.neu.pojo.Individual;

public class GeneticAlgo {

	private static final int BOARD_SIZE = FileIO.sudokuSize();
	
	private static int POPULATION_SIZE = 1200;
	private Random random = new Random();
	private static final int mutationRate = (int) (0.1*POPULATION_SIZE);
	
	public static final int[][] sudoku_board = FileIO.getSudoku();
	private List<Integer> empty = Helper.getZeros(sudoku_board);
	
		public void fitness(Individual ind) {
		 
		int init_fitness = ind.getFitness();
		int count = 1;
		 for (int row = 0; row < BOARD_SIZE; row++) {
	            for (int column = 0; column < BOARD_SIZE; column++) {
	                if(empty.contains(count) && !Helper.isValid(ind.getSudoku(), row, column)) {
	                	init_fitness--;
	                }
	                count++;
	            }
	        }
		 ind.setFitness(init_fitness);
	 }
	 
	public List<Individual> first_gen() {
		List<Individual> individuals = new ArrayList<Individual>();
		for(int i=0;i<POPULATION_SIZE;i++) {
			Individual indi = new Individual();
			Helper.randomFill(indi,random);
			fitness(indi);
			individuals.add(indi);
//			printBoard(indi.getSudoku());
//			System.out.println("-----"+indi.getFitness());
		}
		return selection(individuals, POPULATION_SIZE/2);
	}
	
	public static int getPopulationSize() {
		return POPULATION_SIZE;
	}

	public List<Individual> selection(List<Individual> individuals, int afterMating)
	{
		Collections.sort(individuals, new Comparator<Individual>() {
			@Override
			public int compare(Individual o1, Individual o2) {
				return o2.getFitness() - o1.getFitness();
			}
		});
		
		List<Individual> new_individuals = new ArrayList<Individual>();
		
		for(int i=0; i<afterMating; i++)
		{
			new_individuals.add(individuals.get(i));
		}
		
		return new_individuals;
	}
	
	public Individual mating(Individual ind1, Individual ind2, List<Integer> indexOf1, List<Integer> indexOf2, Individual child) {
		int rows = ind1.getSudoku().length;
		int cols = ind1.getSudoku().length;
		int count = 1;
		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {
				if(indexOf1.contains(count)) {
					child.getSudoku()[i][j] = ind1.getSudoku()[i][j];
				}else if(indexOf2.contains(count)) {
					child.getSudoku()[i][j] = ind2.getSudoku()[i][j];
				}
				count++;
			}
		}
		return child;
	}
	
	
	public void crossover(List<Individual> individuals, int pop) {
		List<Integer> alreadyChoosen = new ArrayList<Integer>();
		for(int i=0;i<pop/2;i++) {
			Collections.shuffle(empty);
			List<Integer> list1 = empty.subList(0, empty.size()/2);
			List<Integer> list2 = empty.subList(empty.size()/2, empty.size());
			int rand1 = random.nextInt(pop);
			int rand2 = random.nextInt(pop);
			while(alreadyChoosen.contains(rand1) || alreadyChoosen.contains(rand2) || rand1 == rand2) {
				rand1 = random.nextInt(pop);
				rand2 = random.nextInt(pop);
			}
			alreadyChoosen.add(rand1);
			alreadyChoosen.add(rand2);
			Individual child1 = mating(individuals.get(rand1), individuals.get(rand2), list1, list2, new Individual());
			Individual child2 = mating(individuals.get(rand1), individuals.get(rand2), list2, list1, new Individual());
			fitness(child1);
			fitness(child2);
			individuals.add(child1);
			individuals.add(child2);
		}
	}
	
	public void mutationSelect(List<Individual> individuals)
	{
		for(int i=0; i<individuals.size(); i++)
		{
			if(random.nextInt(POPULATION_SIZE) <= mutationRate)
			{
				mutation(individuals.get(i));
			}
		}
	}
	
	public int mutation(Individual ind)
	{
		int rows = ind.getSudoku().length;
		int cols = ind.getSudoku().length;
		int index = empty.get(random.nextInt(empty.size()));
		int count=1;
		
		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {	
				
				if(index==count)
				{
					ind.getSudoku()[i][j] = random.nextInt(9)+1;
					return index;
				}
				count++;
			}
		}
		return 1;
	}
}

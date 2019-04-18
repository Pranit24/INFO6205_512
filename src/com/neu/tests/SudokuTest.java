package com.neu.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.neu.pojo.Individual;
import com.neu.solver.GeneticAlgo;

class SudokuTest {

	@Test
	void selectionTest() {
		GeneticAlgo ga = new GeneticAlgo();
		
		Individual obj1 = new Individual(65);
		Individual obj2 = new Individual(20);
		Individual obj3 = new Individual(64);
		Individual obj4 = new Individual(42);
		Individual obj5 = new Individual(10);
		Individual obj6 = new Individual(24);
		
		List<Individual> oldIndividuals = new ArrayList<>();
		oldIndividuals.add(obj1);
		oldIndividuals.add(obj2);
		oldIndividuals.add(obj3);
		oldIndividuals.add(obj4);
		oldIndividuals.add(obj5);
		oldIndividuals.add(obj6);
		
		List<Individual> newIndividuals = ga.selection(oldIndividuals, 3);
		
		assertEquals(65, newIndividuals.get(0).getFitness());
		assertEquals(42, newIndividuals.get(2).getFitness());
	}
	
	@Test
	void mutationTest()
	{
		GeneticAlgo ga = new GeneticAlgo();
		int[][] sudoku = {
				{2,1,9,5,4,0,0,7,8},
	            {5,4,3,8,0,6,9,0,0},
	            {0,7,0,2,1,9,3,0,5},
	            {4,0,2,0,6,5,8,9,1},
	            {7,6,5,1,0,8,2,3,4},
	            {0,9,0,4,3,2,5,6,7},
	            {3,2,1,0,5,4,7,8,9},
	            {6,0,4,9,8,0,1,0,3},
	            {9,0,7,3,0,1,4,5,6}
	    };
		
		Individual ind = new Individual(sudoku);
		
		int index = ga.mutation(ind);
		int count = 1;
		int rows = ind.getSudoku().length;
		int cols = ind.getSudoku().length;
		
		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {	
				
				if(index==count)
				{
					assertNotEquals(0, ind.getSudoku()[i][j]);
				}
				count++;
			}
		}
		
	}
	
	@Test
	void matingTest()
	{
		GeneticAlgo ga = new GeneticAlgo();
		
		int[][] sudoku1 = {
				{2,1,9,5},
	            {5,4,3,8},
	            {6,7,4,2},
	            {4,1,2,3},
	    };
		
		int[][] sudoku2 = {
				{3,1,9,2},
	            {5,4,3,8},
	            {6,7,4,2},
	            {8,1,2,7},
		};
		
		int[][] sudoku3 = new int[4][4];
		
		Integer[] arr1 = new Integer[]{1,2,3,4,5,6,7,8};
		List<Integer> list1 = Arrays.asList(arr1);
		
		Integer[] arr2 = new Integer[]{9,10,11,12,13,14,15,16};
		List<Integer> list2 = Arrays.asList(arr2);
		
		Individual child = ga.mating(new Individual(sudoku1), new Individual(sudoku2), list1, list2, new Individual(sudoku3));
		
		int[][] child_sudoku = {
				{2,1,9,5},
	            {5,4,3,8},
	            {6,7,4,2},
	            {8,1,2,7},
		};
		
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {	
				assertEquals(child_sudoku[i][j], child.getSudoku()[i][j]);
			}
		}
		
	}

}

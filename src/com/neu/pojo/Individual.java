package com.neu.pojo;

import com.neu.io.FileIO;

public class Individual {
	private int fitness; 	
	private int[][] sudoku;
	
	public Individual() {
		this.fitness = FileIO.getMaxFitness();
		sudoku = FileIO.getSudoku();
	}
	public Individual(int[][] sudoku) {
		super();
		this.sudoku = sudoku;
		this.fitness = sudoku.length*sudoku.length;
	}

	public Individual(int fitness) {
		super();
		this.fitness = fitness;
	}
	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public int[][] getSudoku() {
		return sudoku;
	}

	public void setSudoku(int[][] sudoku) {
		this.sudoku = sudoku;
	}
}

package com.neu.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.neu.io.FileIO;
import com.neu.pojo.Individual;

public class Helper {

	private static final int BOARD_SIZE = FileIO.sudokuSize();
	private static final int NO_VALUE = 0;
	private static final int SUBSECTION_SIZE = FileIO.getSubsectionSize();
	
	public static List<Integer> getZeros(int[][] sudoku_board) {
		List<Integer> emptyIndex = new ArrayList<Integer>();
		int count = 1;
		for(int i=0;i<BOARD_SIZE;i++) {
			for(int j=0;j<BOARD_SIZE;j++) {
				if(sudoku_board[i][j] == NO_VALUE) {
					emptyIndex.add(count);
				}
				count++;
			}
		}
		return emptyIndex;
	}
	
	public static boolean isValid(int[][] board, int row, int column) {
        return rowConstraint(board, row) &&
          columnConstraint(board, column) &&
          subsectionConstraint(board, row, column);
    }

    private static boolean subsectionConstraint(int[][] board, int row, int column) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        int subsectionRowStart = (row / SUBSECTION_SIZE) * SUBSECTION_SIZE;
        int subsectionRowEnd = subsectionRowStart + SUBSECTION_SIZE;

        int subsectionColumnStart = (column / SUBSECTION_SIZE) * SUBSECTION_SIZE;
        int subsectionColumnEnd = subsectionColumnStart + SUBSECTION_SIZE;

        for (int r = subsectionRowStart; r < subsectionRowEnd; r++) {
            for (int c = subsectionColumnStart; c < subsectionColumnEnd; c++) {
                if (!checkConstraint(board, r, constraint, c)) return false;
            }
        }
        return true;
    }

    private static boolean columnConstraint(int[][] board, int column) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        return IntStream.range(0, BOARD_SIZE)
          .allMatch(row -> checkConstraint(board, row, constraint, column));
    }

    private static boolean rowConstraint(int[][] board, int row) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        return IntStream.range(0, BOARD_SIZE)
          .allMatch(column -> checkConstraint(board, row, constraint, column));
    }

    private static boolean checkConstraint(int[][] board, int row, boolean[] constraint, int column) {
        if (board[row][column] != NO_VALUE) {
            if (!constraint[board[row][column] - 1]) {
                constraint[board[row][column] - 1] = true;
            } else {
                return false;
            }
        }
        return true;
    }

	      	
	 public static void randomFill(Individual ind, Random random) {
			for(int i=0;i<BOARD_SIZE;i++) {
				for(int j=0;j<BOARD_SIZE;j++) {
					if(ind.getSudoku()[i][j] == NO_VALUE) {
						int r = random.nextInt(BOARD_SIZE)+1;
						ind.getSudoku()[i][j] = r;
					}
				}
			}
	}
	 
	 public static void printBoard(int[][] sudoku) {
	        for (int row = 0; row < BOARD_SIZE; row++) {
	            for (int column = 0; column < BOARD_SIZE; column++) {
	                System.out.print(sudoku[row][column] + " ");
	            }
	            System.out.println();
	        }
	    }
}

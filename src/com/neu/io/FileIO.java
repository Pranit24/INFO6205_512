package com.neu.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileIO {

	private static final String input_fileName = "SudokuInput.csv";
	private static final String output_fileName = "SudokuOutput.csv";
	public static final String UTF8_BOM = "\uFEFF";
	
	public static int sudokuSize()
	{
		int size = 0;
		try (FileReader fr = new FileReader(input_fileName);
	             BufferedReader br = new BufferedReader(fr)) {
	            String inputLine;
	            while ((inputLine = br.readLine()) != null) {
	                size++;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		return size;
	}
	
	public static int[][] getSudoku()
	{
		int size = FileIO.sudokuSize();
		
		int[][] sudoku = new int[size][size];
		int row=0;
		int col=0;
		
		try (
	            BufferedReader br = new BufferedReader(new InputStreamReader(
	            	      new FileInputStream(input_fileName), "UTF-8"))) {
	            String inputLine;
	            while ((inputLine = br.readLine()) != null) {
	            	col=0;
	            	String new_inputLine = FileIO.removeUTF8BOM(inputLine);
	                String[] rowData = new_inputLine.split(",");
	                for(String s: rowData)
	                {
	                	sudoku[row][col++] = Integer.parseInt(s);
	                }
	                row++;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		return sudoku;
	}
	
	public static int getMaxFitness()
	{
		int size = FileIO.sudokuSize();
		return size*size;
	}
	
	private static String removeUTF8BOM(String s) {
	    if (s.startsWith(UTF8_BOM)) {
	        s = s.substring(1);
	    }
	    return s;
	}
	
	public static int getSubsectionSize()
	{
		int size = FileIO.sudokuSize();
		int subsize = (int)Math.pow(new Double(size), 0.5);
		return subsize;
	}
}

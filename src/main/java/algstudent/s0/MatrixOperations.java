package algstudent.s0;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class MatrixOperations {

	private int[][] matrix;

	public int[][] getMatrix() {
		return Arrays.copyOf(matrix, matrix.length);
	}

	/**
	 * Creates a new matrix of size n x n and fills it with random values. These
	 * random values must be parameterizable between a maximum (max) and a minimum
	 * (min) value
	 * 
	 * @param n
	 * @param min
	 * @param max
	 */
	public MatrixOperations(int n, int min, int max) {
		this.matrix = new int[n][n];

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				matrix[i][j] = new Random().nextInt(max + 1 - min) + min;
			}
		}
	}

	/**
	 * Creates a matrix using data of the file provided as parameter. This file must
	 * have 1 integer number as the first line. Following lines contain n values to
	 * represent every element of the matrix rows. Each of the values will be
	 * separated by a tabulator
	 * 
	 * @param fileName
	 */
	public MatrixOperations(String fileName) {

		String line;
		String[] info = null;
		int n = 0;
		try {
			BufferedReader file = new BufferedReader(new FileReader(fileName));
			try {
				int i = 0;
				int k = 0;
				while (file.ready()) {
					if (i == 0) {
						n = Integer.valueOf(file.readLine()); // Reading only the first line
						this.matrix = new int[n][n];
					} else {
						line = file.readLine();
						info = line.split("\t");
						for (int j = 0; j < info.length; j++) { // Iterate through matrix and info using j and k
							matrix[k][j] = Integer.valueOf(info[j]);
						}
						k++;
					}
					i++;
				}
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			System.err.println("File " + fileName + " not found");
		}
	}

	/**
	 * Returns the matrix size (n)
	 * 
	 * @return
	 */
	public int getSize() {
		return this.matrix.length;
	}

	/**
	 * Prints in the console all the matrix elements
	 */
	public void write() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				System.out.print(matrix[i][j] + "\t");
			}
			System.out.println();
		}
	}

	/**
	 * Computes the summation of all the elements of the matrix diagonal. This
	 * implementation must iterate over all the matrix elements, but only sums
	 * appropriate elements. So, the complexity is quadratic.
	 */
	public int sumDiagonal1() {
		int sum = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (i == j) {
					sum += matrix[i][j];
				}
			}
		}
		return sum;
	}

	/**
	 * . Computes the summation of all the elements of the matrix diagonal. This
	 * second version should only consider the elements of the main diagonal. So,
	 * the complexity is linear.
	 * 
	 * @return
	 */
	public int sumDiagonal2() {
		int sum = 0;
		for (int i = 0; i < matrix.length; i++) {
			sum += matrix[i][i];

		}
		return sum;
	}

	/**
	 * Given a matrix with integer numbers between 1 and 4, this method iterates
	 * through the matrix starting at position (i, j) according to the following
	 * number meanings: 1 – move up; 2 – move right; 3 – move down; 4 – move left.
	 * Traversed elements would be set to -1 value. The process will finish if it
	 * goes beyond the limits of the matrix or an already traversed position is
	 * reached.
	 * 
	 * @param i
	 * @param j
	 */
	public void travelPath(int i, int j) {
		int order = matrix[i][j]; // Store the actual value of the position [i][j]
		matrix[i][j] = -1; // Update the position on the matrix to -1 (traversed)
		try {
			switch (order) {
			case 1:
				travelPath(i - 1, j);
				break;
			case 2:
				travelPath(i, j + 1);
				break;
			case 3:
				travelPath(i + 1, j);
				break;
			case 4:
				travelPath(i, j - 1);
				break;
			case -1:
				System.out.println("\nProcess finished \n");
				return;
			}
		} catch (Exception e) {
			System.out.println("\nProcess finished \n");
			return;
		}
	}
}

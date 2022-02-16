package algstudent.s1;

import algstudent.s0.MatrixOperations;

public class MatrixOperationsTimes {

	public static void main(String[] args) {
		
		System.out.printf("Time since Jan 1 1950: %d\n", System.currentTimeMillis());
		int repetitions = Integer.parseInt(args[0]);
		long t1, t2;
		int sum = 0;

		for (int n = 10; n <= Integer.MAX_VALUE; n *= 3) {

			MatrixOperations matrix = new MatrixOperations(n, 0, 10);

			t1 = System.currentTimeMillis();

			for (int rep = 1; rep <= repetitions; rep++) {

				sum = matrix.sumDiagonal2(); // In order to measure sumDiagonal1 or
												// sumDiagonal2 change it here
			}

			t2 = System.currentTimeMillis();

			System.out.printf("SIZE = %d \nTIME = %d milli \nSUM = %d \nRPETITIONS = %d\n\n", n, t2 - t1, sum,
					repetitions);
		}
	}
}
package algstudent.s5;

import java.util.Random;

public class LevenshteinDistanceTimes {

	public static void main(String[] args) {

		for (int i = 100; i < Integer.MAX_VALUE; i *= 2) {
			LevenshteinDistance ld = new LevenshteinDistance(generateStringOfSize(i), generateStringOfSize(i));
			double t1 = System.currentTimeMillis();
			System.out.println("Solution: " + ld.calculate());
			double t2 = System.currentTimeMillis();
			System.out.printf("Time for size %d : %f \n", i, t2 - t1);
		}
	}

	private static String generateStringOfSize(int n) {
		Random r = new Random();
		char[] word = new char[n];
		for (int i = 0; i < n; i++) {
			word[i] = (char) (r.nextInt(26) + 'A');
		}
		return String.valueOf(word);
	}

}

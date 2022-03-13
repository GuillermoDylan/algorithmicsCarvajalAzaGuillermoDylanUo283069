package algstudent.s3;

import java.util.Random;

public class TrominoTimes {

	public static void main(String[] args) {
		
		Tromino t = null;
		for (int k = 16; k < Integer.MAX_VALUE; k *= 2) {
			
			int i = new Random().nextInt(k);
			int j = new Random().nextInt(k);
			
			double t1 = System.currentTimeMillis();
			t = new Tromino(k, i, j);
			double t2 = System.currentTimeMillis();

			System.out.println("Tromino of size " + k + " Time: " + (t2 - t1));
		}
		// t.printBoard(n);
	}

}

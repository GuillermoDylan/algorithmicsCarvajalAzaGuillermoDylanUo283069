package algstudent.s3;

public class Division4 {

	// Complexity must be O(n^2) (and number of subproblems
	// must be 4) therefore:
	// a < b^k, k = 2, a = 4, b >= 2
	public static long rec4(int n) {
		long cont = 0;
		if (n <= 0)
			cont++;
		else {
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					cont++;					// O (n^2)
			rec4(n / 2);
			rec4(n / 2);
			rec4(n / 2);
			rec4(n / 2);
		}
		return cont;
	}

	public static void main(String arg[]) {
		long t1, t2, cont = 0;
		for (int n = 1; n <= 10000000; n *= 2) {
			t1 = System.currentTimeMillis();

			cont = rec4(n);

			t2 = System.currentTimeMillis();

			System.out.println("n=" + n + "**TIME=" + (t2 - t1) + "**cont=" + cont);
		} // for
	} // main
} // class

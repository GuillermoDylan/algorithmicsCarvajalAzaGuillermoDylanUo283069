package algstudent.s1;

public class Vector4 {

	static int[] v;

	public static void main(String[] args) {
		int repetitions = Integer.parseInt(args[0]);
		long t1, t2;
		int sum = 0;
		int max = 0;
		int[] m = null;

		for (int n = 10; n <= Integer.MAX_VALUE; n *= 3) {
			v = new int[n];

			Vector1.fillIn(v);

			sum = Vector1.sum(v);

			t1 = System.currentTimeMillis();
			for (int rep = 1; rep <= repetitions; rep++) {
				m = new int[n];
				Vector1.maximum(v, m);
			}
			t2 = System.currentTimeMillis();

			max = m[0];

			System.out.printf("SIZE = %d \nTIME = %d milli \nSUM = %d \nRPETITIONS = %d \nMAXIMUM = %d\n\n", n, t2 - t1,
					sum, repetitions, max);
		}
	}

}

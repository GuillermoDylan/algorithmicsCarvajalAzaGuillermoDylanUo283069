package algstudent.s1;

public class Vector2 {

	static int[]v;
	
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		v = new int[n];
		Vector1.fillIn(v);
		
		long t1, t2;
		t1 = System.currentTimeMillis();
		var sum = Vector1.sum(v);
		t2 = System.currentTimeMillis();
		
		System.out.printf("SIZE = %d \nTIME = %d milli \nSUM = %d\n", n, t2-t1, sum);
	}

}

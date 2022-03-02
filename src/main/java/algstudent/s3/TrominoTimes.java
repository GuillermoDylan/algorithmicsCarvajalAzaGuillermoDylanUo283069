package algstudent.s3;

public class TrominoTimes {

	public static void main(String[] args) {
		int n = Integer.valueOf(args[0]);
		int i = Integer.valueOf(args[1]);
		int j = Integer.valueOf(args[2]);

		Tromino t = new Tromino(n, i, j);
		t.printBoard(n);
	}

}

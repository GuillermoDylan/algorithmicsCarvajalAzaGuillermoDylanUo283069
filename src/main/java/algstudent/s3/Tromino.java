package algstudent.s3;

public class Tromino {

	private static int[][] board;

	public void printBoard(int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(board[i][j] + "\t");
			}
			System.out.println();
		}
	}

	public Tromino(int n, int i, int j) {

		board = new int[n][n];

		board[i][j] = 0;

		if (n == 2) {
			simpleCase(i, j);
		} else {
			trominoPlacing(i, j, n);
		}

	}

	private void trominoPlacing(int i, int j, int n) {

		// Place a peace depending on the position of the occupied space
		if (i < n / 2 && j < n / 2) {
			board[n / 2][n / 2] = 1;
			board[(n / 2) - 1][n / 2] = 1;
			board[n / 2][(n / 2) - 1] = 1;
			trominoPlacing(n / 2, n / 2, n / 2);
			trominoPlacing((n / 2) - 1, n / 2, n / 2);
			trominoPlacing(n / 2, (n / 2) - 1, n / 2);
		} else if (i < n / 2 && j > n / 2) {
			board[(n / 2) - 1][(n / 2) - 1] = 1;
			board[(n / 2)][(n / 2) - 1] = 1;
			board[(n / 2)][(n / 2)] = 1;
			trominoPlacing((n / 2) - 1, (n / 2) - 1, n / 2);
			trominoPlacing(n / 2, (n / 2) - 1, n / 2);
			trominoPlacing(n / 2, n / 2, n / 2);
		} else if (i > n / 2 && j < n / 2) {
			board[n / 2][n / 2] = 1;
			board[(n / 2) - 1][n / 2] = 1;
			board[(n / 2) - 1][(n / 2) - 1] = 1;
			trominoPlacing(n / 2, n / 2, n / 2);
			trominoPlacing((n / 2) - 1, n / 2, n / 2);
			trominoPlacing((n / 2) - 1, (n / 2) - 1, n / 2);
		} else {
			board[(n / 2) - 1][(n / 2) - 1] = 1;
			board[(n / 2) - 1][n / 2] = 1;
			board[(n / 2)][(n / 2) - 1] = 1;
			trominoPlacing((n / 2) - 1, (n / 2) - 1, n / 2);
			trominoPlacing((n / 2) - 1, n / 2, n / 2);
			trominoPlacing(n / 2, (n / 2) - 1, n / 2);
		}

		trominoPlacing(i, j, n / 2);

	}

	public void simpleCase(int i, int j) {
		// Placing the piece of the simple case
		int n = 2;
		if (i < n / 2 && j < n / 2) {
			board[n / 2][n / 2] = 1;
			board[(n / 2) - 1][n / 2] = 1;
			board[n / 2][(n / 2) - 1] = 1;
		} else if (i < n / 2 && j > n / 2) {
			board[(n / 2) - 1][(n / 2) - 1] = 1;
			board[(n / 2)][(n / 2) - 1] = 1;
			board[(n / 2)][(n / 2)] = 1;
		} else if (i > n / 2 && j < n / 2) {
			board[n / 2][n / 2] = 1;
			board[(n / 2) - 1][n / 2] = 1;
			board[(n / 2) - 1][(n / 2) - 1] = 1;
		} else {
			board[(n / 2) - 1][(n / 2) - 1] = 1;
			board[(n / 2) - 1][n / 2] = 1;
			board[(n / 2)][(n / 2) - 1] = 1;
		}
	}
}

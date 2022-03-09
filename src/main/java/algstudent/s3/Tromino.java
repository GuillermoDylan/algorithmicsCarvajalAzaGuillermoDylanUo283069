package algstudent.s3;

public class Tromino {

	private static int[][] board;

	public void printBoard(int n) {
		System.out.println();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(board[i][j] + "\t");
			}
			System.out.println();
		}
	}

	public Tromino(int n, int i, int j) {

		board = new int[n][n];

		board[i][j] = 1;

		int color = 2;

		if (n == 2) {
			simpleCase(i, j, 0, 0, color);
		} else {
			trominoPlacing(0, 0, n, i, j, color);
		}

	}

	private void trominoPlacing(int startingX, int startingY, int n, int emptyX, int emptyY, int color) {

		// Stop condition: return to simple case
		if (n <= 2) {
			simpleCase(emptyX, emptyY, startingX, startingY, color);
			return;
		}

		// Place a piece depending on the position of the occupied space
		if (emptyX < n / 2 && emptyY < n / 2) { // 1º Quadrant

			board[startingX + n / 2][startingY + n / 2] = color;
			board[startingX + (n / 2) - 1][startingY + n / 2] = color;
			board[startingX + n / 2][startingY + (n / 2) - 1] = color;
			++color;
			trominoPlacing(startingX, startingY, n / 2, emptyX, emptyY, color); // Go to 1st q
			++color;
			trominoPlacing(startingX, startingY + (n / 2), n / 2, startingX + (n / 2) - 1, startingY + (n / 2) - 2,
					color); // Go to 2nd q
			++color;
			trominoPlacing(startingX + (n / 2), startingY, n / 2, startingX + (n / 2) - 2, startingY + (n / 2) - 1,
					color); // Go to 3rd
			++color;
			trominoPlacing(startingX + (n / 2), startingY + (n / 2), n / 2, startingX + (n / 2) - 2,
					startingY + (n / 2) - 2, color); // Go to 4rth q
			++color;

		} else if (emptyX < n / 2 && emptyY > n / 2) { // 2º Quadrant

			board[(n / 2) - 1][(n / 2) - 1] = color;
			board[(n / 2)][(n / 2) - 1] = color;
			board[(n / 2)][(n / 2)] = color;
			++color;
			trominoPlacing(startingX, startingY, n / 2, startingX + (n / 2) - 1, startingY + (n / 2) - 1, color); // Go
																													// to
																													// 1st
																													// q
			++color;
			trominoPlacing(startingX, startingY + (n / 2), n / 2, emptyX, emptyY, color); // Go to 2nd q
			++color;
			trominoPlacing(startingX + (n / 2), startingY, n / 2, startingX + (n / 2) - 2, startingY + (n / 2) - 1,
					color); // Go to 3rd
			++color;
			trominoPlacing(startingX + (n / 2), startingY + (n / 2), n / 2, startingX + (n / 2) - 2,
					startingY + (n / 2) - 2, color); // Go to 4rth q
			++color;

		} else if (emptyX > n / 2 && emptyY < n / 2) { // 3º Quadrant
			board[n / 2][n / 2] = color;
			board[(n / 2) - 1][n / 2] = color;
			board[(n / 2) - 1][(n / 2) - 1] = color;
			++color;
			trominoPlacing(startingX, startingY, n / 2, startingX + (n / 2) - 1, startingY + (n / 2) - 1, color); // Go
																													// to
																													// 1st
																													// q
			++color;
			trominoPlacing(startingX, startingY + (n / 2), n / 2, startingX + (n / 2) - 1, startingY + (n / 2) - 2,
					color); // Go to 2nd q
			++color;
			trominoPlacing(startingX + (n / 2), startingY, n / 2, emptyX, emptyY, color); // Go to 3rd
			++color;
			trominoPlacing(startingX + (n / 2), startingY + (n / 2), n / 2, startingX + (n / 2) - 2,
					startingY + (n / 2) - 2, color); // Go to 4rth q
			++color;
		} else { // 4º Quadrant
			board[(n / 2) - 1][(n / 2) - 1] = color;
			board[(n / 2) - 1][n / 2] = color;
			board[(n / 2)][(n / 2) - 1] = color;
			++color;
			trominoPlacing(startingX, startingY, n / 2, startingX + (n / 2) - 1, startingY + (n / 2) - 1, color); // Go
																													// to
																													// 1st
																													// q
			++color;
			trominoPlacing(startingX, startingY + (n / 2), n / 2, startingX + (n / 2) - 1, startingY + (n / 2) - 2,
					color); // Go to 2nd q
			++color;
			trominoPlacing(startingX + (n / 2), startingY, n / 2, startingX + (n / 2) - 2, startingY + (n / 2) - 1,
					color); // Go to 3rd
			++color;
			trominoPlacing(startingX + (n / 2), startingY + (n / 2), n / 2, emptyX, emptyY, color); // Go to 4rth q
		}
		++color;

	}

	public void simpleCase(int i, int j, int startingX, int startingY, int color) {
		// Placing the piece of the simple case
		int n = 2;

		// Position reducing to meet 2x2 size
		if (i % 2 == 0) {
			i = 0;
		} else {
			i = 1;
		}
		if (j % 2 == 0) {
			j = 0;
		} else {
			j = 1;
		}

		if (i == 0 && j == 0) {
			board[startingX + n / 2][startingY + n / 2] = color;
			board[startingX + (n / 2) - 1][startingY + n / 2] = color;
			board[startingX + n / 2][startingY + (n / 2) - 1] = color;
		} else if (i == 0 && j == 1) {
			board[startingX + (n / 2) - 1][startingY + (n / 2) - 1] = color;
			board[startingX + (n / 2)][startingY + (n / 2) - 1] = color;
			board[startingX + (n / 2)][startingY + (n / 2)] = color;
		} else if (i == 1 && j == 0) {
			board[startingX + n / 2][startingY + n / 2] = color;
			board[startingX + (n / 2) - 1][startingY + n / 2] = color;
			board[startingX + (n / 2) - 1][startingY + (n / 2) - 1] = color;
		} else {
			board[startingX + (n / 2) - 1][startingY + (n / 2) - 1] = color;
			board[startingX + (n / 2) - 1][startingY + n / 2] = color;
			board[startingX + (n / 2)][startingY + (n / 2) - 1] = color;
		}
	}
}

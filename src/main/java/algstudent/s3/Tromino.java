package algstudent.s3;

public class Tromino {

	private static int[][] board;
	private int color;

	/**
	 * Prints the board for size n
	 * @param n sie of the Tromino
	 */
	public void printBoard(int n) {
		System.out.println();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(board[i][j] + "\t");
			}
			System.out.println();
		}
	}

	/**
	 * Solves a Tromino of size n whose empty space is located at row i and col j
	 * 
	 * @param n size of the Tromino
	 * @param i position of the empty space at x (rows)
	 * @param j position of the empty space at y (cols)s
	 */
	public Tromino(int n, int i, int j) {

		board = new int[n][n];

		board[i][j] = 1;

		color = 2;

		if (n == 2) {
			simpleCase(i, j, 0, 0);
		} else {
			trominoPlacing(0, 0, n, i, j);
		}

	}

	/**
	 * Computes the solution for a Tromino of size > 2
	 * 
	 * @param startingX the position from which we start to count on x (rows)
	 * @param startingY the position from which we start to count on y (cols)
	 * @param n         the size of the Tromino
	 * @param emptyX    the x position of the empty digit (row)
	 * @param emptyY    the y position of the empty digit (col)
	 */
	private void trominoPlacing(int startingX, int startingY, int n, int emptyX, int emptyY) {

		// Stop condition: return to simple case
		if (n <= 2) {
			simpleCase(emptyX, emptyY, startingX, startingY);
			return;
		}

		// Place a piece depending on the position of the occupied space
		if (emptyX < n / 2 + startingX && emptyY < n / 2 + startingY) { // 1º Quadrant

			board[startingX + n / 2][startingY + n / 2] = color;
			board[startingX + (n / 2) - 1][startingY + n / 2] = color;
			board[startingX + n / 2][startingY + (n / 2) - 1] = color;

			++color;
			trominoPlacing(startingX, startingY, n / 2, emptyX, emptyY); // 1st
			++color;
			trominoPlacing(startingX, startingY + (n / 2), n / 2, startingX + (n / 2) - 1, startingY + (n / 2)); // 2nd
			++color;
			trominoPlacing(startingX + (n / 2), startingY, n / 2, startingX + (n / 2), startingY + (n / 2) - 1); // 3rd
			++color;
			trominoPlacing(startingX + (n / 2), startingY + (n / 2), n / 2, startingX + (n / 2) - 2,
					startingY + (n / 2) - 2); // 4rth
			++color;

		} else if (emptyX < n / 2 + startingX && emptyY >= n / 2 + startingY) { // 2º Quadrant

			board[(n / 2) - 1 + startingX][(n / 2) - 1 + startingY] = color;
			board[(n / 2) + startingX][n / 2 + startingY] = color;
			board[(n / 2) + startingX][(n / 2) - 1 + startingY] = color;

			++color;
			trominoPlacing(startingX, startingY, n / 2, startingX + (n / 2) - 1, startingY + (n / 2) - 1); // 1st
			++color;
			trominoPlacing(startingX, startingY + (n / 2), n / 2, emptyX, emptyY); // 2nd
			++color;
			trominoPlacing(startingX + (n / 2), startingY, n / 2, startingX + (n / 2), startingY + (n / 2) - 1); // 3rd
			++color;
			trominoPlacing(startingX + (n / 2), startingY + (n / 2), n / 2, startingX + (n / 2), startingY + (n / 2)); // 4rth
			++color;

		} else if (emptyX >= n / 2 + startingX && emptyY < n / 2 + startingY) { // 3º Quadrant
			board[(n / 2) - 1 + startingX][(n / 2) - 1 + startingY] = color;
			board[(n / 2) - 1 + startingX][n / 2 + startingY] = color;
			board[(n / 2) + startingX][(n / 2) + startingY] = color;

			++color;
			trominoPlacing(startingX, startingY, n / 2, startingX + (n / 2) - 1, startingY + (n / 2) - 1); // 1st
			++color;
			trominoPlacing(startingX, startingY + (n / 2), n / 2, startingX + (n / 2) - 1, startingY + (n / 2)); // 2nd
			++color;
			trominoPlacing(startingX + (n / 2), startingY, n / 2, emptyX, emptyY); // 3rd
			++color;
			trominoPlacing(startingX + (n / 2), startingY + (n / 2), n / 2, startingX + (n / 2), startingY + (n / 2)); // 4rth
			++color;

		} else { // 4º Quadrant
			board[(n / 2) - 1 + startingX][(n / 2) - 1 + startingY] = color;
			board[(n / 2) - 1 + startingX][n / 2 + startingY] = color;
			board[(n / 2) + startingX][(n / 2) - 1 + startingY] = color;

			++color;
			trominoPlacing(startingX, startingY, n / 2, startingX + (n / 2) - 1, startingY + (n / 2) - 1); // 1st
			++color;
			trominoPlacing(startingX, startingY + (n / 2), n / 2, startingX + (n / 2) - 1, startingY + (n / 2)); // 2nd
			++color;
			trominoPlacing(startingX + (n / 2), startingY, n / 2, startingX + (n / 2), startingY + (n / 2) - 1); // 3rd
			++color;
			trominoPlacing(startingX + (n / 2), startingY + (n / 2), n / 2, emptyX, emptyY); // 4rth
		}
		++color;

	}

	/**
	 * Computes the solution for a Tromino of simple size (n=2). The method used
	 * consists of reducing the i and j positions of the empty space in order to be
	 * either 0 or 1, in this way it is easier to check in which quadrant the empty
	 * is located on
	 * 
	 * @param i         the i position of the empty digit (row)
	 * @param j         the j position of the empty digit (col)
	 * @param startingX the position from which we start to count on x (row)
	 * @param startingY the position from which we start to count on y (col)
	 */
	public void simpleCase(int i, int j, int startingX, int startingY) {
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

		if (i == 0 && j == 0) { // 1st
			board[startingX + n / 2][startingY + n / 2] = color;
			board[startingX + (n / 2) - 1][startingY + n / 2] = color;
			board[startingX + n / 2][startingY + (n / 2) - 1] = color;
		} else if (i == 0 && j == 1) { // 2nd
			board[(n / 2) - 1 + startingX][(n / 2) - 1 + startingY] = color;
			board[(n / 2) + startingX][n / 2 + startingY] = color;
			board[(n / 2) + startingX][(n / 2) - 1 + startingY] = color;
		} else if (i == 1 && j == 0) { // 3rd
			board[(n / 2) - 1 + startingX][(n / 2) - 1 + startingY] = color;
			board[(n / 2) - 1 + startingX][n / 2 + startingY] = color;
			board[(n / 2) + startingX][(n / 2) + startingY] = color;
		} else { // 4rth
			board[(n / 2) - 1 + startingX][(n / 2) - 1 + startingY] = color;
			board[(n / 2) - 1 + startingX][n / 2 + startingY] = color;
			board[(n / 2) + startingX][(n / 2) - 1 + startingY] = color;
		}
	}
}

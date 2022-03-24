package algstudent.s5;

public class LevenshteinDistance {

	private int[][] matrix;
	private char[] str1;
	private char[] str2;
	private int n;
	private int m;

	public LevenshteinDistance(String str1, String str2) {
		if (str1.length() > str2.length()) {
			this.str1 = str1.toCharArray();
			this.str2 = str2.toCharArray();
		} else {
			this.str1 = str2.toCharArray();
			this.str2 = str1.toCharArray();
		}

		this.n = Math.max(str1.length(), str2.length());
		this.m = Math.min(str1.length(), str2.length());
		matrix = new int[n + 1][n + 1];

		for (int i = 0; i <= n; i++) {
			matrix[i][0] = i;
			matrix[0][i] = i;
		}
	}

	public int calculate() {

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				if (str1[i - 1] == str2[j - 1]) {
					matrix[i][j] = matrix[i - 1][j - 1];
				} else {
					int min = Math.min(matrix[i - 1][j - 1], matrix[i][j - 1]);
					int secondMin = Math.min(matrix[i - 1][j], matrix[i][j - 1]);
					min = Math.min(min, secondMin);
					matrix[i][j] = 1 + min;
				}
			}
		}
		return matrix[n][m];
	}

	/**
	 * By using the Hamming Distance The Hamming Distance is a metric for comparing
	 * two binary data strings. While comparing two binary strings of equal length,
	 * Hamming distance is the number of bit positions in which the two bits are
	 * different. Although it is specially designed for binary data strings, we can
	 * also implement it for common strings, which is what I have done.
	 * 
	 * @return int the distance between the words
	 */
	public int alternativeSolution() {
		int solution = 0;
		for (int i = 0; i < m; i++) {
			if (str1[i] != str2[i]) {
				solution++;
			}
		}
		return solution;
	}

	public void show() {
		System.out.println();
		for (int i = 0; i < n + 1; i++) {
			for (int j = 0; j < m + 1; j++) {
				System.out.print(matrix[i][j] + " ");
				if (j == m) {
					System.out.println();
				}
			}
		}
	}
}

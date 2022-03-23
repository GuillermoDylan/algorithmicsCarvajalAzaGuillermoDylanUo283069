package algstudent.s5;

public class LevenshteinDistanceTest {

	public static void main(String[] args) {
		LevenshteinDistance ld = new LevenshteinDistance("BARCAZAS", "ABRACADABRA");
		System.out.println("Solution: " + ld.calculate());
		System.out.println("Hamming Solution: " + ld.alternativeSolution());
		
		LevenshteinDistance ld2 = new LevenshteinDistance("abcdefg", "bcdefgh");
		System.out.println("Solution: " + ld2.calculate());
		System.out.println("Hamming Solution: " + ld2.alternativeSolution());
	}

}

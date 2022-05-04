package algstudent.test;

import java.nio.file.Paths;

public class ImageAveragerBench {
	
	// Benchmarking settings
	private static String REAL_IMG = Paths.get("").toAbsolutePath().toString() + "/src/main/java/algstudent/test/einstein_1_256.png";
	private static String BAD_IMG = Paths.get("").toAbsolutePath().toString() + "/src/main/java/algstudent/test/einstein_1_256.png";
	private static String OUT_DIR_G = Paths.get("").toAbsolutePath().toString() + "/src/main/java/algstudent/test/out_g";
	private static String OUT_DIR_B = Paths.get("").toAbsolutePath().toString() + "/src/main/java/algstudent/test/out_bt";
	private static String OUT_DIR_BTB = Paths.get("").toAbsolutePath().toString() + "/src/main/java/algstudent/test/out_btb";
	private static int N_IMGS = 6; 
	private static double PERCENTAGE_BAD = 25; // %
	private static double S_NOISE = 5.0; // Noise level - Gaussian sigma
	private static int N_TRIES_GREEDY = 10;
		
	public static void main(String[] args) {
		int n_real, n_bad;
		ImageAverager img_avger;
		
		// Generating and testing a single dataset instance
		n_bad = (int) ((PERCENTAGE_BAD/100.)*N_IMGS);
		n_real = N_IMGS - n_bad;
		img_avger = new ImageAverager(REAL_IMG, BAD_IMG, n_real, n_bad, S_NOISE);
				
		System.out.print("TESTING GREEDY:\n");
		img_avger.splitSubsetsGreedy(N_TRIES_GREEDY);
		System.out.printf("  -ZNCC: %f\n",  img_avger.zncc());
		System.out.printf("  -Counter: %d\n",  img_avger.getCounter());
		img_avger.saveResults(OUT_DIR_G);
		
		double time = System.currentTimeMillis();
		
		System.out.print("TESTING BACKTRACKING BALANCING:\n");
		img_avger.splitSubsetsBacktracking(1);
		System.out.printf("  -ZNCC: %f\n",  img_avger.zncc());
		System.out.printf("  -Counter: %d\n",  img_avger.getCounter());
		img_avger.saveResults(OUT_DIR_BTB);
		System.out.printf("  -Time:%f\n", System.currentTimeMillis()-time);
		
		time = System.currentTimeMillis();
		
		System.out.print("TESTING BACKTRACKING  NO BALANCING:\n");
		img_avger.splitSubsetsBacktracking();
		System.out.printf("  -ZNCC: %f\n",  img_avger.zncc());
		System.out.printf("  -Counter: %d\n",  img_avger.getCounter());
		img_avger.saveResults(OUT_DIR_B);
		System.out.printf("  -Time:%f\n", System.currentTimeMillis()-time);
		
	}

}
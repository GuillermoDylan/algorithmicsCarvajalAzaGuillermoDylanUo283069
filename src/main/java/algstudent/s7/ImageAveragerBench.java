package algstudent.s7;

import java.nio.file.Paths;

import algstudent.s7.util.ImageAverager;

public class ImageAveragerBench {

	// Benchmarking settings
	private static String REAL_IMG = Paths.get("").toAbsolutePath().toString()
			+ "/src/main/java/algstudent/s6/einstein_1_256.png";
	private static String BAD_IMG = Paths.get("").toAbsolutePath().toString()
			+ "/src/main/java/algstudent/s6/einstein_1_256.png";
	private static String OUT_DIR_G = Paths.get("").toAbsolutePath().toString() + "/src/main/java/algstudent/s7/out_g";
	private static String OUT_DIR_B = Paths.get("").toAbsolutePath().toString() + "/src/main/java/algstudent/s7/out_bt";
	private static String OUT_DIR_BNB = Paths.get("").toAbsolutePath().toString() + "/src/main/java/algstudent/s7/out_bnb";
	private static int N_IMGS = 8;
	private static double PERCENTAGE_BAD = 25; // %
	private static double S_NOISE = 5.0; // Noise level - Gaussian sigma
	private static int N_TRIES_GREEDY = 100;

	public static void main(String[] args) {
		int n_real, n_bad;
		ImageAverager img_avger;

		// Generating and testing a single dataset instance
		n_bad = (int) ((PERCENTAGE_BAD / 100.) * N_IMGS);
		n_real = N_IMGS - n_bad;
		img_avger = new ImageAverager(REAL_IMG, BAD_IMG, n_real, n_bad, S_NOISE);

//		System.out.print("TESTING GREEDY:\n");
//		img_avger.splitSubsetsGreedy(N_TRIES_GREEDY);
//		System.out.printf("  -ZNCC: %f\n", img_avger.zncc());
//		System.out.printf("  -Counter: %d\n", img_avger.getCounter());
//		img_avger.saveResults(OUT_DIR_G);
////
		double time = System.currentTimeMillis();
////
		img_avger = new ImageAverager(REAL_IMG, BAD_IMG, n_real, n_bad, S_NOISE);
		System.out.print("TESTING BACKTRACKING BALANCING:\n");
		img_avger.splitSubsetsBacktracking(1);
		System.out.printf("  -ZNCC: %f\n", img_avger.zncc());
		System.out.printf("  -Counter: %d\n", img_avger.getCounter());
		img_avger.saveResults(OUT_DIR_B);
		System.out.printf("  -Time:%f\n", System.currentTimeMillis() - time);
////
////		
////
//		img_avger = new ImageAverager(REAL_IMG, BAD_IMG, n_real, n_bad, S_NOISE);
//		System.out.print("TESTING BACKTRACKING  NO BALANCING:\n");
//		img_avger.splitSubsetsBacktracking();
//		System.out.printf("  -ZNCC: %f\n", img_avger.zncc());
//		System.out.printf("  -Counter: %d\n", img_avger.getCounter());
//		img_avger.saveResults(OUT_DIR_B);
//		System.out.printf("  -Time:%f\n", System.currentTimeMillis() - time);
//
//		// TODO execute Branch and Bound
//		// create NodeAvg passing img avg
//		// Crate branch and bound
//		// bnb.branchAndBound()
//		// bnb.printSol
//		
//		//Branch and Bound without pruning

		img_avger = new ImageAverager(REAL_IMG, BAD_IMG, n_real, n_bad, S_NOISE);
		time = System.currentTimeMillis();
		System.out.print("TESTING BACKTRACKING  BRANCH AND BOUND:\n");
		NodeAvg node = new NodeAvg(img_avger, false);
		ImageAveragerBnB bnb = new ImageAveragerBnB(node);
		bnb.branchAndBound(node);
		bnb.printSolutionTrace();
		img_avger = ((NodeAvg) bnb.getBestNode()).getManager();
		System.out.printf("  -ZNCC: %f\n", img_avger.zncc());
		System.out.printf("  -Counter: %d\n", img_avger.getCounter());
		img_avger.saveResults(OUT_DIR_BNB);
		System.out.printf("  -Time:%f\n", System.currentTimeMillis() - time);
		System.out.printf("Number of processed nodes %d \nNumber of generated nodes %d \nNumber of trimmed nodes %d\n",
				bnb.generatedNodes, bnb.processedNodes, bnb.generatedNodes - bnb.notTrimmedNodes);
		// Branch and bound with pruning

		img_avger = new ImageAverager(REAL_IMG, BAD_IMG, n_real, n_bad, S_NOISE);
		time = System.currentTimeMillis();
		System.out.print("TESTING BACKTRACKING  BRANCH AND BOUND:\n");
		NodeAvg node2 = new NodeAvg(img_avger, false);
		ImageAveragerBnB bnb2 = new ImageAveragerBnB(node2);
		bnb2.branchAndBound(node2);
		bnb2.printSolutionTrace();
		img_avger = ((NodeAvg) bnb2.getBestNode()).getManager();
		System.out.printf("  -ZNCC: %f\n", img_avger.zncc());
		System.out.printf("  -Counter: %d\n", img_avger.getCounter());
		img_avger.saveResults(OUT_DIR_BNB);
		System.out.printf("  -Time:%f\n", System.currentTimeMillis() - time);
		System.out.printf("Number of processed nodes %d \nNumber of generated nodes %d \nNumber of trimmed nodes %d\n",
				bnb2.generatedNodes, bnb2.processedNodes, bnb2.generatedNodes - bnb2.notTrimmedNodes);

	}

}
package algstudent.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ImageAverager {

	private Image real_img, bad_img; // to store the main good and main bad image
	private Image avg_img, quarter1_img, quarter2_img, quarter3_img, quarter4_img; // to store the final tests to see if
																					// we improve the previous results
	private Image[] dataset; // dataset to store all the images (good and bad ones)
	private int[] sol; // to store the partial results (where I am putting the pictures? 0->not
						// assigned, 1->first quarter, 2->second quarter, 3->third quarter, 4-> fourth
						// quarter
	private int[] bestSol; // to store the best solution
	private int width, height; // to store the width and height of the image
	// backtracking variables
	private int counter; // to store the number of times we assign an image to quarter1, quarter2,
							// quarter3, quarter4 or no group
	private double max_zncc; // to store the best ZNCC
	private int totalCounter; // To store the total counter

	/**
	 * Constructor
	 * 
	 * @real_path path to the real image (pattern to find) on disk
	 * @bad_path path to the bad image on disk
	 * @n_real number of real images in the dataset (>= 1)
	 * @n_bad number of bad images in the dataset
	 * @s_noise standard deviation for noise
	 */
	public ImageAverager(String real_path, String bad_path, int n_real, int n_bad, double s_noise) {
		assert (n_real >= 1) && (n_bad < n_real);// assert at least one reference image

		// load reference and bad images
		this.real_img = new Image(real_path);
		this.bad_img = new Image(bad_path);
		this.width = this.real_img.getWidth();
		this.height = this.real_img.getHeight();

		// create the dataset as an array of unordered randomly chosen real and bad
		// images
		int total_imgs = n_real + n_bad; // the total number of images are the good + the bad ones
		this.dataset = new Image[total_imgs]; // the data set for the total of images
		this.sol = new int[total_imgs]; // we will use this variable during the process 0->not assigned, 1->first
										// quarter,
										// 2->second quarter, 3->third quarter, 4->fourth quarter
		this.bestSol = new int[total_imgs]; // we will use this variable to store the best results
		int[] rand_index = this.randomIndexes(total_imgs); // random array of positions to mix images
		Image hold_img; // temp images
		int region = 0; // 0-up, 1-down, 2-left, 3-right
		for (int i = 0; i < n_real; i++) { // to save good images
			hold_img = new Image(this.width, this.height); // generate image
			hold_img.addSignal(this.real_img); // save the image
			hold_img.suppressRegion(region); // a half part of the image is deleted
			hold_img.addNoise(s_noise); // add some noise
			this.dataset[rand_index[i]] = hold_img; // save image
			if (region == 3)
				region = 0;
			else
				region++;
		}
		region = 0;
		for (int i = n_real; i < n_real + n_bad; i++) { // to save bad images
			hold_img = new Image(this.width, this.height); // generate image
			hold_img.addSignal(this.bad_img); // save the image
			hold_img.invertSignal(); // corrupt the image
			hold_img.suppressRegion(region); // the fourth part of the image is deleted
			hold_img.addNoise(s_noise); // add some noise
			this.dataset[rand_index[i]] = hold_img; // save image
			if (region == 3)
				region = 0;
			else
				region++;
		}
	}

	/**
	 * To generate a random array of positions
	 * 
	 * @param n Length of the array
	 * @return
	 */
	public int[] randomIndexes(int n) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < n; i++)
			list.add(i);
		Collections.shuffle(list);
		int[] array = new int[n];
		for (int i = 0; i < n; i++)
			array[i] = list.get(i);
		return array;
	}

	/**
	 * Store resulting images for testing
	 * 
	 * @out_dir directory save the output images
	 */
	public void saveResults(String out_dir) {
		this.avg_img.save(out_dir + "/img_avg.png");
		this.quarter1_img.save(out_dir + "/img_quarter1_avg.png");
		this.quarter2_img.save(out_dir + "/img_quarter2_avg.png");
		this.quarter3_img.save(out_dir + "/img_quarter3_avg.png");
		this.quarter4_img.save(out_dir + "/img_quarter4_avg.png");
		for (int i = 0; i < this.dataset.length; i++) {
			this.dataset[i].save(out_dir + "/img_" + i + "_klass_" + this.bestSol[i] + ".png");
		}
	}

	/**
	 * @return the number of steps carried out by the algorithm to solve the problem
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * Computes the ZNCC between the four image dataset quarters
	 * 
	 * @return the computed ZNCC
	 */
	public double zncc() {
		return this.quarter1_img.zncc(this.quarter2_img) + this.quarter3_img.zncc(this.quarter4_img);
	}

	/**
	 * Greedy algorithm: random instances for each quarter, the best one is the final
	 * solution
	 * 
	 * @n_tries number of random tries
	 */
	public void splitSubsetsGreedy(int n_tries) {
		this.counter = 0;
		this.max_zncc = -1;
		this.quarter1_img = new Image(this.width, this.height);
		this.quarter2_img = new Image(this.width, this.height);
		this.quarter3_img = new Image(this.width, this.height);
		this.quarter4_img = new Image(this.width, this.height);
		this.avg_img = new Image(this.width, this.height);
		for (int i = 0; i < n_tries; i++) {
			for (int j = 0; j < dataset.length; j++) {
				int position = new Random().nextInt(5);
				counter++;

				switch (position) {
				case 0:
					sol[j] = 0;
					break;
				case 1: // Image used on for the first quarter
					sol[j] = 1;
					break;
				case 2: // Image used for the second quarter
					sol[j] = 2;
					break;
				case 3: // Image used for the third quarter
					sol[j] = 3;
					break;
				case 4: // Image used for the fourth quarter
					sol[j] = 4;
					break;
				}
			}
			this.quarter1_img = calculateImage(1, sol);
			this.quarter2_img = calculateImage(2, sol);
			this.quarter3_img = calculateImage(3, sol);
			this.quarter4_img = calculateImage(4, sol);
			if (zncc() > max_zncc) {
				max_zncc = zncc();
				bestSol = sol.clone();
			}

		}
		storeBestSolution();
	}

	/**
	 * Backtracking algorithm
	 * 
	 * @max_unbalancing: (pruning condition) determines the maximum difference
	 *                   between the number of images on each quarter set
	 */
	public void splitSubsetsBacktracking(int max_unbalancing) {
		this.counter = 0;
		this.max_zncc = -1;
		this.totalCounter = 0;
		this.quarter1_img = new Image(this.width, this.height);
		this.quarter2_img = new Image(this.width, this.height);
		this.quarter3_img = new Image(this.width, this.height);
		this.quarter4_img = new Image(this.width, this.height);
		this.avg_img = new Image(this.width, this.height);
		this.sol = new int[sol.length];
		this.bestSol = new int[bestSol.length];

		backtrackingWithUnbalancing(0, max_unbalancing, 0, 0, 0, 0, 0);
		storeBestSolution();
		System.out.println("Best solution: ");
		printSol(bestSol);
	}

	/**
	 * Applies backtacking with balancing to the dataset of images in order to get
	 * the best one
	 * 
	 * @param level           the level on which the "node" is
	 * @param max_unbalancing the maximum unbalancing allowed
	 * @param set1            the number of images on the 1st quarter
	 * @param set2            the number of images on the 2nd quarter
	 * @param set0            the number of images on the 0 quarter (not used, not
	 *                        really usefull)
	 * @param set3            the number of images on the 3rd quarter
	 * @param set4            the number of images on the 4th quarter
	 */
	private void backtrackingWithUnbalancing(int level, int max_unbalancing, int set1, int set2, int set3, int set4,
			int set0) {
		if (level == dataset.length) { // if it is a solution
			// Solution found
			this.quarter1_img = calculateImage(1, sol); // calculate quarter 1
			this.quarter2_img = calculateImage(2, sol); // calculate quarter 2
			this.quarter3_img = calculateImage(3, sol); // calculate quarter 3
			this.quarter4_img = calculateImage(4, sol); // calculate quarter 4
			if (zncc() > max_zncc) { // calculate if zncc is the best one yet
				max_zncc = zncc(); // update max_zncc
				bestSol = sol.clone(); // clone solution
			}
		} else {

			if (calculatePruning(set1, set2, set3, set4, max_unbalancing)) { // Pruning
				sol[level] = 1; // add to level 1
				counter++;
				set1++;
				backtrackingWithUnbalancing(level + 1, max_unbalancing, set1, set2, set3, set4, set0);
				set1--; // undo changes

				sol[level] = 2; // add to level 2
				set2++;
				counter++;
				backtrackingWithUnbalancing(level + 1, max_unbalancing, set1, set2, set3, set4, set0);
				set2--; // undo change

				sol[level] = 3; // add to level 3
				counter++;
				set3++;
				backtrackingWithUnbalancing(level + 1, max_unbalancing, set1, set2, set3, set4, set0);
				set3--; // undo change

				sol[level] = 4; // add to level 4
				counter++;
				set4++;
				backtrackingWithUnbalancing(level + 1, max_unbalancing, set1, set2, set3, set4, set0);
				set4--; // undo change

				sol[level] = 0; // add to level 0
				counter++;
				set0++;
				backtrackingWithUnbalancing(level + 1, max_unbalancing, set1, set2, set3, set4, set0);
				set0--; // undo change
			}
		}
	}

	/**
	 * Calculates if pruning is necessary for the 4 quarters
	 *
	 * @param set1            the first quarter set
	 * @param set2            the second quarter set
	 * @param set3            the third quarter set
	 * @param set4            the fourth quarter set
	 * @param max_unbalancing the maximum unbalancing allowed
	 * @return true if pruning is not necessary, false otherwise
	 */
	private boolean calculatePruning(int set1, int set2, int set3, int set4, int max_unbalancing) {
		return Math.abs(set1 - set2) <= max_unbalancing && Math.abs(set1 - set3) <= max_unbalancing
				&& Math.abs(set1 - set4) <= max_unbalancing && Math.abs(set2 - set3) <= max_unbalancing
				&& Math.abs(set2 - set4) <= max_unbalancing && Math.abs(set3 - set4) <= max_unbalancing;
	}

	/**
	 * Calculates the Image given the value to check (1 or 2) and the solution array
	 * 
	 * @param i     can be 1, 2, 3, 4 or 0 (0 is not useful), and it calculates the
	 *              respective quarter
	 * @param array the solution array
	 * @return Image the quarter calculated
	 */
	private Image calculateImage(int i, int array[]) {
		Image quarter = new Image(this.width, this.height);
		for (int j = 0; j < sol.length; j++) {
			if (array[j] == i) {
				quarter.addSignal(dataset[j]);
			}
		}
		return quarter;
	}

	/**
	 * Prints the solution given the solution
	 * 
	 * @param x the solution array
	 */
	private void printSol(int[] x) {
		for (int i = 0; i < x.length; i++) {
			if (i != x.length - 1)
				System.out.printf("%d, ", x[i]);
			else
				System.out.printf("%d \n", x[i]);
		}
	}

	/**
	 * Backtracking algorithm without balancing. Using a larger than the number of
	 * images in the dataset ensures no prunning
	 */
	public void splitSubsetsBacktracking() {
		this.counter = 0;
		this.max_zncc = -1;
		this.quarter1_img = new Image(this.width, this.height);
		this.quarter2_img = new Image(this.width, this.height);
		this.quarter3_img = new Image(this.width, this.height);
		this.quarter4_img = new Image(this.width, this.height);
		this.avg_img = new Image(this.width, this.height);
		this.sol = new int[sol.length];
		this.bestSol = new int[bestSol.length];
		backtracking(0);
		storeBestSolution();
		System.out.println("Best solution: ");
		printSol(bestSol);
		counter = totalCounter;
	}

	/**
	 * Applies backtracking to get the best image on the dataset of Images
	 * 
	 * @param level the level at which the backtracking "node" is at
	 */
	private void backtracking(int level) {
		if (level == dataset.length) { // if it is a solution
			// Solution found
			this.quarter1_img = calculateImage(1, sol); // calculate quarter 1
			this.quarter2_img = calculateImage(2, sol);// calculate quarter 2
			this.quarter3_img = calculateImage(3, sol);// calculate quarter 3
			this.quarter4_img = calculateImage(4, sol);// calculate quarter 4
			if (zncc() > max_zncc) {
				this.max_zncc = zncc(); // update zncc
				this.bestSol = sol.clone(); // update solution
			}
		} else {

			sol[level] = 1; // add to level 1
			counter++;
			totalCounter++;
			backtracking(level + 1);

			sol[level] = 2; // add to level 2
			counter++;
			totalCounter++;
			backtracking(level + 1);

			sol[level] = 3; // add to level 3
			counter++;
			totalCounter++;
			backtracking(level + 1);

			sol[level] = 4; // add to level 4
			counter++;
			totalCounter++;
			backtracking(level + 1);

			sol[level] = 0; // add to level 0
			counter++;
			totalCounter++;
			backtracking(level + 1);
		}
	}

	/**
	 * Stores the best solution calculating the quarters and the average image based
	 * on the bestSol array
	 */
	private void storeBestSolution() {
		Image baseImageQuarter1 = calculateImage(1, bestSol);
		Image baseImageQuarter2 = calculateImage(2, bestSol);
		Image baseImageQuarter3 = calculateImage(3, bestSol);
		Image baseImageQuarter4 = calculateImage(4, bestSol);
		Image finalImage = new Image(this.width, this.height);
		finalImage.addSignal(baseImageQuarter1);
		finalImage.addSignal(baseImageQuarter2);
		finalImage.addSignal(baseImageQuarter3);
		finalImage.addSignal(baseImageQuarter4);
		this.quarter1_img = baseImageQuarter1;
		this.quarter2_img = baseImageQuarter2;
		this.quarter3_img = baseImageQuarter3;
		this.quarter4_img = baseImageQuarter4;
		this.avg_img = finalImage;
	}

}

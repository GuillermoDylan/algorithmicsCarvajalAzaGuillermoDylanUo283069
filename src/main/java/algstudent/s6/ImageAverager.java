package algstudent.s6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ImageAverager {

	private Image real_img, bad_img; // to store the main good and main bad image
	private Image avg_img, half1_img, half2_img; // to store the final tests to see if we improve the previous results
	private Image[] dataset; // dataset to store all the images (good and bad ones)
	private int[] sol; // to store the partial results (where I am putting the pictures? 0->not
						// assigned, 1->first half, 2->second half
	private int[] bestSol; // to store the best solution
	private int width, height; // to store the width and height of the image
	// backtracking variables
	private int counter; // to store the number of times we assign an image to half1, half2 or no group
	private double max_zncc; // to store the best ZNCC
	private static int NUMBER_OF_SETS = 3; // Represents the number of sets (G1, G2, G0) where the images can be sent

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
		this.sol = new int[total_imgs]; // we will use this variable during the process 0->not assigned, 1->first half,
										// 2->second half
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
		this.half1_img.save(out_dir + "/img_half1_avg.png");
		this.half2_img.save(out_dir + "/img_half2_avg.png");
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
	 * Computes the ZNCC between both image dataset halves
	 * 
	 * @return the computed ZNCC
	 */
	public double zncc() {
		return this.half1_img.zncc(this.half2_img);
	}

	/**
	 * Greedy algorithm: random instances for each half, the best one is the final
	 * solution
	 * 
	 * @n_tries number of random tries
	 */
	public void splitSubsetsGreedy(int n_tries) {
		this.counter = 0;
		this.half1_img = new Image(this.width, this.height);
		this.half2_img = new Image(this.width, this.height);
		this.avg_img = new Image(this.width, this.height);
		for (int i = 0; i < n_tries; i++) {
			for (int j = 0; j < dataset.length; j++) {
				int position = new Random().nextInt(3);
				counter++;

				switch (position) {
				case 0:
					// In this case we do not use the image
					break;
				case 1: // Image used on for the first half
					this.half1_img.addSignal(dataset[j]);
					break;
				case 2: // Image used for the second half
					this.half2_img.addSignal(dataset[j]);
					break;
				}
			}

		}
		this.avg_img.addSignal(this.half1_img);
		this.avg_img.addSignal(this.half2_img);
	}

	/**
	 * Backtracking algorithm
	 * 
	 * @max_unbalancing: (pruning condition) determines the maximum difference
	 *                   between the number of images on each half set
	 */
	public void splitSubsetsBacktracking(int max_unbalancing) {
		this.counter = 0;
		backtrackingWithUnbalancing(0, max_unbalancing, 0, 0);
	}

	private void backtrackingWithUnbalancing(int level, int max_unbalancing, int set1, int set2) {
		if (level == dataset.length) {
			// Solution found
			counter = 0;
			avg_img.addSignal(half1_img);
			avg_img.addSignal(half2_img);
			if (avg_img.zncc(real_img) > max_zncc) {
				max_zncc = avg_img.zncc(real_img);
				printSol(sol);
				sol = new int[dataset.length];
			}
		} else {
			for (int j = 0; j < NUMBER_OF_SETS; j++) {
				switch (j) {
				case 1:
					if (Math.abs(set1 - set2) <= max_unbalancing) {
						sol[counter] = 1;
						set1++;
						counter++;
						this.half1_img.addSignal(dataset[j]);
						backtrackingWithUnbalancing(level + 1, max_unbalancing, set1, set2);
						this.half1_img.removeSignal(dataset[j]);
					}
					break;
				case 2:
					if (Math.abs(set1 - set2) <= max_unbalancing) {
						sol[counter] = 2;
						set2++;
						counter++;
						this.half2_img.addSignal(dataset[j]);
						backtrackingWithUnbalancing(level + 1, max_unbalancing, set1, set2);
						this.half2_img.removeSignal(dataset[j]);
					}
					break;
				case 0: // Image not used
					sol[counter] = 0;
					counter++;
					backtrackingWithUnbalancing(level + 1, max_unbalancing, set1, set2);
					break;
				}
			}
		}
	}

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
		backtracking(0);
	}

	private void backtracking(int level) {
		if (level == dataset.length) {
			// Solution found
			avg_img.addSignal(half1_img);
			avg_img.addSignal(half2_img);
			if (avg_img.zncc(real_img) > max_zncc) {
				max_zncc = avg_img.zncc(real_img);
				printSol(sol);
			}
		} else {
			
			sol[counter] = 1;
			this.half1_img.addSignal(dataset[1]);
			counter++;
			backtracking(level + 1);
			counter--;
			this.half1_img.removeSignal(dataset[1]);
			
			sol[counter] = 2;
			this.half2_img.addSignal(dataset[2]);
			counter++;
			backtracking(level + 1);
			counter--;
			this.half2_img.removeSignal(dataset[2]);

			sol[counter] = 0;
			counter++;
			backtracking(level + 1);
			counter--;
		}
	}

}

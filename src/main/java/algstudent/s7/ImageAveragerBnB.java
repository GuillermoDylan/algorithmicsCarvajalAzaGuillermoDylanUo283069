package algstudent.s7;

import java.util.ArrayList;
import java.util.UUID;

import algstudent.s7.pyramid.utils.BranchAndBound;
import algstudent.s7.pyramid.utils.Node;

public class ImageAveragerBnB extends BranchAndBound {

	private Image half1_image;
	private Image half2_image;
	private Image avrg_image;
	private Image[] dataset;
	private int width;
	private int height;

	public ImageAveragerBnB(ImageAveragerBnBBoard board, int width, int height, Image[] dataset) {
		rootNode = board;
		this.width = width;
		this.height = height;
		this.dataset = dataset;
	}
}

class ImageAveragerBnBBoard extends Node {

	private int[] solution; // The solution array(?)
	private static int n; // Size of the images array

	public ImageAveragerBnBBoard(int[] solution, int size) {
		this.solution = solution;
		ImageAveragerBnBBoard.n = size;

	}

	@Override
	public void calculateHeuristicValue() {
		if (prune()) {
			heuristicValue = Integer.MAX_VALUE;
		} else {

		}
	}

	public void calculateHeuristicValueGradient() {
		if (prune()) {
			heuristicValue = Integer.MAX_VALUE;
		} else {

		}
	}

	private boolean prune() {
		
	}

	@Override
	public ArrayList<Node> expand() {
		ArrayList<Node> result = new ArrayList<Node>();
		
		return result;
	}

	@Override
	public boolean isSolution() {
		return heuristicValue == 0 && this.getDepth() == n;
	}

}
package algstudent.s7;

import java.util.ArrayList;
import java.util.UUID;

import algstudent.s7.util.BranchAndBound;
import algstudent.s7.util.ImageAverager;
import algstudent.s7.util.Node;

public class ImageAveragerBnB extends BranchAndBound {
	
	public ImageAveragerBnB(NodeAvg node) {
		rootNode = node;
	}
}

class NodeAvg extends Node {

	private ImageAverager manager;
	private boolean pruneState;

	public ImageAverager getManager() {
		this.manager.calculateImage(this.manager.sol);
		return this.manager;
	}

	public NodeAvg(ImageAverager imageAvg, boolean prune) {
		this.manager = imageAvg;
		this.solution = new int[manager.dataset.length];
		this.pruneState = prune;
		calculateHeuristicValue();
	}

	public NodeAvg(ImageAverager imageAvg, int depth, UUID parentID) {
		this.manager = imageAvg;
		this.depth = depth;
		this.parentID = parentID;
		this.ID = UUID.randomUUID();
		this.solution = manager.sol;
		calculateHeuristicValue();
	}

	@Override
	public void calculateHeuristicValue() {
		// If we are using the version without pruning
		if (!pruneState) {
			this.manager.calculateImage(solution);
			heuristicValue = this.manager.zncc() * -1;
		// Else, on the version with pruning
		} else {
			if (this.depth > 0) { // For all nodes except the root node
				if (prune()) { // If we have to prune, set to max value
					this.heuristicValue = Double.MAX_VALUE;
				} else { // Else calculate the heuristic value
					this.heuristicValue = this.manager.zncc() * -1;
				}
			} else {
				this.manager.calculateImage(solution);
				this.heuristicValue = this.manager.zncc() * -1;
			}
		}
	}

	public double calculateValueGradient() {
		if (depth != 0) {
			// Using the gradient, we calculate the zncc for every node (except the first
			// ones)
			double parentZNCC = this.manager.zncc() * - 1;
			this.manager.calculateImage(solution);
			return (this.manager.zncc() * - 1) - parentZNCC;
		} else {
			this.manager.calculateImage(solution);
			return (this.manager.zncc() * - 1);
		}

	}

	private boolean prune() {
		double gradient = calculateValueGradient();
		if (gradient >= 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public ArrayList<Node> expand() {
		ArrayList<Node> result = new ArrayList<Node>();
		int[] solCopy = solution.clone();
		for (int i = 0; i <= 2; i++) {
			solCopy[depth] = i;
			result.add(new NodeAvg(new ImageAverager(this.manager, solCopy), depth + 1, this.getID()));
			solCopy[depth] = 0;
		}
		return result;
	}

	@Override
	public boolean isSolution() {
		return depth == this.manager.dataset.length;
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < solution.length; i++) {
			s += String.format("Image %d to set %d\n", i, solution[i]);
		}
		return s;
	}

}
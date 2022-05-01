package algstudent.s7;

import java.util.ArrayList;
import java.util.UUID;

public class ImageAveragerBnB extends BranchAndBound {

	public ImageAveragerBnB(NodeAvg node) {
		rootNode = node;
	}
}

class NodeAvg extends Node {

	private ImageAverager manager;

	public ImageAverager getManager() {
		this.manager.calculateImage(this.manager.sol);
		return this.manager;
	}

	public NodeAvg(ImageAverager imageAvg) {
		this.manager = imageAvg;
		this.solution = new int[manager.dataset.length];
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
		this.manager.calculateImage(solution);
		heuristicValue = this.manager.zncc() * -1;
	}

	public void calculateValueGradient() {
		if (depth != 0) {
			// Using the gradient, we calculate the zncc for every node (except the first
			// ones)
			double parentZNCC = this.manager.zncc();
			this.manager.calculateImage(solution);
			this.heuristicValue = this.manager.zncc() - parentZNCC;
		} else {
			this.manager.calculateImage(solution);
			heuristicValue = this.manager.zncc();
		}

	}

//	private boolean prune() {
//		int numZeros = 0;
//		for (int i = 0; i < solution.length; i++) {
//			if(solution[i] == 0) {
//				numZeros++;
//			}
//		}
//		return true;
//	}

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
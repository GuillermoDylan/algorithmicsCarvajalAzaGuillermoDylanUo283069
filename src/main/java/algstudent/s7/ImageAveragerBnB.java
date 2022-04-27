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
		this.manager.calculateImage(solution);
		this.manager.storeBestSolution();
		return this.manager;
	}

	public NodeAvg(ImageAverager imageAvg) {
		this.manager = imageAvg;
		this.solution = new int[manager.dataset.length];
	}

	public NodeAvg(ImageAverager imageAvg, int depth, UUID parentID) {
		this.manager = imageAvg;
		this.depth = depth;
		this.parentID = parentID;
		this.ID = UUID.randomUUID();
		this.solution = manager.sol;
		calculateHeuristicValueGradient();
	}

	@Override
	public void calculateHeuristicValue() {
		this.manager.calculateImage(solution);
		heuristicValue = this.manager.zncc();
	}

	public void calculateHeuristicValueGradient() {
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

		for (int i = 0; i <= 2; i++) {
			this.manager.sol[depth] = i; // Why arent values just coppied???
			result.add(new NodeAvg(new ImageAverager(this.manager), depth + 1, this.getID()));
			this.manager.sol[depth] = 0;
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
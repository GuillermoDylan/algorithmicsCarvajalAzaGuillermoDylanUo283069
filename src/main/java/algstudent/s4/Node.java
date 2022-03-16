package algstudent.s4;

import java.util.List;

public class Node {

	private String name;
	private List<String> adjacent;
	private String color;

	public Node(String name, List<String> adjacent) {
		this.name = name;
		this.adjacent = adjacent;
	}

	public String getName() {
		return name;
	}

	public List<String> getAdjacent() {
		return adjacent;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}

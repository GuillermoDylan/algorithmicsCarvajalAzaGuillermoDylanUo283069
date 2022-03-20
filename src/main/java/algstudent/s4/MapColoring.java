package algstudent.s4;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MapColoring {

	private List<Node> countries;
	private String[] colors;
	private HashMap<String, Integer> coloredCountries;

	public MapColoring() {
		this.countries = FileUtil.loadBorders();
		this.colors = FileUtil.loadColors();
		this.coloredCountries = new HashMap<String, Integer>();
	}

	public void calculate() {

		int i = 0;
		int[] occupiedColors = new int[colors.length];
		while (i < countries.size()) {
			occupiedColors = new int[colors.length];

			if (countries.get(i).getColor() == null) {

				// Iterate through each countries adjacent countries
				for (String string : countries.get(i).getAdjacent()) {
					// If any of the adjacent countries is already colored, store its color
					if (coloredCountries.containsKey(string.strip())) {
						occupiedColors[coloredCountries.get(string.strip())] = 1;
					}
				}
				// Choose from the unoccupied colors
				chooseUnocupiedColor(occupiedColors, i);
			}
			i++;
		}
	}

	private void chooseUnocupiedColor(int[] occupiedColors, int i) {
		// Choose the unoccupied color
		for (int j = 0; j < occupiedColors.length; j++) {
			if (occupiedColors[j] != 1) {
				countries.get(i).setColor(colors[j]);
				coloredCountries.put(countries.get(i).getName(), j);
				break;
			}
		}
	}

	public void show() {
		for (Node n : countries) {
			System.out.printf("%s: %s \n", n.getName(), n.getColor());
		}
	}

	/**
	 * Small test to check if all the countries have a different color than their
	 * adjacent and if all the countries have a color
	 */
	public void testOk() {
		List<String> color = new LinkedList<String>();
		for (Node n : countries) {
			if(!color.contains(n.getColor())){
				color.add(n.getColor());
				System.out.println(n.getColor());
			}
			for (String s : n.getAdjacent()) {
				if (n.getColor().strip().equals(colors[coloredCountries.get(s.strip())])) {
					System.out.println("Not working");
				}
			}
			if (n.getColor() == null) {
				System.out.println("Not working " + n.getName());
			}
		}
	}

	public static void main(String[] args) {
		MapColoring m = new MapColoring();
		m.calculate();
		// m.show();
		m.testOk();
	}

}

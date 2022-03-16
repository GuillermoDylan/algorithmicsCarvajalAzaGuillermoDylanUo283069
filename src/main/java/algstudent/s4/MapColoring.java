package algstudent.s4;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MapColoring {

	private List<Node> countries;
	private String[] colors;
	private HashMap<String, Integer> coloredCountries;
	private List<String> unColoredCountries;

	public MapColoring() {
		this.countries = FileUtil.loadBorders();
		this.colors = FileUtil.loadColors();
		this.coloredCountries = new HashMap<String, Integer>();
		this.unColoredCountries = new LinkedList<String>();
	}

	public void calculate() {

		int i = 0;
		int[] occupiedColors = new int[colors.length];
		int numberOfOccupiedColors = 0;
		while (i < countries.size()) {
			occupiedColors = new int[colors.length];
			numberOfOccupiedColors = 0;

			if (countries.get(i).getColor() == null) {

				for (String string : countries.get(i).getAdjacent()) {
					if (coloredCountries.containsKey(string.strip())) {
						occupiedColors[coloredCountries.get(string.strip())] = 1;
						numberOfOccupiedColors++;
					}
				}

				// If the country has less than numColors adjacent country we leave it for later
				// TODO
				if (numberOfOccupiedColors < occupiedColors.length) {
					unColoredCountries.add(countries.get(i).getName());
				} else {

					// Choose the unoccupied color
					for (int j = 0; j < occupiedColors.length; j++) {
						if (occupiedColors[j] != 1) {
							countries.get(i).setColor(colors[j]);
							coloredCountries.put(countries.get(i).getName(), j);
							break;
						}
					}
				}
			}
			i++;
		}
	}

	public void show() {
		for (Node n : countries) {
			System.out.printf("%s: %s \n", n.getName(), n.getColor());
		}
	}

	public void testOk() {
		for (Node n : countries) {
			for (String s : n.getAdjacent()) {
				if (n.getColor().equals(colors[coloredCountries.get(s.strip())])) {
					System.out.println("Not working");
				}
			}
		}
	}

	public static void main(String[] args) {
		MapColoring m = new MapColoring();
		m.calculate();
		m.show();
		m.testOk();
	}

}

package algstudent.s4;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MapColoring {

	private List<Node> countries;
	private String[] colors;
	private HashMap<String, Integer> coloredCountries;

	/**
	 * Calculates the color of each country in order to not have adjacent countries
	 * of the same color, it uses the FileUtil to load the files
	 */
	public MapColoring() {
		this.countries = FileUtil.loadBorders();
		this.colors = FileUtil.loadColors();
		this.coloredCountries = new HashMap<String, Integer>();
	}

	/**
	 * Calculates the color of each country in order to not have two adjacent
	 * countries fof the same color
	 */
	public void calculate() {

		int i = 0;
		int[] occupiedColors = new int[colors.length]; // The occupied colors for each country in each iteration
		while (i < countries.size()) {
			occupiedColors = new int[colors.length];

			if (countries.get(i).getColor() == null) { // If the country does not have a color

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
		} // while loop
	}

	/**
	 * Chooses an unoccupied color for the country in position i Note that we do not
	 * need an exception or a condition for when we cannot assign the country a
	 * color, this is because if we where on that case, the algorithm would not be
	 * working
	 * 
	 * @param occupiedColors the array of colors that are occupied
	 * @param i              the position of the country on the countries list
	 */
	private void chooseUnocupiedColor(int[] occupiedColors, int i) {
		// Choose the unoccupied color
		for (int j = 0; j < occupiedColors.length; j++) {
			if (occupiedColors[j] != 1) { // If the color in position j is not occupied
				countries.get(i).setColor(colors[j]);
				coloredCountries.put(countries.get(i).getName(), j);
				break;
			}
		}
	}

	/**
	 * Shows the countries and their colors
	 */
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
			if (!color.contains(n.getColor())) {
				color.add(n.getColor());
				System.out.println(n.getColor());
			}
			for (String s : n.getAdjacent()) { // If 2 countries have the same color
				if (n.getColor().strip().equals(colors[coloredCountries.get(s.strip())])) {
					System.out.println("Not working");
				}
			}
			if (n.getColor() == null) { // If one country does not have color
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

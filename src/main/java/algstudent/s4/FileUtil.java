package algstudent.s4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class FileUtil {

	private final static String bordersFile = Paths.get("").toAbsolutePath().toString()
			+ "/src/main/java/algstudent/s4/borders.txt";
	private final static String colorsFile = Paths.get("").toAbsolutePath().toString()
			+ "/src/main/java/algstudent/s4/colors.txt";

	public static List<Node> loadBorders() {

		List<Node> nodes = new LinkedList<Node>();
		List<String> adjacency;

		try (BufferedReader br = new BufferedReader(new FileReader(new File(bordersFile)))) {
			String line;

			while ((line = br.readLine()) != null) {

				adjacency = new LinkedList<String>();
				String[] lines = line.split(":");
				String name = lines[0];
				String[] adjacent = lines[1].split(",");

				for (int i = 0; i < adjacent.length; i++) {
					if (!adjacent[i].strip().equals("NO")) {
						adjacency.add(adjacent[i]);
					}
				}
				nodes.add(new Node(name, adjacency));

			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nodes;
	}

	public static String[] loadColors() {

		List<String> colors = new LinkedList<String>();

		try (BufferedReader br = new BufferedReader(new FileReader(new File(colorsFile)))) {
			String line;

			while ((line = br.readLine()) != null) {
				colors.add(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] colorsArray = new String[colors.size()];
		int i = 0;
		for (String string : colors) {
			colorsArray[i] = string;
			i++;
		}

		return colorsArray;
	}

}

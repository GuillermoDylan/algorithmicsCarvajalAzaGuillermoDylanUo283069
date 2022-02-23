package algstudent.s2;

/* This program can be used to sort n elements with 
 * a "bad" algorithm (quadratic). 
 * It is the SELECTION */
public class Selection extends Vector {
	public Selection(int nElements) {
		super(nElements);
	}
	
	@Override
	public void sort() {
		for (int i = 0; i < elements.length - 1; i++) {
			int n = Vector.findMinimumPositions(elements, i);
			int aux = elements[i];
			elements[i] = elements[n];
			elements[n] = aux;
		}
	}  
	
	@Override
	public String getName() {
		return "Selection";
	} 
} 

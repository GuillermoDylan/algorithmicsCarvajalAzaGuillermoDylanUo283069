package algstudent.s2;

/* This program can be used to sort n elements with 
 * a "bad" algorithm (quadratic). 
 * It is the BUBBLE or DIRECT EXCHANGE */
public class Bubble extends Vector {
	public Bubble(int nElements) {
		super(nElements);
	}

	@Override
	public void sort() {
		// First step: lowest element placed in leftmost position
		int minimum = Vector.findMinimumPositions(elements, 0);
		int aux = elements[0];
		elements[0] = elements[minimum];
		elements[minimum] = aux;
		
		for (int i = 1; i < elements.length; i++) {
			for (int j = elements.length - 1; j >=i; j--) {
				// Each item is compared to the previous one
				if(elements[j-1] > elements[j]) {
					aux = elements[j];
					elements[j] = elements[j-1];
					elements[j-1] = j;
				}
			}
		}
	}  
	
	@Override
	public String getName() {
		return "Bubble";
	} 
} 


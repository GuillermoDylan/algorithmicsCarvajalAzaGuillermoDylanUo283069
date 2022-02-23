package algstudent.s2;

/* This program can be used to sort n elements with 
 * the best algorithm of this lab. It is the QUICKSORT */
public class QuicksortCentralElement extends Vector {

	public QuicksortCentralElement(int nElements) {
		super(nElements);
	}

	private void quickSort(int left, int right) {
		
		// Probably wrong
		int central = (right + 1) / 2;
		for (int k = 0; k < elements.length; k++) {
			
			int pivot = elements[central];
			
			elements[central] = elements[elements.length - 1];
			elements[elements.length - 1] = pivot;
			
			for (int i = 0; i < elements.length; i++) {
				for (int j = elements.length - 2; j >= i; j--) {
					if (elements[i] > pivot && elements[j] < pivot) {
						int aux = elements[i];
						elements[i] = elements[j];
						elements[j] = aux;
						break;
					}
				}
			}
			elements[elements.length - 1] = elements[central];
			elements[central] = pivot;
		}
	}

	@Override
	public void sort() {
		quickSort(0, elements.length - 1);
	}

	@Override
	public String getName() {
		return "Quicksort - Central element";
	}
}

package algstudent.s2;

/* This program can be used to sort n elements with 
 * the best algorithm of this lab. It is the QUICKSORT */
public class QuicksortCentralElement extends Vector {

	public QuicksortCentralElement(int nElements) {
		super(nElements);
	}

	private void quickSort(int left, int right) {
		int i = left;
		int j = right - 1;
		int pivot;
		
		if (left < right){ //if there is one element it is not necessary
			int toParticionate = (right + 1) / 2;; // Now we use the center as the pivot to particionate
			pivot = elements[toParticionate]; //choose the pivot
			interchange(toParticionate, right); //hide the pivot
				
			do {         
		    	while (elements[i] <= pivot && i < right) i++; //first element > pivot
		    	while (elements[j] >= pivot && j > left) j--; //first element < pivot
		        if (i < j) interchange(i, j);
		    } while (i < j);   //end while
				
			//we set the position of the pivot
			interchange(i, right);
			quickSort(left, i-1);
			quickSort(i+1, right);		
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

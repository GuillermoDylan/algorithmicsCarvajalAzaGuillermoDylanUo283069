package algstudent.s2;

/* This program can be used to sort n elements with 
 * the best algorithm of this lab. It is the QUICKSORT */
public class QuicksortCentralElement extends Vector {

	public QuicksortCentralElement(int nElements) {
		super(nElements);
	}

	int partition(int left, int right){
		int i = left, j = right;
		int tmp;
		int pivot = elements[(left + right) / 2];
		while (i <= j) {
			while (elements[i] < pivot)
				i++;
			while (elements[j] > pivot)
				j--;
			if (i <= j) {
				tmp = elements[i];
				elements[i] = elements[j];
				elements[j] = tmp;
				i++;
				j--;
			}
		}
		return i;
	}

	void quickSort(int left, int right) {
		int index = partition(left, right);
		if (left < index - 1)
			quickSort(left, index - 1);
		if (index < right)
			quickSort(index, right);
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

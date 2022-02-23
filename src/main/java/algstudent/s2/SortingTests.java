package algstudent.s2;

import algstudent.s2.Bubble;
import algstudent.s2.Insertion;
import algstudent.s2.QuicksortCentralElement;
import algstudent.s2.QuicksortFateful;
import algstudent.s2.QuicksortMedianOfThree;
import algstudent.s2.Selection;
import algstudent.s2.Vector;

public class SortingTests {

	/** This program is used to check that sorting algorithms are working
	 * */
	public static void main(String arg []) {
		int n = Integer.parseInt(arg[0]);  //problem size
		
//		testSortingAlgorithm(new Insertion(n));
//		
		testSortingAlgorithm(new Selection(n));
		
//		testSortingAlgorithm(new Bubble(n));
		
//		testSortingAlgorithm(new QuicksortFateful(n));
//		
//		testSortingAlgorithm(new QuicksortCentralElement(n));
//		
//		testSortingAlgorithm(new QuicksortMedianOfThree(n));
	}
	
	public static void testSortingAlgorithm(Vector v) {
		System.out.println("\n\nSorting test: "+ v.getName());
		
		System.out.println("\nSorting an already-sorted vector");
		v.directlySorted();
		System.out.println("Vector to be sorted:");
		v.write(System.out);	
		v.sort();
		System.out.println("Sorted vector");
		v.write(System.out);

		System.out.println("\nSorting an inverse vector");
		v.inverselySorted();
		System.out.println("Vector to be sorted:");
		v.write(System.out);	
		v.sort();
		System.out.println("Sorted vector");
		v.write(System.out);

		System.out.println("\nSorting a random vector");
		v.randomlySorted();
		System.out.println("Vector to be sorted:");
		v.write(System.out);	
		v.sort();
		System.out.println("Sorted vector");
		v.write(System.out);
	}
	
}

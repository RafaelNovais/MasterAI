import java.util.ArrayList;
import java.util.Comparator;

public class New_Sort_Integer_Sequential {

	public static void main(String[] args) {
		Integer n = 100000;

		Comparator<Integer> comparator = Integer::compare;

		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < n; i++)
			list.add((int) Math.ceil(Math.random() * n));

		// Call the sorting method
		sort(list, 0, n - 1, comparator);

		// Display the sorted list
		for (int i = 0; i < n; i++) {
			System.out.print(list.get(i) + ", ");
			if (i > 0 && i % 20 == 0)
				System.out.println();
		}
	}

	// Recursive sorting method with multithreading
	static void sort(ArrayList<Integer> list, Integer left, Integer right, Comparator<Integer> comparator) {
		// Base case: If the sublist is already sorted or empty
		if (right <= left)
			return;

		// Partition the list and get the pivot
		Integer s = part(list, left, right, comparator);

		// Create two threads for the left and right Partition
		Thread leftThread = new Thread(() -> sort(list, left, s - 1, comparator));
		Thread rightThread = new Thread(() -> sort(list, s + 1, right, comparator));

		// Start the threads
		leftThread.start();
		rightThread.start();

		try {
			// Wait for the threads to complete
			leftThread.join();
			rightThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Partitioning method
	static Integer part(ArrayList<Integer> list, Integer left, Integer right, Comparator<Integer> comparator) {
		assert (left < right);

		Integer i = left - 1, j = right;

		for (;;) {
			while (comparator.compare(list.get(++i), list.get(right)) < 0)
				;

			while (comparator.compare(list.get(right), list.get(--j)) < 0)
				if (j.equals(left))
					break;

			if (i >= j)
				break;

			swap(list, i, j);
		}

		swap(list, i, right);

		return i;
	}

	// Swap elements in the list
	static void swap(ArrayList<Integer> list, Integer i, Integer j) {
		Integer h = list.get(i);
		list.set(i, list.get(j));
		list.set(j, h);
	}
}

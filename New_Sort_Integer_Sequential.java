import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class New_Sort_Integer_Sequential {
	/*Q3*/

	public static void main(String[] args) {

		Integer n = 100000;


		Comparator<Integer> comparator = Integer::compare;


		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < n; i++)
			list.add((int) Math.ceil(Math.random() * n));


		parallelSort(list, comparator);


		for (int i = 0; i < n; i++) {
			System.out.print(list.get(i) + ", ");
			if (i > 0 && i % 20 == 0)
				System.out.println();
		}
	}

	static void parallelSort(ArrayList<Integer> list, Comparator<Integer> comparator) {
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(new ParallelSortTask(list, 0, list.size() - 1, comparator));
		pool.shutdown();
	}

	static class ParallelSortTask extends RecursiveAction {
		private static final int THRESHOLD = 1000;
		private final ArrayList<Integer> list;
		private final int left;
		private final int right;
		private final Comparator<Integer> comparator;

		public ParallelSortTask(ArrayList<Integer> list, int left, int right, Comparator<Integer> comparator) {
			this.list = list;
			this.left = left;
			this.right = right;
			this.comparator = comparator;
		}

		@Override
		protected void compute() {
			if (right - left <= THRESHOLD) {

				New_Sort_Integer_Sequential.sort(list, left, right, comparator);
			} else {

				int s = New_Sort_Integer_Sequential.part(list, left, right, comparator);
				ParallelSortTask leftTask = new ParallelSortTask(list, left, s - 1, comparator);
				ParallelSortTask rightTask = new ParallelSortTask(list, s + 1, right, comparator);

				invokeAll(leftTask, rightTask);
			}
		}
	}

	static void sort(ArrayList<Integer> list, Integer left, Integer right, Comparator<Integer> comparator) {
		if (right <= left)
			return;

		Integer s = part(list, left, right, comparator);

		sort(list, left, s - 1, comparator);

		sort(list, s + 1, right, comparator);
	}

	static Integer part(ArrayList<Integer> list, Integer left, Integer right, Comparator<Integer> comparator) {
		assert (left < right);

		Integer i = left - 1, j = right;

		for (;;) {
			while (comparator.compare(list.get(++i), list.get(right)) < 0);

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

	static void swap(ArrayList<Integer> list, Integer i, Integer j) {
		Integer h = list.get(i);
		list.set(i, list.get(j));
		list.set(j, h);
	}
}

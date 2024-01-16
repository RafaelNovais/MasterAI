import java.util.ArrayList;
import java.util.Comparator;

class SortThread implements Runnable {

    private final ArrayList<Integer> list;
    private final Integer left;
    private final Integer right;
    private final Comparator<Integer> comparator;

    public SortThread(ArrayList<Integer> list, Integer left, Integer right, Comparator<Integer> comparator) {
        this.list = list;
        this.left = left;
        this.right = right;
        this.comparator = comparator;
    }

    @Override
    public void run() {
        New_Sort_Integer_Sequential2.sort(list, left, right, comparator);
    }
}

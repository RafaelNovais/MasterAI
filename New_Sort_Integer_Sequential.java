import java.util.ArrayList;
import java.util.Comparator;

public class New_Sort_Integer_Sequential {

    public static void main(String[] args) {
        Integer n = 100000;

        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++)
            list.add((int) Math.ceil(Math.random() * n));

        SortThread sortThread = new SortThread(list, 0, n - 1, comparator);
        Thread thread = new Thread(sortThread, "SortThread"); //jconsole
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < n; i++) {
            System.out.print(list.get(i) + ", ");
            if (i > 0 && i % 20 == 0)
                System.out.println();
        }
    }

    //synchronized
    static void sort(ArrayList<Integer> list, Integer left, Integer right, Comparator<Integer> comparator) {
        if (right <= left)
            return;

        Integer s = part(list, left, right, comparator);

        synchronized (list) {
            sort(list, left, s - 1, comparator); //x*y
        }

        synchronized (list) {
            sort(list, s + 1, right, comparator); //t+u
        }
    }
//x*y/t+u
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
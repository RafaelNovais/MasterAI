import java.util.ArrayList;
import java.util.Comparator;

public class Q2 {

    public static void main(String[] args) {

        // We test the sorting procedure with a list of random integer objects
        Integer n = 10;


        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++)
            list.add((int) Math.ceil(Math.random() * n));

        sort(list, 0, n - 1, comparator);

        for (int i = 0; i < n; i++) {
            System.out.print(list.get(i) + ", ");
            if (i > 0 && i % 20 == 0)
                System.out.println();
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

    static void swap(ArrayList<Integer> list, Integer i, Integer j) {
        Integer h = list.get(i);
        list.set(i, list.get(j));
        list.set(j, h);
    }
}
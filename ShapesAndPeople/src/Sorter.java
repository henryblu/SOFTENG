import java.util.List;

public class Sorter {
    public static <T> void sort(List<T> items, Sortable<T> cmp) {
        for (int i = 1; i < items.size(); i++) {
            T key = items.get(i);
            int j = i - 1;

            // shift while left item > key
            while (j >= 0 && cmp.isBigger(items.get(j), key)) {
                items.set(j + 1, items.get(j));
                j--;
            }
            items.set(j + 1, key);
        }
    }
}

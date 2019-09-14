import java.util.ArrayList;
import java.util.List;

public class Sorter {

    public static <T extends Comparable<T>> List<T> mergeSortAsc (List<T> list) {
        int len = list.size();
        if (len <= 1)
            return list;
        List<T> left = mergeSortAsc(list.subList(0, len / 2));
        List<T> right = mergeSortAsc(list.subList(len / 2, len));
        List<T> combined = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;
        while (leftIndex < left.size() && rightIndex < right.size()) {
            T leftElement = left.get(leftIndex);
            T rightElement = right.get(rightIndex);
            if (leftElement.compareTo(rightElement) < 0) {
                combined.add(leftElement);
                leftIndex++;
            } else {
                combined.add(rightElement);
                rightIndex++;
            }
        }
        combined.addAll(left.subList(leftIndex, left.size()));
        combined.addAll(right.subList(rightIndex, right.size()));
        return combined;
    }

    public static <T extends Comparable<T>> List<T> mergeSortDesc (List<T> list) {
        int len = list.size();
        if (len <= 1)
            return list;
        List<T> left = mergeSortDesc(list.subList(0, len / 2));
        List<T> right = mergeSortDesc(list.subList(len / 2, len));
        List<T> combined = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;
        while (leftIndex < left.size() && rightIndex < right.size()) {
            T leftElement = left.get(leftIndex);
            T rightElement = right.get(rightIndex);
            if (leftElement.compareTo(rightElement) > 0) {
                combined.add(leftElement);
                leftIndex++;
            } else {
                combined.add(rightElement);
                rightIndex++;
            }
        }
        combined.addAll(left.subList(leftIndex, left.size()));
        combined.addAll(right.subList(rightIndex, right.size()));
        return combined;
    }
}
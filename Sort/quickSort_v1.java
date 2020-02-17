import java.util.*;

public class Sort {
    public static void sort(List<Integer> items)
    {
        if(items == null)
            return;
        else if(items.size() == 1)
            return;

        List<Integer> smaller = new ArrayList<>();
        List<Integer> same = new ArrayList<>();
        List<Integer> larger = new ArrayList<>();

        Integer chosenItem = items.get(items.size() / 2);
        for(Integer i : items)
            if(i < chosenItem)
                smaller.add(i);
            else if(i == chosenItem)
                same.add(i);
            else
                larger.add(i);

        sort(smaller);
        sort(same);
        sort(larger);

        items.clear();
        items.addAll(smaller);
        items.addAll(same);
        items.addAll(larger);
    }
}

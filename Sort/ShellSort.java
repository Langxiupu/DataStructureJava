public class ShellSort {
    public static <T extends Comparable<? super T>>
    void shellSort(T[] a)
    {
        for(int gap = a.length / 2; gap > 0; gap /= 2)
            for(int i = gap; i < a.length; ++i)
            {
                T tmp = a[i];
                int j;
                for(j = i; j >= gap && tmp.compareTo(a[j - gap]) < 0;
                j -= gap)
                    a[j] = a[j - gap];
                a[j] = tmp;
            }
    }
}

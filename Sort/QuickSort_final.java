public class quickSort {
    public static <T extends Comparable<? super T>>
    void quickSort(T[] a)
    {
        quickSort(a, 0, a.length - 1);
    }

    private static <T extends Comparable<? super T>>
    T median(T[] a, int left, int right)
    {
        int center = (left + right) / 2;

        if(a[left].compareTo(a[center]) > 0)
            swapReferences(a, left, center);
        if(a[left].compareTo(a[right]) > 0)
            swapReferences(a, left, right);
        if(a[center].compareTo(a[right]) > 0)
            swapReferences(a, center, right);

        swapReferences(a, center, right - 1);
        return a[right - 1];
    }

    private static final <T extends Comparable<? super T>>
    void swapReferences(T[] a, int i, int j)
    {
        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static <T extends Comparable<? super T>>
    void quickSort(T[] a, int left, int right)
    {
        if(a.length >= CUTOFF)
        {
            T pivot = median(a, left, right);

            int i = left, j = right - 1;
            for(; ;)
            {
                while(a[++i].compareTo(pivot) < 0) {}
                while(a[--j].compareTo(pivot) > 0) {}

                if(i < j)
                    swapReferences(a, i, j);
                else
                    break;
            }

            swapReferences(a, i, right - 1);
            quickSort(a, left, i - 1);
            quickSort(a,i + 1, right);
        }
        else
        {
            // use insertionSort
            for(int i = left; i <= right; ++i)
            {
                T tmp = a[i];
                int j;
                for(j = i; j >= left + 1
                        && tmp.compareTo(a[j - 1]) < 0; --j)
                    a[j] = a[j - 1];
                a[j] = tmp;
            }
        }
    }

    private static final int CUTOFF = 10;
}

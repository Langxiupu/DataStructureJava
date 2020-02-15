public class HeapSort {
    public static <T extends Comparable<? super T>>
    void heapSort(T[] a)
    {
        for(int i = a.length / 2 - 1; i >= 0; --i)
            percDown(a, i, a.length);
        for(int i = a.length - 1; i > 0; i--)
        {
            T tmp = a[0];
            a[0] = a[i];
            a[i] = tmp;
            percDown(a, 0, i);
        }
    }

    private static <T extends Comparable<? super T>>
    void percDown(T[] a, int i, int n)
    {
        T tmp = a[i];

        while(leftChild(i) < n)
        {
            int child = leftChild(i);
            if(child < n - 1
                    && a[child].compareTo(a[child + 1]) < 0)
                child++;
            if(tmp.compareTo(a[child]) < 0)
            {
                a[i] = a[child];
                i = child;
            }
            else
                break;
        }
        a[i] = tmp;
    }

    private static int leftChild(int i)
    {
        return 2 * i + 1;
    }
}

public class MergeSort {
    public static <T extends Comparable<? super T>>
    void mergeSort(T[] a)
    {
        T[] tmpArray = (T[]) new Comparable[a.length];
        mergeSort(a, tmpArray, 0, a.length - 1);
    }

    private static <T extends Comparable<? super T>>
    void mergeSort(T[] a, T[] tmpArray, int left, int right)
    {
        if(left < right)
        {
            int center = (left + right) / 2;
            mergeSort(a, tmpArray, left, center);
            mergeSort(a, tmpArray, center + 1, right);
            merge(a, tmpArray, left, center + 1, right);
        }
    }

    private static <T extends Comparable<? super T>>
    void merge(T[] a, T[] tmpArray, int left, int center,
               int right)
    {
        int leftEnd = center - 1;
        int tmpPos = left;
        int numElements = right - left + 1;

        while(left <= leftEnd && center <= right)
            if(a[left].compareTo(a[center]) <= 0)
                tmpArray[tmpPos++] = a[left++];
            else
                tmpArray[tmpPos++] = a[center++];

            while(left <= leftEnd)
                tmpArray[tmpPos++] = a[left++];

            while(center <= right)
                tmpArray[tmpPos++] = a[center++];

            for(int i = 0; i < numElements; ++i, right--)
                a[right] = tmpArray[right];
    }
}

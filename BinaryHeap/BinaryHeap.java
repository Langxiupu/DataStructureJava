import java.nio.BufferUnderflowException;

public class BinaryHeap<T extends Comparable<? super T>>
{
    public BinaryHeap()
    {
        this(DEFAULT_SIZE);
    }

    public BinaryHeap(int size)
    {
        currentSize = 0;
        array = (T[]) new Comparable[size];
    }

    public BinaryHeap(T[] items)
    {
       currentSize = items.length;
       array = (T[]) new Comparable[(currentSize + 2) * 11 / 10];

       int i = 1;
       for (T ele : items)
           array[i++] = ele;

       buildHeap();

    }

    public void insert(T x)
    {
        if(currentSize == array.length - 1)
            enlargeArray(array.length * 2);

        int hole = ++currentSize;
        for (array[0] = x; x.compareTo(array[hole / 2]) < 0; hole /= 2)
            array[hole] = array[hole / 2];
        array[hole] = array[0];
    }

    public T deleteMin()
    {
        if (isEmpty())
            throw new BufferUnderflowException();

        T minItem = findMin();
        array[1] = array[currentSize--];
        percolateDown(1);

        return minItem;
    }

    public boolean isEmpty()
    {
        return currentSize == 0;
    }

    public T findMin()
    {
        return array[1];
    }

    public void makeEmpty()
    {
        currentSize = 0;
    }

    private static final int DEFAULT_SIZE = 10;

    private T[] array;
    private int currentSize;

    private void enlargeArray(int newSize)
    {
        T[] oldArray = array;
        array = (T[]) new Comparable[newSize];

        for(int i = 1; i < currentSize; ++i)
            array[i] = oldArray[i];
    }

    private void percolateDown(int hole)
    {
        int child;
        T tmp = array[hole];

        for(; hole * 2 <= currentSize; hole = child)
        {
            child = hole * 2;
            if (child != currentSize &&
            array[child + 1].compareTo(array[child]) < 0)
                child ++;
            if(array[child].compareTo(array[hole]) < 0)
                array[hole] = array[child];
            else
                break;
        }

        array[hole] = tmp;
    }

    private void buildHeap()
    {
        for (int i = currentSize / 2; i > 0; --i)
            percolateDown(i);
    }
}

import java.util.*;

public class CuckooHashTable<T>
{
    public CuckooHashTable(HashFamily<? super T> hf)
    {
        this(hf, DEFAULT_TABLE_SIZE);
    }

    public CuckooHashTable(HashFamily<? super T> hf, int size)
    {
        allocateArray(size);
        doClear();
        hashFunctions = hf;
        numHashFunctions = hf.getNumberOfFunctions();
    }

    public void makeEmpty()
    {
        doClear();
    }

    public boolean contains(T x)
    {
        return findPos(x) != -1;
    }

    public boolean remove(T x)
    {
        int pos = findPos(x);

        if (pos != -1)
        {
            array[pos] = null;
            currentSize--;
        }

        return pos != -1;
    }

    public boolean insert(T x)
    {
        if (contains(x))
            return false;

        if (currentSize >= array.length * MAX_LOAD)
            expand();

        return insertHelper(x);
    }

    private static final double MAX_LOAD = 0.4;
    private static final int ALLOWED_REHASHES = 1;
    private static final int DEFAULT_TABLE_SIZE = 101;

    private final HashFamily<? super T> hashFunctions;
    private final int numHashFunctions;
    private T[] array;
    private int currentSize;
    private int rehashes = 0;
    private Random r = new Random();

    private void doClear()
    {
        currentSize = 0;
        for (int i = 0; i < array.length; ++i)
            array[i] = null;
    }

    private void allocateArray(int size)
    {
        array = (T[]) new Object[nextPrime(size)];
    }

    private boolean isPrime(int x)
    {
        for (int i = 2; i <= (int)Math.sqrt(x); ++i)
            if (x % i == 0)
                return false;
        return true;
    }

    private int nextPrime(int x)
    {
        while (!isPrime(x))
            x++;
        return x;
    }

    private int myHash(T x, int which)
    {
        int hashVal = hashFunctions.hash(x, which);

        hashVal %= array.length;
        if (hashVal < 0)
            hashVal += array.length;

        return hashVal;
    }

    private int findPos(T x)
    {
        for (int i = 0; i < numHashFunctions; ++i)
        {
            int hashVal = myHash(x, i);
            if (array[hashVal] != null && array[hashVal].equals(x))
                return hashVal;
        }

        return -1;
    }

    private void expand()
    {
        rehash((int) (array.length / MAX_LOAD));
    }

    private void rehash()
    {
        rehash((int) (array.length / MAX_LOAD));
    }

    private void rehash(int newLength)
    {
        T[] oldArray = array;
        allocateArray(newLength);
        currentSize = 0;

        for (T e : oldArray)
            if (e != null)
                insert(e);
    }

    private boolean insertHelper(T x)
    {
        final int COUNT_LIMIT = 100;

        while(true)
        {
            int lastPos = -1;
            int pos;

            for(int count = 0; count < COUNT_LIMIT; ++count) {
                for (int i = 0; i < numHashFunctions; ++i) {
                    pos = myHash(x, i);

                    if (array[pos] == null) {
                        array[pos] = x;
                        currentSize++;
                        return true;
                    }
                }

                int i = 0;
                do {
                        pos = myHash(x, r.nextInt(numHashFunctions));
                    }while(pos == lastPos && i++ < 5);
                    T temp = array[lastPos = pos];
                    array[pos] = x;
                    x = temp;
                }
            if (++rehashes > ALLOWED_REHASHES)
            {
                expand();
                rehashes = 0;
            }
            else
                rehash();
        }
    }
}



class UnderFlowException extends Exception{}

public class BinomialQueue<T extends Comparable<? super T>>
{
    public BinomialQueue()
    {
        theTrees = new Node[DEFAULT_TRESS];
        makeEmpty();
    }

    public BinomialQueue(T x)
    {
        currentSize = 1;
        makeEmpty();
        theTrees[0] = new Node<>(x);
    }

    public void makeEmpty()
    {
        currentSize = 0;
        for(int i = 0; i < theTrees.length; ++i)
            theTrees[i] = null;
    }

    public boolean isEmpty()
    {
        return currentSize == 0;
    }

    public void merge(BinomialQueue<T> rhs)
    {
        if(this == rhs)
            return;

        currentSize += rhs.currentSize;

        if(currentSize > capacity())
        {
            int maxLength = Math.max(theTrees.length, rhs.theTrees.length);
            expandTheTrees(maxLength + 1);
        }

        Node<T> carry = null;
        for(int i = 0, j = 1; j <= currentSize; i++, j *= 2)
        {
            Node<T> t1 = theTrees[i];
            Node<T> t2 = i < rhs.theTrees.length ? rhs.theTrees[i] : null;

            int whichCase = t1 == null ? 0 : 1;
            whichCase += t2 == null ? 0 : 2;
            whichCase += carry == null ? 0 : 4;

            switch(whichCase)
            {
                case 0:  //no trees
                case 1:  //only this
                    break;
                case 2:
                    theTrees[i] = t2;
                    rhs.theTrees[i] = null;
                    break;
                case 3:
                    carry = combineTrees(t1, t2);
                    theTrees[i] = null;
                    rhs.theTrees[i] = null;
                    break;
                case 4:
                    theTrees[i] = carry;
                    carry = null;
                    break;
                case 5:
                    carry = combineTrees(carry, t1);
                    theTrees[i] = null;
                    break;
                case 6:
                    carry = combineTrees(carry, t2);
                    rhs.theTrees[i] = null;
                    break;
                case 7:
                    theTrees[i] = carry;
                    carry = combineTrees(t1, t2);
                    rhs.theTrees[i] = null;
                    break;
            }
        }

        rhs.currentSize = 0;
    }

    public void insert(T x)
    {
        merge(new BinomialQueue<>(x));
    }

    public T deleteMin() throws UnderFlowException
    {
        if(isEmpty())
            throw new UnderFlowException();

        int minIndex = findMinIndex();
        T minItem = theTrees[minIndex].element;

        Node<T> deletedTree = theTrees[minIndex].left;

        BinomialQueue<T> deletedQueue = new BinomialQueue<>();
        deletedQueue.expandTheTrees(minIndex);

        deletedQueue.currentSize = (1 << minIndex) - 1;

        for(int j = minIndex - 1; j >= 0; j--)
        {
            deletedQueue.theTrees[j] = deletedTree;
            deletedTree = deletedTree.nextSibling;
            deletedQueue.theTrees[j].nextSibling = null;
        }

        theTrees[minIndex] = null;
        currentSize -= deletedQueue.currentSize - 1;
        merge(deletedQueue);

        return minItem;
    }

    public T findMin() throws UnderFlowException
    {
        if(isEmpty())
            throw new UnderFlowException();

        return theTrees[findMinIndex()].element;
    }

    private static class Node<T>
    {
        Node(T x)
        {
            this(x, null, null);
        }

        Node(T theElement, Node<T> lt, Node<T> nt)
        {
            element = theElement;
            left = lt;
            nextSibling = nt;
        }

        T element;
        Node<T> left;
        Node<T> nextSibling;
    }

    private static final int DEFAULT_TRESS = 1;

    private int currentSize;
    private Node<T>[] theTrees;

    private void expandTheTrees(int newLength)
    {
        Node<T>[] oldTrees = theTrees;
        theTrees = new Node[newLength];
        for(int i = 0; i < Math.min(oldTrees.length, newLength))
            theTrees[i] = oldTrees[i];
    }

    private int capacity()
    {
        return (1 << theTrees.length) - 1;
    }

    private Node<T> combineTrees(Node<T> t1, Node<T> t2)
    {
        if(t1.element.compareTo(t2.element) > 0)
            return combineTrees(t2, t1);

        t2.nextSibling = t1.left;
        t1.left = t2;

        return t1;
    }

    private int findMinIndex()
    {
        int minIndex = 0;
        while(theTrees[minIndex] == null)
            minIndex++;

        for(int i = minIndex + 1; i < theTrees.length; ++i)
        {
            if(theTrees[i] != null
            && theTrees[i].element.compareTo(theTrees[minIndex].element) < 0)
                minIndex = i;
        }
        return minIndex;
    }
}

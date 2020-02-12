import java.nio.BufferUnderflowException;

public class LeftistHeap<T extends Comparable<? super T>>
{
    public LeftistHeap()
    {
        root = null;
    }

    public void merge(LeftistHeap<T> rhs)
    {
        if(this == rhs)
            return;

        root = merge(root, rhs.root);
        rhs.root = null;
    }

    public void insert(T x)
    {
        root = merge(root, new Node<>(x));
    }

    public T deleteMin()
    {
        if(isEmpty())
            throw new BufferUnderflowException();

        T theItem = root.element;
        root = merge(root.left, root.right);

        return theItem;
    }

    public T findMin()
    {
        return root.element;
    }

    public boolean isEmpty()
    {
        return root == null;
    }

    private static class Node<T>
    {
        public Node(T theElement)
        {
            this(theElement, null, null);
        }

        public Node(T theElement, Node<T> lt, Node<T> rt)
        {
            element = theElement;
            left = lt;
            right = rt;
            npl = 0;
        }

        T element;
        Node<T> left;
        Node<T> right;
        int npl;
    }

    private Node<T> root;

    private Node<T> merge(Node<T> h1, Node<T> h2)
    {
        if(h1 == null)
            return  h2;
        if(h2 == null)
            return h1;

        if(h1.element.compareTo(h2.element) < 0)
            return merge1(h1, h2);
        else
            return merge1(h2, h1);
    }

    private Node<T> merge1(Node<T> h1, Node<T> h2)
    {
        if(h1.left == null)
            h1.left = h2;
        else
        {
            h1.right = merge(h1.right, h2);
            if(h1.left.npl < h1.right.npl)
                swapChildren(h1);
            h1.npl = h1.right.npl + 1;
        }

        return h1;
    }

    private void swapChildren(Node<T> r)
    {
        Node<T> tmp = r.left;
        r.left = r.right;
        r.right = tmp;
    }
}

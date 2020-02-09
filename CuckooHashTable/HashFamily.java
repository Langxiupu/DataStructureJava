public interface HashFamily<T>
{
    int hash(T x, int which);
    int getNumberOfFunctions();
    void generateNewFunctions();
}

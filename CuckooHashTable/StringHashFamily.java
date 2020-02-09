public class StringHashFamily implements HashFamily<String>
{
    private final int[] MULTIPLIER;
    private final java.util.Random r =
            new java.util.Random();

    public StringHashFamily(int d)
    {
        MULTIPLIER = new int[d];
        generateNewFunctions();
    }

    public int getNumberOfFunctions()
    {
        return MULTIPLIER.length;
    }

    public void generateNewFunctions()
    {
        for(int i = 0; i < MULTIPLIER.length; ++i)
            MULTIPLIER[i] = r.nextInt();
    }

    public int hash(String x, int which)
    {
        final int multiplier = MULTIPLIER[which];
        int hashVal = 0;

        for (int i = 0; i < x.length(); ++i)
            hashVal = multiplier * hashVal + x.charAt(i);

        return hashVal;
    }
}

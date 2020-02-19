import java.util.*;

public class Solution {
    public static void radixSort(String[] a, int stringLen)
    {
        final int BUCKETS = 256;
        List<String>[] buckets = new ArrayList[BUCKETS];
        for(int i = 0; i < BUCKETS; ++i)
            buckets[i] = new ArrayList<>();

        for(int i = stringLen - 1; i >= 0; --i)
        {
            for(String s : a)
                buckets[s.charAt(i)].add(s);

            int cursor = 0;
            for(int j = 0; j < BUCKETS; ++j)
            {
                for(String str : buckets[j])
                    a[cursor++] = str;

                buckets[j].clear();
            }
        }
        }
    }



/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842,
 * Algorithm Name: Lazy Algorithm
 **************************************************************************** */

public class QuickFindUF {
    int[] id;

    public QuickFindUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    public int[] union(int p, int q) {
        if (!connected(p, q)) {
            int idp = id[p];
            int idq = id[q];
            for (int i = 0; i < id.length; i++) {
                if (id[i] == idp)
                    id[i] = idq;
            }
        }
        return id;
    }
}

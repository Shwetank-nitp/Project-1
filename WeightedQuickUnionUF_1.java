
public class WeightedQuickUnionUF_1 {
    int[] id;
    int[] sz;

    public int find(int i) {
        int max = i;
        while (i != root(i)) {
            i = id[i];
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    public WeightedQuickUnionUF_1(int N) {
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    private int root(int x) {
        // While-loop iterates till it finds its root by simple....
        while (x != id[x]) {
            id[x] = id[id[x]];  // path-compression.
            x = id[x];// 'x' is replaced by its id at each successful iteration.
        }
        return x;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        if (connected(p, q)) return;
        int rootp = root(p);
        int rootq = root(q);
        if (sz[rootp] > sz[rootq]) {
            id[rootq] = rootp;
            sz[rootp] += sz[rootq];
        }
        else {
            id[rootp] = rootq;
            sz[rootq] += sz[rootp];
        }
    }
}

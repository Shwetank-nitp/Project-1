import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] lattic;
    private boolean[][] latticStat;
    private int siteOpen = 0;
    private WeightedQuickUnionUF wq;
    private int virtualTop;
    private int virtualBottom;
    private int gridSize;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        gridSize = n;
        virtualBottom = n * n + 1;
        virtualTop = 0;
        wq = new WeightedQuickUnionUF(n * n + 2);
        lattic = new int[n][n];
        latticStat = new boolean[n][n];
        int x = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                lattic[i][j] = x;
                latticStat[i][j] = false;
                x++;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize)
            throw new IllegalArgumentException("Error");
        if (isOpen(row, col)) return;
        latticStat[row - 1][col - 1] = true;
        siteOpen++;
        // virtualTop union with open first row blocks
        if (row == 1) {
            wq.union(virtualTop, lattic[row - 1][col - 1]);
        }
        if (row == gridSize) {
            wq.union(virtualBottom, lattic[row - 1][col - 1]);
        }
        // Connect the newly opened site to adjacent open sites
        int block1 = lattic[row - 1][col - 1];
        // Check the site above
        if (row > 1 && isOpen(row - 1, col)) {
            int block2 = lattic[row - 2][col - 1];
            wq.union(block1, block2);
        }
        // Check the site below
        if (row < gridSize && isOpen(row + 1, col)) {
            int block2 = lattic[row][col - 1];
            wq.union(block1, block2);
        }
        // Check the site to the left
        if (col > 1 && isOpen(row, col - 1)) {
            int block2 = lattic[row - 1][col - 2];
            wq.union(block1, block2);
        }
        // Check the site to the right
        if (col < gridSize && isOpen(row, col + 1)) {
            int block2 = lattic[row - 1][col];
            wq.union(block1, block2);
        }
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize)
            throw new IllegalArgumentException("Error");
        return latticStat[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize)
            throw new IllegalArgumentException("Error");
        int block = lattic[row - 1][col - 1];
        return wq.find(block) == wq.find(virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return siteOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return wq.find(virtualTop) == wq.find(virtualBottom);
    }

    // test client
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Percolation percolation = new Percolation(n);
        Stopwatch stopwatch = new Stopwatch();
        while (!percolation.percolates()) {
            int row = StdRandom.uniformInt(1, n + 1);
            int col = StdRandom.uniformInt(1, n + 1);
            percolation.open(row, col);
        }

        double time = stopwatch.elapsedTime();
        double th = (double) percolation.numberOfOpenSites() / (n * n);
        if (percolation.percolates()) {
            System.out.println("Lattice is conducting");
            System.out.println(
                    "Total number of open lattice is : " + percolation.numberOfOpenSites());
            System.out.println("Percolation threshold is : " + th);
            StdOut.println("Time taken : " + time);
        }
    }
}

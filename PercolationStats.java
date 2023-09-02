import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] t;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Both 'n' and 'trials' must be greater than zero.");
        }
        t = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                percolation.open(row, col);
            }
            double th = (double) percolation.numberOfOpenSites() / (n * n);
            t[i] = th;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(t);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(t);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double stddev = stddev();
        double rootT = Math.sqrt(t.length);
        return mean - (1.96 * stddev) / rootT;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double stddev = stddev();
        double rootT = Math.sqrt(t.length);
        return mean + (1.96 * stddev) / rootT;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats pStats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + pStats.mean());
        StdOut.println("stddv                   = " + pStats.stddev());
        StdOut.println("95% confidence interval = [" + pStats.confidenceLo() + ", "
                               + pStats.confidenceHi() + "]");
    }
}

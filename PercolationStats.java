package percolation;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int numberOfTrials;
    private Percolation percolation;
    private double[] fractionNumberOfOpenSites;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if(n <= 0 || trials <= 0)
            throw new IllegalArgumentException("n/trials cannot be less or equal to 0");

        numberOfTrials = trials;
        fractionNumberOfOpenSites = new double[trials];
        int randomRowNumber;
        int randomColNumber;

        
        for(int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            while(!percolation.percolates()) {
                randomRowNumber = StdRandom.uniformInt(1, n + 1);
                randomColNumber = StdRandom.uniformInt(1, n + 1);

                percolation.open(randomRowNumber, randomColNumber);
            }
            fractionNumberOfOpenSites[i] = percolation.numberOfOpenSites() / ((n * n) * 1.0);
            percolation = null;
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractionNumberOfOpenSites);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractionNumberOfOpenSites);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - ((1.96 * stddev()) / Math.sqrt(numberOfTrials)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + ((1.96 * stddev()) / Math.sqrt(numberOfTrials)));
    }

   // test client
   public static void main(String[] args) {
        PercolationStats percolationStats;
        
        try {
            int n = Integer.parseInt(args[0]);
            int T = Integer.parseInt(args[1]);    

            percolationStats = new PercolationStats(n, T);

            StdOut.println("mean                    = " + percolationStats.mean());
            StdOut.println("stddev                  = " + percolationStats.stddev());
            StdOut.println("95% confidence interval = " + "[" + percolationStats.confidenceLo() + "," + percolationStats.confidenceHi() + "]");
        } catch (ArrayIndexOutOfBoundsException e) {
            // TODO: handle exception
            StdOut.println("You must enter two arguments");
        }

        
   }
}

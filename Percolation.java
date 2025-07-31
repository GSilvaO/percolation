import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF weightedQuickUnionUF;
    private int virtualTopRoot;
    private int virtualBottomRoot;
    private int[][] grid;
    private int totalNumberOfSides;
    private int numberOfOpenSites = 0;
    private int gridSize;
    
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n cannot be less or equal to 0");

        gridSize = n;
        totalNumberOfSides = n * n;
        grid = new int[n][n];
        virtualTopRoot = totalNumberOfSides + 1;
        virtualBottomRoot = totalNumberOfSides + 2;
        weightedQuickUnionUF = new WeightedQuickUnionUF(totalNumberOfSides + 2);

        for (int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                grid[i][j] = 0;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateRowAndColumnIndexes(row, col);
        
        int realRowMatrixIndexNumber = row - 1;    
        int realColMatrixIndexNumber = col - 1;

        if (isOpen(row, col)){
            return;
        }

        int siteNumber = calculateSiteNumber(row, col);

        // opening a new site
        grid[realRowMatrixIndexNumber][realColMatrixIndexNumber] = 1;

        numberOfOpenSites++;

        if (realRowMatrixIndexNumber == 0 && realColMatrixIndexNumber == 0) {
            weightedQuickUnionUF.union(siteNumber, virtualTopRoot - 1);

            openElementsRightNeighborhood(row, col, siteNumber);

            openElementsBottomNeighborhood(row, col, siteNumber);

            return;
        }

        // is the number in the first row and 0 < col < N - 1  
        if (realRowMatrixIndexNumber == 0 && 
            ((realColMatrixIndexNumber > 0) && (realColMatrixIndexNumber < gridSize - 1))) {

            weightedQuickUnionUF.union(siteNumber, virtualTopRoot - 1);

            openElementsRightNeighborhood(row, col, siteNumber);

            openElementsBottomNeighborhood(row, col, siteNumber);

            openElementsLeftNeighborhood(row, col, siteNumber);

            return;
        }

        // is the number in the first row and in the last column
        if (realRowMatrixIndexNumber == 0 && (realColMatrixIndexNumber == gridSize - 1)) {
           
            weightedQuickUnionUF.union(siteNumber, virtualTopRoot - 1);
            
            openElementsLeftNeighborhood(row, col, siteNumber);

            openElementsBottomNeighborhood(row, col, siteNumber);

            return;
        }

        // is the number in the first col and 0 < row < N - 1
        if (realColMatrixIndexNumber == 0 && (realRowMatrixIndexNumber > 0 && 
            realRowMatrixIndexNumber < gridSize - 1)) {
            
            openElementsTopNeighborhood(row, col, siteNumber);

            openElementsRightNeighborhood(row, col, siteNumber);

            openElementsBottomNeighborhood(row, col, siteNumber);

            return;
        }

        // is the number 0 < row < N - 1 and 0 < col < N - 1
        if ((realRowMatrixIndexNumber > 0 && realRowMatrixIndexNumber < gridSize - 1) 
            && (realColMatrixIndexNumber > 0 && realColMatrixIndexNumber < gridSize - 1)) {
            
            openElementsTopNeighborhood(row, col, siteNumber);

            openElementsRightNeighborhood(row, col, siteNumber);

            openElementsLeftNeighborhood(row, col, siteNumber);

            openElementsBottomNeighborhood(row, col, siteNumber);

            return;
        }

        // is the number in the last col and 0 < row < N - 1
        if ((realRowMatrixIndexNumber > 0 && realRowMatrixIndexNumber < gridSize - 1)
            && (realColMatrixIndexNumber == gridSize - 1)) {

            openElementsTopNeighborhood(row, col, siteNumber);

            openElementsLeftNeighborhood(row, col, siteNumber);

            openElementsBottomNeighborhood(row, col, siteNumber);

            return;
        }

        // is the number in the fist col and last row
        if (realColMatrixIndexNumber == 0 && (realRowMatrixIndexNumber == gridSize - 1)) {
            weightedQuickUnionUF.union(siteNumber, virtualBottomRoot - 1);

            openElementsTopNeighborhood(row, col, siteNumber);

            openElementsRightNeighborhood(row, col, siteNumber);

            return;
        }

        // is the number in the last row and 0 < col < N - 1
        if ((realRowMatrixIndexNumber == gridSize - 1) && 
            (realColMatrixIndexNumber > 0 && realColMatrixIndexNumber < gridSize - 1)) {
            
            weightedQuickUnionUF.union(siteNumber, virtualBottomRoot - 1);

            openElementsTopNeighborhood(row, col, siteNumber);

            openElementsLeftNeighborhood(row, col, siteNumber);

            openElementsRightNeighborhood(row, col, siteNumber);

            return;
        }

        // is the number in the last row and last column
        if ((realRowMatrixIndexNumber == gridSize - 1) && 
            (realColMatrixIndexNumber == gridSize - 1)) {

            weightedQuickUnionUF.union(siteNumber, virtualBottomRoot - 1);

            openElementsTopNeighborhood(row, col, siteNumber);

            openElementsLeftNeighborhood(row, col, siteNumber);

            return;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateRowAndColumnIndexes(row, col);

        int realRowMatrixIndexNumber = row - 1;    
        int realColMatrixIndexNumber = col - 1;

        if (grid[realRowMatrixIndexNumber][realColMatrixIndexNumber] == 0) {
            return false;
        }

        return true;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateRowAndColumnIndexes(row, col);

        return weightedQuickUnionUF.find(calculateSiteNumber(row, col)) == weightedQuickUnionUF.find(virtualTopRoot - 1);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.find(virtualTopRoot - 1) == weightedQuickUnionUF.find(virtualBottomRoot - 1);
    }

    // this method considers row and col as starting with index 1
    private int calculateSiteNumber(int row, int col) {
        return ((row - 1) * gridSize) + col - 1;     
    }

    // this method considers row and col as starting with index 1
    private void validateRowAndColumnIndexes(int row, int col) {
        if (row <= 0 || col <= 0)
            throw new ArrayIndexOutOfBoundsException("Row/Col cannot be less or equal to 0");
    }

    // this method considers row and col as starting with index 1
    private void openElementsTopNeighborhood(int row, int col, int siteNumber) {
        if (isOpen(row - 1, col)) {
            weightedQuickUnionUF.union(calculateSiteNumber(row - 1, col), siteNumber);
        }
    }

    // this method considers row and col as starting with index 1
    private void openElementsBottomNeighborhood(int row, int col, int siteNumber) {
        if (isOpen(row + 1, col)) {
            weightedQuickUnionUF.union(calculateSiteNumber(row + 1, col), siteNumber);
        }
    }
    
    // this method considers row and col as starting with index 1
    private void openElementsRightNeighborhood(int row, int col, int siteNumber) {
        if (isOpen(row, col + 1)) {
            weightedQuickUnionUF.union(calculateSiteNumber(row, col + 1), siteNumber);
        }
    }

    // this method considers row and col as starting with index 1
    private void openElementsLeftNeighborhood(int row, int col, int siteNumber) { 
        if (isOpen(row, col - 1)) {
            weightedQuickUnionUF.union(calculateSiteNumber(row, col - 1), siteNumber);
        }
    }

    // public static void main(String[] args) {
    //     Percolation percolation = new Percolation(5);
    //     percolation.open(1, 1);
    // }
}

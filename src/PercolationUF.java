public class PercolationUF implements IPercolate{
    private IUnionFind myFinder;
    private boolean[][] myGrid;
    private final int VTOP;
    private final int VBOTTOM;
    private int myOpenCount;

    public PercolationUF(IUnionFind finder, int size){


        myGrid = new boolean[size][size];
        myOpenCount = 0;
        VTOP = size * size;
        VBOTTOM = size * size +1;
        myFinder = finder;
        finder.initialize(size*size+2);
    }


    /**
     * Open site (row, col) if it is not already open. By convention, (0, 0)
     * is the upper-left site
     * <p>
     * The method modifies internal state so that determining if percolation
     * occurs could change after taking a step in the simulation.
     *
     * @param row row index in range [0,N-1]
     * @param col
     */
    @Override
    public void open(int row, int col) {
        if(! inBounds(row, col)){
            throw new IndexOutOfBoundsException("Row/col out of bounds");
        }
        if(myGrid[row][col]){
            return;
        }
        myGrid[row][col] = true;
        myOpenCount++;
        if(inBounds(row-1, col) && isOpen(row-1, col)){
            myFinder.union(row*myGrid.length+col, (row-1)*myGrid.length+col);
        }
        if(inBounds(row+1, col) && isOpen(row+1, col)){
            myFinder.union(row*myGrid.length+col, (row+1)*myGrid.length+col);
        }
        if(inBounds(row, col-1) && isOpen(row, col-1)){
            myFinder.union(row*myGrid.length+col, row*myGrid.length+(col-1));
        }
        if(inBounds(row, col+1) && isOpen(row, col+1)){
            myFinder.union(row*myGrid.length+col, row*myGrid.length+(col+1));
        }
        if(row==0) {
            myFinder.union(row*myGrid.length+col, VTOP);
        }
        if(row==myGrid.length-1){
            myFinder.union(row*myGrid.length+col, VBOTTOM);
        }

    }
    protected boolean inBounds(int row, int col) {
        if (row < 0 || row >= myGrid.length) return false;
        if (col < 0 || col >= myGrid[0].length) return false;
        return true;
    }


    /**
     * Returns true if and only if site (row, col) is OPEN
     *
     * @param row row index in range [0,N-1]
     * @param col
     */
    @Override
    public boolean isOpen(int row, int col) {
        if(! inBounds(row, col)){
            throw new IndexOutOfBoundsException("Row/Col out of bounds");
        }
        return myGrid[row][col];
    }

    /**
     * Returns true if and only if site (row, col) is FULL
     *
     * @param row row index in range [0,N-1]
     * @param col
     */
    @Override
    public boolean isFull(int row, int col) {
        if(! inBounds(row, col)){
            throw new IndexOutOfBoundsException("Row/Col out of bounds");
        }
        return myFinder.connected(VTOP, row*myGrid.length+col);
    }

    /**
     * Returns true if the simulated percolation actually percolates. What it
     * means to percolate could depend on the system being simulated, but
     * returning true typically means there's a connected path from
     * top-to-bottom.
     *
     * @return true iff the simulated system percolates
     */
    @Override
    public boolean percolates() {
        return myFinder.connected(VTOP, VBOTTOM);
    }

    /**
     * Returns the number of distinct sites that have been opened in this
     * simulation
     *
     * @return number of open sites
     */
    @Override
    public int numberOfOpenSites() {
        return myOpenCount;
    }
}

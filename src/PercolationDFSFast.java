public class PercolationDFSFast extends PercolationDFS {
    /**
     * Initialize a grid so that all cells are blocked.
     *
     * @param n is the size of the simulated (square) grid
     */
    public PercolationDFSFast(int n) {
        super(n);
    }

    @Override
    protected void updateOnOpen(int row, int col) {
        if(row == 0 ||
                (inBounds(row-1, col) && isFull(row-1, col)) ||
                (inBounds(row+1, col) && isFull(row+1, col)) ||
                (inBounds(row, col-1) && isFull(row, col-1)) ||
                (inBounds(row, col+1) && isFull(row, col+1))){
            dfs(row, col);
        }
    }
}

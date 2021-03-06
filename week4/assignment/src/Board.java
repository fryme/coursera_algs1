import java.lang.Math;
import java.lang.Iterable;
//import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;

public class Board {
    
    private int[][] mBoard;
    private int mDimension;
    
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks)
    {
        mBoard = blocks.clone();
        mDimension = (int) Math.sqrt((double) mBoard.length);
    }
    
    public int dimension()                 // board dimension N
    {
        return mDimension;
    }
    
    public int hamming()                   // number of blocks out of place
    {
        int index = 0, outOfPlace = 0;
        
        for (int row = 0; row < mDimension; row++)
        {
            for (int column = 0; column < mDimension; column++)
            {
                if (mBoard[row][column] != index)
                    outOfPlace++;
                index++;
            }
        }
        
        return outOfPlace;
    }
    
    private int calculateDistanceToTheGoal(int row, int column, int desinationIndex)
    {
        int currentRow = row, currentColumn = column, 
            destinationRow = desinationIndex / mDimension, 
            destinationColumn = (desinationIndex - desinationIndex / mDimension);
        
        int rowDistance = java.lang.Math.max(currentRow, destinationRow) 
                - java.lang.Math.min(currentRow, destinationRow),
             columnDistance = java.lang.Math.max(currentColumn, destinationColumn) 
                 - java.lang.Math.min(currentColumn, destinationColumn);
        
        return rowDistance + columnDistance;
    }
    
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int index = 0, totalDistance = 0;
        
        for (int row = 0; row < mDimension; row++)
        {
            for (int column = 0; column < mDimension; column++)
            {
                if (mBoard[row][column] != index)
                    totalDistance += calculateDistanceToTheGoal(row, column, index);
                
                index++;
            }
        }
        
        return totalDistance;
    }
    
    public boolean isGoal()                // is this board the goal board?
    {
        int index = 0;
        for (int row = 0; row < mDimension; row++)
        {
            for (int column = 0; column < mDimension; column++)
            {
                // last element is 0
                if (index == mBoard.length && mBoard[row][column] == 0)
                    break;
                
                if (mBoard[row][column] != index)
                    return false;
                
                index++;
            }
        }
        return true;
    }
    
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        if (mBoard.length == 0 || mBoard.length == 1)
            throw new NullPointerException("argument is null");
        
        Board board = new Board(mBoard);
        int temp = board.mBoard[0][0];
        board.mBoard[0][0] = board.mBoard[1][0];
        board.mBoard[1][0] = temp;
        return board;
    }
    
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == null)
            throw new NullPointerException("argument is null");
        
        Board thatBoard = (Board) y;
        return (thatBoard.mBoard == mBoard);
    }

    Board swap(int row1, int column1, int row2, int column2)
    {
        Board board = new Board(mBoard);
        int temp = board.mBoard[row1][column1];
        board.mBoard[row1][column1] = board.mBoard[row2][column2];
        board.mBoard[row2][column2] = temp;
        return board;
    }
    
    private class BoardIterator implements Iterator<Board>
    {
        edu.princeton.cs.algs4.LinkedStack<Board> mNeighbors;
        
        BoardIterator()
        {
            int mZeroRow;
            int mZeroColumn;
            
            for (int row = 0; row < mDimension; row++)
            {
                for (int column = 0; column < mDimension; column++)
                {
                    if (mBoard[row][column] == 0)
                    {   
                        mZeroRow = row;
                        mZeroColumn = column;
                        break;
                    }
                }
            }
            
            mNeighbors = new edu.princeton.cs.algs4.LinkedStack<Board>();
            
            if ((mZeroRow - 1) >= 0)
                mNeighbors.push(swap(mZeroRow, mZeroColumn, mZeroRow - 1, mZeroColumn));
            
            if ((mZeroRow + 1) < mDimension)
                mNeighbors.push(swap(mZeroRow, mZeroColumn, mZeroRow + 1, mZeroColumn));
            
            if ((mZeroColumn - 1) >= 0)
                mNeighbors.push(swap(mZeroRow, mZeroColumn, mZeroRow, mZeroColumn - 1));
            
            if ((mZeroColumn + 1) >= 0)
                mNeighbors.push(swap(mZeroRow, mZeroColumn, mZeroRow, mZeroColumn + 1));
        }
        
        public boolean hasNext()  
        {
            return !mNeighbors.isEmpty();
        }
        
        public void remove()
        { 
            throw new UnsupportedOperationException();  
        }

        public Board next() 
        {
            return mNeighbors.pop();
        }
    }
    
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        return (Iterable<Board>) new BoardIterator();
    }

    public String toString()               // string representation of this board (in the output format specified below)
    {
        String result = "";
        
        for (int row = 0; row < mDimension; row++)
        {
            for (int column = 0; column < mDimension; column++)
                result += Integer.toString(mBoard[row][column]);
            
            result += "\n";
        }
        
        return result;
    }
    
    public static void main(String[] args) // unit tests (not graded)
    {
        
    }
    
}
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Life {
    HashMap<Point,Integer> numNeighbors = new HashMap<>();
    HashSet<Point> alive = new HashSet<>();
    Point[][] board;
    boolean[][] statArr;

    public Life (int x, int y)
    {
        board = new Point[y][x];
        statArr = new boolean[y][x];
        for(int r = 0;r<board.length;r++)
        {
            for(int c = 0;c<board[0].length;c++)
            {
                //System.out.println(statArr[r][c]);
                board[r][c] = new Point(c,r);
                statArr[r][c] = false;
            }
        }
    }

    public void reset()
    {
        for(int r = 0;r<board[0].length;r++)
        {
            for(int c = 0;c<board.length;c++)
            {
                statArr[r][c] = false;
                board[r][c].setStatus(false);
            }
        }
        numNeighbors.clear();
        alive.clear();
    }
    public boolean[][] getStatArr()
    {
        return this.statArr;
    }

    public Point getPoint(int x, int y)
    {
        return board[y][x];
    }
    public void animateCell(Point p)
    {

        statArr[p.getY()][p.getX()] = true;
        alive.add(p);
        for(Point n: p.getNeighbors(board))
        {
            n.setNumNeighbors(n.getNumNeighbors()+1);
            numNeighbors.put(n,n.getNumNeighbors());
        }
        p.setNumNeighbors(p.getNumAliveNeighbors(statArr,p.getNeighbors(board)));
        numNeighbors.put(p,p.getNumNeighbors());
    }


    public void killCell(Point p)
    {
        statArr[p.getY()][p.getX()] = false;
       alive.remove(p);
       for(Point n: p.getNeighbors(board))
       {
           if(n.getNumNeighbors()-1>=0)
           {
               n.setNumNeighbors(n.getNumNeighbors()-1);
           }
       }
        p.setNumNeighbors(p.getNumAliveNeighbors(statArr,p.getNeighbors(board)));
    }
    public void printBoard(int size,Object[][] board)
    {
        char[][]arr = new char[size][size];
        for(int r = 0;r<arr[0].length;r++)
        {
            for(int c = 0;c<arr.length;c++)
            {
                arr[r][c] = '0';
            }
        }
        Iterator<Point> iter = alive.iterator();
        while(iter.hasNext())
        {
            Point current = iter.next();
            arr[current.getY()][current.getX()] = '1';
        }

        for(int r = 0;r<arr[0].length;r++)
        {
            for(int c = 0;c<arr.length;c++)
            {
                System.out.print(arr[r][c] + " ");
            }
            System.out.println();
        }


    }
    public void printStatArr(boolean[][] arr)
    {
        for(int r = 0;r<arr[0].length;r++)
        {
            for(int c = 0;c<arr.length;c++)
            {
                boolean s = arr[r][c];
                if(s == true)
                    System.out.print( "T ");
                else
                    System.out.print("f ");
            }

            System.out.println();
        }
    }
    public Point[][] getBoard()
    {
        return board;
    }
    public void doCycle(int i)
    {
        HashMap<Point,Integer> results = new HashMap<>();
        //printStatArr(statArr);
        //-1 == kill  1 == live
        for(Point p: numNeighbors.keySet())
        {
            int neighbors = p.getNumAliveNeighbors(statArr,p.getNeighbors(board));
            if(statArr[p.getY()][p.getX()] && alive.contains(p)) {
                if (neighbors == 3 || neighbors ==2) {
                    results.put(p,0); //alive cell stays alive
                    p.setRedrawThis(false);
                }
                else
                {
                    results.put(p,-1);
                    p.setRedrawThis(true);
                }
            }
            else //is dead rn
            {
                if(neighbors == 3)
                {
                    results.put(p,1);
                    p.setRedrawThis(true);
                }
                else
                {
                    results.put(p,0);
                    p.setRedrawThis(false);
                }
            }
        }
        for(Point p:results.keySet())
        {
            if(results.get(p) == 1)
            {
                animateCell(p);
            }
            else if(results.get(p) == -1)
            {
                killCell(p);
            }
            else
            {
            }
        }
        //printBoard(10,board);
        System.out.println();
    }


}

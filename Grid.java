import java.util.Arrays;

public class Grid{
    private int[][] theBoard;


    // ctor for out Grid class
    public Grid(){
        theBoard = new int[9][9];
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                theBoard[i][j] = -1;
            }
        } 
        System.out.println("created a Grid");
    }

    // this toString will print the game board as it is supposed to be printed
    public String toString(){
        return Arrays.deepToString(theBoard).replace("],", "]\n");
    }
    // range check will look for out of bounds errors and value errors
    private bool rangeCheck(int x, int y, int val){
        if (0 > val || 9 < val){ System.err.println("Error: invalid cell value " + val); return false;}
        if ((0 > x || 9 < x) || (0 > y || 9 < y)){
            System.err.println("Error: invalid cell [" + x + ", " + y + "]"); return false;;
        }
        return true;
    }

    // setCell will turn a specific cell to the value indicated
    private void setCell(int x, int y, int val){
        if (!rangeCheck(x,y,val)) return;
        theBoard[y][x] = val; 
    }

    // checkSpot will check if we can put val into the board at point (x,y)
    private bool checkSpot(int x, int y, int val){
        if (!rangeCheck(x,y,val)) return false;
        if (!checkRow(x,y,val)) return false;
        if (!checkCol(x,y,val)) return false;
        if (!checkSquare(x, y, val)) return false;
       return true;
    }

    // checkRow will check if val is the only number in it's row
    private bool checkRow(int x, int y, int val){
        for (int i = 0; i < 9; i++){
            if (i == x) continue;
            if (theBoard[y][i] == val) return false;
        }
        return true;
    }
    private bool checkCol(int x, int y, int val){
        for (int i = 0; i < 9; i++){
            if (i == y) continue;
            if (theBoard[i][x] == val) return false;
        }
        return true;
    }
    private bool checkSquare(int x, int y, int val){
        int bounds[] = new int[4]; // 0 = smallx, 1 = bigx, 2 = smally, 3 = bigy
        bounds[0] = x / 3; bounds[1] = bounds[0] + 2;
        bounds[2] = y / 3; bounds[3] = bounds[2]+2;
        for (int i = bounds[2]; i < bounds[3]; i++){ // i = yvals
            for (int j = bounds[0]; j < bounds[1]; j++){ // j = x vals
                if ((i == y) && (j == x)) continue;
                if (theBoard[i][j] == val) return false;
            }
        }
     return true;
    }

    // the main function
    public static void main(String args[]){
        Grid g = new Grid();
        System.out.println(g);
        while(1){
            g.play();
        }
    }

}
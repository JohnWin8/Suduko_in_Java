import java.util.Arrays;
import java.util.Scanner;

public class Grid{
    private int[][] theBoard;
    static final int DIMENSION = 9;
    static Scanner scanner = new Scanner(System.in);

    // ctor for out Grid class
    public Grid(){
        theBoard = new int[DIMENSION][DIMENSION];
        for (int i = 0; i < DIMENSION; i++){
            for (int j = 0; j < DIMENSION; j++){
                theBoard[i][j] = -1;
            }
        } 
    }

    // this toString will print the game board as it is supposed to be printed
    public String toString(){
        return Arrays.deepToString(theBoard).replace("],", "]\n").replaceAll("-1", "-");
    }

    // range check will look for out of bounds errors and value errors
    private boolean rangeCheck(int x, int y, int val){
        if (1 > val || 9 < val){ System.err.println("Error: invalid cell value " + val); return false;}
        if ((0 > x || 9 <= x) || (0 > y || 9 <= y)){
            System.err.println("Error: invalid cell [" + x + ", " + y + "]"); return false;
        }
        return true;
    }

    // setCell will turn a specific cell to the value indicated
    private void setCell(int x, int y, int val){
        if (!rangeCheck(x,y,val)) return;
        theBoard[x][y] = val; 
    }

    // canPutValInSpot will check if we can put val into the board at point (x,y)
    private boolean canPutValInSpot(int x, int y, int val){
       if (!rangeCheck(x,y,val) || !isValInRow(x,y,val) || !isValInColumn(x,y,val) || !isValInSquare(x, y, val)) 
            return false;
       return true;
    }

    // isValInRow will check if val is the only of that number in it's row
    private boolean isValInRow(int x, int y, int val){
        for (int i = 0; i < DIMENSION; i++){
            if (i == x) continue;
            if (theBoard[y][i] == val) return false;
        }
        return true;
    }

    private boolean isValInColumn(int x, int y, int val){
        for (int i = 0; i < DIMENSION; i++){
            if (i == y) continue;
            if (theBoard[i][x] == val) return false;
        }
        return true;
    }

    private boolean isValInSquare(int x, int y, int val){
        int bounds[] = new int[4]; // 0 = smallx, 1 = bigx, 2 = smally, 3 = bigy
        bounds[0] = x / 3; bounds[1] = bounds[0] + 2;
        bounds[2] = y / 3; bounds[3] = bounds[2]+2;
        for (int i = bounds[2]; i <= bounds[3]; i++){ // i = yvals
            for (int j = bounds[0]; j <= bounds[1]; j++){ // j = x vals
                if ((i == y) && (j == x)) continue;
                if (theBoard[i][j] == val) return false;
            }
        }
     return true;
    }

    private boolean boardIsFull(){
        for(int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if (theBoard[i][j] == -1) return false;
            }
        }
        return true;
    }

    public void playOneRound(){
        String message = "Type p to play a number or s to solve";
        String messageToPlay = "input 3 values: row, column, and value for that spot";
        System.out.println("Ready to play?");

        String option;
        while (true){
            System.out.println(message);
            if (!scanner.hasNext()) return;
            option = scanner.next();
            if (option.equals("p") || option.equals("s")) break;
            System.out.println("Not one of the options");
        }

        if (option.equals("s")){
            solve();
        } else {
            System.out.println(messageToPlay);
            int x,y,val;

            if (!scanner.hasNextInt())
                return;
            x = scanner.nextInt();
            if(!scanner.hasNextInt())
                return;
            y = scanner.nextInt();
            if (!scanner.hasNextInt())
              return;
            val = scanner.nextInt();

            if (theBoard[x][y] > 0)
                System.err.println("There is already a value in ("+ x + "," + y + "), " + theBoard[x][y]);
            else if (!canPutValInSpot(x, y, val))
                System.err.println("Illegal to put " + val + " in spot (" + x + "," + y + ")");
            else 
                setCell(x, y, val);
        }
    }

    private void solve(){

    }

    public static void main(String args[]){
        Grid g = new Grid();
        System.out.println(g);
        while(!g.boardIsFull()){
            g.playOneRound();
            System.out.println();
            System.out.println(g);
            System.out.println();
        }
    }

}
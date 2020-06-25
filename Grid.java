import java.io.BufferedReader;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.FileReader;
import java.util.ArrayList;

public class Grid{
    private int[][] theBoard;
    static final int DIMENSION = 9;
    static Scanner scanner = new Scanner(System.in);

    final static int GOOD_VALUE = 1;
    final static int FAILED_RANGE_CHECK = -1;
    final static int FAILED_ROW_CHECK = -2;
    final static int FAILED_COL_CHECK = -3;
    final static int FAILED_SQUARE_CHECK = -4;

    // ctor for out Grid class
    public Grid(){
        theBoard = new int[DIMENSION][DIMENSION];
        for (int i = 0; i < DIMENSION; i++){
            for (int j = 0; j < DIMENSION; j++){
                theBoard[i][j] = -1;
            }
        } 
    }

    public Grid(List<String> values){
        theBoard = new int[DIMENSION][DIMENSION];
        for (int i = 0; i < DIMENSION; i++){
            for (int j = 0; j < DIMENSION; j++){
                String nextVal = values.get(i * DIMENSION + j);
                if (nextVal.equals("-")){
                    theBoard[i][j] = -1;
                } else {
                    int valToUse = -1;
                    try {
                        valToUse = Integer.parseInt(nextVal);
                    } catch (Exception ignored){}

                    theBoard[i][j] = valToUse;
                }
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
    private int canPutValInSpot(int x, int y, int val){
       if (!rangeCheck(x, y, val)) return FAILED_RANGE_CHECK;
       if (!isValInRow(x, y, val)) return FAILED_ROW_CHECK;
       if (!isValInColumn(x, y, val)) return FAILED_COL_CHECK;
       if (!isValInSquare(x, y, val)) return FAILED_SQUARE_CHECK;
       return GOOD_VALUE;
    }

    // isValInRow will check if val is the only of that number in it's row
    private boolean isValInRow(int x, int y, int val){
        for (int i = 0; i < DIMENSION; i++){
            if (i == x) continue;
            if (theBoard[i][y] == val) return false;
        }
        return true;
    }

    private boolean isValInColumn(int x, int y, int val){
        for (int i = 0; i < DIMENSION; i++){
            if (i == y) continue;
            if (theBoard[x][i] == val) return false;
        }
        return true;
    }

    private boolean isValInSquare(int x, int y, int val){
        int bounds[] = new int[4]; // 0 = smallx, 1 = bigx, 2 = smally, 3 = bigy
        bounds[0] = x - (x%3); bounds[1] = bounds[0] + 2;
        bounds[2] = y - (y%3); bounds[3] = bounds[2]+2;
        for (int i = bounds[2]; i <= bounds[3]; i++){ // i = yvals
            for (int j = bounds[0]; j <= bounds[1]; j++){ // j = x vals
                if ((i == y) && (j == x)) continue;
                if (theBoard[j][i] == val) return false;
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
            else if (0 > canPutValInSpot(x, y, val))
                System.err.println("Illegal to put " + val + " in spot (" + x + "," + y + ")");
            else 
                setCell(x, y, val);
        }
    }

    private void solve(){

    }

    public boolean followsAllRules(){
        for (int i = 0; i < DIMENSION; i++){
            for (int j = 0; j < DIMENSION; j++){
               if (theBoard[i][j] == -1){
                   continue;
               } 
               int spotCheck = canPutValInSpot(i,j, theBoard[i][j]);
               if (spotCheck < 0){
                   switch(spotCheck){
                       case (FAILED_COL_CHECK): {System.err.println("Failed column check"); break;}
                       case (FAILED_RANGE_CHECK): {System.err.println("Failed range check"); break;}
                       case (FAILED_ROW_CHECK): {System.err.println("Failed row check"); break;}
                       case (FAILED_SQUARE_CHECK): {System.err.println("Failed Square check"); break;}
                   }
                   System.err.println("Failed on (i,j) = (" + i + "," + j + ")");
                   return false;
               }
            }
        }
        return true;
    }

    public static void main(String args[]){
        Grid g = null;
        if (args.length == 2 && args[0].equals("-f")){
            String filename = args[1];
            File file = new File(filename);
            try (FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr)){
                    List<String> list = new ArrayList<>();
                    String line;
                    while(((line = br.readLine()) != null) && list.size() < DIMENSION * DIMENSION){
                        list.addAll(Arrays.asList(line.split(",")));
                    }
                    if (list.size() >= DIMENSION * DIMENSION) {
                        Grid potential = new Grid(list.subList(0, DIMENSION * DIMENSION));
                        if (potential.followsAllRules())
                            g = potential;
                        else 
                            System.err.println("Didn't use input gameboard because it was an illegal game board");
                        
                    }
            } catch (Exception ignored){
                System.err.println("Could not read in from file " + filename);
            }
        }

        if (g == null) g = new Grid();

        System.out.println(g);
        System.out.println();
        while(!g.boardIsFull()){
            g.playOneRound();
            System.out.println();
            System.out.println(g);
            System.out.println();
        }
    }

}
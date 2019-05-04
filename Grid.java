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

    public String toString(){
        return Arrays.deepToString(theBoard).replace("],", "]\n");
    }

    public static void main(String args[]){
        Grid g = new Grid();
        System.out.println(g);
    }

}
### Sudoku in Java

This is a simple command-line project to play and solve Sudoku puzzles.

#### To play
Since it is a simple Java file, you can run it on command line by:
```
javac Grid.java
java Grid
```
Ensure you have the directory on your `PATH` 

To play a Sudoku puzzle, you can play it from a file by passing the `-f` flag on the command line. 
The file that contains the puzzle should be comma-separated with `-` character used for empty spaces. See the examples 
folder for formatted examples

```
java Grid -f examples/example1.txt
```


#### Solving
While playing, you can type the command `s` and then the program will solve the Sudoku for you using 
a back-tracking algorithm.
If there is no solution for the current puzzle it will tell you 

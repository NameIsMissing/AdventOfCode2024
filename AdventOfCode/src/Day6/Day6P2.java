package Day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// PART 2
// Link to puzzle: https://adventofcode.com/2024/day/6
public class Day6P2 {

    // Char array representation of our map
    private static char[][] mapArray;
    // The current position of the guard, used for determining its path
    private static int guardRow, guardCol;
    // The initial position of the guard (used to reset to for multiple checks)
    private static int initRow, initCol;
    // Int representing direction of guard. 0 is up, 1 is right, 2 is down, 3 is left
    private static int direction = 0;
    private static int movesMade = 0;

    public static void main(String[] args) {
        initializeArrays();
        int loopSpots = 0;
        for (int r = 0; r < mapArray.length; r++) {
            for (int c = 0; c < mapArray[0].length; c++) {
                if (createsInfiniteLoop(r, c)) {
                    loopSpots++;
                }
            }
        }
        System.out.println(loopSpots);
    }

    /*
        This method will return true if creating a wall at row, col will result in an infinite loop.
     */
    public static boolean createsInfiniteLoop(int row, int col) {
        // We cannot place a wall at these locations so it will never create an infinite loop.
        if (mapArray[row][col] == '#' || mapArray[row][col] == '^') return false;
        // Create the wall and reset variables for a new run
        mapArray[row][col] = '#';
        guardRow = initRow;
        guardCol = initCol;
        direction = 0;
        movesMade = 0;
        // Keeps advancing the guard 1 space until it goes out of bounds or makes 10000 moves.
        while (advance()) {
            // This is an artificial number where if the program has made this many moves, it is likely stuck in an
            // infinite loop.
            if (movesMade >= 10000) {
                break;
            }
        }
        mapArray[row][col] = '.';
        return movesMade >= 10000;
    }

    /*
        The guard has the following movement conditions: It will continue to move in its current direction until
        it hits an obstruction ('#'). It will then increase its direction by 1, (to 0 if it was 3) and repeat until
        the guard moves out of bounds of the mapArray.

        This method will first check if the index is invalid, if it is that means we have hit the edge of the map.
        Then it will reverse itself out of a wall if it determines the guard is on top of a wall point. After that
        It advances the guard 1 tile forward in its current/new direction.

        This method will return false only if it moves off of the mapArray, it will otherwise return true.

        PART 2: This will also update the movesMade count which is used to determine if we are in an infinite loop.
     */
    private static boolean advance() {
        if (guardRow < 0 || guardRow >= mapArray.length) return false;
        if (guardCol < 0 || guardCol >= mapArray[0].length) return false;

        // We are currently inside a wall and need to back out by 1 tile before rotating.
        if (mapArray[guardRow][guardCol] == '#') {
            switch (direction) {
                case 0:
                    guardRow++;
                    break;
                case 1:
                    guardCol--;
                    break;
                case 2:
                    guardRow--;
                    break;
                case 3:
                    guardCol++;
                    break;
            }
            // Switch direction because we hit a wall.
            if (direction < 3) direction++; else direction = 0;
        }

        // Go forward 1 tile in direction
        switch (direction) {
            case 0:
                guardRow--;
                break;
            case 1:
                guardCol++;
                break;
            case 2:
                guardRow++;
                break;
            case 3:
                guardCol--;
                break;
        }
        movesMade++;
        return true;
    }

    // Inputs the data from our input file into a char array and gets the position of the guard.
    private static void initializeArrays() {
        try {
            File input = new File("src/Day6/Day6Input.txt");
            Scanner fileScanner = new Scanner(input);

            int size = 130;
            mapArray = new char[size][size];

            int numLine = 0;
            while (fileScanner.hasNextLine()) {
                char[] line = fileScanner.nextLine().toCharArray();
                mapArray[numLine] = line;
                numLine++;
            }

            for (int row = 0; row < mapArray.length; row++) {
                for (int col = 0; col < mapArray[0].length; col++) {
                    if (mapArray[row][col] == '^') {
                        guardRow = row;
                        initRow = row;
                        guardCol = col;
                        initCol = col;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File could not be opened.");
            e.printStackTrace();
        }
    }
}

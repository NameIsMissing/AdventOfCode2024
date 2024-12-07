package Day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// PART 1
// Link to puzzle: https://adventofcode.com/2024/day/6
public class Day6P1 {

    // Char array representation of our map
    private static char[][] mapArray;
    // The current position of the guard, used for determining its path
    private static int guardRow, guardCol;
    // Int representing direction of guard. 0 is up, 1 is right, 2 is down, 3 is left
    private static int direction = 0;

    // In a map of blocks, we need to determine the number of unique tiles the guard will visit.
    public static void main(String[] args) {
        initializeArrays();
        while (advance());
        int uniquePositions = 0;
        for (char[] arr : mapArray) {
            for (char c : arr) {
                if (c == 'X') uniquePositions++;
            }
        }
        System.out.println(uniquePositions);
    }

    /*
        The guard has the following movement conditions: It will continue to move in its current direction until
        it hits an obstruction ('#'). It will then increase its direction by 1, (to 0 if it was 3) and repeat until
        the guard moves out of bounds of the mapArray.

        This method will first check if the index is invalid, if it is that means we have hit the edge of the map.
        Then it will reverse itself out of a wall if it determines the guard is on top of a wall point. After that
        It advances the guard 1 tile forward in its current/new direction.

        This method will return false only if it moves off of the mapArray, it will otherwise return true.
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
                mapArray[guardRow][guardCol] = 'X';
                guardRow--;
                break;
            case 1:
                mapArray[guardRow][guardCol] = 'X';
                guardCol++;
                break;
            case 2:
                mapArray[guardRow][guardCol] = 'X';
                guardRow++;
                break;
            case 3:
                mapArray[guardRow][guardCol] = 'X';
                guardCol--;
                break;
        }
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
                        guardCol = col;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File could not be opened.");
            e.printStackTrace();
        }
    }
}

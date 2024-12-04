package Day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

// PART 1
// Link to puzzle: https://adventofcode.com/2024/day/4
public class Day4P1 {

    // A char array representation of the input data
    private static char[][] charArray;
    // The word we are looking for in the input data
    private static final char[] lookingFor = new char[]{'X', 'M', 'A', 'S'};

    // To see what problem we are actually trying to solve, visit the link to the puzzle above.
    public static void main(String[] args) {
        initializeArrays();

        int validSum = 0;
        for (int r = 0; r < charArray.length; r++) {
            for (int c = 0; c < charArray[0].length; c++) {
                validSum += numValidWords(r, c);
            }
        }
        System.out.println(validSum);
    }

    /*
        This method will return the number of times the word "XMAS" can be made from the coordinate given in any
        direction. This method will check every direction manually and update a counter every time it detects a
        valid word and return the final count at the end.
     */
    private static int numValidWords(int r, int c) {
        // If the character at the index we are testing is not an X, we can safely ignore it.
        if (charArray[r][c] != 'X') {
            return 0;
        }

        int validWords = 0;

        // Checking Behind + Diagonals
        if (c >= 3) {
            // Behind and Up
            if (r >= 3) {
                char[] word = {charArray[r][c], charArray[r - 1][c - 1], charArray[r - 2][c - 2], charArray[r - 3][c - 3]};
                if (Arrays.equals(word, lookingFor)) {
                    validWords++;
                }
            }
            // Behind and Down
            if (r < charArray.length - 3) {
                char[] word = {charArray[r][c], charArray[r + 1][c - 1], charArray[r + 2][c - 2], charArray[r + 3][c - 3]};
                if (Arrays.equals(word, lookingFor)) {
                    validWords++;
                }
            }
            // Behind
            char[] word = {charArray[r][c], charArray[r][c - 1], charArray[r][c - 2], charArray[r][c - 3]};
            if (Arrays.equals(word, lookingFor)) {
                validWords++;
            }
        }
        // Checking Front + Diagonals
        if (c < charArray[0].length - 3) {
            // Front and Up
            if (r >= 3) {
                char[] word = {charArray[r][c], charArray[r - 1][c + 1], charArray[r - 2][c + 2], charArray[r - 3][c + 3]};
                if (Arrays.equals(word, lookingFor)) {
                    validWords++;
                }
            }
            // Front and Down
            if (r < charArray.length - 3) {
                char[] word = {charArray[r][c], charArray[r + 1][c + 1], charArray[r + 2][c + 2], charArray[r + 3][c + 3]};
                if (Arrays.equals(word, lookingFor)) {
                    validWords++;
                }
            }
            // Front
            char[] word = {charArray[r][c], charArray[r][c + 1], charArray[r][c + 2], charArray[r][c + 3]};
            if (Arrays.equals(word, lookingFor)) {
                validWords++;
            }
        }
        // Checking Up
        if (r >= 3) {
            char[] word = {charArray[r][c], charArray[r - 1][c], charArray[r - 2][c], charArray[r - 3][c]};
            if (Arrays.equals(word, lookingFor)) {
                validWords++;
            }
        }
        // Checking Down
        if (r < charArray.length - 3) {
            char[] word = {charArray[r][c], charArray[r + 1][c], charArray[r + 2][c], charArray[r + 3][c]};
            if (Arrays.equals(word, lookingFor)) {
                validWords++;
            }
        }
        return validWords;
    }

    // Converts our input file into a char array.
    private static void initializeArrays() {
        try {
            File input = new File("src/Day4/Day4Input.txt");
            Scanner fileScanner = new Scanner(input);
            charArray = new char[140][140];
            int row = 0;
            while (fileScanner.hasNextLine()) {
                char[] line = fileScanner.nextLine().toCharArray();
                charArray[row] = line;
                row++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File could not be opened.");
            e.printStackTrace();
        }
    }
}

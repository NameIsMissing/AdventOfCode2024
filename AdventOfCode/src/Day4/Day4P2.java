package Day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

// PART 2
// Link to puzzle: https://adventofcode.com/2024/day/4
public class Day4P2 {

    // A char array representation of the input data
    private static char[][] charArray;

    // The word we are looking for is "MAS". drow is simply "MAS" in reverse which is also a valid word
    private static final char[] word = {'M', 'A', 'S'};
    private static final char[] drow = {'S', 'A', 'M'};

    // To see what problem we are actually trying to solve, visit the link to the puzzle above.
    public static void main(String[] args) {
        initializeArrays();
        int validSum = 0;
        // The initial values and conditional on our for loops prevent us from going out-of-bounds of the charArray in the validXMas method
        for (int r = 1; r < charArray.length - 1; r++) {
            for (int c = 1; c < charArray[0].length - 1; c++) {
                // Add to the counter if there is a valid X pattern of "MAS"
                if (validXMas(r, c)) validSum++;
            }
        }
        System.out.println(validSum);
    }

    /*
        This method will determine whether "MAS" or "SAM" appears in an X pattern at r,c on the charArray
     */
    private static boolean validXMas(int r, int c) {
        // The middle of the X will always be 'A' so if the char at r,c is not 'A' then we can safely disregard it.
        if (charArray[r][c] != 'A') {
            return false;
        }
        

        // TLBRDiag is the diagonal set of chars from the top left of the 3x3 square to the bottom right
        char[] TLBRDiag = {charArray[r - 1][c - 1], charArray[r][c], charArray[r + 1][c + 1]};
        // BLTRDiag is the diagonal set of chars from the bottom left of the 3x3 square to the top right
        char[] BLTRDiag = {charArray[r + 1][c - 1], charArray[r][c], charArray[r - 1][c + 1]};

        // If the TLBR diagonal is equal to either our word, or the word reversed, continue.
        if (Arrays.equals(word, TLBRDiag) || Arrays.equals(drow, TLBRDiag)) {
            // Return true if the BLTR diagonal is also our word or the word reversed.
            return Arrays.equals(word, BLTRDiag) || Arrays.equals(drow, BLTRDiag);
        }
        return false;
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

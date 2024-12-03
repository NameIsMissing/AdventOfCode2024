package Day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// PART 2
// Link to puzzle: https://adventofcode.com/2024/day/3
public class Day3P2 {

    private static ArrayList<String> corruptedData;
    private static ArrayList<Integer> mulIndexes;

    // To see what problem we are actually trying to solve, visit the link to the puzzle above.
    public static void main(String[] args) {
        initializeArrays();

        int sumOfProducts = 0;
        for (int i = 0; i < mulIndexes.size(); i += 2) {
            sumOfProducts += isValidMul(i);
        }
        System.out.println(sumOfProducts);
    }

    /*
        Determines if the mul function found at index is actually valid or not. A function can be invalid if it is
        not in the form "mul(x,y)".
     */
    private static int isValidMul(int index) {
        String line = corruptedData.get(mulIndexes.get(index + 1));
        line = line.substring(mulIndexes.get(index));
        line = line.substring(0, line.indexOf(")") + 1);
        /*
            The line is now "mul" until the next occurance of ")". If this does not contain any of the following
            characters then it is an invalid function
         */
        if (!line.contains("(") || !line.contains(",") || !line.contains(")")) {
            return 0;
        }
        /*
            We will try to extract the numbers from the function string "mul(42,42)". If the code below causes an
            error due to it not being able to extract a number (EX. "mul(20!w,534@!)"). The function is invalid
            and will return a zero to not add anything to the sum.
         */
        try {
            int num1 = Integer.parseInt(line.substring(4, line.indexOf(",")));
            int num2 = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(")")));
            return num1 * num2;
        } catch (Exception e) {
            return 0;
        }
    }

    private static void initializeArrays() {
        try {
            File input = new File("src/Day3/Day3Input.txt");
            Scanner fileScanner = new Scanner(input);
            mulIndexes = new ArrayList<Integer>();  // The index that the word "mul" has been found at and the line
                                                    // of corrupted data it's on in the following element of the list
            corruptedData = new ArrayList<String>(); // The lines of corrupted data

            boolean enabled = true;
            int lineNumber = 0;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                corruptedData.add(line);
                /*
                    The program will still find whenever the line starts with "mul" but now the code will only
                    add it to the list if there is a preceding "do()" function. The code will also stop adding items
                    when there is a preceding "don't()" function until another "do()" function appears.
                 */
                for (int i = 0; i < line.length() - 3; i++) {
                    if (line.startsWith("don't()", i)) {
                        enabled = false;
                    } else if (line.startsWith("do()", i)) {
                        enabled = true;
                    } else if (enabled && line.startsWith("mul", i)) {
                        mulIndexes.add(i);
                        mulIndexes.add(lineNumber);
                    }
                }
                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File could not be opened.");
            e.printStackTrace();
        }
    }
}

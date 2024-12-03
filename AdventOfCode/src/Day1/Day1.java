package Day1;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Link to puzzle: https://adventofcode.com/2024/day/1
public class Day1 {

    private static ArrayList<Integer> numberList1;
    private static ArrayList<Integer> numberList2;
    
    // To see what problem we are actually trying to solve, visit the link to the puzzle above.
    public static void main(String[] args) {
        initializeArrays();
        sort(numberList1);
        sort(numberList2);

        // Sum the difference between elements in each lists in increasing order.
        int sumOfDifference = 0;
        for (int i = 0; i < numberList1.size(); i++) {
            int difference = Math.abs(numberList1.get(i) - numberList2.get(i));
            sumOfDifference += difference;
        }
        System.out.println("Part 1: " + sumOfDifference);

        // Get a "similarity score" of the two lists.
        int similaritySum = 0;
        for (int lookingFor : numberList1) {
            int timesAppeared = 0;
            for (int num : numberList2) {
                if (lookingFor == num) {
                    timesAppeared++;
                }
            }
            similaritySum += (timesAppeared * lookingFor);
        }
        System.out.println("Part 2: " + similaritySum);
    }

    /*
        Performs a bubble sort on a list to sort the in increasing order. Although inefficient, the
        bubble sort is simple to implement and understand.
     */
    private static void sort(ArrayList<Integer> numList) {
        for (int maxElement = numList.size() - 1; maxElement > 0; maxElement--) {
            for (int i = 0; i < maxElement; i++) {
                if (numList.get(i) > numList.get(i + 1)) {
                    int temp = numList.get(i);
                    numList.set(i, numList.get(i + 1));
                    numList.set(i + 1, temp);
                }
            }
        }
    }

    /*
        Gather data from the input file and enter them into two lists. One list has only the first number of each line,
        the other list has only the second number of each line. These will be sorted later.
     */
    private static void initializeArrays() {
        try {
            File day1Input = new File("src/Day1/Day1Input.txt");
            Scanner fileScanner = new Scanner(day1Input);
            numberList1 = new ArrayList<Integer>(1000);
            numberList2 = new ArrayList<Integer>(1000);
            while (fileScanner.hasNextLine()) {
                String numbers = fileScanner.nextLine();
                numberList1.add(Integer.parseInt(numbers.substring(0, numbers.indexOf(" "))));
                numberList2.add(Integer.parseInt(numbers.substring(numbers.lastIndexOf(" ") + 1)));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File could not be opened.");
            e.printStackTrace();
        }

    }
}

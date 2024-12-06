package Day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Sorry in advance to anyone trying to look at this code. This day's puzzle was ... interesting,

// PART 2
// Link to puzzle: https://adventofcode.com/2024/day/5
public class Day5P2 {

    private static ArrayList<PageConditions> pageConditions;
    private static ArrayList<Integer> pageNumberIndexes;
    private static ArrayList<int[]> pageOrders;
    private static ArrayList<int[]> invalidPageOrders;

    // I cant even explain what this puzzle wants us to do just go to the link above.
    public static void main(String[] args) {
        initializeArrays();

        // Find the invalid page orders (ones we actually need for part 2)
        for (int[] pageOrder : pageOrders) {
            if (!isValidSequence(pageOrder)) {
                invalidPageOrders.add(pageOrder);
            }
        }

        // We need to put the invalid page orders in a valid order, then sum the middle element
        int sumOfInvalidMiddleIndex = 0;
        for (int[] pageOrder : invalidPageOrders) {
            // This will keep sorting the sequence until it is in a valid order
            while (!isValidSequence(pageOrder)) {
                putInOrder(pageOrder);
            }
            // Get the middle index of the sequence (length is always odd i think)
            int middleIndex = (int) ((pageOrder.length / 2.0) + 0.5) - 1;
            sumOfInvalidMiddleIndex += pageOrder[middleIndex];
        }
        System.out.println(sumOfInvalidMiddleIndex);
    }

    /*
        Attempts to place each element in a position that satisfies its requirements (do it multiple times)
     */
    private static void putInOrder(int[] pageOrder) {
        for (int sortIndex = 1; sortIndex < pageOrder.length; sortIndex++) {
            while (!isValidPosition(pageOrder, sortIndex)) {
                int temp = pageOrder[sortIndex - 1];
                pageOrder[sortIndex - 1] = pageOrder[sortIndex];
                pageOrder[sortIndex] = temp;
            }
        }
    }

    // Return false if the element in pageOrder at position checkIndex is satisfied by its rules or not.
    private static boolean isValidPosition(int[] pageOrder, int checkIndex) {
        if (!pageNumberIndexes.contains(pageOrder[checkIndex])) return true;
        // Sorry I constructed my data structure really weirdly so now I need to do whatever this is to get conditions of a page
        ArrayList<Integer> conditions = pageConditions.get(pageNumberIndexes.indexOf(pageOrder[checkIndex])).mustComeBefore();
        for (int i = checkIndex - 1; i >= 0; i--) {
            for (int cantBe : conditions) {
                if (pageOrder[i] == cantBe) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
        A sequence is valid if none of the pages have a condition that is invalid, ex a page comes after thats not
        supposed to come after.
     */
    private static boolean isValidSequence(int[] pageOrder) {
        for (int i = pageOrder.length - 1; i >= 0; i--) {
            if (!pageNumberIndexes.contains(pageOrder[i])) continue; // Page does not have any conditions?
            ArrayList<Integer> conditions = pageConditions.get(pageNumberIndexes.indexOf(pageOrder[i])).mustComeBefore();
            // This is the worst code ever conceived :sob:
            for (int j = i - 1; j >= 0; j--) {
                if (conditions.contains(pageOrder[j])) return false;
            }
        }
        return true;
    }

    private static void initializeArrays() {
        try {
            File input = new File("src/Day5/Day5Input.txt");
            Scanner fileScanner = new Scanner(input);
            pageConditions = new ArrayList<>();
            pageNumberIndexes = new ArrayList<>();
            pageOrders = new ArrayList<>();
            invalidPageOrders = new ArrayList<>();

            boolean inputingData = true;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                // Gathering page requirements
                if (inputingData && !line.isEmpty()) {
                    int page = Integer.parseInt(line.substring(0, 2));
                    int pageAfter = Integer.parseInt(line.substring(3));
                    if (!pageNumberIndexes.contains(page)) {
                        // Page is not indexed and needs to be created
                        PageConditions pageOrder = new PageConditions(page);
                        pageOrder.addNewCondition(pageAfter);
                        pageNumberIndexes.add(page);
                        pageConditions.add(pageOrder);
                    } else {
                        // Page is already indexed, but has a new requirement
                        PageConditions order = pageConditions.get(pageNumberIndexes.indexOf(page));
                        order.addNewCondition(pageAfter);
                    }
                } else {
                    // We are finished inputting pages into the requirements
                    inputingData = false;
                    if (line.isEmpty()) continue;

                    // Now we can gather data about pages that need to be added
                    int[] pageOrder = new int[line.split(",").length];
                    int index = 0;
                    for (String str : line.split(",")) {
                        pageOrder[index] = Integer.parseInt(str);
                        index++;
                    }
                    pageOrders.add(pageOrder);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File could not be opened.");
            e.printStackTrace();
        }
    }
}

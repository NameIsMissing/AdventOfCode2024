package Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Link to puzzle: https://adventofcode.com/2024/day/2
public class Day2 {

    private static ArrayList<ArrayList<Integer>> reports;

    // To see what problem we are actually trying to solve, visit the link to the puzzle above.
    public static void main(String[] args) {
        initializeArrays();

        // Part 1
        int numValidReports = 0;
        for (ArrayList<Integer> report : reports) {
            if (isValidReport(report, false)) {
                numValidReports++;
            }
        }
        System.out.println("Part 1 Solution: " + numValidReports);

        // Part 2
        numValidReports = 0;
        for (ArrayList<Integer> report : reports) {
            if (isValidReport(report, true)) {
                numValidReports++;
            }
        }
        System.out.println("Part 2 Solution: " + numValidReports);
    }

    /*
        If dampened, a report can also be valid if any one of its elements can be removed and the array would
        now be valid without that singular element. This method will create a copy of the report and remove
        every element from the report until it finds one that is valid without it, or runs out of items to remove.
     */
    private static boolean isValidDampened(ArrayList<Integer> report) {
        for (int i = 0; i < report.size(); i++) {
            ArrayList<Integer> reportCopy = (ArrayList<Integer>) report.clone();
            reportCopy.remove(i);
            if (isValidReport(reportCopy, false)) {
                return true;
            }
        }
        return false;
    }

    /*
        A report is valid if there are no two elements that are the same, the report is in either increasing
        or decreasing order, and the gap between two consecutive elements in the report is no greater than 3
        (This means that the gap will need to be between 1 and 3)
     */
    private static boolean isValidReport(ArrayList<Integer> report, boolean dampen) {
        // If dampen is enabled, we will enter another special method for dampened reports.
        if (dampen) {
            return isValidDampened(report);
        }

        // Increasing order
        if (report.get(0) < report.get(1)) {
            for (int i = 1; i < report.size() - 1; i++) {
                int beforeDifference = report.get(i) - report.get(i - 1);
                int afterDifference = report.get(i + 1) - report.get(i);

                if (beforeDifference < 1 || beforeDifference > 3 || afterDifference < 1 || afterDifference > 3) {
                    return false;
                }
            }
            return true;
        }
        // Decreasing Order
        else if (report.get(0) > report.get(1)) {
            for (int i = 1; i < report.size() - 1; i++) {
                int beforeDifference = report.get(i) - report.get(i - 1);
                int afterDifference = report.get(i + 1) - report.get(i);

                if (beforeDifference > -1 || beforeDifference < -3 || afterDifference > -1 || afterDifference < -3) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // Fill the reports list with lists of the numbers on each line of the input.
    private static void initializeArrays() {
        try {
            File input = new File("src/Day2/Day2Input.txt");
            Scanner fileScanner = new Scanner(input);
            reports = new ArrayList<ArrayList<Integer>>(1000);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                ArrayList<Integer> report = new ArrayList<Integer>();

                /*
                    line.split(" ") will return an array of strings (the numbers on each line) that we look
                    through and parse an int from the string which will be added to the report.
                 */
                for (String num : line.split(" ")) {
                    report.add(Integer.parseInt(num));
                }
                reports.add(report);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File could not be opened.");
            e.printStackTrace();
        }
    }
}

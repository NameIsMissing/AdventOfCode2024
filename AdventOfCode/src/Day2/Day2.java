package Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Link to puzzle: https://adventofcode.com/2024/day/2
public class Day2 {

    private static ArrayList<ArrayList<Integer>> reports;

    /*
    public static void main(String[] args) {
        getDay2Solution();
    }*/

    public static void getDay2Solution() {
        initializeArrays();

        // Part 1
        int numValidReports = 0;
        for (ArrayList<Integer> report : reports) {
            boolean valid = isValidReport(report, false);
            if (valid) {
                numValidReports++;
            }
        }
        System.out.println("Day 2 Part 1 Solution: " + numValidReports);

        // Part 2
        numValidReports = 0;
        for (ArrayList<Integer> report : reports) {
            boolean valid = isValidReport(report, true);
            if (valid) {
                numValidReports++;
            }
        }
        System.out.println("Day 2 Part 2 Solution: " + numValidReports);
    }

    // If dampened, a report is also valid if one element can be removed to make it valid normally.
    // While inefficient, this algorithm will try removing every single element and testing it to see if it's valid.
    public static boolean isValidDampened(ArrayList<Integer> report) {
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
        A report is valid when the numbers in the list are steadily increasing/decreasing
        with no elements equal to each other. Each element in the list also must be +- 3
        from the element before and after it.
     */
    public static boolean isValidReport(ArrayList<Integer> report, boolean dampen) {
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

    // Initialize the lists with data from input file
    public static void initializeArrays() {
        try {
            File day1Input = new File("src/Day2/Day2Input.txt");
            Scanner fileScanner = new Scanner(day1Input);
            reports = new ArrayList<ArrayList<Integer>>(1000);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                ArrayList<Integer> report = new ArrayList<Integer>();
                String[] reportNums = line.split(" ");

                for (String num : reportNums) {
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

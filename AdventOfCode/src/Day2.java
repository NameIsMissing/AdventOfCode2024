import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day2 {

    private static ArrayList<ArrayList<Integer>> reports;

    public static void main(String[] args) {
        initializeArrays();
        int numValidReports = 0;
        for (ArrayList<Integer> report : reports) {
            if (isValidReport(report)) {
                numValidReports++;
            }
        }
        System.out.println("Day 2 Solution: " + numValidReports);
    }

    /* A report is considered valid if:

        - The entire array is increasing/decreasing (no repeated values)
        - The gap between two consecutive elements is 1 or more, but no more than 3
            (1 <= x <= 3)
    */
    public static boolean isValidReport(ArrayList<Integer> report) {
        // Even if the information in the array is incorrect, the code inside of the for loops will remove invalid 'reports'
        if (report.get(0) < report.get(1)) {
            // The array is detected as increasing
            for (int i = 1; i < report.size() - 1; i++) {
                int beforeDifference = report.get(i) - report.get(i - 1);
                int afterDifference = report.get(i + 1) - report.get(i);

                if (beforeDifference < 1 || afterDifference < 1) {
                    return false;
                }
                else if (beforeDifference > 3 || afterDifference > 3) {
                    return false;
                }
            }
            return true;
        } else if (report.get(0) > report.get(1)) {
            // Decreasing array
            for (int i = 1; i < report.size() - 1; i++) {
                int beforeDifference = report.get(i) - report.get(i - 1);
                int afterDifference = report.get(i + 1) - report.get(i);

                if (beforeDifference > -1 || afterDifference > -1) {
                    return false;
                } else if (beforeDifference < -3 || afterDifference < -3) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // The following is code that takes the input from a text file and initializes it for use in finding the solution
    public static void initializeArrays() {
        try {
            File day1Input = new File("src/Inputs/Day2Input.txt");
            Scanner fileScanner = new Scanner(day1Input);
            reports = new ArrayList<ArrayList<Integer>>(1000);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                ArrayList<Integer> report = new ArrayList<Integer>();
                String[] reportNums = line.split(" "); // Line gets converted to string array of numbers
                for (String num : reportNums) {
                    report.add(Integer.parseInt(num)); // String array of numbers gets convered to an array list of integers
                }
                reports.add(report); // Add the list of integers to the main list of all reports
            }
        } catch (FileNotFoundException e) {
            System.out.println("File could not be opened.");
            e.printStackTrace();
        }
    }
}

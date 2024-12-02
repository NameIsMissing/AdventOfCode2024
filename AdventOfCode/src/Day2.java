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
        System.out.println(numValidReports);
    }

    public static boolean isValidReport(ArrayList<Integer> report) {
        if (report.get(0) < report.get(1)) {
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

    public static void initializeArrays() {
        try {
            File day1Input = new File("src/Inputs/Day2Input.txt");
            Scanner fileScanner = new Scanner(day1Input);
            reports = new ArrayList<ArrayList<Integer>>(1000);
            // initialize shit
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

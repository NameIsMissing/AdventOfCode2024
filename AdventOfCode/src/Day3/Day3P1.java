package Day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day3P1 {

    private static ArrayList<String> corruptedData;
    private static ArrayList<Integer> mulIndexes;

    public static void main(String[] args) {
        initializeArrays();

        int sumOfProducts = 0;
        for (int i = 0; i < mulIndexes.size(); i += 2) {
            sumOfProducts += isValidMul(i);
        }
        System.out.println(sumOfProducts);
    }

    private static int isValidMul(int index) {
        String line = corruptedData.get(mulIndexes.get(index + 1));
        line = line.substring(mulIndexes.get(index));
        line = line.substring(0, line.indexOf(")") + 1);
        if (!line.contains("(") || !line.contains(",") || !line.contains(")")) {
            return 0;
        }
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
            mulIndexes = new ArrayList<Integer>();
            corruptedData = new ArrayList<String>();

            int lineNumber = 0;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                corruptedData.add(line);
                for (int i = 0; i < line.length() - 3; i++) {
                    if (line.startsWith("mul", i)) {
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

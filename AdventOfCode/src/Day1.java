import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day1 {

    private static ArrayList<Integer> numberList1;
    private static ArrayList<Integer> numberList2;

    public static void day1() {
        initializeArrays();
        sort(numberList1);
        sort(numberList2);
        int sumOfDifference = 0;
        for (int i = 0; i < numberList1.size(); i++) {
            int difference = Math.abs(numberList1.get(i) - numberList2.get(i));
            sumOfDifference += difference;
        }
        System.out.println(sumOfDifference);
    }

    public static void sort(ArrayList<Integer> numList) {
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

    public static void initializeArrays() {
        try {
            File day1Input = new File("src/Inputs/Day1Input.txt");
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
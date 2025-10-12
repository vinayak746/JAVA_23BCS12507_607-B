import java.util.ArrayList;
import java.util.Scanner;

public class Autoboxing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> numbers = new ArrayList<>();

        System.out.println("Enter integers as strings (type 'end' to stop):");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("end")) break;
            try {
                // String Parsing and Autoboxing
                Integer n = Integer.parseInt(input);
                numbers.add(n);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer or 'end'.");
            }
        }

        int sum = 0;
        // Unboxing while using enhanced for-loop
        for (Integer num : numbers) {
            sum += num;
        }
        System.out.println("Total Sum: " + sum);
    }
}

//package com.mark;

import java.util.Scanner;

/**
 * This class provides input methods to capture and parse
 * User's input values.
 */
public class Input {
    // Creates scanner object.
    private static Scanner scanner = new Scanner(System.in);

    public static int getPositiveIntInput(String question) {
        // Converts input to an integer.
        if (question != null) {
            System.out.println(question);
        }
        // Won't continue unless an integer is provided.
        while (true) {
            try {
                String stringInput = scanner.nextLine();
                int intInput = Integer.parseInt(stringInput);
                if (intInput >= 0) {
                    return intInput;
                }
                else {
                    System.out.println("Please enter a positive number.");
                }
            }
            catch (NumberFormatException err) {
                System.out.println("Please type a positive number.");
            }
        }
    }

    public static double getDoubleInput(String question) {
        if (question != null) {
            System.out.println(question);
        }
        while (true) {
            try {
                String stringInput = scanner.nextLine();
                double doubleInput = Double.parseDouble(stringInput);
                if (doubleInput >= 0) {
                    return doubleInput;
                }
                else {
                    System.out.println("Please enter a positive decimal number.");
                }
            }
            catch (NumberFormatException err) {
                System.out.println("Please type a correct time number.");
            }
        }
    }

    public static String getStringInput(String question) {
        // Captures input.
        if (question != null) {
            System.out.println(question);
        }
        String entry = scanner.nextLine();
        return entry;
    }
}
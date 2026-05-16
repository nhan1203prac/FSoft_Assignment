package Assignment_Day1;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StringProcessor {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter string S: ");
        String s = sc.nextLine();

        // a. reverse
        System.out.println("Reverse: " + new StringBuilder(s).reverse());

        // b. uppercase
        System.out.println("Uppercase: " + s.toUpperCase());

        // c. lowercase
        System.out.println("Lowercase: " + s.toLowerCase());

        // d. frequency table
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : s.toCharArray()) freq.put(c, freq.getOrDefault(c, 0) + 1);
        System.out.println("Frequency: " + freq);

        // e. substring from n to m
        System.out.print("Enter n (start index): "); int n = sc.nextInt();
        System.out.print("Enter m (end index): ");   int m = sc.nextInt();
        System.out.println("Substring: " + s.substring(n, m + 1));
    }
}
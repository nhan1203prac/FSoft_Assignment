package Assignment_Day1;

import java.util.Scanner;

public class DigitSumProduct {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter m: ");
        int m = Math.abs(sc.nextInt());
        int sum = 0, product = 1;
        while (m > 0) {
            int digit = m % 10;
            sum += digit;
            product *= digit;
            m /= 10;
        }
        System.out.println("Sum = " + sum + ", Product = " + product);
    }
}
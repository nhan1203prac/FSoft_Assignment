package Assignment_Day1;

import java.util.Scanner;

public class SumPart2 {
    static long factorial(int k) {
        long result = 1;
        for (int i = 2; i <= k; i++) result *= i;
        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter n: ");
        int n = sc.nextInt();
        double s = 0;
        for (int i = 1; i <= n; i++) s += 1.0 / factorial(2 * i - 1);
        System.out.printf("S = %.6f%n", s);
    }
}

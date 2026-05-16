package Assignment_Day1;

import java.util.Scanner;

public class SumPart1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter n: ");
        int n = sc.nextInt();
        double s = 0;
        for (int i = 1; i <= n; i++) s += 1.0 / i;
        System.out.printf("S = %.4f%n", s);
    }
}

package Assignment3;

import java.util.Scanner;
import java.util.Stack;

public class FibonacciSequence {
    public static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n - 1) + fib(n - 2);
    }

    public static void printFibSequence(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(fib(i) + " ");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("n = ");
        int n = sc.nextInt();
        System.out.print("Fibonacci series of " + n + " numbers is: ");
        printFibSequence(n);
    }
}

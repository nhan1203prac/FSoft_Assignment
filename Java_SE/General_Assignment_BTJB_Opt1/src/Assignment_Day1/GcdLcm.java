package Assignment_Day1;

import java.util.Scanner;

public class GcdLcm {
    static int gcd(int a, int b) {
        if(b == 0) return a;
        return gcd(b, a % b);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a: "); int a = sc.nextInt();
        System.out.print("Enter b: "); int b = sc.nextInt();
        int g = gcd(a, b);
        System.out.println("GCD = " + g);
        System.out.println("LCM = " + (a * b) / g);
    }
}
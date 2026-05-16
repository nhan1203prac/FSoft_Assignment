package Assignment_Day1;

import java.util.Scanner;

public class MatrixProcessor {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter m (rows): "); int m = sc.nextInt();
        System.out.print("Enter n (cols): "); int n = sc.nextInt();
        int[][] A = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                System.out.print("A[" + i + "][" + j + "]: ");
                A[i][j] = sc.nextInt(); }

        // b. Product of multiples of 3 in first row
        long product = 1;
        boolean found = false;
        for (int j = 0; j < n; j++)
            if (A[0][j] % 3 == 0) {
                product *= A[0][j];
                found = true;
            }
        System.out.println("Product of multiples of 3 in row 0: " + (found ? product : 0));

        // c. X[i] = max of row i
        System.out.print("X (max of each row): ");
        for (int i = 0; i < m; i++) {
            int max = A[i][0];
            for (int j = 1; j < n; j++)
                if (A[i][j] > max)
                    max = A[i][j];
            System.out.print(max + " ");
        }
        System.out.println();
    }
}
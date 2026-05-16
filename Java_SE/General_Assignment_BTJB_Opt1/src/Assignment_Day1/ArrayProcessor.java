package Assignment_Day1;

import java.util.Arrays;
import java.util.Scanner;

public class ArrayProcessor {
    public static int binarySearch(int[] a, int key) {
        int left = 0;
        int right = a.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (a[mid] == key) {
                return mid;
            } else if (a[mid] < key) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -(left) - 1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter n: "); int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.print("a[" + i + "]: ");
            a[i] = sc.nextInt();
        }

        // b. Sum of positive odd numbers
        int sum = 0;
        for (int x : a){
            if (x > 0 && x % 2 != 0){
                sum += x;
            }
        }
        System.out.println("Sum of positive odd numbers: " + sum);

        // c. Search for k
        System.out.print("Enter k: ");
        int k = sc.nextInt();
        System.out.print("Indices of " + k + ": ");
        for (int i = 0; i < n; i++){
            if (a[i] == k){
                System.out.print(i + " ");
            }
        }
        System.out.println();

        // d. Sort ascending
        Arrays.sort(a);
        System.out.println("Sorted: " + Arrays.toString(a));

        // e. Insert p maintaining sorted order
        System.out.print("Enter p: ");
        int p = sc.nextInt();
        int[] b = new int[n + 1];
        int pos = binarySearch(a, p);
        if (pos < 0) pos = -pos - 1;
        for (int i = 0, j = 0; i < b.length; i++) {
            if (i == pos) {
                b[i] = p;
            } else {
                b[i] = a[j++];
            }
        }

        System.out.println("After insert: " + Arrays.toString(b));
    }


}
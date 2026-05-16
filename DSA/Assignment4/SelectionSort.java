package Assignment4;

import java.util.ArrayList;
import java.util.Scanner;

public class SelectionSort {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> list = new ArrayList<>();
        String choice;

        do {
            System.out.print("Nhập giá trị phần tử: ");
            int value = sc.nextInt();
            list.add(value);

            System.out.print("Bạn có muốn nhập tiếp không? (Y/N): ");
            choice = sc.next();
        } while (choice.equalsIgnoreCase("Y"));

        System.out.println();

        int n = list.size();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = list.get(i);
        }

        selectionSort(arr);

        System.out.println("\nMảng sau khi đã sắp xếp xong: ");
        printArray(arr);

        sc.close();
    }

    public static void selectionSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;

            System.out.print("Mảng sau bước " + (i + 1) + ": ");
            printArray(arr);
        }
    }

    public static void printArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}

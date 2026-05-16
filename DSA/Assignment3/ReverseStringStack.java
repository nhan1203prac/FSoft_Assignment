package Assignment3;

import java.util.Scanner;


public class ReverseStringStack {
    static class Stack {
        private int maxSize;
        private int capacity;
        private char[] arr;

        public Stack(int size) {
            this.maxSize = size;
            this.arr = new char[maxSize];
            this.capacity = -1;
        }

        public void push(char item) {
            if (capacity < maxSize - 1) {
                arr[++capacity] = item;
            }
        }

        public char pop() {
            if (capacity == -1) return ' ';
            return arr[capacity--];
        }

        public boolean isEmpty() {
            return capacity == -1;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        Stack stack = new Stack(str.length());

        for (int i = 0; i < str.length(); i++) {
            stack.push(str.charAt(i));
        }

        while (!stack.isEmpty()) {
            System.out.print(stack.pop());
        }
    }
}

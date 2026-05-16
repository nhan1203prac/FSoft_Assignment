package Assignment3;

import java.util.Scanner;



public class StackSimulation {
    static class Node {
        char val;
        Node next;
        Node() {}
        Node(char val) { this.val = val; }
        Node(char val, Node next) { this.val = val; this.next = next; }
    }

    static class Stack {
        private Node top;

        public void push(char item) {
            Node newElement = new Node(item);
            newElement.next = this.top;
            this.top = newElement;
        }

        public char pop() {
            if (this.top == null) return ' ';
            char val = this.top.val;
            this.top = this.top.next;
            return val;
        }

        public boolean isEmpty() {
            return this.top == null;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Stack stack = new Stack();
        String str = sc.nextLine();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != '*') {
                stack.push(c);
            } else {
                if (!stack.isEmpty()) {
                    System.out.print(stack.pop());
                }
            }
        }
    }
}

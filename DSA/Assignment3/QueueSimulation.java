package Assignment3;

import java.util.Scanner;

public class QueueSimulation {
    static class Node {
        private char val;
        private Node next;
        Node() {}
        Node(char val) {this.val = val;}
        Node(char val, Node next) {this.val = val; this.next = next;}
    }

    static class Queue {
        private Node head;
        private Node tail;
        private int size;

        Queue() {
            this.head = null;
            this.tail = null;
            this.size = 0;
        }

        public void enqueue(char val) {
            Node newElement = new Node(val);

            if (tail == null) {
                this.head = this.tail = newElement;
            } else {
                this.tail.next = newElement;
                this.tail = newElement;
            }
            size++;
        }

        public char dequeue() {
            if (this.head == null) return ' ';
            char val = this.head.val;
            this.head = this.head.next;

            if (this.head == null) {
                this.tail = null;
            }

            size--;
            return val;
        }

        public char peek() {
            if (this.head == null) return ' ';
            return this.head.val;
        }

        public boolean isEmpty() {
            return this.head == null;
        }

        public int getSize() {
            return this.size;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập chuỗi thao tác (format: E A S * Y *): ");
        String str = sc.nextLine();

        Queue queue = new Queue();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (c == ' ') continue;

            if (c == '*') {
                if (!queue.isEmpty()) {
                    System.out.print(queue.dequeue() + " ");
                }
            } else {
                queue.enqueue(c);
            }
        }
    }
}

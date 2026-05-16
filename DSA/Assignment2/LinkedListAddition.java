package Assignment2;

import java.util.Objects;
import java.util.Scanner;

public class LinkedListAddition {
    private static class Node {
        private int val;
        private Node next;

        Node() {}
        Node(int val) {
            this.val = val;
        }
        Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    public static Node createList(int[] arr) {
        if (arr.length == 0) return null;
        Node head = new Node(arr[0]);
        Node current = head;
        for (int i = 1; i < arr.length; i++) {
            current.next = new Node(arr[i]);
            current = current.next;
        }
        return head;
    }

    public static void printList(Node head) {
        if (head == null) {
            System.out.println("[]");
            return;
        }
        System.out.print("[");
        Node current = head;
        while (current != null) {
            System.out.print(current.val + (current.next != null ? ", " : ""));
            current = current.next;
        }
        System.out.println("]");
    }

    public static Node add(Node int1, Node int2) {
        Node res = new Node();
        Node cur = res;
        int carry = 0;

        while (int1 != null || int2 != null || carry != 0) {
            int sum = carry;

            if (int1 != null) {
                sum += int1.val;
                int1 = int1.next;
            }

            if (int2 != null) {
                sum += int2.val;
                int2 = int2.next;
            }

            carry = sum / 10;
            cur.next = new Node(sum % 10);
            cur = cur.next;
        }

        return res.next;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== CỘNG 2 LINKED LIST ===");

        System.out.print("Nhập số thứ nhất (format: 2,4,3): ");
        String line1 = sc.nextLine();
        Node list1 = createFromLine(line1);

        System.out.print("Nhập số thứ hai (format: 2,4,3): ");
        String line2 = sc.nextLine();
        Node list2 = createFromLine(line2);

        System.out.print("List 1: "); printList(list1); System.out.println();
        System.out.print("List 2: "); printList(list2); System.out.println();

        Node result = add(list1, list2);

        System.out.print("Kết quả: ");
        printList(result);
    }

    private static Node createFromLine(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        String[] parts = line.split(",");
        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i].trim());
        }
        return createList(arr);
    }
}

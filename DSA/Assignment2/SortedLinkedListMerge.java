package Assignment2;

import java.util.LinkedList;
import java.util.Scanner;

public class SortedLinkedListMerge {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode merged = new ListNode();
        ListNode cur = merged;

        while (list1 != null && list2 != null) {
            if (list1.val > list2.val) {
                cur.next = list2;
                list2 = list2.next;
            } else {
                cur.next = list1;
                list1 = list1.next;
            }
            cur = cur.next;
        }

        cur.next = (list1 != null) ? list1 : list2;
        return merged.next;
    }

    public static ListNode createList(int[] arr) {
        if (arr.length == 0) return null;
        ListNode head = new ListNode(arr[0]);
        ListNode current = head;
        for (int i = 1; i < arr.length; i++) {
            current.next = new ListNode(arr[i]);
            current = current.next;
        }
        return head;
    }

    public static void printList(ListNode head) {
        if (head == null) {
            System.out.println("[]");
            return;
        }
        System.out.print("[");
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val + (current.next != null ? ", " : ""));
            current = current.next;
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] input1 = sc.nextLine().split(",");
        String[] input2 = sc.nextLine().split(",");

        int[] arr1 = new int[input1.length];
        int[] arr2 = new int[input2.length];

        for (int i = 0; i < input1.length; i++) {
            arr1[i] = Integer.parseInt(input1[i].trim());
        }
        for (int i = 0; i < input2.length; i++) {
            arr2[i] = Integer.parseInt(input2[i].trim());
        }

        ListNode list1 = createList(arr1);
        ListNode list2 = createList(arr2);
        ListNode mergedList = new SortedLinkedListMerge().mergeTwoLists(list1, list2);
        printList(mergedList);
    }
}

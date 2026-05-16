package Assignment_Day1;

public class PassValue {
    static void changeValue(int x){
        x = 100;
    }

    static void changeArray(int a[]){
        a[0] = 100;
    }

    public static void main(String[] args) {
        int a = 10;
        changeValue(a);
        System.out.println("a after changeValue: " + a);

        int[] arr = {10, 20};
        changeArray(arr);
        System.out.println("arr[0] after changeArray: " + arr[0]);
    }
}

package Assignment_Day1;

public class JavaDoc_Comment {
    // Standard comment: a is the first integer, b is the second integer

    /**
     * Javadoc comment:
     * @param a số nguyên thứ nhất
     * @param b số nguyên thứ hai
     * @return ước chung lớn nhất
     */
    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}

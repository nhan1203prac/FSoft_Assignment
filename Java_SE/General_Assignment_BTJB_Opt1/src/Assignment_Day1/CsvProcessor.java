package Assignment_Day1;


import java.io.*;

public class CsvProcessor {
    static final String PHONE_REGEX = "^(0|\\+84)[0-9]{9}$";
    static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

    public static void readAndDisplay(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    public static void validateAndReport(String filePath, String errorFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(errorFile))) {
            String line; int row = 1;
            br.readLine();
            while ((line = br.readLine()) != null) {
                row++;
                String[] parts = line.split(",");
                if (!parts[2].matches(PHONE_REGEX))
                    bw.write("Row " + row + ": Invalid phone - " + parts[2] + "\n");
                if (!parts[3].matches(EMAIL_REGEX))
                    bw.write("Row " + row + ": Invalid email - " + parts[3] + "\n");
            }
        }
        System.out.println("Validation done. Check error.txt");
    }

    public static void main(String[] args) {
        String inputFile = "STD.csv";
        String errorFile = "error.txt";

        try {
            System.out.println("--- File Content ---");
            CsvProcessor.readAndDisplay(inputFile);

            System.out.println("\n--- Validating ---");
            CsvProcessor.validateAndReport(inputFile, errorFile);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
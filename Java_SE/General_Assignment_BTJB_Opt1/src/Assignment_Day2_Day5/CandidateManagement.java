package Assignment_Day2_Day5;

import Assignment_Day2_Day5.DAO.CadidateDAO;
import Assignment_Day2_Day5.Entities.*;
import Assignment_Day2_Day5.Exception.BirthDayException;
import Assignment_Day2_Day5.Exception.EmailException;
import Assignment_Day2_Day5.Validate.Validation;

import java.util.*;

public class CandidateManagement {
    private static final Scanner sc = new Scanner(System.in);
    private static final CadidateDAO dao = new CadidateDAO();

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== CANDIDATE MANAGEMENT =====");
            System.out.println("1. Add candidates");
            System.out.println("2. Display all candidates");
            System.out.println("3. Update candidate (CONCUR_UPDATABLE)");
            System.out.println("4. Insert candidate (CONCUR_UPDATABLE)");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addCandidates();
                case 2 -> displayAllCandidates();
                case 3 -> updateCandidate();
                case 4 -> insertCandidateUpdatable();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }


    private static void addCandidates() {
        Candidate.resetCount();
        List<Candidate> sessionList = new ArrayList<>();

        while (true) {
            System.out.println("\n--- Add Candidate ---");
            System.out.println("1. Experience");
            System.out.println("2. Fresher");
            System.out.println("3. Intern");
            System.out.println("0. Stop");
            System.out.print("Type: ");

            int type = -1;
            while (true) {
                try {
                    System.out.print("Type: ");
                    type = Integer.parseInt(sc.nextLine());
                    if (type >= 0 && type <= 3) break;
                    System.out.println("Invalid type! Please enter 0-3.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a number.");
                }
            }

            if (type == 0) break;

            try {
                Candidate c = inputCandidate(type);
                dao.insertCandidate(c);
                dao.insertExtraInfo(c);
                sessionList.add(c);
                System.out.println("Added successfully!");

            } catch (BirthDayException e) {
                System.out.println("Birthday error: " + e.getMessage());
            } catch (EmailException e) {
                System.out.println("Email error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("The system has encountered an unexpected problem, sincerely sorry!!!");
            }
        }

        System.out.println("\nTotal candidates added this session: " + Candidate.getCandidateCount());

        String resultString = "";
        for (Candidate c : sessionList){
            resultString += c.getFullName() + ", ";
        }
        System.out.println("[String] Names: " + resultString);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < sessionList.size(); i++) {
            sb.append(sessionList.get(i).getFullName());
            if (i < sessionList.size() - 1) sb.append(", ");
        }
        System.out.println("[StringBuffer] Names: " + sb);
    }

    // ==================== CASE 2 ====================

    private static void displayAllCandidates() {
        try {
            List<Candidate> list = dao.getAllCandidates();
            if (list.isEmpty()) {
                System.out.println("No candidates found.");
                return;
            }

            list.forEach(Candidate::showInfo);

            String resultString = "";
            for (Candidate c : list) resultString += c.getFullName() + ", ";
            System.out.println("[String] Names: " + resultString);

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).getFullName());
                if (i < list.size() - 1) sb.append(", ");
            }
            System.out.println("[StringBuffer] Names: " + sb);

        } catch (Exception e) {
            System.out.println("The system has encountered an unexpected problem, sincerely sorry!!!");
        }
    }

    // ==================== CASE 3 ====================

    private static void updateCandidate() {
        try {
            System.out.print("Enter CandidateID to update: ");
            String id = sc.nextLine();
            if (!dao.isIDExist(id)) {
                System.out.println("CandidateID not found!");
                return;
            }

            System.out.print("Type (1=Experience, 2=Fresher, 3=Intern): ");
            int type = sc.nextInt();
            sc.nextLine();

            Candidate c = inputCandidateWithID(id, type);
            dao.updateCandidateUpdatable(c);
            System.out.println("Updated successfully!");

        } catch (BirthDayException e) {
            System.out.println("Birthday error: " + e.getMessage());
        } catch (EmailException e) {
            System.out.println("Email error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("The system has encountered an unexpected problem, sincerely sorry!!!");
        }
    }

    // ==================== CASE 4 ====================

    private static void insertCandidateUpdatable() {
        try {
            System.out.print("Type (1=Experience, 2=Fresher, 3=Intern): ");
            int type = sc.nextInt();
            sc.nextLine();

            Candidate c = inputCandidate(type);
            dao.insertCandidateUpdatable(c);
            System.out.println("Inserted successfully!");

        } catch (BirthDayException e) {
            System.out.println("Birthday error: " + e.getMessage());
        } catch (EmailException e) {
            System.out.println("Email error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("The system has encountered an unexpected problem, sincerely sorry!!!");
        }
    }


    private static Candidate inputCandidate(int type) throws Exception {
        CandidateType candidateType = CandidateType.getType(type);
        String id = "";

        while(true){
            try {
                System.out.print("ID (" + candidateType.getPrefix() + "xxx): ");
                id = sc.nextLine();

                Validation.validateCandidateID(id, candidateType);

                if (dao.isIDExist(id)) {
                    System.out.println("CandidateID '" + id + "' already exists!");
                    continue;
                }

                break;

            } catch (Exception e) {
                System.err.println("Input Error: " + e.getMessage());
            }
        }

        return inputCandidateWithID(id, type);
    }

    private static Candidate inputCandidateWithID(String id, int type) throws Exception {
        System.out.print("Full Name: ");
        String name = sc.nextLine();
        System.out.print("Birthday (yyyy-MM-dd): ");
        String dob = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();

        Validation.validateBirthDay(dob);
        Validation.validateEmail(email);

        return switch (type) {
            case 1 -> {
                System.out.print("Exp In Year: ");
                int exp = sc.nextInt(); sc.nextLine();
                System.out.print("Pro Skill: ");
                String skill = sc.nextLine();
                yield new Experience(id, name, dob, phone, email, exp, skill);
            }
            case 2 -> {
                System.out.print("Graduation Date (yyyy-MM-dd): ");
                String gd = sc.nextLine();
                System.out.print("Graduation Rank: ");
                String gr = sc.nextLine();
                System.out.print("Education: ");
                String edu = sc.nextLine();
                yield new Fresher(id, name, dob, phone, email, gd, gr, edu);
            }
            case 3 -> {
                System.out.print("Majors: ");
                String maj = sc.nextLine();
                System.out.print("Semester: ");
                int sem = sc.nextInt(); sc.nextLine();
                System.out.print("University: ");
                String uni = sc.nextLine();
                yield new Intern(id, name, dob, phone, email, maj, sem, uni);
            }
            default -> throw new Exception("Invalid type!");
        };
    }


}
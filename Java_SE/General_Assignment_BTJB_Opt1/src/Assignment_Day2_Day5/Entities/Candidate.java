package Assignment_Day2_Day5.Entities;

public abstract class Candidate {
    private String candidateID;
    private String fullName;
    private String birthDay;
    private String phone;
    private String email;
    private CandidateType candidateType;
    private static int candidateCount = 0;

    public Candidate(String candidateID, String fullName, String birthDay,
                     String phone, String email, CandidateType candidateType) {
        this.candidateID = candidateID;
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.phone = phone;
        this.email = email;
        this.candidateType = candidateType;
        candidateCount++;
    }

    public String getCandidateID() { return candidateID; }
    public String getFullName() { return fullName; }
    public String getBirthDay() { return birthDay; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public CandidateType getCandidateType() { return candidateType; }
    public static int getCandidateCount() { return candidateCount; }
    public static void resetCount() { candidateCount = 0; }

    public abstract void showInfo();
}
package Assignment_Day2_Day5.Entities;

public class Fresher extends Candidate {
    private String graduationDate;
    private String graduationRank;
    private String education;

    public Fresher(String id, String name, String dob, String phone, String email,
                   String graduationDate, String graduationRank, String education) {
        super(id, name, dob, phone, email, CandidateType.FRESHER);
        this.graduationDate = graduationDate;
        this.graduationRank = graduationRank;
        this.education = education;
    }

    public String getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(String graduationDate) {
        this.graduationDate = graduationDate;
    }

    public String getGraduationRank() {
        return graduationRank;
    }

    public void setGraduationRank(String graduationRank) {
        this.graduationRank = graduationRank;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Override
    public void showInfo() {
        System.out.println("=== Fresher Candidate ===");
        System.out.println("ID: " + getCandidateID());
        System.out.println("Name: " + getFullName());
        System.out.println("Birthday: " + getBirthDay());
        System.out.println("Phone: " + getPhone());
        System.out.println("Email: " + getEmail());
        System.out.println("GraduationDate: " + graduationDate);
        System.out.println("GraduationRank: " + graduationRank);
        System.out.println("Education: " + education);
    }
}
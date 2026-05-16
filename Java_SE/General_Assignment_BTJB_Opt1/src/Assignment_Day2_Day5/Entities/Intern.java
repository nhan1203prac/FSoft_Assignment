package Assignment_Day2_Day5.Entities;

public class Intern extends Candidate {
    private String majors;
    private int semester;
    private String universityName;

    public Intern(String id, String name, String dob, String phone, String email,
                  String majors, int semester, String universityName) {
        super(id, name, dob, phone, email, CandidateType.INTERN);
        this.majors = majors;
        this.semester = semester;
        this.universityName = universityName;
    }

    public String getMajors() {
        return majors;
    }

    public void setMajors(String majors) {
        this.majors = majors;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    @Override
    public void showInfo() {
        System.out.println("=== Intern Candidate ===");
        System.out.println("ID: " + getCandidateID());
        System.out.println("Name: " + getFullName());
        System.out.println("Birthday: " + getBirthDay());
        System.out.println("Phone: " + getPhone());
        System.out.println("Email: " + getEmail());
        System.out.println("Majors: " + majors);
        System.out.println("Semester: " + semester);
        System.out.println("University: " + universityName);
    }
}
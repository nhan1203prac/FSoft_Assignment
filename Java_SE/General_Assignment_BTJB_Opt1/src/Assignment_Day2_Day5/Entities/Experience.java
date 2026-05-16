package Assignment_Day2_Day5.Entities;

public class Experience extends Candidate {
    private int expInYear;
    private String proSkill;

    public Experience(String id, String name, String dob, String phone,
                      String email, int expInYear, String proSkill) {
        super(id, name, dob, phone, email, CandidateType.EXPERIENCE);
        this.expInYear = expInYear;
        this.proSkill = proSkill;
    }

    public int getExpInYear() {
        return expInYear;
    }

    public void setExpInYear(int expInYear) {
        this.expInYear = expInYear;
    }

    public String getProSkill() {
        return proSkill;
    }

    public void setProSkill(String proSkill) {
        this.proSkill = proSkill;
    }

    @Override
    public void showInfo() {
        System.out.println("=== Experience Candidate ===");
        System.out.println("ID: " + getCandidateID());
        System.out.println("Name: " + getFullName());
        System.out.println("Birthday: " + getBirthDay());
        System.out.println("Phone: " + getPhone());
        System.out.println("Email: " + getEmail());
        System.out.println("ExpInYear: " + expInYear);
        System.out.println("ProSkill: " + proSkill);
    }
}
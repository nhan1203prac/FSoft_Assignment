package Assignment_Day2_Day5.Entities;

public enum CandidateType {
    EXPERIENCE(0, "EXP"), FRESHER(1, "FRE"),
    INTERN(2, "INT");

    private final int value;
    private final String prefix;
    CandidateType(int value, String prefix) {
        this.value = value;
        this.prefix = prefix;
    }
    public int getValue() {
        return value;
    }

    public String getPrefix() {
        return prefix;
    }

    public static CandidateType getType(int value) {
        for(CandidateType ct : CandidateType.values()) {
            if(ct.value == value) {
                return ct;
            }

        }
        return null;
    }


}

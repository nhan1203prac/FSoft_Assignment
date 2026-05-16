package Assignment_Day2_Day5.Entities;

public class Certificate {
    private String cerfiticatedID;
    private String certificatedName;
    private String certificateRank;
    private String certificatedDate;

    public Certificate() {
    }

    public Certificate(String cerfiticatedID, String certificatedName, String certificateRank, String certificatedDate) {
        this.cerfiticatedID = cerfiticatedID;
        this.certificatedName = certificatedName;
        this.certificateRank = certificateRank;
        this.certificatedDate = certificatedDate;
    }

    public String getCerfiticatedID() {
        return cerfiticatedID;
    }

    public void setCerfiticatedID(String cerfiticatedID) {
        this.cerfiticatedID = cerfiticatedID;
    }

    public String getCertificatedName() {
        return certificatedName;
    }

    public void setCertificatedName(String certificatedName) {
        this.certificatedName = certificatedName;
    }

    public String getCertificateRank() {
        return certificateRank;
    }

    public void setCertificateRank(String certificateRank) {
        this.certificateRank = certificateRank;
    }

    public String getCertificatedDate() {
        return certificatedDate;
    }

    public void setCertificatedDate(String certificatedDate) {
        this.certificatedDate = certificatedDate;
    }
}


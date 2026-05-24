package com.hr.model;


public class Personal {

    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private String telephone;
    private String email;
    private String region;
    private String hobbies;
    private String description;

    public Personal() {
    }

    public Personal(int id, String firstName, String lastName, String gender,
                    String telephone, String email, String region,
                    String hobbies, String description) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.telephone = telephone;
        this.email = email;
        this.region = region;
        this.hobbies = hobbies;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String v) {
        this.firstName = v;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String v) {
        this.lastName = v;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String v) {
        this.gender = v;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String v) {
        this.telephone = v;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String v) {
        this.email = v;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String v) {
        this.region = v;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String v) {
        this.hobbies = v;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String v) {
        this.description = v;
    }

    @Override
    public String toString() {
        return "Personal{id=" + id + ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' + '}';
    }
}

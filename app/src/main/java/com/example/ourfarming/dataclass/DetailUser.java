package com.example.ourfarming.dataclass;

public class DetailUser {

    String name;
    String email;
    String phone;
    String profileimage;
    String userId;

    public DetailUser() {
        // Default constructor required
    }

    public DetailUser(String name, String email, String phone, String userId, String profileimage) {
        this.userId = userId;
        this.profileimage = profileimage;
        this.name = name;
        this.email = email;
        this.phone = phone;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
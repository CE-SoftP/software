package com.app.customer;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DataForm {

    private int userId;
    private String userName;
    private String email;

    private String password;
    private String confirmPassword;

    private Date birthDate;
    private String role ;

    @DateTimeFormat(pattern = "mm-dd-yyyy")
    public Date getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String gender;

    public DataForm() {
        // default constructor
    }
    public String getUserName() {
        return userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getEmail() {
        return email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }


    @Override
    public String toString() {
        return "DataForm{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}


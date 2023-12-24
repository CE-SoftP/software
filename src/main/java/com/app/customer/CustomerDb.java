package com.app.customer;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Entity
@Table(name = "CUSTOMER")
public class CustomerDb {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name = "customer_seq", sequenceName = "CAR_DB.CUSTOMER_SEQ ", allocationSize = 1)
    @Column(name="CUST_ID")
    private int id;
    private String name;

    public String getGender() {
        return gender;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    private String email;
 private String pass;
    @Column(name="conf_pass")
 private String confPass;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="birthdate")
 private Date birthDate;
 private String gender;
 private String role;

    public String getEmail() {
        return email;
    }
    public void setConfPass(String confPass) {
        this.confPass = confPass;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public int getId() {return id;}

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name =name;
    }

    public void setEmail(String mail) {

        this.email=mail;
    }


    public void setId(int userId) {
        this.id =userId ;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Column(name = "profile_image")
    private String profileImage;

}

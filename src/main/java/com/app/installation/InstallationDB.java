package com.app.installation;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "INSTALLATION")
public class InstallationDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "CUSTOMER_ID")
    private int customerId;

    @Column(name = "CAR_MODEL")
    private String carModel;


    @Column(name = "AVAILABILITY")
    private String availability;

    @Column(name = "CHECKED")
    private String checked;

    @Column(name = "CHECKED_USER")
    private String checkedUser;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="INSTALLDATE")
    private Date installDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="OTHERDATE")
    private Date otherDate;

    @DateTimeFormat(pattern = "HH:mm:ss")
    @Column(name = "INSTALLTIME")
    private String installTime;


    public String getCheckedUser() {
        return checkedUser;
    }

    public void setCheckedUser(String checkedUser) {
        this.checkedUser = checkedUser;
    }

    public String  getInstallTime() {
        return installTime;
    }

    public void setInstallTime(String installTime) {
        this.installTime = installTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
    }

    public Date getOtherDate() {
        return otherDate;
    }

    public void setOtherDate(Date otherDate) {
        this.otherDate = otherDate;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public void setCHECKED_USER(String yes) {

    }
}


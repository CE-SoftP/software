package com.app.Installation;
// Installation.java
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


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="INSTALLDATE")
    private Date installDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="OTHERDATE")
    private Date otherDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerName() {
        return customerId;
    }

    public void setCustomerName(int customerId) {
        this.customerId = customerId;
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
}


package com.app.customer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;


@Entity
@Table(name = "Appointment")

public class AppointmentDb {
    @Id
    @Column(name = "app_id")
    private int appId;

    private String service;
    private String isreceive;
    private String day;
    private String hour;

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getIsreceive() {
        return isreceive;
    }

    public void setIsreceive(String isreceive) {
        this.isreceive = isreceive;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}

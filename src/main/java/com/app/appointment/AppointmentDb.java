package com.app.appointment;

import jakarta.persistence.*;



@Entity
@Table(name = "appointment")

public class AppointmentDb {
    @Id
    @Column(name = "app_id")
    private int appointmentId;

    @Column(name = "service")
    private String apponitmentService;
    @Column(name = "isreceive")
    private String apponitmentIsReceive;
    @Column(name = "day")
    private String apponitmentDay;
    @Column(name = "hour")
    private String apponitmentHour;

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getApponitmentService() {
        return apponitmentService;
    }

    public void setApponitmentService(String apponitmentService) {
        this.apponitmentService = apponitmentService;
    }

    public String getApponitmentIsReceive() {
        return apponitmentIsReceive;
    }

    public void setApponitmentIsReceive(String apponitmentIsReceive) {
        this.apponitmentIsReceive = apponitmentIsReceive;
    }

    public String getApponitmentHour() {
        return apponitmentHour;
    }

    public void setApponitmentHour(String apponitmentHour) {
        this.apponitmentHour = apponitmentHour;
    }

    public String getApponitmentDay() {
        return apponitmentDay;
    }

    public void setApponitmentDay(String apponitmentDay) {
        this.apponitmentDay = apponitmentDay;
    }
}

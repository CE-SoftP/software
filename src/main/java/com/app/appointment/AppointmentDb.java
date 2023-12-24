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



    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }



    public void setApponitmentService(String apponitmentService) {
        this.apponitmentService = apponitmentService;
    }



    public void setApponitmentHour(String apponitmentHour) {
        this.apponitmentHour = apponitmentHour;
    }


    public void setApponitmentDay(String apponitmentDay) {
        this.apponitmentDay = apponitmentDay;
    }
}

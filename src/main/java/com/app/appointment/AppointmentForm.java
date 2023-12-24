package com.app.appointment;

public class AppointmentForm {
    private int appId;
    private int custId;
    private String day;
    private String service;
    private String isreceive;
    private String hour;



    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}

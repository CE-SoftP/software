package com.app.appointment;

public class AppointmentForm {
    private int appId;
    private int custId;
    private String day;
    private String service;
    private String isreceive;
    private String hour;

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

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
}

package com.example.repairit;

public class AppointmentObject {

    String appointmentId;
    String vehicleNo;
    String vehicleModel;
    String appointedJob;
    String date;

    public AppointmentObject() {}

    public AppointmentObject(String appointmentId, String vehicleNo, String vehicleModel, String appointedJob, String date) {
        this.appointmentId = appointmentId;
        this.vehicleNo = vehicleNo;
        this.vehicleModel = vehicleModel;
        this.appointedJob = appointedJob;
        this.date = date;
    }



    public String getAppointmentId() {
        return appointmentId;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public String getAppointedJob() {
        return appointedJob;
    }

    public String getDate() {
        return date;
    }
}

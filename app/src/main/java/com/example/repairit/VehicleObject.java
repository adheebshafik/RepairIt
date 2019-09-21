package com.example.repairit;

public class VehicleObject {

    private String vehicleNo;
    private String vehicleModel;
    private String vehicleType;
    private String vehicleColor;

    public VehicleObject() {}

    public VehicleObject(String vehicleNo, String vehicleModel, String vehicleType, String vehicleColor) {
        this.vehicleNo = vehicleNo;
        this.vehicleModel = vehicleModel;
        this.vehicleType = vehicleType;
        this.vehicleColor = vehicleColor;
    }



    public String getVehicleNo() {
        return vehicleNo;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }
}

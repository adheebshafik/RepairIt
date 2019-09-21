package com.example.repairit;

public class QuotationObject {

    String quotationId, vehicleNo, vehicleModel, imgurl, jobAppointed;

    public QuotationObject() {}

    public QuotationObject(String quotationId, String vehicleNo, String vehicleModel, String imgurl, String jobAppointed) {
        this.quotationId = quotationId;
        this.vehicleNo = vehicleNo;
        this.vehicleModel = vehicleModel;
        this.imgurl = imgurl;
        this.jobAppointed = jobAppointed;
    }

    public QuotationObject(String quotationId, String vehicleNo, String vehicleModel, String jobAppointed) {
        this.quotationId = quotationId;
        this.vehicleNo = vehicleNo;
        this.vehicleModel = vehicleModel;
        this.jobAppointed = jobAppointed;
    }

    public QuotationObject(String quotationId, String imgurl) {
        this.quotationId = quotationId;
        this.imgurl = imgurl;
    }

    public String getQuotationId() {
        return quotationId;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getJobAppointed() {
        return jobAppointed;
    }


}

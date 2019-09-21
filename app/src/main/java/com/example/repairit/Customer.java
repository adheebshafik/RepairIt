package com.example.repairit;

public class Customer {

    String custId, firstName, address, mobile, email, password ;

    public Customer(){}

    public Customer(String custId, String firstName, String address, String mobile, String email, String password) {
        this.custId = custId;
        this.firstName = firstName;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
    }

    public Customer(String custId, String firstName, String address, String mobile, String email) {
        this.custId = custId;
        this.firstName = firstName;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
    }

    public String getCustId() {
        return custId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getAddress() {
        return address;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

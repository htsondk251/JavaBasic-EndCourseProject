package com.SonHoang;

public class Customer {
    private String id;
    private String fullName, birthDate, address, email, phone;

    public Customer(String id, String fullName, String birthDate, String address, String email, String phone) {
        setId(id);
        setFullName(fullName);
        setBirthDate(birthDate);
        setAddress(address);
        setEmail(email);
        setPhone(phone);
    }

    public Customer() {}

    //setter
    public void setId(String id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //getter
    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String toString() {
        return fullName;
    }

    public void showDetails() {
        System.out.println("name: " + fullName
                            + ", address: " + address
                            + ", email: " + email
                            + ", phone: " + phone);
    }

}

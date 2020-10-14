package com.SonHoang;

public class Customer {
    private String id;
    private String firstName, lastName, birthDate, address, email, phone;
//    long accountNumber;


    public Customer(String id, String firstName, String lastName, String birthDate, String address, String email, String phone) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
        setAddress(address);
        setEmail(email);
        setPhone(phone);
    }

    //setter
    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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
        return lastName + " " + firstName;
    }

    public void showDetails() {
        System.out.println("name: " + lastName + " " + firstName
                            + ", address: " + address
                            + ", email: " + email
                            + ", phone: " + phone);
    }

}

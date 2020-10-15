package com.SonHoang;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Bank {
    private String name, address;
    private List<Account> accounts;
    private List<Customer> customers;
    private Map<String, String> userDatas;

    public Bank(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public Map<String, String> getUserDatas() {
        return userDatas;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public void setUserDatas(Map<String, String> userDatas) {
        this.userDatas = userDatas;
    }

    public void go() {}     //TODO: UI

    public void logIn() {
        while(true) {
            String email, password;
            Scanner in = new Scanner(System.in);
            System.out.println("enter the email");
            email = in.nextLine();
            System.out.println("enter the password");
            password = in.nextLine();
            try {
                if (checkValidity(email, password)) {
                    System.out.println("login successfully");
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean checkValidity(String username, String password) {
        boolean isCompleted = false;
        //check user
        //todo: handle nullpointerexception here or outer circle
        if (userDatas.keySet().contains(username) == false) {
            throw new IllegalArgumentException("email not exist");
        } else {
            //check password
            if (password.equals(userDatas.get(username)) == false) {
                throw new IllegalArgumentException("password not correct");
            } else {
                isCompleted = true;
            }
        }
        return isCompleted;
    }
}

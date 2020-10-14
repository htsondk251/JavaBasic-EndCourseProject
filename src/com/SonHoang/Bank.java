package com.SonHoang;

import java.util.List;

public class Bank {
    private String name, address;
    private List<Account> accounts;

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

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void go() {}     //TODO: UI
}

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

    //TODO: UI
    public void go() {
        System.out.println("Welcome to ABC Bank");
        selectInHompage();
    }

    public void selectInHompage() {
        int choice = 0;
        boolean do1 = false;
        Scanner in = new Scanner(System.in);

        do {
            showMenuHomepage();
            choice = verifyInputNumber(in, 2);
            switch(choice) {
                case 1:
                    openAccount();
                    break;
                case 2:
                    if (logIn()) {
                        selectInAccountPage();
                    }
                    break;
                case 0:
                    do1 = true;
                    break;
            }
        } while(!do1);
    }

    public void selectInAccountPage() {
        //todo: consider initialize in only 1 methods and transfer or initialize in every methods
//        String input="";
        int choice;
        boolean do1 = false;
        Scanner in = new Scanner(System.in);

        do {
            showMenuAccountPage();
            choice = verifyInputNumber(in, 4);
            switch(choice) {
                case 1:
                    System.out.println("Deposit money");
                    break;
                case 2:
                    System.out.println("Withdraw money");
                    break;
                case 3:
                    System.out.println("Close account");
                    break;
                case 4:
                    System.out.println("Open saving");
                    break;
                case 0:
                    do1 = true;
                    break;
            }
        } while(!do1);
    }

    public boolean logIn() {
        String email, password;
        int failTimes = 0, input;
        boolean success = false;
        while(true) {
            Scanner in = new Scanner(System.in);
            System.out.println("enter the email");
            //todo: pre-process input data
            email = in.nextLine();
            System.out.println("enter the password");
            password = in.nextLine();

            try {
                if (verifyAccount(email, password)) {
                    System.out.println("login successfully");
                    success = true;
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                failTimes++;
            }
            if (failTimes >= 3) {
                System.out.print("Forgot password? do you want to reset password? (y/n) ");
                input = verifyInputYN(in);
                if (1 == input) {
//                    success = true;
                    break;
                } else {
                    failTimes = 0;
                }
            }
        }
        if (success == true) {
            Account acc = getAccount(email);
            //todo: handle the case of account not available ??
            if (acc != null) {
                acc.showDetails();
            }
        }
        else {
            resetPassword();
        }
        return success;
    }

    public Account getAccount(String email) {
//        long accountNumber;
        String customerId = "";
        for (Customer c : customers) {
            if (email.equals(c.getEmail())) {
                customerId = c.getId();
                break;
            }
        }
        //todo: handle the case of email not available ??
        for (Account acc : accounts) {
            if (customerId.equals(acc.getCustomerId())) {
                return acc;
            }
        }
        return null;
    }

    public void resetPassword() {
        //TODO: handle forgot password?
        System.out.println("feature not available");
    }

    public boolean verifyAccount(String username, String password) {
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

    //main utility methods

    public int verifyInputNumber(Scanner in, int caseNumber) {
        String input;
        int choice;
        while(true) {
            input = in.nextLine();
            input = input.trim();
            if (input.isEmpty() || (!isNumber(input)) || (isNumber(input) && ((Integer.parseInt(input)) > caseNumber) || ((Integer.parseInt(input)) < 0))) {
                System.out.println("enter integer from 0 -> " + caseNumber);
            } else {
                break;
            }
        }
        choice = Integer.parseInt(input);
        return choice;
    }

    public int verifyInputYN(Scanner in) {
        String input;
        while(true) {
            input = in.nextLine();
            input = input.trim().toLowerCase();
            if (("y".equals(input)) || ("yes".equals(input))) {
                return 1;
            } else if (("n".equals(input)) || ("no".equals(input))) {
                return 0;
            } else {
                System.out.println("enter yes or no");
            }
        }
    }

    //todo: test again
    private boolean isNumber(String s) {
        if(s != "") {
            for (int i = 0; i < s.length(); i++) {
                if (Character.isDigit(s.charAt(i)) == false) return false;
            }
        }
        return true;
    }

    public void showMenuHomepage() {
        System.out.println("Please select the service");
        System.out.println("-----------------------------------");
        System.out.println("1. Open account");
        System.out.println("2. Login");
        System.out.println("0. Quit");
        System.out.println("-----------------------------------");
    }

    public void showMenuAccountPage() {
        System.out.println("Please select the service");
        System.out.println("-----------------------------------");
        System.out.println("1. Deposit money");
        System.out.println("2. Withdraw money");
        System.out.println("3. Close account");
        System.out.println("4. Open saving");
        System.out.println("0. Quit");
        System.out.println("-----------------------------------");
    }

    public void openAccount() {
        String input;
        Scanner in = new Scanner(System.in);
        System.out.println("enter the name");
        input = in.nextLine();


    }
}


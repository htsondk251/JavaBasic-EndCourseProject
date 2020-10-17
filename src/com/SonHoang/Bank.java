package com.SonHoang;

import java.util.*;

public class Bank {
    private String name, address;
    private List<Account> accounts = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private Map<String, String> userDatas = new HashMap<>();

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
                    Customer c = inputCustomerInfo();
                    if (c != null) {
                        openAccount(c);
                    }
                    break;
                case 2:
                    Account account = logIn();
                    if (null != account) {
                        selectInAccountPage(account);
                    }
                    break;
                case 0:
                    do1 = true;
                    break;
            }
        } while(!do1);
    }

    public void selectInAccountPage(Account sourceAccount) {
        //todo: consider initialize in only 1 methods and transfer or initialize in every methods
        int choice;
        boolean do1 = false;
        Scanner in = new Scanner(System.in);
        do {
            showMenuAccountPage();
            choice = verifyInputNumber(in, 4);
            switch(choice) {
                case 1:
                    transferMoneyProcess(sourceAccount);
//                    System.out.println("Transfer money");
                    break;
                case 2:
                    System.out.println("Open saving");
                    break;
                case 3:
                    System.out.println("Open card");
                    break;
                case 4:
                    System.out.println("Close account");
                    break;
                case 0:
                    do1 = true;
                    break;
            }
        } while(!do1);
    }

    public Account logIn() {
        String email, password;
        int failTimes = 0, input;
        boolean success = false;
        Account account = null;
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.println("enter the email");
            //todo: validate input data
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
                    break;
                } else if (0 == input){
                    failTimes = 0;
                }
            }
        }
        if (success == true) {
            account = getAccountFromEmail(email);
            //todo: handle the case of account not available ??
            if (account != null) {
                account.showDetails();
            }
        }
        else {
            resetPassword();
        }
//        return success;
        return account;
    }

    public void transferMoneyProcess(Account sourceAccount) {
        String input;
        Scanner in = new Scanner(System.in);
        long destination;
        double amount;
        System.out.print("enter the destination Account: ");
        //todo: validate the input data
        input = in.nextLine();
        destination = Long.parseLong(input);
        System.out.print("enter the amount of money: ");
        amount = verifyInputDouble(in);
        try {
            if(transferMoney(sourceAccount.getAccountNumber(), destination, amount)) {
                System.out.println("success");
            }
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
    }

    public boolean transferMoney(long sourceAccountNumber, long destinationAccountNumber, double amount) throws IllegalArgumentException {
        if (amount <= 0 ) {
            throw new IllegalArgumentException("amount must be positive, minimum 5.0$");
        }
        Account sourceAccount = getAccountFromAccountNumber(sourceAccountNumber);
        Account destinationAccount = getAccountFromAccountNumber(destinationAccountNumber);
        try {
            sourceAccount.subValue(amount + sourceAccount.getFee());
            destinationAccount.addValue(amount);
            return true;
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Account openAccount(Customer customer) {
        //todo: generate unique accountNumber
        long id = new Date().getTime();
        Account account = new Account(id, customer.getId());
        System.out.println("Account created. Please set the password");
        Scanner in = new Scanner(System.in);
        //todo: verify requirements of password
        String password = in.nextLine();
        userDatas.put(customer.getEmail(), password);
        accounts.add(account);
        return account;
    }

    //todo: separate to Utility package
    public Account getAccountFromEmail(String email) {
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

    public Account getAccountFromAccountNumber(long accountNumber) {
        for (Account acc : accounts) {
            if (accountNumber == acc.getAccountNumber()) {
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

    public double verifyInputDouble(Scanner in) {
        String input;
        double choice;
        while(true) {
            input = in.nextLine();
            input = input.trim();
            if (input.isEmpty() || (!isNumber(input)) || (isNumber(input) && (Double.parseDouble(input) <= 5.0))) {
                System.out.println("enter positive number, minimum 5.0$");
            } else {
                break;
            }
        }
        choice = Double.parseDouble(input);
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
        System.out.println("1. Transfer money");
        System.out.println("2. Open saving");
        System.out.println("3. Open card");
        System.out.println("4. Close account");
        System.out.println("0. Quit");
        System.out.println("-----------------------------------");
    }

    public Customer inputCustomerInfo()  {
        Scanner in = new Scanner(System.in);
        String input;
        int choice;
        Customer c = new Customer();
        System.out.println("enter the full name");
        input = in.nextLine();
        c.setFullName(input);
        System.out.println("enter the email");
        input = in.nextLine();
        c.setEmail(input);
        //...
        Account acc = getAccountFromEmail(c.getEmail());
        if (null != acc) {
            System.out.println("Account existed. Do you want to try reset password? (y/n)");
            choice = verifyInputYN(in);
            if (1 == choice) {
                resetPassword();
            } else if (0 == choice) {
                System.out.println("Try log in again.");
                logIn();
            }
            return null;
        } else {
            System.out.println("We recorded the information. Result will be informed to you asap");
            return c;
        }
    }
}


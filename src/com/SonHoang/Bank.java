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

    //UI
    public void go() {
        System.out.println("Welcome to ABC Bank");
        selectInHompage();
    }

    public boolean logIn(String email, String password) {
        boolean success = false;
        try {
            if (verifyAccount(email, password)) {
                System.out.println("login successfully");
                success = true;
//                break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return success;
    }

    public boolean transferMoney(long sourceAccountNumber, long destinationAccountNumber, double amount) throws IllegalArgumentException {
        Account sourceAccount = getAccountFromAccountNumber(sourceAccountNumber);
        Account destinationAccount = getAccountFromAccountNumber(destinationAccountNumber);
        if (null == destinationAccount) {
            throw new IllegalArgumentException("false destination account number");
        } else if (amount <= 0 ) {
            throw new IllegalArgumentException("amount must be positive, minimum 5.0$");
        } else {
            try {
                sourceAccount.subValue(amount + sourceAccount.getFee());
                destinationAccount.addValue(amount);
                return true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
    }

    public Account openAccount(Customer customer) {
        //todo: generate unique accountNumber
        long id = new Date().getTime();
        Account account = new Account(id, customer.getId());
        customers.add(customer);
        accounts.add(account);
        System.out.println("Welcome. Please reset the password");
        resetPassword(customer.getEmail());
        return account;
    }

    //todo: separate to Utility package
    public void selectInHompage() {
        int choice = 0;
        boolean do1 = false;
        Scanner in = new Scanner(System.in);
        do {
            showMenuHomepage();
            choice = verifyInputNumber(in, 2);
            switch(choice) {
                case 1:
                    Customer c = openAccountProcess();
                    if (c != null) {
                        openAccount(c);
                    }
                    break;
                case 2:
                    Account account = logInProcess();
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

    public Account logInProcess() {
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
            if (logIn(email, password) ) {
                account = getAccountFromEmail(email);
                //todo: handle the case of account not available ??
                if (account != null) {
                    account.showDetails();
                }
                break;
            } else {
                failTimes++;
            }
            if (failTimes >= 3) {
                System.out.print("Forgot password? do you want to reset password?(y/n) ");
                input = verifyInputYN(in);
                if (1 == input) {
                    resetPassword(email);
                    break;
                } else if (0 == input){
                    failTimes = 0;
                }
            }
        }
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
            System.out.println("fail");
            System.out.println(e.getMessage());
        }
    }

    public Customer getCustomerFromEmailAndId(String email, String idNumber) {
        for (Customer customer : customers) {
            if (email.equals(customer.getEmail()) || idNumber.equals(customer.getId())) {
                return customer;
            }
        }
        return null;
    }

    public Account getAccountFromCustomerId(String customerId) {
        if (null != customerId || ("").equals(customerId) == false)
            for (Account account : accounts) {
                if (customerId.equals(account.getCustomerId())) {
                    return account;
                }
            }
        return null;
    }

    public Account getAccountFromEmailAndId(String email, String idNumber) {
        String customerId = "";
        for (Customer customer : customers) {
            if (email.equals(customer.getEmail()) || idNumber.equals(customer.getId())) {
                customerId = customer.getId();
            }
        }
        //todo: handle the case of email not available ??
        if (null != customerId || ("").equals(customerId) == false)
            for (Account account : accounts) {
                if (customerId.equals(account.getCustomerId())) {
                    return account;
                }
            }
        return null;
    }

    public Account getAccountFromEmail(String email) {
        String customerId = "";
        for (Customer customer : customers) {
            if (email.equals(customer.getEmail())) {
                customerId = customer.getId();
            }
        }
        //todo: handle the case of email not available ??
        if (null != customerId || ("").equals(customerId) == false)
            for (Account account : accounts) {
                if (customerId.equals(account.getCustomerId())) {
                    return account;
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

    public void resetPassword(String email) {
        //TODO: handle forgot password?
        Scanner in = new Scanner(System.in);
        //todo: verify requirements of password (8 chars min, including Uppercase,...)
        System.out.print("Enter the new password: ");
        String password = in.nextLine();
        userDatas.put(email, password);
        System.out.println("reset successfully");
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
        System.out.println("-------------------------");
        System.out.println("1. Open account");
        System.out.println("2. Login");
        System.out.println("0. Quit");
        System.out.println("-------------------------");
    }

    public void showMenuOpenAccountPage() {
        System.out.println("Please select the service");
        System.out.println("-------------------------");
        System.out.println("1. Try log-in again");
        System.out.println("2. Reset the password");
        System.out.println("3. Delete the old account & create a new one");
        System.out.println("0. Quit");
        System.out.println("-------------------------");
    }
    public void showMenuAccountPage() {
        System.out.println("Please select the service");
        System.out.println("-------------------------");
        System.out.println("1. Transfer money");
        System.out.println("2. Open saving");
        System.out.println("3. Open card");
        System.out.println("4. Close account");
        System.out.println("0. Quit");
        System.out.println("-------------------------");
    }

    public Customer openAccountProcess()  {
        Scanner in = new Scanner(System.in);
        String input, email, idNumber;
        int choice;
        boolean do1 = false;
        Customer c = new Customer();
        //todo: validate the input data
        System.out.println("enter the full name");
        input = in.nextLine();
        c.setFullName(input);
        System.out.println("enter the id number");
        idNumber = in.nextLine();
        c.setId(idNumber);
        System.out.println("enter the email");
        email = in.nextLine();
        c.setEmail(email);
        //...
        Customer customer = getCustomerFromEmailAndId(email, idNumber);
        if (null != customer) {
            System.out.print("Account existed. ");
            do {
                showMenuOpenAccountPage();
                choice = verifyInputNumber(in, 3);
                switch (choice) {
                    case 1:
                        logInProcess();
                        do1 = true;
                        break;
                    case 2:
                        System.out.println("... process ...");
                        resetPassword(customer.getEmail());
                        do1 = true;
                        break;
                    case 3:
                        System.out.println("Service not online supported. Please contact 1900... or go the nearest office to be supported");
                        break;
                    case 0:
                        do1 = true;
                        break;
                }
            } while(!do1);
            return null;
        } else {
            System.out.println("We recorded the information. Result will be informed to you asap");
            System.out.println("... process ...");
            return c;
        }
    }

}
package com.SonHoang;

public class Account {
    private long accountNumber;
    private double balance = 50.0;
    private double interestRate = 0.01;
    private String customerId;

    //todo: change to multiple types of fee
    private double fee = 0.01; //simple: only 1 fee level for all transaction

    //todo: open cards, open savings
//    private List<Card> cards;
//    private List<Saving> savings;


    public Account(long accountNumber, String customerId) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
    }

    //getter
    public long getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getFee() {
        return fee;
    }

    //setter
    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public void setCustomerId(String customerId) {
        customerId = customerId;
    }

    private void changeBalance(double amount) {
        setBalance(balance + amount);
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public void subValue(double amount) throws IllegalArgumentException {
        //check if balance enough to deduct
        if (balance < (amount + fee)) {
            throw new IllegalArgumentException("not enough money");
        } else {
            //changeBalance
            changeBalance(- amount - fee);
//            isCompleted = true;
        }
//        return isCompleted;
    }

    public void addValue(double amount) {
        changeBalance(balance + amount);
    }

    private double calculateMonthlyInterest() {
        return balance * interestRate / 12;
    }

    //TODO: add monthlyInterest at the last day of every month
    public void addMonthlyInterest() {
        setBalance(balance + calculateMonthlyInterest());
    }

    public void showDetails() {
        System.out.println("id: " + accountNumber
                + ", balance: " + balance);
    }

    public void closeAccount() {}
}

//todo: transferMoney() in Account ??
//    public boolean transferMoney(long destinationAccountNumber, double amount) throws IllegalArgumentException {
//        if (amount <= 0 ) {
//            throw new IllegalArgumentException("amount must be positive");
//        }
//        Account destinationAccount = getAccountFromAccountNumber(destinationAccountNumber);
//        try {
//            this.subValue(amount + this.getFee());
//            destinationAccount.addValue(amount);
//            return true;
//        } catch(IllegalArgumentException e) {
//            e.getMessage();
//            return false;
//        }
//    }
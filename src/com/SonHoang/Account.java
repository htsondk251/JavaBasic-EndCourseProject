package com.SonHoang;

public class Account {
    private long id;
    private double balance = 0.0;
    private double interestRate = 0.01;
    private String customerId;

    private double fee; //simple: only 1 fee level for all transaction

    //in-future characteristics
//    private List<Card> cards;
//    private List<Saving> savings;


    public Account(long id, String customerId) {
        this.id = id;
        this.customerId = customerId;
    }

    //getter
    public long getId() {
        return id;
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

    //setter
    public void setId(long id) {
        this.id = id;
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

    public void subValue(double amount) throws IllegalArgumentException {
//        boolean isCompleted = false;
        //check if balance enough to deduct
        if (balance < (amount + fee)) {
            throw new IllegalArgumentException("not enough money");
        } else {
            //changeBalance
            changeBalance(-amount - fee);
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

    //TODO: data structure to link account and customer
    public void showDetails() {
        System.out.println("id: " + id
                + ", balance: " + balance);
                //+ ", owner: " + customer.getLastName() + " " + customer.getFirstName()
    }

    //TODO: delete() should be in Account or Bank
    public void delete() {}


}

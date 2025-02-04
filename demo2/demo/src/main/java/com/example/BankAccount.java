package com.example;

public class BankAccount {
    private String firstName;
    private String lastName;
    private double balance;

    public static final int CHECKING = 1;
    public static final int SAVINGS = 2;

    private int accountType;

    public BankAccount(double balance, String firstName, String lastName, int typeOfAccount) {
        this.balance = balance;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountType = typeOfAccount;
    }

    public double deposit(double amount, boolean branch){
        this.balance += amount;
        return this.balance;
    }

    public double withdraw(double amount, boolean branch){
        if ((amount > 500.00) && !branch){
            throw new IllegalArgumentException();
        }
        this.balance -= amount;
        return this.balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public boolean isChecking(){
        return this.accountType == BankAccount.CHECKING;
    }
}

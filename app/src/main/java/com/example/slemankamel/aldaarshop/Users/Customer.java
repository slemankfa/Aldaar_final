package com.example.slemankamel.aldaarshop.Users;

/**
 * Created by SlemanKamel on 10/18/2017.
 */

public class Customer extends Users {

    private  float balance   ;

    private  int numberOfPurchases ;

    public Customer(String id, String name, String email, String picture, String gender, float balance, int numberOfPurchases) {
        super(id, name, email, picture, gender);
        this.balance = balance;
        this.numberOfPurchases = numberOfPurchases;
    }

    public int getNumberOfPurchases() {
        return numberOfPurchases;
    }

    public void setNumberOfPurchases(int numberOfPurchases) {
        this.numberOfPurchases = numberOfPurchases;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance)
    {

        if (balance > 0 )
        {
            this.balance = balance;

        }
    }



}

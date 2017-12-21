package com.example.slemankamel.aldaarshop.Users;

/**
 * Created by SlemanKamel on 10/18/2017.
 */

public class Client extends Users {


    private float totalAmount;

    private int numberOfProducts;

    public Client(String id, String name, String email, String picture, String gender, float totalAmount, int numberOfProducts) {
        super(id, name, email, picture, gender);
        this.totalAmount = totalAmount;
        this.numberOfProducts = numberOfProducts;
    }

//    public Client(float totalAmount, int numberOfProducts) {
//        this.totalAmount = totalAmount;
//        this.numberOfProducts = numberOfProducts;
//    }


    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {

        if (totalAmount > 0)
        {
            this.totalAmount = totalAmount;
        }
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }
}

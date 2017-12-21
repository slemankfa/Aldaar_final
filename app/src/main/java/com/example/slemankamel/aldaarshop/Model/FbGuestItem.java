package com.example.slemankamel.aldaarshop.Model;

/**
 * Created by SlemanKamel on 10/13/2017.
 */

public class FbGuestItem {

     private  String imagePath  , productName , productDescrption  , productPrice  ;

    public FbGuestItem() {
    }

    public FbGuestItem(String imagePath, String productName, String productDescrption, String productPrice) {
        this.imagePath = imagePath;
        this.productName = productName;
        this.productDescrption = productDescrption;
        this.productPrice = productPrice;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescrption() {
        return productDescrption;
    }

    public void setProductDescrption(String productDescrption) {
        this.productDescrption = productDescrption;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}

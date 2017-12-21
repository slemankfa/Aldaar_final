package com.example.slemankamel.aldaarshop.Model;

/**
 * Created by SlemanKamel on 11/30/2017.
 */

public class ViewItems {

    private  String   Uid , productImage  , productName , productPrice  , productQuntity  , productType  , username  , productKey ;

    public String getUid() {
        return Uid;
    }

    public ViewItems(String productName, String productImage, String productPrice) {
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
    }

    // constructor   for Fav List  We  Just Add Product Key


    public ViewItems(String productName, String productImage, String productPrice, String productKey) {
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productKey = productKey;
    }

    public ViewItems() {
    }



    public void setUid(String uid) {
        Uid = uid;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQuntity() {
        return productQuntity;
    }

    public void setProductQuntity(String productQuntity) {
        this.productQuntity = productQuntity;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }
}

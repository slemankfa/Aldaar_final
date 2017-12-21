package com.example.slemankamel.aldaarshop.Model;

/**
 * Created by SlemanKamel on 10/21/2017.
 */

public class AddProductTypeObject {


    private  String proudctId ;
    private  String userId ;
    private  String Image ;
    private  String productName  ;

    public AddProductTypeObject(String proudctId, String userId, String image, String productName) {
        this.proudctId = proudctId;
        this.userId = userId;
        Image = image;
        this.productName = productName;
    }

    public AddProductTypeObject() {
    }

    public String getProudctId() {
        return proudctId;
    }

    public void setProudctId(String proudctId) {
        this.proudctId = proudctId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}

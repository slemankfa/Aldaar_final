package com.example.slemankamel.aldaarshop.Model;

/**
 * Created by SlemanKamel on 10/22/2017.
 */

public class MyProductItem {

    private  String title ;

    private  String image  , uid  ,  product_type_key , username ;


    public MyProductItem(String title, String image, String uid, String product_type_key, String username) {
        this.title = title;
        this.image = image;
        this.uid = uid;
        this.product_type_key = product_type_key;
        this.username = username;
    }


    public MyProductItem() {
    }

    public MyProductItem( String title , String image   ) {

        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProduct_type_key() {
        return product_type_key;
    }

    public void setProduct_type_key(String product_type_key) {
        this.product_type_key = product_type_key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

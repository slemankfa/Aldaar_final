package com.example.slemankamel.aldaarshop.Products;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.slemankamel.aldaarshop.Adapters.MyProductAdapter;
import com.example.slemankamel.aldaarshop.Adapters.ViewFavoriteItemsAdapter;
import com.example.slemankamel.aldaarshop.Adapters.ViewItemsRecyclerAdapter;
import com.example.slemankamel.aldaarshop.Model.MyProductItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.util.List;

/**
 * Created by SlemanKamel on 10/27/2017.
 */

public class ProductsMaker {


    /**
     * facade pattern
     * to  deny  system  directly  to  products
     */

    private ViewMyProductType ViewmyProduct ;

    private  AddNewProductType addNewProductType ;

    private  AddProductItem addProductItem ;

    private  ViewProductItems  viewProductItems ;

    private  ViewFavoriteList viewFavoriteList ;

    private  ViewProducts  viewProducts ;
    public ProductsMaker(Context context) {

        ViewmyProduct = new ViewMyProductType(context);
        addNewProductType = new AddNewProductType(context);
        addProductItem  = new AddProductItem(context);

        viewProductItems = new ViewProductItems(context);
        viewProducts  = new ViewProducts(context);

        viewFavoriteList = new ViewFavoriteList(context);
    }


    public FirebaseRecyclerAdapter getViewmyProductType()
    {
        return ViewmyProduct.getFbRecycler();
    }


    public FirebaseRecyclerAdapter getAllProducts()
    {
        return  viewProducts.getAllProducts();
    }


    public ViewItemsRecyclerAdapter getViewProductsItems()
    {
        return  viewProductItems.getAllProducts();
    }

    public ViewFavoriteItemsAdapter getAllFavItem()
    {

        return viewFavoriteList.getAllFavItem();
    }
    public Boolean  getAddNewTypeResult (String titl_val, Uri ImageUri)

    {
        return  addNewProductType.StartPosting( titl_val , ImageUri  );
    }
//    public List<MyProductItem> getViewmyProductType()
//    {
//        return ViewmyProductAdapter.getMyProductItem();
//    }


    public  Boolean startAddItem (String productName  , String  productType  , Uri productImage , String price  , final String productQuntity  , final String  countryOfOrgin)
    {
        return addProductItem.startAddItem(productName , productType , productImage , price , productQuntity , countryOfOrgin);
    }


}

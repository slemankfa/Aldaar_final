package com.example.slemankamel.aldaarshop.Activites;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.slemankamel.aldaarshop.Adapters.ViewItemsRecyclerAdapter;
import com.example.slemankamel.aldaarshop.FB.FBMaker;
import com.example.slemankamel.aldaarshop.Products.ProductsMaker;
import com.example.slemankamel.aldaarshop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class ViewProductItem extends AppCompatActivity {


    FBMaker  fbMaker ;
//    public  static String productTypeKey  =  null ;

    FloatingActionButton mAddProduct ;

    RecyclerView tvData ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product_item);

//        fbMaker = new FBMaker();
//
//        FirebaseAuth mAuth  =  fbMaker.getmAuth();
//        FirebaseDatabase mDataBase  = fbMaker.getmDatabase();
//        FirebaseStorage Storge =  fbMaker.getStrorge();


        ProductsMaker maker  = new ProductsMaker(this);

        tvData  = (RecyclerView) findViewById(R.id.viewproduct_items_rycview)  ;
        tvData.setLayoutManager( new GridLayoutManager(getApplicationContext() , 2));

        tvData.setHasFixedSize(true);


        /**
         * products -> products(productTypeKey)
         */
        ViewItemsRecyclerAdapter viewItemsRecyclerAdapter = maker.getViewProductsItems();

//        viewItemsRecyclerAdapter.notifyDataSetChanged();
//
        tvData.setAdapter(viewItemsRecyclerAdapter);


        mAddProduct = (FloatingActionButton) findViewById(R.id.viewproduct_additem_fb);




        mAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addNewItemIntent  = new Intent(ViewProductItem.this  , AddProductItem.class);
//                addNewItemIntent.putExtra("productTypeKey" , productTypeKey);
                startActivity(addNewItemIntent);
            }
        });


    }



}

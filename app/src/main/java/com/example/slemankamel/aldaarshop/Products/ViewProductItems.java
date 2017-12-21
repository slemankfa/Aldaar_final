package com.example.slemankamel.aldaarshop.Products;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.slemankamel.aldaarshop.Adapters.ViewItemsRecyclerAdapter;
import com.example.slemankamel.aldaarshop.FB.FBMaker;
import com.example.slemankamel.aldaarshop.Model.ViewItems;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.slemankamel.aldaarshop.Products.ViewMyProductType.productTypeKey;

/**
 * Created by SlemanKamel on 11/28/2017.
 */

public class ViewProductItems   {


    FBMaker fbMaker = new FBMaker();

    List<ViewItems> items  = new ArrayList<>();



//    FirebaseAuth mAuth = fbMaker.getmAuth();

    FirebaseDatabase mDatabase = fbMaker.getmDatabase();

    public static Context context ;


    ViewItemsRecyclerAdapter  viewItemsRecyclerAdapter = null;

    public ViewProductItems(Context context) {
        this.context = context;
    }




    DatabaseReference  allProducts  = mDatabase.getReference().child("Products") ;


    public ViewItemsRecyclerAdapter getAllProducts ()
    {


//        Toast.makeText(context, "login  to method ", Toast.LENGTH_SHORT).show();


        allProducts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot  snapshot  : dataSnapshot.getChildren())
                {


//                    Toast.makeText(context, snapshot.getValue().toString() , Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context,productTypeKey, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, productTypeKey , Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, snapshot.child("productType").getValue().toString() , Toast.LENGTH_SHORT).show();

                    if(snapshot.child("productType").getValue().toString().equals(productTypeKey))
                    {

                        Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();


                        items.add(new ViewItems(snapshot.child("productName").getValue().toString() ,snapshot.child("productImage").getValue().toString() , snapshot.child("productPrice").getValue().toString()));

//                        Toast.makeText(context, snapshot.child("productName").getValue().toString() , Toast.LENGTH_SHORT).show();

                    }

                }

                viewItemsRecyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        items.add(new ViewItems("add" , "asdasdasd" , "sadasdasd"));
//        items.add(new ViewItems("add" , "asdasdasd" , "sadasdasd"));
//        items.add(new ViewItems("add" , "asdasdasd" , "sadasdasd"));
//        items.add(new ViewItems("add" , "asdasdasd" , "sadasdasd"));

        viewItemsRecyclerAdapter = new ViewItemsRecyclerAdapter(context , items );



        return viewItemsRecyclerAdapter ;
    }









}

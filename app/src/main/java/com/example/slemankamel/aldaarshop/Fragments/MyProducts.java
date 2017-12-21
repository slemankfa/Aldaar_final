package com.example.slemankamel.aldaarshop.Fragments;


import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.slemankamel.aldaarshop.Adapters.MyProductsAdapter;
import com.example.slemankamel.aldaarshop.Adapters.MyProductAdapter;
import com.example.slemankamel.aldaarshop.FB.FBAuth;
import com.example.slemankamel.aldaarshop.FB.FBDataBase;
import com.example.slemankamel.aldaarshop.Model.MyProductItem;
import com.example.slemankamel.aldaarshop.Products.ProductsMaker;
import com.example.slemankamel.aldaarshop.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProducts extends Fragment {


    View root ;
    Button  addNewType ;
    RecyclerView  tvData  ;
    FirebaseDatabase mRef ;
    DatabaseReference myProduct ;

    FirebaseAuth   mAuth ;


    ArrayList<MyProductItem> myProductItems ;

    ProductsMaker  productsMaker ;

    MyProductAdapter myProductAdapter ;

    //    FragmentManager  getFragmentManager ;
    public MyProducts() {
        // Required empty public constructor
        myProductItems = new ArrayList<>() ;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_my_products, container, false);

        mAuth = FBAuth.getmAuth();

        addNewType = (Button) root.findViewById(R.id.add_new_product_type);

        tvData = (RecyclerView) root.findViewById(R.id.tv_data_myproduct);


        /**
         *
         * use  facade design pattern
         */
        productsMaker = new ProductsMaker(getActivity());

        FirebaseRecyclerAdapter firebaseRecyclerAdapter  =  productsMaker.getViewmyProductType();


        tvData.setAdapter(firebaseRecyclerAdapter);









//                tvData.setHasFixedSize(true);
        tvData.setLayoutManager( new GridLayoutManager(getContext() , 2));

        addNewType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FragmentTransaction ft =  getFragmentManager().beginTransaction();
                ft.replace(R.id.content_client , new AddProductType() );
                ft.commit();

                ft.addToBackStack(null);

            }
        });





        return  root   ;

    }





}





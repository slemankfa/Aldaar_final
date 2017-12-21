package com.example.slemankamel.aldaarshop.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slemankamel.aldaarshop.FB.FBMaker;
import com.example.slemankamel.aldaarshop.Products.ProductsMaker;
import com.example.slemankamel.aldaarshop.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewProducts extends Fragment {

    private RecyclerView tvData  ;
    ProductsMaker productsMaker ;
    View root  ;

    public ViewProducts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        root = inflater.inflate(R.layout.fragment_view_products, container, false);

        tvData = (RecyclerView) root.findViewById(R.id.view_products_prodList_rec);

        /**
         *
         * use  facade design pattern
         */
        productsMaker = new ProductsMaker(getActivity());

        FirebaseRecyclerAdapter firebaseRecyclerAdapter  =  productsMaker.getAllProducts();


        tvData.setAdapter(firebaseRecyclerAdapter);

//        tvData.setLayoutManager( new GridLayoutManager(getContext() , 2));
        tvData.setLayoutManager( new LinearLayoutManager(getContext()));


//        view_products_prodList_rec
        return root ;



    }

}

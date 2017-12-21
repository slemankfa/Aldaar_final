package com.example.slemankamel.aldaarshop.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slemankamel.aldaarshop.Adapters.ViewFavoriteItemsAdapter;
import com.example.slemankamel.aldaarshop.Products.ProductsMaker;
import com.example.slemankamel.aldaarshop.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewFavoriteList extends Fragment {

    private RecyclerView tvData  ;
    ProductsMaker productsMaker ;
    View root  ;


    public ViewFavoriteList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        root = inflater.inflate(R.layout.fragment_view_favorite_list, container, false);

        tvData = (RecyclerView) root.findViewById(R.id.view_favlist_rec);
        productsMaker = new ProductsMaker(getActivity());


        ViewFavoriteItemsAdapter  favoriteItemsAdapter  = productsMaker.getAllFavItem();

        tvData.setAdapter(favoriteItemsAdapter);

        tvData.setLayoutManager( new LinearLayoutManager(getContext()));




        return root ;
    }

}

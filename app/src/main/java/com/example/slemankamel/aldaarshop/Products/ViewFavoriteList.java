package com.example.slemankamel.aldaarshop.Products;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slemankamel.aldaarshop.Adapters.ViewFavoriteItemsAdapter;
import com.example.slemankamel.aldaarshop.Adapters.ViewItemsRecyclerAdapter;
import com.example.slemankamel.aldaarshop.FB.FBMaker;
import com.example.slemankamel.aldaarshop.Model.ViewItems;
import com.example.slemankamel.aldaarshop.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SlemanKamel on 12/7/2017.
 */

public class ViewFavoriteList {

    FBMaker fbMaker  = new FBMaker();

    FirebaseAuth mAuth =  fbMaker.getmAuth();

    FirebaseDatabase mDatabase =  fbMaker.getmDatabase();

    DatabaseReference  favList = mDatabase.getReference().child("Favorites").child(mAuth.getCurrentUser().getUid()) ;
    DatabaseReference  productList = mDatabase.getReference().child("Products");


    Context context ;

    /**
     * For all the products have been added in Favorite list
     */
    ArrayList<String> itemsKey = new ArrayList<>();

    ArrayList<ViewItems> favItems  = new ArrayList<>();

    ViewFavoriteItemsAdapter viewFavoriteItemsAdapter = null  ;

    public ViewFavoriteList(Context context) {
        this.context = context;
    }

    public void getAllitems()
    {

        favList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               for (DataSnapshot snapshot  : dataSnapshot.getChildren())
               {

                   itemsKey.add(snapshot.getKey().toString());



               }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    /**
     *
     * then go  to the products  List  and get  products
     */


    public ViewFavoriteItemsAdapter  getAllFavItem()
    {

        getAllitems();

        productList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                //  compare  with  the  elemnt  in array  with  product  if equal  that mean  the product allready added to fav list

                 for ( DataSnapshot snapshot  : dataSnapshot.getChildren())
                 {

                     for ( String   viewItems :  itemsKey)
                     {


//                         Toast.makeText(context, " item key = " + viewItems , Toast.LENGTH_SHORT).show();

                         if (viewItems.equals(snapshot.getKey()))
                         {


//                             Toast.makeText(context, " snapshot key = " + snapshot.getKey()  , Toast.LENGTH_SHORT).show();


                             favItems.add(new ViewItems(snapshot.child("productName").getValue().toString() ,snapshot.child("productImage").getValue().toString() ,
                                     snapshot.child("productPrice").getValue().toString() ,  snapshot.getKey()  ));


                             new ViewItems(snapshot.child("productName").getValue().toString() ,snapshot.child("productImage").getValue().toString() ,
                                     snapshot.child("productPrice").getValue().toString() ,snapshot.getKey() );
                         }


                     }


                 }


                viewFavoriteItemsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        viewFavoriteItemsAdapter = new ViewFavoriteItemsAdapter(favItems , context);


        return  viewFavoriteItemsAdapter  ;
    }





}

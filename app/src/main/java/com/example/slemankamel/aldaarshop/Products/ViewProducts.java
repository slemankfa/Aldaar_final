package com.example.slemankamel.aldaarshop.Products;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slemankamel.aldaarshop.FB.FBMaker;
import com.example.slemankamel.aldaarshop.Model.MyProductItem;
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

/**
 * Created by SlemanKamel on 12/7/2017.
 */

public class ViewProducts {


    FBMaker fbMaker  = new FBMaker();

    FirebaseDatabase mDatabase = fbMaker.getmDatabase();

    FirebaseAuth  mAuth  = fbMaker.getmAuth() ;
    DatabaseReference  viewProduct = mDatabase.getReference().child("Products") ;


    private boolean addToFavorite = false ;



    Context context  ;

    public ViewProducts(Context context) {
        this.context = context;
    }

    public FirebaseRecyclerAdapter  getAllProducts()
    {
        FirebaseRecyclerAdapter<ViewItems, ViewProductHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<ViewItems, ViewProductHolder>(

                ViewItems.class ,
                R.layout.customer_product_item ,
                ViewProductHolder.class ,
                viewProduct
        ) {
            @Override
            protected void populateViewHolder(ViewProductHolder viewHolder, ViewItems model, int position) {


                viewHolder.setProductName(model.getProductName());
                viewHolder.setProductPrice(model.getProductPrice() + "$");
                viewHolder.setProductImage(model.getProductImage() , context);


                /**
                 * here  we will  add  to fav
                 * or remove  from fav
                 */

                final DatabaseReference  FavList   = mDatabase.getReference().child("Favorites");


                final  String productKey = getRef(position).getKey();


                viewHolder.mFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        addToFavorite = true ;


                        FavList.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if(addToFavorite)
                                {

                                    if(dataSnapshot.child(mAuth.getCurrentUser().getUid()).hasChild(productKey))
                                    {
                                        FavList.child(mAuth.getCurrentUser().getUid()).child(productKey).removeValue();
                                        addToFavorite = false ;
                                    }
                                    else
                                    {
                                        FavList.child(mAuth.getCurrentUser().getUid()).child(productKey).setValue(" Product Added to Fav") ;
                                        addToFavorite = false;
                                    }
//
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }
                });



                viewHolder.AddToFav(productKey);




            }
        };




        return firebaseRecyclerAdapter;
    }


       public  static class ViewProductHolder  extends RecyclerView.ViewHolder{


        ImageView  mProductImage ;
        TextView  mProductName  , mProductPrice ;
        ImageButton mFav ;

        FBMaker fbMaker  = new FBMaker();
        FirebaseDatabase mDatabase = fbMaker.getmDatabase();
        FirebaseAuth mAuth = fbMaker.getmAuth() ;


        DatabaseReference  FavList   = mDatabase.getReference().child("Favorites");


        public ViewProductHolder(View itemView) {
            super(itemView);

            mProductImage  = (ImageView) itemView.findViewById(R.id.customer_product_itemimage_imgview);
            mProductName = (TextView) itemView.findViewById(R.id.customer_product_itemname_txt);
            mProductPrice = (TextView) itemView.findViewById(R.id.customer_product_itemprice_txt);
            mFav = (ImageButton) itemView.findViewById(R.id.customer_product_fav_imgbtn);
        }


        public  void setProductName(String name )
        {

            mProductName.setText(name);
        }

        public  void setProductPrice(String price )
        {
            mProductPrice.setText(price);
        }

        public  void setProductImage(String Image , Context context)
        {
            Picasso.with(context).load(Image).into(mProductImage);
        }


        public  void AddToFav(final String  productKey)
        {



            FavList.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.child(mAuth.getCurrentUser().getUid()).hasChild(productKey))
                    {
                        mFav.setImageResource(R.drawable.ic_fav_addes);
                    }else
                    {
                        mFav.setImageResource(R.drawable.ic_favourite);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }





    }


}

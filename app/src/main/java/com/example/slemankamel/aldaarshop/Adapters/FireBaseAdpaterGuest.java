package com.example.slemankamel.aldaarshop.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.slemankamel.aldaarshop.FB.FBDataBase;
import com.example.slemankamel.aldaarshop.FB.FBMaker;
import com.example.slemankamel.aldaarshop.Model.FbGuestItem;
import com.example.slemankamel.aldaarshop.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by SlemanKamel on 10/13/2017.
 */


public class FireBaseAdpaterGuest {


    FBMaker  fbMaker  = new FBMaker();

//    FirebaseDatabase mRef  = FBDataBase.getmDatabse();
    FirebaseDatabase mRef  = fbMaker.getmDatabase();

    DatabaseReference  mProduct = mRef.getReference().child("Product");


    Context context ;



    public FireBaseAdpaterGuest(Context context) {
        this.context = context;
    }


    public FirebaseRecyclerAdapter  Adapter ()
    {

        FirebaseRecyclerAdapter <FbGuestItem , ProductViewHolder > FbRecyclerAdapter
                = new FirebaseRecyclerAdapter<FbGuestItem, ProductViewHolder>
                (
                        FbGuestItem.class ,
                        R.layout.product_item ,
                        ProductViewHolder.class,
                        mProduct
                ) {

            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, FbGuestItem model, int position) {


                viewHolder.setImage(model.getImagePath() ,context  );

                viewHolder.setPrice(model.getProductPrice());

            }
        };

        return  FbRecyclerAdapter ;
    }

//    FirebaseRecyclerAdapter <FbGuestItem , ProductViewHolder > FbRecyclerAdapter
//             = new FirebaseRecyclerAdapter<FbGuestItem, ProductViewHolder>
//            (
//                    FbGuestItem.class ,
//                    R.layout.product_item ,
//                    ProductViewHolder.class,
//                    mProduct
//            ) {
//
//        @Override
//        protected void populateViewHolder(ProductViewHolder viewHolder, FbGuestItem model, int position) {
//
//
//            viewHolder.setImage(model.getImagePath() ,  );
//
//            viewHolder.setPrice(model.getProductPrice());
//
//        }
//    };




    /**
     * view holder  to featch items
     */


    public static class ProductViewHolder extends RecyclerView.ViewHolder{



        View root ;

        private ImageView  productImage ;
        private TextView  productPrice ;


        public ProductViewHolder(View itemView) {
            super(itemView);

            root = itemView ;

        }

        public void setImage (String imagePath  , Context context)
        {

//            productImage = (ImageView) root.findViewById(R.id.product_image_item);
            Picasso.with(context).load(imagePath).into(productImage);

        }


        public  void setPrice ( String price )
        {

//            productPrice = (TextView) root.findViewById(R.id.product_price_item);

            productPrice.setText(price);

        }




    }



}




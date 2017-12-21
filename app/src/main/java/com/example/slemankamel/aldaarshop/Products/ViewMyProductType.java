package com.example.slemankamel.aldaarshop.Products;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slemankamel.aldaarshop.Activites.ClientActivity;
import com.example.slemankamel.aldaarshop.Activites.ViewProductItem;
import com.example.slemankamel.aldaarshop.Adapters.MyProductAdapter;
import com.example.slemankamel.aldaarshop.FB.FBAuth;
import com.example.slemankamel.aldaarshop.FB.*;
import com.example.slemankamel.aldaarshop.FB.FBStorge;
import com.example.slemankamel.aldaarshop.Model.MyProductItem;
import com.example.slemankamel.aldaarshop.R;
import com.example.slemankamel.aldaarshop.Users.Client;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SlemanKamel on 10/24/2017.
 */

public class ViewMyProductType    {


    FBMaker fbMaker = new FBMaker();


    FirebaseAuth  mAuth  =  fbMaker.getmAuth();
    FirebaseDatabase mDataBase  = fbMaker.getmDatabase();
    FirebaseStorage Storge =  fbMaker.getStrorge();

//
//    FirebaseAuth mAuth = FBAuth.getmAuth();
//    FirebaseDatabase mRef  = FBDataBase.getmDatabse();
//    FirebaseStorage mStorge = FBStorge.getstorage();

    DatabaseReference myProducts   =  mDataBase.getReference().child("ProductsType").child(mAuth.getCurrentUser().getUid().toString());


    Context context  ;


    List<MyProductItem>  items ;
//    List<DataSnapshot>  child = new ArrayList<>();

    MyProductAdapter  testadapter;


    public static  String productTypeKey ;


    public ViewMyProductType(Context context) {
        this.context = context;
        items  = new ArrayList<>() ;


    }



    /**
     * check the  the  auth
     * then get   all  products
     * how  have  same uid for Client
     */






//    public  void ViewProducts()
//    {
//
//
//        myProducts.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
////                  Toast.makeText(context, " login to Value Listner ", Toast.LENGTH_SHORT).show();
//
//                final String user_id = mAuth.getCurrentUser().getUid().toString();
//
//                Toast.makeText(context, "uid" +user_id  , Toast.LENGTH_SHORT).show();
//
//                for ( DataSnapshot  products : dataSnapshot.getChildren() )
//                {
//
//                    Toast.makeText(context, ""+products.child("uid").getValue() , Toast.LENGTH_SHORT).show();
//
//
//                    if( products.child("uid").getValue().equals(user_id) )
//                    {
//
////                          Toast.makeText(context, "Login to  chillds ", Toast.LENGTH_SHORT).show();
//                        /**
//                         * get value  from child
//                         */
//                        String image = (String) products.child("image").getValue();
//                        String product_type_key = (String) products.child("product_type_key").getValue();
//                        String title = (String) products.child("title").getValue();
//                        String uid = (String) products.child("uid").getValue();
//                        String username = (String) products.child("username").getValue();
//
//                        Toast.makeText(context, title  + username , Toast.LENGTH_SHORT).show();
//
//                        items.add( new MyProductItem( title,  image,  uid,  product_type_key,  username));
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//
//
//    }
//
//
//
//
//
//
//   public MyProductAdapter  getMyProductAdpater()
//   {
//
//
//       return  new MyProductAdapter (items , context );
//
//   }



        public FirebaseRecyclerAdapter getFbRecycler()
        {


//            final String user_id = mAuth.getCurrentUser().getUid().toString();


            FirebaseRecyclerAdapter<MyProductItem , myProductViewHolder> firebaseRecyclerAdapter
                    = new FirebaseRecyclerAdapter<MyProductItem, myProductViewHolder>(
                    MyProductItem.class,
                    R.layout.my_products_item ,
                    myProductViewHolder.class,
                    myProducts
            ) {
                @Override
                protected void populateViewHolder(myProductViewHolder viewHolder, MyProductItem model, final int position) {


                    viewHolder.set_Title(model.getTitle());
                    viewHolder.setImage(model.getImage(), context);


                    /**
                     * get  inforamtion about  product type
                     * to create items For Product
                     * abd convtert it  To view Product Item
                     */



                    viewHolder.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

//       back  and start  from here  ,   after make fragment  Maker
//        Convert  to frgament  View product item   ....


                            productTypeKey  =  getRef(position).getKey();

                            Intent viewProductItemIntent = new Intent(context  , ViewProductItem.class);

//                            viewProductItemIntent.putExtra("productTypeKey" , productTypeKey);
                            context.startActivity(viewProductItemIntent);


                        }
                    });

                }
            };

            return firebaseRecyclerAdapter  ;
        }



    public  static class myProductViewHolder extends RecyclerView.ViewHolder{


        View root ;

        FirebaseAuth mAuth ;
        private ImageView myProductImage  ;
        private TextView prouductTypeTitle ;

        public myProductViewHolder(View itemView) {
            super(itemView);

            root =  itemView ;

        }


        public void set_Title(String title)
        {

            prouductTypeTitle = (TextView) root.findViewById(R.id.my_product_name_item)  ;
            prouductTypeTitle.setText(title);
        }

        public  void setImage(String image , Context context)
        {

            myProductImage = (ImageView) root.findViewById(R.id.my_product_image_item);
            Picasso.with(context).load(image).into(myProductImage);

        }
    }


}

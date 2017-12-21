package com.example.slemankamel.aldaarshop.Products;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.slemankamel.aldaarshop.Activites.ViewProductItem;
import com.example.slemankamel.aldaarshop.FB.FBMaker;
import com.example.slemankamel.aldaarshop.Fragments.MyProducts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

/**
 * Created by SlemanKamel on 11/30/2017.
 */

public class AddProductItem  extends AppCompatActivity {


    FBMaker fbMaker  = new FBMaker();

   private FirebaseAuth mAuth  =  fbMaker.getmAuth();
    private FirebaseDatabase mDataBase  = fbMaker.getmDatabase();
    private FirebaseStorage Storge =  fbMaker.getStrorge();

    private  DatabaseReference  mDataItem = mDataBase.getReference().child("Products") ;
    private  DatabaseReference   mDataBaseUser  = mDataBase.getReference().child("Users").child(mAuth.getCurrentUser().getUid());
    private StorageReference   mStorage   = Storge.getReference();

//    private FirebaseUser  currentUser = mAuth.getCurrentUser() ;




    AddProductItem addProductItem = null;



    ProgressDialog mProgressDialog ;
    Context  context  ;


    boolean result = true   ;

    public AddProductItem(Context context) {
        this.context = context;
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setCanceledOnTouchOutside(false);

    }


    private  String productName  ,  productType  , price  , productQuntity  , countryOfOrgin ;

    Uri  productImage ;


    public AddProductItem( Builder builder )
    {
        this.productName = builder.productName ;
        this.productType = builder.productType ;
        this.productImage = builder.productImage ;
        this.price = builder.price ;
        this.productQuntity = builder.productQuntity;
        this.countryOfOrgin = builder.countryOfOrgin ;
    }


    public String getProductName() {
        return productName;
    }

    public String getProductType() {
        return productType;
    }

    public Uri getProductImage() {
        return productImage;
    }

    public String getPrice() {
        return price;
    }

    public String getProductQuntity() {
        return productQuntity;
    }

    public String getCountryOfOrgin() {
        return countryOfOrgin;
    }


    /**
     * create Methods retuern  boolean  to see if item Allready added  or  not
     *  contain builder  for product    if the  client wont to add  more  details
     *  will  check  it at the  first
     */



    public Boolean startAddItem (String productName  , String  productType  , Uri productImage , String price  , final String productQuntity  , final String  countryOfOrgin)
    {



        if(productQuntity.isEmpty() && countryOfOrgin.isEmpty())
        {
            addProductItem = new AddProductItem.Builder().setProductName(productName).setProductType(productType)
                    .setProductImage(productImage).setPrice(price).createItem();
        }
        else if(productQuntity.isEmpty())
        {
            addProductItem = new AddProductItem.Builder().setProductName(productName).setProductType(productType)
                    .setProductImage(productImage).setPrice(price).setCountryOfOrgin(countryOfOrgin).createItem();
        }
        else if(countryOfOrgin.isEmpty())
        {
            addProductItem = new AddProductItem.Builder().setProductName(productName).setProductType(productType)
                    .setProductImage(productImage).setPrice(price).setproductQuntity(productQuntity).createItem();
        }else if (!productQuntity.isEmpty() && !countryOfOrgin.isEmpty())
        {
            addProductItem = new AddProductItem.Builder().setProductName(productName).setProductType(productType)
                    .setProductImage(productImage).setPrice(price).setproductQuntity(productQuntity).setCountryOfOrgin(countryOfOrgin).createItem();
        }


        /**
         * after check  will  add  to  firebase data base
         */

        mProgressDialog.show();


        StorageReference  filePath =  mStorage.child("Product_Item_Image").child(addProductItem.getProductImage().getLastPathSegment());

        filePath.putFile(addProductItem.getProductImage()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                /**
                 * if image load  successfly   then  add  to database real time
                 */
                final  Uri  downloadUri  = taskSnapshot.getDownloadUrl();

                final  DatabaseReference  newItem = mDataItem.push();


                mDataBaseUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        newItem.child("productName").setValue(addProductItem.getProductName());
                        newItem.child("productImage").setValue(downloadUri.toString());
                        newItem.child("productType").setValue(addProductItem.getProductType());
                        newItem.child("productPrice").setValue(addProductItem.getPrice());
                        newItem.child("Uid").setValue(mAuth.getCurrentUser().getUid());

                        // after  check  the  builder
                        if(!productQuntity.isEmpty())
                        {
                            newItem.child("productQuntity").setValue(addProductItem.getProductQuntity());
                        }else if(!countryOfOrgin.isEmpty())
                        {
                            newItem.child("countryOfOrgin").setValue(addProductItem.getCountryOfOrgin());
                        }


                        newItem.child("username").setValue(dataSnapshot.child("UserName").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful())
                                {
                                    result = true ;
                                    Intent ViewProdItemIntent = new Intent(context , ViewProductItem.class);
                                    context.startActivity(ViewProdItemIntent);
                                    finish();
                                    mProgressDialog.dismiss();
                                }else
                                {
                                    Toast.makeText(context, "Error Uploading ... ", Toast.LENGTH_SHORT).show();
                                    mProgressDialog.dismiss();
                                }
                            }
                        });



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {


                        result = false ;

                        mProgressDialog.dismiss();
                    }
                });


            }
        });



        return  result ;


    }
    /**
     * Builder Class
     * quntity
     * country of orgin
     */

    public static  class  Builder {

        private  String productName  ,  productType  , price  , productQuntity  , countryOfOrgin ;

        Uri  productImage ;


        public Builder  setProductName  (String productName )
        {
            this.productName = productName  ;

            return this  ;
        }


        public Builder  setProductType  (String productType )
        {
            this.productType = productType  ;

            return this  ;
        }


        public Builder  setProductImage  (Uri productImage )
        {
            this.productImage = productImage  ;

            return this  ;
        }

        public Builder  setPrice  (String price )
        {
            this.price = price  ;

            return this  ;
        }

        public Builder  setproductQuntity  (String productQuntity )
        {
            this.productQuntity = productQuntity  ;

            return this  ;
        }



        public Builder  setCountryOfOrgin  (String countryOfOrgin )
        {
            this.countryOfOrgin = countryOfOrgin  ;

            return this  ;
        }



        public  AddProductItem  createItem()
        {
            return  new AddProductItem(this);
        }

    }

}

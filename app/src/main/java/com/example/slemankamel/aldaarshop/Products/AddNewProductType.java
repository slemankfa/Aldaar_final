package com.example.slemankamel.aldaarshop.Products;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.slemankamel.aldaarshop.FB.FBAuth;
import com.example.slemankamel.aldaarshop.FB.FBDataBase;
import com.example.slemankamel.aldaarshop.FB.FBMaker;
import com.example.slemankamel.aldaarshop.FB.FBStorge;
import com.example.slemankamel.aldaarshop.Fragments.MyProducts;
import com.example.slemankamel.aldaarshop.Model.AddProductTypeObject;
import com.example.slemankamel.aldaarshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by SlemanKamel on 10/27/2017.
 */

public class AddNewProductType {



    FBMaker fbMaker = new FBMaker();


    FirebaseAuth  mAuth  =  fbMaker.getmAuth();
    FirebaseDatabase mDataBase  = fbMaker.getmDatabase();
    FirebaseStorage Storge =  fbMaker.getStrorge();


//
//    FirebaseAuth mAuth = FBAuth.getmAuth();
//    FirebaseDatabase mDataBase = FBDataBase.getmDatabse();
//    FirebaseStorage Storge = FBStorge.getstorage();

    StorageReference mStorge = Storge.getReference();
    AddProductTypeObject addProductType = new AddProductTypeObject();
    private DatabaseReference productTypeRrf = mDataBase.getReference().child("ProductsType");
    DatabaseReference mDatabaseUser = mDataBase.getReference().child("Users").child(mAuth.getCurrentUser().getUid());
    boolean result = true;

    Context context;

    ProgressDialog mProgressDialog  ;

    public AddNewProductType() {
    }

    public AddNewProductType(Context context) {
        this.context = context;

        mProgressDialog  = new ProgressDialog(context) ;
        mProgressDialog.setCanceledOnTouchOutside(false);
    }



    public Boolean StartPosting(String titl_val, Uri ImageUri  ) {


        final String titl_Val = titl_val;
        mProgressDialog.show();

        if (!TextUtils.isEmpty(titl_Val) && ImageUri != null) {

            //  add to  data base


            StorageReference filePath = mStorge.child("Product_Type_Image").child(ImageUri.getLastPathSegment());

            filePath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    final Uri downloadUri = taskSnapshot.getDownloadUrl();


//                    final DatabaseReference newProductTypePost = productTypeRrf.push();
                    String user_id = mAuth.getCurrentUser().getUid().toString();
                    final DatabaseReference newProductTypePost = productTypeRrf.child(user_id).push();

                    addProductType.setUserId(mAuth.getCurrentUser().getUid());
                    addProductType.setProudctId(newProductTypePost.getKey());
                    addProductType.setImage(downloadUri.toString());
                    addProductType.setProductName(titl_Val);


                    mDatabaseUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            newProductTypePost.child("title").setValue(addProductType.getProductName());
                            newProductTypePost.child("image").setValue(addProductType.getImage());
                            newProductTypePost.child("uid").setValue(addProductType.getUserId());
                            newProductTypePost.child("product_type_key").setValue(addProductType.getProudctId());
//                            newProductTypePost.child("product_type_key").setValue(addProductType.getProudctId());
                            newProductTypePost.child("username")
                                    .setValue(dataSnapshot.child("UserName").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                          result = true ;
                                        mProgressDialog.dismiss();
                                    } else {

                                        result = false;
                                        mProgressDialog.dismiss();
                                    }
                                }
                            });

                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            });


        }



        return result;
    }
}
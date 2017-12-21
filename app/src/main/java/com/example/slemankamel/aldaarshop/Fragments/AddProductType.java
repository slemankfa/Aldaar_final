package com.example.slemankamel.aldaarshop.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.slemankamel.aldaarshop.FB.FBAuth;
import com.example.slemankamel.aldaarshop.FB.FBDataBase;
import com.example.slemankamel.aldaarshop.FB.FBMaker;
import com.example.slemankamel.aldaarshop.FB.FBStorge;
import com.example.slemankamel.aldaarshop.Model.AddProductTypeObject;
import com.example.slemankamel.aldaarshop.Products.ProductsMaker;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductType extends Fragment {

    View root ;

    ImageView imageProductType ;
    EditText productTitle ;
    Button addNewProduct  ;

    private FirebaseAuth   mAuth ;
    private FirebaseDatabase mDataBase ;
    private DatabaseReference productTypeRrf  ;
    private FirebaseStorage Storge;
    private StorageReference  mStorge ;
    private DatabaseReference  mDatabaseUser   ;

    AddProductTypeObject addProductType ;

    private  static  final  int GALLERY_REQUEST = 1 ;
    private Uri ImageUri = null ;


    ProductsMaker maker ;
    ProgressDialog mProgressDialog ;
    public AddProductType() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.

        root = inflater.inflate(R.layout.fragment_add_product_type, container, false);

        addProductType = new AddProductTypeObject() ;

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCanceledOnTouchOutside(false);


        FBMaker fbMaker = new FBMaker();


          mAuth  =  fbMaker.getmAuth();
          mDataBase = fbMaker.getmDatabase();
          Storge =  fbMaker.getStrorge();


//        mAuth = FBAuth.getmAuth() ;
//        mDataBase = FBDataBase.getmDatabse();
//        Storge = FBStorge.getstorage();

        mStorge = Storge.getReference();


        productTypeRrf = mDataBase.getReference().child("ProductsType");
        mDatabaseUser   =  mDataBase.getReference().child("Users").child(mAuth.getCurrentUser().getUid()) ;


        imageProductType = (ImageView) root.findViewById(R.id.img_view_add_product_type_image);
        productTitle = (EditText) root.findViewById(R.id.produt_type_name);
        addNewProduct = (Button) root.findViewById(R.id.add_new_product_type);


         maker  = new ProductsMaker(getActivity());

  

        imageProductType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent  = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent ,GALLERY_REQUEST );
            }
        });



        addNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



//                StartPosting();


//

                final String titl_Val = productTitle.getText().toString().trim();

                boolean result =  maker.getAddNewTypeResult( titl_Val ,ImageUri  );


                if(TextUtils.isEmpty(titl_Val))
                {
                    productTitle.setError("Required field");
                    return;
                }
                else if ( ImageUri == null )
                {
                    Toast.makeText(getActivity(), " Please select Image For Type ", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(result == true)
                {

                    final FragmentTransaction ft =  getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_client , new MyProducts() );
                    ft.commit();

                    ft.addToBackStack(null);

                }
                else
                {
                    Toast.makeText(getActivity(), " error  , Try a gain ", Toast.LENGTH_SHORT).show();
                }


            }
        });


        return root ;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK)
//        {
//
//
//            ImageUri =  data.getData() ;
//            imageProductType.setImageURI(ImageUri);
//        }

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK)
        {

            ImageUri = data.getData();

            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setInitialCropWindowPaddingRatio(0)
                    .setMinCropResultSize(400,400)
//                    .setMaxCropResultSize(200,200)
                    .setCropShape(CropImageView.CropShape.OVAL)
//                    .setMaxCropResultSize(200,200)
                    .start(getContext(), this);
        }


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                ImageUri  = result.getUri() ;

                imageProductType.setImageURI(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }



//    private void StartPosting() {
//
//        mProgressDialog.setMessage("...");
//
//        final String titl_Val = productTitle.getText().toString().trim();
//
//        if(!TextUtils.isEmpty(titl_Val) && ImageUri != null )
//        {
//
//            mProgressDialog.show();
//            //  add to  data base
//
//            StorageReference filePath = mStorge.child("Product_Type_Image").child(ImageUri.getLastPathSegment());
//
//            filePath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                    final  Uri downloadUri = taskSnapshot.getDownloadUrl();
//
//
////                    final DatabaseReference newProductTypePost = productTypeRrf.push();
//                     String user_id =  mAuth.getCurrentUser().getUid().toString();
//                    final DatabaseReference newProductTypePost = productTypeRrf.child(user_id).push();
//
//                    addProductType.setUserId(mAuth.getCurrentUser().getUid());
//                    addProductType.setProudctId(newProductTypePost.getKey());
//                    addProductType.setImage(downloadUri.toString());
//                    addProductType.setProductName(titl_Val);
//
//
//                    mDatabaseUser.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                            newProductTypePost.child("title").setValue(addProductType.getProductName());
//                            newProductTypePost.child("image").setValue(addProductType.getImage());
//                            newProductTypePost.child("uid").setValue(addProductType.getUserId());
//                            newProductTypePost.child("product_type_key").setValue(addProductType.getProudctId());
////                            newProductTypePost.child("product_type_key").setValue(addProductType.getProudctId());
//                            newProductTypePost.child("username")
//                                    .setValue(dataSnapshot.child("UserName").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//
//                                    if( task.isSuccessful())
//                                    {
//
//                                        final FragmentTransaction ft =  getFragmentManager().beginTransaction();
//                                        ft.replace(R.id.content_client , new MyProducts() );
//                                        ft.commit();
//
//                                        mProgressDialog.dismiss();
//
//                                        ft.addToBackStack(null);
//
//
//                                    }
//                                    else
//                                    {
//                                        Toast.makeText( getActivity() , " Error  Uploding .. Try A gain ", Toast.LENGTH_SHORT).show();
//                                        mProgressDialog.dismiss();
//
//                                    }
//                                }
//                            });
//
//                        }
//
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    }) ;
//
//
//                    mProgressDialog.dismiss();
////                    mProgressDialog.dismiss();
//
//
//                }
//            });
//
//
//
//
//
//        }else if(TextUtils.isEmpty(titl_Val))
//        {
//            productTitle.setError("Required field");
//            return;
//        }else if ( ImageUri != null )
//        {
//            Toast.makeText(getActivity(), " Please select Image For Type ", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//
//    }





}

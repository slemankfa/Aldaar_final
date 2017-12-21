package com.example.slemankamel.aldaarshop.Activites;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.slemankamel.aldaarshop.Products.ProductsMaker;
import com.example.slemankamel.aldaarshop.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import static com.example.slemankamel.aldaarshop.Products.ViewMyProductType.productTypeKey;

public class AddProductItem extends AppCompatActivity {


    private ImageView  mProductImage ;
    private EditText mProductName , mProductPrice , mProductQuntity ,  mCountryOfOrgin ;

    private Button  mAddItem ;


     private Uri imageUri  =  null ;

//    String productTypeKey  =  null ;



    private  static  final  int GALLERY_REQUEST = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_item);

        final ProductsMaker maker = new ProductsMaker(this) ;

//        productTypeKey = getIntent().getExtras().getString("productTypeKey");

        mProductImage = (ImageView) findViewById(R.id.addproductitem_image_imgview);

        mProductName = (EditText)findViewById(R.id.addproductitem_name_edt);
        mProductPrice = (EditText)findViewById(R.id.addproductitem_price_edt);
        mProductQuntity  = (EditText)findViewById(R.id.addproductitem_quntity_edt);
        mCountryOfOrgin = (EditText)findViewById(R.id.addproductitem_countryoforgin_edt);


        mAddItem = (Button) findViewById(R.id.addproductitem_add_bttn);


        mProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  gallery Intent

                Intent galleryIntent =  new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult( galleryIntent , GALLERY_REQUEST);
            }
        });


        /**
         *
         * after  check will  go to the maker
         * and start add  item To Data Base
         *
         */
        mAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(mProductName.getText()) )
                {
                    mProductName.setError("Please  , fill the Name ");
                    return;
                }
                if(TextUtils.isEmpty(mProductPrice.getText()) )
                {
                    mProductName.setError("Please  , fill the Name ");
                    return;
                }
                if(imageUri == null)
                {
                    Toast.makeText(getApplicationContext(), " please , Choose  Pic From Gallery ", Toast.LENGTH_SHORT).show();
                    return;
                }

//                String productName  , String  productType  , String productImage , String price  , final String productQuntity  , final String  countryOfOrgin

//                Toast.makeText(AddProductItem.this, mProductName.getText().toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(AddProductItem.this, String.valueOf(imageUri) , Toast.LENGTH_SHORT).show();
//                Toast.makeText(AddProductItem.this,mProductPrice.getText().toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(AddProductItem.this,mProductQuntity.getText().toString() , Toast.LENGTH_SHORT).show();
//                Toast.makeText(AddProductItem.this,mCountryOfOrgin.getText().toString() , Toast.LENGTH_SHORT).show();
//                Toast.makeText(AddProductItem.this,productTypeKey , Toast.LENGTH_SHORT).show();




                maker.startAddItem(mProductName.getText().toString() , productTypeKey ,  imageUri , mProductPrice.getText().toString() , mProductQuntity.getText().toString() , mCountryOfOrgin.getText().toString() );



            }
        });
        // end of  on create
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST &&  resultCode == RESULT_OK)
        {

            imageUri  = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setInitialCropWindowPaddingRatio(0)
//                    .setMaxCropResultSize(500,500)
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .setSnapRadius(50)
                    .start(this);


        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageUri = result.getUri();


                mProductImage.setImageURI(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }




    }
}

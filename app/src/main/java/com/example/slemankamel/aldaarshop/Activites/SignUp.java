package com.example.slemankamel.aldaarshop.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slemankamel.aldaarshop.FB.FBAuth;
import com.example.slemankamel.aldaarshop.FB.FBDataBase;
import com.example.slemankamel.aldaarshop.FB.FBMaker;
import com.example.slemankamel.aldaarshop.FB.FBStorge;
import com.example.slemankamel.aldaarshop.MainActivity;
import com.example.slemankamel.aldaarshop.R;
import com.example.slemankamel.aldaarshop.Users.Client;
import com.example.slemankamel.aldaarshop.Users.Customer;
import com.example.slemankamel.aldaarshop.Users.UserFactory;
import com.example.slemankamel.aldaarshop.Users.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class SignUp extends AppCompatActivity {


    private Button clientBtn , cosutmerBtn  , regstierBtn;
    boolean visible ;

    RadioButton  male , female  ;

    private ImageView addPhoto ;

    private EditText mName  , mPassowrd , mConfirmPassword ;

    private AutoCompleteTextView mEmail;


    private ProgressDialog mProgressDialog ;

    private  static  final int GALLERY_REQUEST = 1 ;


    Uri imageUri  = null  ;


    FirebaseAuth mAuth;

    FirebaseDatabase mdatabase ;
    private DatabaseReference mRef ;
    private DatabaseReference mDatabaseUser ;

    private FirebaseUser mCurrentUser ;

    FirebaseStorage mStorage ;
    StorageReference storge;


    DatabaseReference  mUsers ;


    private Uri downloadUri ;

    private boolean userType = true ;


    Client  client  ;
    Customer customer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        clientBtn = (Button) findViewById(R.id.client_btn);
        cosutmerBtn = (Button) findViewById(R.id.costumer_btn);
        regstierBtn = (Button) findViewById(R.id.sinup_bttn_regstir);


        addPhoto = (ImageView) findViewById(R.id.add_photo_regstir) ;

        mName = (EditText)  findViewById(R.id.name_input_regsiter);
        mPassowrd = (EditText) findViewById(R.id.password_input_regstier);
        mConfirmPassword = (EditText) findViewById(R.id.conf_password_input_regstier);

        mEmail = (AutoCompleteTextView) findViewById(R.id.email_input_regstir);

        male = (RadioButton) findViewById(R.id.rdobtn_male);
        female = (RadioButton) findViewById(R.id.rdobtn_female);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);



//         mAuth = FBAuth.getmAuth();
//         mdatabase = FBDataBase.getmDatabse();

        FBMaker fbMaker  =  new FBMaker();

        mAuth = fbMaker.getmAuth();
        mdatabase = fbMaker.getmDatabase();

        mStorage = fbMaker.getStrorge();



//         mRef = mdatabase.getReference().child("Users");

//         mStorage = FBStorge.getstorage();

        storge = mStorage.getReference() ;




        /**
         * put defult image uri
         */

        imageUri = Uri.parse("android.resource://com.example.slemankamel.aldaarshop/" + R.drawable.common_google_signin_btn_icon_dark);
        String imgPath = imageUri.toString();

        addPhoto.setImageURI(Uri.parse(imgPath));



//        if(mAuth.getCurrentUser() != null)
//        {
//            mCurrentUser = mAuth.getCurrentUser();
//
//        }



        mDatabaseUser = mdatabase.getReference().child("Users");





                clientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                visible  = !visible ;
//
                clientBtn.setTextColor(Color.argb(255 , 255 ,253, 253));

                cosutmerBtn.setTextColor(Color.argb(255 , 32  ,31, 31));



                userType = true;
                regstierBtn.setEnabled(true);


                /**
                 * create object from Users  via User Factory
                 *   object from Client
                 */

                client = (Client) UserFactory.getUser("Client") ;
//                faceBtn.setVisibility( visible ? View.GONE : View.VISIBLE);


            }
        });


        cosutmerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                cosutmerBtn.setTextColor(Color.argb(255 , 255 ,253, 253));

                clientBtn.setTextColor(Color.argb(255 , 32  ,31, 31));

                userType = false;
                regstierBtn.setEnabled(true);


                /**
                 * create object from Users  via User Factory
                 *   object from Customer
                 */

                customer = (Customer) UserFactory.getUser("Customer") ;

            }
        });



        regstierBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Toast.makeText(SignUp.this, "nmnssa,mnasnsadasd", Toast.LENGTH_SHORT).show();
                
                regsister();
            }
        });


        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , GALLERY_REQUEST );


            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK)
        {

          imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setInitialCropWindowPaddingRatio(0)
                    .setMinCropResultSize(100,100)
                    .setMaxCropResultSize(200,200)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setMaxCropResultSize(200,200)
                    .setSnapRadius(50)
                    .start(this);
        }


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageUri = result.getUri();


                addPhoto.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    private void regsister() {

        final String name = mName.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        String password = mPassowrd.getText().toString();
        String ConfirmPassword = mConfirmPassword.getText().toString().trim();


        if(TextUtils.isEmpty(name))
        {
            mName.setError("Required field");
            return ;
        }
        if(TextUtils.isEmpty(email))
        {
            mEmail.setError("Required field");
            return ;
        }
        if(TextUtils.isEmpty(password))
        {
            mPassowrd.setError("Required field");
            return ;
        }
        if(TextUtils.isEmpty(ConfirmPassword))
        {
            mConfirmPassword.setError("Required field");
            return ;
        }
        if(!isEmailValid(email))
        {
            mEmail.setError("error invalid email");
            return ;
        }
        if(password.length()<6)
        {
            mPassowrd.setError("This password is too short");
            return ;
        }
        if(!ConfirmPassword.equals(password))
        {

            mConfirmPassword.setError("No Match with Password try a gain ");
            return;
        }




        mProgressDialog.setMessage("signing up ...");
        mProgressDialog.show();

        mAuth.createUserWithEmailAndPassword(email , password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {



                        final String user_id = mAuth.getCurrentUser().getUid();


                        if(task.isSuccessful())
                        {

                            // creat new User
                            final DatabaseReference newUser = mDatabaseUser.child(user_id);

                            // get Image
                            StorageReference filePath = storge.child("profile_pic").child(imageUri.getLastPathSegment());


                            Toast.makeText(SignUp.this, " Sign up done", Toast.LENGTH_SHORT).show();

                            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    downloadUri = taskSnapshot.getDownloadUrl();


                                    //  here  public information about  User


                                    if(userType == true )
                                    {
                                        /**
                                         * Client Information
                                         *  valdtion from object
                                         */
                                        client.setName(name);
                                        client.setEmail(email);
                                        client.setId(user_id);
                                        client.setPicture(downloadUri.toString());
                                        client.setTotalAmount(0);
                                        client.setNumberOfProducts(0);


                                        if(male.isChecked())
                                        {
                                            client.setGender("Male");
                                        }else if (female.isChecked())
                                        {
                                            client.setGender("Female");
                                        }


                                        newUser.child("UserName").setValue(client.getName());
                                        newUser.child("profilePic").setValue(client.getPicture());
                                        newUser.child("Email").setValue(client.getEmail());
                                        newUser.child("UserType").setValue("Client");
                                        newUser.child("TotalAmount").setValue(client.getTotalAmount());
                                        newUser.child("NumberOfProducts").setValue(client.getNumberOfProducts());
                                        newUser.child("Gender").setValue(client.getGender());

                                        mProgressDialog.dismiss();

                                        startActivity( new Intent(SignUp.this , ClientActivity.class));

                                        finish();

                                    }else
                                    {


                                        customer.setId(user_id);
                                        customer.setName(name);
                                        customer.setEmail(email);
                                        customer.setId(user_id);
                                        customer.setPicture(downloadUri.toString());
                                        customer.setBalance(0);
                                        customer.setNumberOfPurchases(0);

                                        if(male.isChecked())
                                        {
                                            customer.setGender("Male");
                                        }else if (female.isChecked())
                                        {
                                            customer.setGender("Female");
                                        }


                                        newUser.child("UserName").setValue(customer.getName());
                                        newUser.child("profilePic").setValue(customer.getPicture());
                                        newUser.child("Email").setValue(customer.getEmail());
                                        newUser.child("UserType").setValue("Customer");
                                        newUser.child("Balance").setValue(customer.getBalance());
                                        newUser.child("NumberOfPurchases").setValue(customer.getNumberOfPurchases());
                                        newUser.child("Gender").setValue(customer.getGender());

//                                        newUser.child("UserType").setValue("Costumer");
                                        mProgressDialog.dismiss();

                                        startActivity( new Intent(SignUp.this , CustomerActivity.class));

                                        finish();

                                    }


//                                    Toast.makeText(SignUp.this, " up to data base", Toast.LENGTH_SHORT).show();





                                }
                            });



                        }
                        else
                        {
                            mProgressDialog.dismiss();
                            Toast.makeText(SignUp.this, " field ..  try A gian ", Toast.LENGTH_SHORT).show();

                        }

                    }
                });



    }

    private boolean isEmailValid(String email )
    {
        return email.contains("@");
    }

}

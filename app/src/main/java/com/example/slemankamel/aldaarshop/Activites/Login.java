package com.example.slemankamel.aldaarshop.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.slemankamel.aldaarshop.FB.FBAuth;
import com.example.slemankamel.aldaarshop.FB.FBDataBase;
import com.example.slemankamel.aldaarshop.FB.FBMaker;
import com.example.slemankamel.aldaarshop.MainActivity;
import com.example.slemankamel.aldaarshop.R;
import com.example.slemankamel.aldaarshop.Users.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private Button emailBtn , faceBtn , googleBtn , signinBtn  , signupBtn  ;

    boolean visible ;


    AutoCompleteTextView mEmail ;
    EditText mPassword ;

      TextInputLayout  input1 , input2 ;

    FirebaseAuth  mAuth ;

    FirebaseDatabase  mDataBase ;
    DatabaseReference  mRef ;

    ProgressDialog mProgressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



//        mAuth = FBAuth.getmAuth();
//        mDataBase = FBDataBase.getmDatabse();

        FBMaker fbMaker  =  new FBMaker();

        mAuth = fbMaker.getmAuth();
        mDataBase = fbMaker.getmDatabase();


        mRef = mDataBase.getReference().child("Users");


        mProgressDialog = new ProgressDialog(this);

        mProgressDialog.setCanceledOnTouchOutside(false);


        emailBtn = (Button)  findViewById(R.id.email_signin_btn);
        faceBtn = (Button) findViewById(R.id.facebook_signin_btn);
        googleBtn = (Button) findViewById(R.id.google_signin_btn);

        signinBtn = (Button) findViewById(R.id.signin_bttn);
        signupBtn = (Button) findViewById(R.id.sinup_bttn);


        mEmail = (AutoCompleteTextView) findViewById(R.id.email_input_login);
        mPassword = (EditText) findViewById(R.id.password_input_login);

        input1 =(TextInputLayout) findViewById(R.id.txt_input);
        input2 =(TextInputLayout) findViewById(R.id.txt_input2);

        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                showbuttn();
            }

            
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity( new Intent(Login.this  , SignUp.class));
            }
        });



        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                attempLogin();

            }
        });
    }

    private void attempLogin() {



        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString();



        if(TextUtils.isEmpty(email))
        {
            mEmail.setError("Required field");
            return ;
        }
        if(TextUtils.isEmpty(password))
        {
            mPassword.setError("Required field");
            return ;
        }

        if(!isEmailValid(email))
        {
            mEmail.setError("error invalid email");
            return ;
        }
        if(password.length()<6)
        {
            mPassword.setError("This password is too short");
            return ;
        }


        mProgressDialog.setMessage("Sign in ...");
        mProgressDialog.show();
        mAuth.signInWithEmailAndPassword(email , password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful())
                        {

                            final String curr_user  = mAuth.getCurrentUser().getUid();

                            mRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if(dataSnapshot.hasChild(curr_user) && dataSnapshot.child(curr_user).child("UserType").getValue().equals("Client"))
                                    {

                                        Intent Client = new Intent(Login.this , ClientActivity.class) ;
                                        startActivity( Client);
                                        finish();
                                        mProgressDialog.dismiss();

                                    }
                                    else if(dataSnapshot.hasChild(curr_user) && dataSnapshot.child(curr_user).child("UserType").getValue().equals("Customer"))
                                    {
                                        Intent Customer = new Intent(Login.this , CustomerActivity.class) ;
                                        startActivity( Customer );
                                        finish();
                                        mProgressDialog.dismiss();
                                    }



//                                    Toast.makeText(Login.this, " login filed  ", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
//                            Toast.makeText(Login.this, " Login Done ", Toast.LENGTH_SHORT).show();
//
//                            mProgressDialog.dismiss();
//                            startActivity(new Intent( Login.this , MainActivity.class));
//                            finish();
                        }
                        else
                        {
                            Toast.makeText(Login.this, " Login Filed ", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    }
                });



    }


    private void showbuttn() {

        visible  = !visible ;

        faceBtn.setVisibility( visible ? View.GONE : View.VISIBLE);
        googleBtn.setVisibility( visible ? View.GONE : View.VISIBLE);


        input1.setVisibility( visible ? View.VISIBLE  : View.GONE);
        input2.setVisibility( visible ? View.VISIBLE  : View.GONE);


        if(emailBtn.getText().equals("Use Email"))
        {
            emailBtn.setText("Back");
        }
        else
        {
            emailBtn.setText("Use Email");
        }

    }


    private boolean isEmailValid(String email )
    {
        return email.contains("@");
    }



}

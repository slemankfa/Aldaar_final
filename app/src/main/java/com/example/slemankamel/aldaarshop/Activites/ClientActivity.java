package com.example.slemankamel.aldaarshop.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slemankamel.aldaarshop.FB.FBAuth;
import com.example.slemankamel.aldaarshop.FB.FBDataBase;
import com.example.slemankamel.aldaarshop.FB.FBMaker;
import com.example.slemankamel.aldaarshop.Fragments.MyProducts;
import com.example.slemankamel.aldaarshop.MainActivity;
import com.example.slemankamel.aldaarshop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ClientActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseAuth.AuthStateListener   mAuthStateListener;



    private FirebaseAuth mAuth ;
    FirebaseDatabase mDataBase ;
    DatabaseReference mRef ;

    View header ;

    private ImageView  profilePic ;
    private TextView userName , emailUser ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        mAuth = FBAuth.getmAuth();
//        mDataBase = FBDataBase.getmDatabse();

        FBMaker fbMaker  =  new FBMaker();

        mAuth = fbMaker.getmAuth();
        mDataBase = fbMaker.getmDatabase();


        mRef = mDataBase.getReference().child("Users");





        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(mAuth.getCurrentUser() == null)
                {
                    startActivity( new Intent(ClientActivity.this , MainActivity.class));
                    finish();
                }
            }
        };





        // end  Bundle

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /**
         * to fetch item  from nav view
         */

//        header = LayoutInflater.from(this) .inflate(R.layout.nav_header_client , null);
//        navigationView.addHeaderView(header);

        header = navigationView.getHeaderView(0);

        profilePic = (ImageView) header.findViewById(R.id.profile_pic_client);
        userName = (TextView) header.findViewById(R.id.user_name_client);
        emailUser = (TextView) header.findViewById(R.id.email_client);


        String user_id = mAuth.getCurrentUser().getUid();

        mRef.child(user_id).addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
//

                String mUserName = (String) dataSnapshot.child("UserName").getValue();
                String mEmail = (String) dataSnapshot.child("Email").getValue();
                String mProfilePic = (String) dataSnapshot.child("profilePic").getValue();
//
//                Toast.makeText(ClientActivity.this, mUserName, Toast.LENGTH_SHORT).show();
//                Toast.makeText(ClientActivity.this, mEmail, Toast.LENGTH_SHORT).show();

                userName.setText(mUserName);
                emailUser.setText(mEmail);
                Picasso.with(getApplicationContext()).load(mProfilePic).into(profilePic);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthStateListener);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_account) {

        } else if (id == R.id.nav_my_products) {

            getSupportFragmentManager().beginTransaction().replace(R.id.content_client  , new MyProducts()).commit();



        } else if (id == R.id.nav_mesages) {

            // test
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_client  , new MyProductTypeItem()).commit();

        } else if (id == R.id.nav_my_wallet) {

        } else if (id == R.id.nav_sing_out) {

            mAuth.signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

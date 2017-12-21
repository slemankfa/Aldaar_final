package com.example.slemankamel.aldaarshop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slemankamel.aldaarshop.Activites.ClientActivity;
import com.example.slemankamel.aldaarshop.Activites.CustomerActivity;
import com.example.slemankamel.aldaarshop.Activites.Login;
import com.example.slemankamel.aldaarshop.Adapters.FireBaseAdpaterGuest;
import com.example.slemankamel.aldaarshop.FB.FBAuth;
import com.example.slemankamel.aldaarshop.FB.FBDataBase;
import com.example.slemankamel.aldaarshop.FB.FBMaker;
import com.example.slemankamel.aldaarshop.Model.FbGuestItem;
import com.example.slemankamel.aldaarshop.Model.MyProductItem;
import com.example.slemankamel.aldaarshop.Users.Customer;
import com.example.slemankamel.aldaarshop.ViewPager.ViewPagerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseAuth mAuth ;
    FirebaseDatabase mRef  ;
    DatabaseReference mProduct  , mUser ;

    ViewPager  productViewPager ;
    ViewPagerAdapter  productHeaderAdpater ;
    PageIndicatorView  pageIndicatorView ;

    ArrayList<String> imagePath ;


    RecyclerView tvDataGuest ;

    FireBaseAdpaterGuest fireBaseAdpaterGuest;


    ProgressDialog mProgressDialog ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FBMaker  fbMaker = new FBMaker();

        mAuth  =  fbMaker.getmAuth();
        mRef = fbMaker.getmDatabase();


//        mAuth = FBAuth.getmAuth();
//        mRef  = FBDataBase.getmDatabse();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);



        mProduct = mRef.getReference().child("ProductsType");
        mUser = mRef.getReference().child("Users");


        // set Defult User
        mProgressDialog.show();
        SetDefutleUser();
        mProgressDialog.dismiss();
//        CheckUserType();




        fireBaseAdpaterGuest = new FireBaseAdpaterGuest(this);


        imagePath  =new ArrayList<>();


        tvDataGuest  = (RecyclerView) findViewById(R.id.tv_data_guest_product);

        tvDataGuest.setHasFixedSize(true);
        tvDataGuest.setLayoutManager( new LinearLayoutManager(this));
//        tvDataGuest.setLayoutManager( new GridLayoutManager(this , 2));


        /**
         * creat view pager to add  new product image
         * add uri to imageview  from Array list we pass in from View pager Constructoe
         */

        imagePath.add("http://lorempixel.com/400/200");
        imagePath.add("http://lorempixel.com/g/400/200");
        imagePath.add("http://lorempixel.com/400/200/sports");
        imagePath.add("http://lorempixel.com/400/200/sports/1");

        productViewPager  = (ViewPager) findViewById(R.id.header_product_container) ;
        productHeaderAdpater  =  new ViewPagerAdapter(getApplicationContext() , imagePath) ;



        productViewPager.setAdapter(productHeaderAdpater);


        pageIndicatorView  = (PageIndicatorView)findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setViewPager(productViewPager);


//        pageIndicatorView.setAnimationType(AnimationType.SWAP);
//        pageIndicatorView.setUnselectedColor(Color.GREEN);
//        pageIndicatorView.setSelectedColor(Color.BLUE);



        // end of  product view Pager

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);



        // end of onCreate
    }


    @Override
    protected void onStart() {
        super.onStart();


        /**
         *
         * check  the Type of Users  if defult convert him to main
         * if Client Convert him to Client Activity
         * if Costumer Convert him to costumer Activity
         */


//        CheckUserType();
        SetDefutleUser();


        // end

//        FirebaseRecyclerAdapter<MyProductItem,ProductViewHolder> FbRecyclerAdapter
//             =new FirebaseRecyclerAdapter<MyProductItem, ProductViewHolder>(
//                MyProductItem.class ,
//                     R.layout.my_products_item ,
//                     ProductViewHolder.class,
//                     mProduct
//             ) {
//
//            @Override
//            protected void populateViewHolder(ProductViewHolder viewHolder, MyProductItem model, int position) {
//
//
//                viewHolder.setTilte(model.getTitle());
//                viewHolder.setImage(model.getImage() , getApplicationContext());
//
////            @Override
////            protected void populateViewHolder(ProductViewHolder viewHolder, FbGuestItem model, int position) {
////
////
////                viewHolder.setImage(model.getImagePath()  , getApplicationContext() );
////
//////                viewHolder.setPrice(model.getProductPrice());
////
////            }
//        };





//        tvDataGuest.setAdapter(FbRecyclerAdapter);
//        tvDataGuest.setAdapter(fireBaseAdpaterGuest.Adapter());

    }


    /**
     * view holder  to featch items
     */


//    public static class ProductViewHolder extends RecyclerView.ViewHolder{
//
//
//
//        View root ;
//
//        private ImageView productImage ;
//        private TextView productPrice ;
//
//
//        public ProductViewHolder(View itemView) {
//            super(itemView);
//
//            root = itemView ;
//
//        }
//
//        public void setImage (String imagePath  , Context context)
//        {
//
//            productImage = (ImageView) root.findViewById(R.id.product_image_item);
//            Picasso.with(context).load(imagePath).into(productImage);
//
//        }
//
//
//        public  void setTilte ( String title )
//        {
//
//            productPrice = (TextView) root.findViewById(R.id.product_price_item);
//
//            productPrice.setText(title);
//
//        }
//
//
//
//
//    }






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
        getMenuInflater().inflate(R.menu.main, menu);
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

            mAuth.signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

            startActivity( new Intent(MainActivity.this  , Login.class));


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public  void CheckUserType()
    {

        mProgressDialog.setMessage("Check .... ");

//        Toast.makeText(this, " login Checkuser Type ", Toast.LENGTH_SHORT).show();
        final String curr_user = mAuth.getCurrentUser().getUid();

       mUser.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {


               if(dataSnapshot.hasChild(curr_user) && dataSnapshot.child(curr_user).child("UserType").getValue().equals("Client") )
               {

//                   Toast.makeText(MainActivity.this, " login to  client ", Toast.LENGTH_SHORT).show();
                   startActivity( new Intent(MainActivity.this , ClientActivity.class));
                   finish();
                   mProgressDialog.dismiss();

               }else
               {

//                   Toast.makeText(MainActivity.this, " login to Customer  ", Toast.LENGTH_SHORT).show();

                   startActivity( new Intent(MainActivity.this , CustomerActivity.class));
                   finish();
                   mProgressDialog.dismiss();

               }


               mProgressDialog.dismiss();
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });


    }


    public  void SetDefutleUser()
    {


        if(mAuth.getCurrentUser() == null )
        {

            String dEmail = "def@def.com" ;
            String dPassword = "123456" ;
//            Toast.makeText(this, "set defult user ", Toast.LENGTH_SHORT).show();

            mAuth.signInWithEmailAndPassword(dEmail , dPassword)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {

                                mProgressDialog.dismiss();

                                Toast.makeText(MainActivity.this, "done ", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Check Your Connection ", Toast.LENGTH_SHORT).show();

                                mProgressDialog.dismiss();

                            }


                        }

                    });

        }
        else
        {
//            Toast.makeText(this, "  There user  ", Toast.LENGTH_SHORT).show();
            CheckUserType() ;

        }
    }
}

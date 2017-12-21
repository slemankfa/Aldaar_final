package com.example.slemankamel.aldaarshop.FB;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

/**
 * Created by SlemanKamel on 11/27/2017.
 */

public class FBMaker {

//
//    private  FBAuth fbAuth ;
//    private FBDataBase fbDataBase ;
//    private FBStorge fbStorge ;


    public  void FBMaker()
    {

    }

    public FirebaseAuth  getmAuth()
    {
      return   FBAuth.getmAuth();
    }

    public FirebaseStorage  getStrorge()
    {
        return FBStorge.getstorage();
    }

    public FirebaseDatabase getmDatabase()
    {

        return  FBDataBase.getmDatabse();
    }
}

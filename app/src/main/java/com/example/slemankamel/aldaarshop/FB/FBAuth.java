package com.example.slemankamel.aldaarshop.FB;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by SlemanKamel on 10/6/2017.
 */

public class FBAuth {



    private  static FirebaseAuth  mAuth  ;

    private  FBAuth()
    {

    }


    public  static FirebaseAuth getmAuth()
    {
        if(mAuth == null )
        {
            return  mAuth =  FirebaseAuth.getInstance() ;
        }

        return  mAuth ;

    }
}

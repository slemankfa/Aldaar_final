package com.example.slemankamel.aldaarshop.FB;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by SlemanKamel on 10/6/2017.
 */

public class FBDataBase {

    private  static FirebaseDatabase mDatabse ;

    private FBDataBase()
    {

    }

    public  static FirebaseDatabase getmDatabse()
    {
        if(mDatabse == null )
        {
            return  mDatabse = FirebaseDatabase.getInstance() ;
        }

        return  mDatabse ;

    }
}

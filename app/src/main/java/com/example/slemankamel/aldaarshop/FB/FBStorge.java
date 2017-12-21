package com.example.slemankamel.aldaarshop.FB;

import com.google.firebase.storage.FirebaseStorage;

/**
 * Created by SlemanKamel on 10/6/2017.
 */

public class FBStorge {

    private static FirebaseStorage storage ;


    private FBStorge()
    {

    }


    public  static FirebaseStorage getstorage()
    {
        if(storage == null )
        {
            return  storage = FirebaseStorage.getInstance() ;
        }

        return  storage ;

    }
}

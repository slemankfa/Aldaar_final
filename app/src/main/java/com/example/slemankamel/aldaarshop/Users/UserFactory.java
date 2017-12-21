package com.example.slemankamel.aldaarshop.Users;

/**
 * Created by SlemanKamel on 10/18/2017.
 */

public class UserFactory {


    public  static  Users  getUser(String type )
    {
        if (type == null )
        {
            return  null ;
        }
        if (type.equalsIgnoreCase("Client"))
        {

            return  new Client("1" , "client" , "client@client.com", "picPath", "M" , 0 , 0);
        }
        if (type.equalsIgnoreCase("Customer"))
        {

            return  new Customer("1" , "Customer" , "Customer@client.com", "picPath", "M" , 0 , 0);
        }

        return   null ;
    }

}

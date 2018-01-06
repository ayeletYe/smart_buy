package com.example.yaeli.smart_buy;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by yaeli on 30/11/2017.
 */

public class UserLocalStore {
    public static final String SP_NAME= "userDetails"; //name of the file where the shared preference data is coming from
    SharedPreferences userLocalDatabase;


    public UserLocalStore(Context context){ //only activities can instatiate Shared Preference so we force the activity to give its context
        context.getSharedPreferences(SP_NAME,0);
    }

    public void storeUserData(User user,Context context){
        userLocalDatabase= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=userLocalDatabase.edit();
        //SharedPreferences.Editor editor= userLocalDatabase.edit();
        editor.putString("userName",user.userName);
        //editor.putString("password",user.password);
        editor.putString("firstName",user.firstName);
        editor.putString("lastName",user.lastName);
        editor.putString("address",user.address);
        editor.putString("city",user.city);
        editor.putString("Email",user.Email);
        editor.commit();
    }

    public User getUserData(){
        String userName=userLocalDatabase.getString("userName","");
        //String password=userLocalDatabase.getString("password","");
        String firstName=userLocalDatabase.getString("firstName","");
        String lastName=userLocalDatabase.getString("lastName","");
        String address=userLocalDatabase.getString("address","");
        String city=userLocalDatabase.getString("city","");
        String Email=userLocalDatabase.getString("Email","");
        return new User(userName,firstName,lastName,address,city);
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor editor=userLocalDatabase.edit();
        editor.putBoolean("loggedIn: ",loggedIn);
        editor.commit();
    }

    public void clearUserData(){ //when user logges out
        SharedPreferences.Editor editor=userLocalDatabase.edit();
        editor.clear();
        editor.commit();
    }


}

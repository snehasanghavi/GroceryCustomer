package com.teammandroid.dairyapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.teammandroid.dairyapplication.activities.AuthenticationActivity;
import com.teammandroid.dairyapplication.model.UserModel;


public class SessionManager {

    private static final String TAG = SessionManager.class.getSimpleName();

    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared pref file name
    private static final String PREF_NAME = "SPF_PREF";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User id
    public static final String KEY_USERDATA = "USERDATA";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();
    }

    public void createLoginSession(UserModel user){

        editor.putBoolean(IS_LOGIN, true);
        try {
            Gson gson = new Gson();
            String json = gson.toJson(user);
            editor.putString(KEY_USERDATA, json);
            editor.commit();
        } catch (Exception ex) {
            Log.e(TAG, "setStudentInSharedPref: ", ex);
        }
    }
    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGIN, isLoggedIn);
        // commit changes
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }


    public boolean checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, AuthenticationActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
            return false;
        }
        return true;

    }


    public UserModel getUserDetails(){
        UserModel user = null;

        try {
            String response =  pref.getString(KEY_USERDATA, null);
            Gson gson = new Gson();
            user = gson.fromJson(response, UserModel.class);
        } catch (Exception ex) {
            Log.e(TAG, "getStudentFromSharedPref: ", ex);
        }
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.remove(KEY_USERDATA);
        editor.remove(IS_LOGIN);
        editor.commit();

        Intent i = new Intent(_context, AuthenticationActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

}
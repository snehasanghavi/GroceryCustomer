package com.teammandroid.dairyapplication.activities;

import android.app.Dialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.error.ANError;
import com.google.android.material.snackbar.Snackbar;
import com.teammandroid.dairyapplication.Network.AppVersionServices;
import com.teammandroid.dairyapplication.R;
import com.teammandroid.dairyapplication.admin.activities.DeliveryboyStatusListActivity;
import com.teammandroid.dairyapplication.admin.activities.SelectRoleActivity;
import com.teammandroid.dairyapplication.interfaces.ApiStatusCallBack;
import com.teammandroid.dairyapplication.model.AppVersionModel;
import com.teammandroid.dairyapplication.model.UserModel;
import com.teammandroid.dairyapplication.utils.PrefManager;
import com.teammandroid.dairyapplication.utils.SessionHelper;
import com.teammandroid.dairyapplication.utils.Utility;

import java.util.ArrayList;

public class UserSplashActivity extends AppCompatActivity {

    private static final String TAG = UserSplashActivity.class.getSimpleName();

    SessionHelper sessionHelper;
    PrefManager prefManager;

    String sversion;
    double version_code;

    Handler handler;
    Dialog resultbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        sessionHelper = new SessionHelper(UserSplashActivity.this);
        prefManager = new PrefManager(UserSplashActivity.this);

        // current version
        PackageManager manager = getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(
                    getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        sversion = info.versionName;
        version_code = Double.parseDouble(sversion);

        //  getAppVersion();
        animation();
        Log.e("versionName", sversion +" "+version_code);
    }

    private void getAppVersion() {
        try {
            if (Utility.isNetworkAvailable(UserSplashActivity.this)) {

                Log.e("CheckReponseVideostry", "Called");
                AppVersionServices.getInstance(UserSplashActivity.this).getAppVersion(
                        new ApiStatusCallBack<ArrayList<AppVersionModel>>() {

                            @Override
                            public void onSuccess(ArrayList<AppVersionModel> arraylist) {
                                Log.e("CheckReponseVideosSucs", "Called");

                                Log.e("cHkVersion", "" + arraylist.get(0).getAppversionid());
                                //Change code
                                if (version_code < Double.parseDouble(arraylist.get(0).getVersionno())) {
                                    // if (version_code > Double.parseDouble(arraylist.get(0).getVersionno())) {
                                    final AppVersion_Dialog dialog = new AppVersion_Dialog(UserSplashActivity.this);
                                    dialog.show();
                                    dialog.setCanceledOnTouchOutside(false);
                                } else {
                                    animation();
                                }

                                //  prefManager.setAPP_VERSION(String.valueOf(arraylist.get(0).getAppversionid()));

                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.e("CheckReponseanError", "" + anError.getMessage());
                                Utility.showErrorMessage(UserSplashActivity.this, "Network:" + anError.getMessage(), Snackbar.LENGTH_LONG);
                            }

                            @Override
                            public void onUnknownError(Exception e) {
                                Log.e("CheckReponseUnknown", "Called");
                                //   Utility.showErrorMessage(getActivity(), e.getMessage(), Snackbar.LENGTH_LONG);
                            }
                        });
            } else {
                Utility.showErrorMessage(UserSplashActivity.this, "Could not connect to the internet", Snackbar.LENGTH_LONG);
            }
        } catch (Exception ex) {
            Log.e("CheckReponseOther", "Called");
            Log.e("GetVideoPackages", "InsideGetVideoPackagesExtra" + ex);
            Utility.showErrorMessage(UserSplashActivity.this, "No record found", Snackbar.LENGTH_LONG);
        }
    }

    public void animation() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //1 admin
                // 2 delievryboy
                // 3 user

                Utility.launchActivity(UserSplashActivity.this, HomepageActivity.class, true);

                /*else if (prefManager.getROLE_ID() == 3) {
                    } */
               /* if(sessionHelper.isLoggedIn()) {
                    if (prefManager.getROLE_ID() == 1) {
                        Utility.launchActivity(UserSplashActivity.this, HomepageActivity.class, true);
                    } else if (prefManager.getROLE_ID() == 2) {
                        Utility.launchActivity(UserSplashActivity.this, DeliveryboyStatusListActivity.class, true);
                    } else if (prefManager.getROLE_ID() == 5) {
                        Utility.launchActivity(UserSplashActivity.this, SelectRoleActivity.class, true);
                    }
                }
                else {
                    Utility.launchActivity(UserSplashActivity.this, SelectRoleActivity.class, true);
                }*/
            }
        }, 3000);
    }

    private boolean validateUser() {
        boolean result = false;
        try {
            SessionHelper sessionHelper=new SessionHelper(UserSplashActivity.this);
            //SessionManager sessionManager=new SessionManager(SplashActivity.this);
            //UserResponse response = PrefHandler.getUserFromSharedPref(SplashActivity.this);
            UserModel response = sessionHelper.getUserDetails();
            Log.e(TAG, "validateUser: "+response.toString());
            if (response.getUserid() > 0) {
                result = true;
            }
        } catch (Exception ex) {
            Log.e(TAG, "validateUser: ", ex);
        }
        return result;
    }



}

/*Log.e("versionName", sversion);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               /* if (validateUser()) {
                    Utility.launchActivity(SplashActivity.this, HomepageActivity.class, true);
                } else {
                    Utility.launchActivity(SplashActivity.this, AuthenticationActivity.class, true);
                }*/

        /*        if(sessionHelper.isLoggedIn()) {
                        if (prefManager.getROLE_ID() == 1) {
                        Utility.launchActivity(SplashActivity.this, HomepageActivity.class, true);
        } else if (prefManager.getROLE_ID() == 2) {
        Utility.launchActivity(SplashActivity.this, HomepageActivity.class, true);
        } else if (prefManager.getROLE_ID() == 5) {
        Utility.launchActivity(SplashActivity.this, AuthenticationActivity.class, true);
        }
        }
        else {
        Utility.launchActivity(SplashActivity.this, AuthenticationActivity.class, true);
        }
        }
        }, 2000);*/
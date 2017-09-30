package com.example.chen1.uncom;



import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;


/**
 * A login screen that offers login via email/password.
 */
public class AccessActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);
        FragmentManager fragmentManager=  getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        LoginPageFragment loginPageFragment =LoginPageFragment.getInstance();
        fragmentTransaction.add(R.id.access_viewgroup,loginPageFragment);
        fragmentTransaction.commit();

        // Set up the login form.

    }

}


package com.example.chen1.uncom;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chen1.uncom.LoginPageFragment;
import com.example.chen1.uncom.MePageMainFragment;
import com.example.chen1.uncom.R;


/**
 * A login screen that offers login via email/password.
 */
public class AccessActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);
        FragmentManager fragmentManager= this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        LoginPageFragment loginPageFragment =new LoginPageFragment();
        fragmentTransaction.replace(R.id.access_viewgroup,loginPageFragment,"test");
        fragmentTransaction.commit();

        // Set up the login form.

    }

}


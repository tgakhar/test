package com.example.beingamit.blooddonor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity
{
    TextView tvHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.iconf);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        tvHelp=(TextView)findViewById(R.id.textHelp);
        tvHelp.setText("This app provides you the blood donors in your city or state.You can directly contact to the blood donor without " +
                "any registration and it will not consume any time.We are " +
                "providing you the address and contact no. of the donor.If you are willing to " +
                "donate the blood,then you have to register yourself by clicking the register button." +
                "But if you are already registered,then you have to login by clicking the login button.After" +
                " login the account,you can check your profile and other related settings of the profile.");
    }


}

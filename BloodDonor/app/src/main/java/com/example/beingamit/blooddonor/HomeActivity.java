package com.example.beingamit.blooddonor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity
{

    TextView tvRegister,tvSearch,tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.iconf);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        tvRegister=(TextView)findViewById(R.id.textRegister);
        tvSearch=(TextView)findViewById(R.id.textSearch);
        tvLogin=(TextView)findViewById(R.id.textLogin);


        SharedPreferences sp_userdetails=getSharedPreferences("donordetails", Context.MODE_PRIVATE);
           String name=sp_userdetails.getString("name","");
           if(!name.equals(""))
           {
               //tvRegister.setVisibility(View.INVISIBLE);
               //tvLogin.setVisibility(View.INVISIBLE);

               Intent intent=new Intent(HomeActivity.this,NavigationActivity.class);
               startActivity(intent);



           }






        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(intent);
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help)
        {
            Intent intent=new Intent(getApplicationContext(),HelpActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_share)
        {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/html");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>This is the text that will be shared.</p>"));
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
            return true;
        }
        if (id == R.id.action_about)
        {
            Intent intent=new Intent(getApplicationContext(),AboutActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}

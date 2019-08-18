package com.example.beingamit.blooddonor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity
{
    TextView tvAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.iconf);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        tvAbout=(TextView)findViewById(R.id.textAbout);
        tvAbout.setText("As you read this,a patient may be critically ill."+
                "Whether he lives or dies on whether a unit of blood of the right type is available."+
                "India is always facing a shortage of blood.People have to run from pillar to post for a unti of blood in their own city or some other city where they go from treatment.At times,a patient dies for want of blood.There are" +
                "unscruplous elements who sell blood for money.\n" +
                "This mobile app based helpline which helps patients who need blood donors in their own city or some other city where they go for treatment.The services" +
                "of the blood donors and helpline is free of cost.\n" +
                "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

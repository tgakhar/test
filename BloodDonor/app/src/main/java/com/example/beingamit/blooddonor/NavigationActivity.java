package com.example.beingamit.blooddonor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.ActionBarDrawerToggle;

import static android.content.Intent.ACTION_CALL;


public class NavigationActivity extends ActionBarActivity
{

    String name,number,address;
    TextView na,no,ad;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;

    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        na=(TextView)findViewById(R.id.textNameProfile);
        no=(TextView)findViewById(R.id.textMobileProfile);
        ad=(TextView)findViewById(R.id.textEmailProfile);

        SharedPreferences sp_userdetails=getSharedPreferences("donordetails", Context.MODE_PRIVATE);
        name=sp_userdetails.getString("name","");
        number=sp_userdetails.getString("mobile","");
        address=sp_userdetails.getString("address","");
        na.setText("Name: "+name);
        no.setText("Mobile No:"+number);
        ad.setText("Address: "+address);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



    }

    private void addDrawerItems() {
        final String[] menu = { "Search Donor", "Update Profile","Change Password", "Account Status", "Feedback", "Call us","About us" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, menu);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //    Toast.makeText(NavigationActivity.this,  menu[position], Toast.LENGTH_SHORT).show();
                switch (position)
                {
                    case 0:
                        startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getApplicationContext(),UpdateActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(),ChangePasswordActivity.class));
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(),"Status",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        startActivity(new Intent(getApplicationContext(),FeedbackActivity.class));
                        break;
                    case 5:
                        String mob="9646646757";
                        Intent inte=new Intent(ACTION_CALL);
                        inte.setData(Uri.parse("tel:"+mob));
                        startActivity(inte);
                        break;
                    case 6:
                        startActivity(new Intent(getApplicationContext(),AboutActivity.class));
                        break;
                }

            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.drawer_open,R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout)
        {
            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_helpNavi)
        {
            Intent intent=new Intent(getApplicationContext(),HelpActivity.class);
            startActivity(intent);
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

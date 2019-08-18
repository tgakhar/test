package com.example.beingamit.blooddonor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appdatasearch.GetRecentContextCall;


import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity
{
    Spinner spBlood,spState;
    Context cntx;
    AutoCompleteTextView spCity;
    String blood[]={"--Select Blood Group--","A+","B+","AB+","O+","A-","B-","AB-","O-"};
    ArrayList citylist,statlist;
    Button btnFind;

    String url="https://raw.githubusercontent.com/nshntarora/Indian-Cities-JSON/master/cities-name-list.json";

    InputStream is=null;

    String bg,st,ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.iconf);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        cntx=this;
       // spState=(Spinner)findViewById(R.id.spinnerStateSearch);
        spCity=(AutoCompleteTextView) findViewById(R.id.spinnerCitySearch);
        spBlood=(Spinner)findViewById(R.id.spinnerBloodSearch);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, blood);
        spBlood.setAdapter(ad);
        new json().execute();

        btnFind=(Button)findViewById(R.id.buttonFindSearch);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bg=spBlood.getSelectedItem().toString();
               // st=spState.getSelectedItem().toString();
                ct=spCity.getText().toString();
                Bundle bd=new Bundle();
                bd.putString("blood",bg);
                //bd.putString("sta",st);
                bd.putString("cit",ct);

                if (bg.isEmpty()  || ct.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Enter all field", Toast.LENGTH_LONG).show();
                }
                else
                {
                  startActivity(new Intent(getApplicationContext(),ListActivity.class).putExtra("data",bd));
                }

                /*Intent intent=new Intent(getApplicationContext(),ListActivity.class);
                intent.putExtra("data",bd);
                startActivity(intent);*/


            }
        });
    }


    class json extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog pd;
        String incommingJson;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(cntx);
            pd.setMessage("loading...");
            pd.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            citylist=new ArrayList();
            statlist=new ArrayList();
            try {
                JSONArray mainarr = new JSONArray(incommingJson);

                String city;
                for (int i = 0; i < mainarr.length(); i++) {
                    city = mainarr.getString(i);
                  citylist.add(city);
                }


                citylist.add(0,"--Select City--");
                ArrayAdapter ad = new ArrayAdapter(cntx, android.R.layout.simple_spinner_dropdown_item, citylist);
                spCity.setAdapter(ad);



        }
            catch (Exception ex)
            {
            }
        }


        @Override
        protected Void doInBackground(Void... params) {
            incommingJson=httpCall();
            return null;
        }

        public String httpCall()
        {
            String sb="";
            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder()
                    .url(url)
                    .build();
            try
            {
                Response response=client.newCall(request).execute();
                sb=response.body().string();
            }catch (Exception ex)
            {

            }


            return sb;
        }
    }










    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

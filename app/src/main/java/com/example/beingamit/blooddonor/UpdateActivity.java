package com.example.beingamit.blooddonor;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpdateActivity extends AppCompatActivity {
    Spinner spState, spCity;
    String url = "https://raw.githubusercontent.com/nshntarora/Indian-Cities-JSON/master/cities.json";
    Button btnUpdate;
    ArrayList citylist, statlist;
    Context cntx;
    String url2;
    String e, m, add, c, s;
    EditText tbEmail, tbMobile, tbAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.iconf);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        tbEmail = (EditText) findViewById(R.id.editEmailUpdate);
        tbMobile = (EditText) findViewById(R.id.editMobileUpdate);
        tbAddress = (EditText) findViewById(R.id.editAddressUpdate);
        spState = (Spinner) findViewById(R.id.spinnerStateUpdate);
        spCity = (Spinner) findViewById(R.id.spinnerCityUpdate);
        btnUpdate = (Button) findViewById(R.id.buttonUpdate);
        cntx = this;
        new json().execute();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e = tbEmail.getText().toString();
                m = tbMobile.getText().toString();
                add = tbAddress.getText().toString();
                c = spCity.getSelectedItem().toString();
                url2 = "http://www.icsitprojects.com/blooddonor/updatedonor.php?email=" + e + "&mobile=" + m + "&address=" + add + "&city=" + c;


                new json2().execute();
            }
        });
    }

    class json extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;
        String incommingJson;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(cntx);
            pd.setMessage("loading...");
            pd.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            citylist = new ArrayList();
            statlist = new ArrayList();
            try {
                JSONArray mainarr = new JSONArray(incommingJson);
                JSONObject childobj;
                String instate;
                for (int i = 0; i < mainarr.length(); i++) {
                    childobj = mainarr.getJSONObject(i);
                    //al.add(childobj.getString("name"));
                    instate = childobj.getString("state");
                    if (!statlist.contains(instate)) {
                        statlist.add(instate);
                    }

                }


                citylist.add("--Select City--");
                ArrayAdapter ad = new ArrayAdapter(cntx, android.R.layout.simple_spinner_dropdown_item, citylist);
                spCity.setAdapter(ad);

                //code for state
                Collections.sort(statlist);
                statlist.add(0, "--Select State--");
                ArrayAdapter ad1 = new ArrayAdapter(cntx, android.R.layout.simple_spinner_dropdown_item, statlist);
                spState.setAdapter(ad1);
                spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedstate = spState.getSelectedItem().toString();

                        try {
                            JSONArray mainarr = new JSONArray(incommingJson);
                            JSONObject childobj;
                            String instate;
                            citylist = new ArrayList();
                            for (int i = 0; i < mainarr.length(); i++) {

                                childobj = mainarr.getJSONObject(i);
                                instate = childobj.getString("state");
                                if (selectedstate.equals(instate)) {
                                    citylist.add(childobj.getString("name"));
                                }

                            }
                            Collections.sort(citylist);
                            citylist.add(0, "--Select City--");
                            ArrayAdapter ad = new ArrayAdapter(cntx, android.R.layout.simple_spinner_dropdown_item, citylist);
                            spCity.setAdapter(ad);
                        } catch (Exception ex) {


                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            } catch (Exception ex) {


            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            incommingJson = httpCall();
            return null;
        }

        public String httpCall() {
            String sb = "";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                sb = response.body().string();
            } catch (Exception ex) {

            }


            return sb;
        }
    }

    class json2 extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;
        String incommingJson;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(cntx);
            pd.setMessage("loading...");
            pd.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            try {
                JSONObject obj = new JSONObject(incommingJson);
                int ress = obj.getInt("res");
                if (ress > 0) {
                    Toast.makeText(getApplicationContext(), "Record updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Record not updated", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception ex) {


            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            incommingJson = httpCall();
            return null;
        }

        public String httpCall() {
            String sb = "";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url2)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                sb = response.body().string();
            } catch (Exception ex) {

            }


            return sb;
        }
    }


}



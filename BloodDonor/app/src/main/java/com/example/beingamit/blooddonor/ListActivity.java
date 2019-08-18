package com.example.beingamit.blooddonor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListActivity extends AppCompatActivity
{
    String name,num,adr,ste,ctyy;
    ArrayList nameList,mobileList,addList,latlist,lnglist;
    String bldgrp,state,cityy;
    String ltt,lgg;
    Context cntx;
    ListView lv;
    TextView tvHeading;
    String urlselect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.iconf);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        lv=(ListView)findViewById(R.id.listView1);
        cntx=this;
        tvHeading=(TextView)findViewById(R.id.textheading);
        nameList=new ArrayList();
        mobileList=new ArrayList();
        addList=new ArrayList();
        latlist=new ArrayList();
        lnglist=new ArrayList();
        Intent intcoming=this.getIntent();
        Bundle bd=intcoming.getBundleExtra("data");
        bldgrp=bd.getString("blood");
        cityy=bd.getString("cit");

        urlselect="http://www.icsitprojects.com/blooddonor/getdonor.php?bgroup="+bldgrp+"&city="+cityy;
        urlselect=urlselect.replace("+","%2B");
        new selectData().execute();

    }
    class selectData extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog pd;
        String incoming;
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
            //code for listview
            try
            {
                JSONArray obj=new JSONArray(incoming);
                JSONObject json_data ;
                for(int i=0;i<obj.length();i++)
                {
                    json_data=obj.getJSONObject(i);
                    name=json_data.getString("name");
                    //.makeText(getBaseContext(), "Name : " + name,Toast.LENGTH_SHORT).show();
                    num=json_data.getString("mobileno");
                    adr=json_data.getString("address");
                    //ste=json_data.getString("state");
                    ctyy=json_data.getString("city");
                    ltt=json_data.getString("dlattitude");
                    lgg=json_data.getString("dlongitude");

                    nameList.add(name);
                    mobileList.add(num);
                    addList.add(adr+ctyy);
                    latlist.add(ltt);
                    lnglist.add(lgg);

                }

                if(!nameList.isEmpty()) {
                    Customadapter cad = new Customadapter(getApplicationContext(), nameList, mobileList, addList,latlist,lnglist);
                    lv.setAdapter(cad);
                }else
                {
                  tvHeading.setText("Sorry ! Donor Not Found");

                }
                    //Toast.makeText(getBaseContext(), "Name : " + name,Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected Void doInBackground(Void... params) {
            incoming=httpCall();
            return null;

        }
        public String httpCall()
        {
           String sb="";
            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder()
                    .url(urlselect)
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

}

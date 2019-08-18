package com.example.beingamit.blooddonor;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChangePasswordActivity extends AppCompatActivity
{
    EditText tbOldPass,tbNewPass,tbRePass;
    Button btnSubmit;
    String op,np,rp;
    Context cntx;
    String urlselect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.iconf);
        cntx = this;
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        tbOldPass = (EditText) findViewById(R.id.editPassChange);
        tbNewPass = (EditText) findViewById(R.id.editNewPassChange);
        tbRePass = (EditText) findViewById(R.id.editRePassChange);
        btnSubmit = (Button) findViewById(R.id.buttonSubmitChange);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                op = tbOldPass.getText().toString();
                np = tbNewPass.getText().toString();
                rp = tbRePass.getText().toString();
                if (np.equals(rp))
                {

                    urlselect="http://www.icsitprojects.com/blooddonor/changepass.php?opass="+op+"&npass="+np+"";

                    new selectData().execute();

                } else
                {
                    Toast.makeText(getApplicationContext(),"Password doesn't match",Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                    JSONObject jsonObject=new JSONObject(incoming);
                    int code=jsonObject.getInt("res");

                    if (code==1)
                    {
                        Toast.makeText(getApplicationContext(),"Update Successfully",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Sorry!! try again",Toast.LENGTH_SHORT).show();
                    }

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

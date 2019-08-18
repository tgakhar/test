package com.example.beingamit.blooddonor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


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

public class LoginActivity extends AppCompatActivity
{
    EditText eid,pwd;
    TextView tvForgetPass;
    Button bLogin;
    Context cntx;
    String urlregister;
    String id,pdd,result,name,nu,ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.iconf);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        cntx=this;

        eid=(EditText)findViewById(R.id.editEmailLogin);
        pwd=(EditText)findViewById(R.id.editPassLogin);
        tvForgetPass=(TextView)findViewById(R.id.textForgetPassword);


        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
            }
        });
        bLogin=(Button)findViewById(R.id.buttonLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), NavigationActivity.class));
                id=eid.getText().toString();
                pdd=pwd.getText().toString();

                urlregister="http://www.icsitprojects.com/blooddonor/donorlogin.php?email="+id+"&password="+pdd;
                new reginsert().execute();



            }
        });
    }
    class reginsert extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog pd;
        String incoming;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(cntx);
            pd.setMessage("Loading...");
            pd.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            //   Toast.makeText(getApplicationContext(), incoming, Toast.LENGTH_LONG).show();
            try
            {


                if(incoming.contains("[]"))
                {
                    Toast.makeText(getApplicationContext(),"Incorrect id & password", Toast.LENGTH_LONG).show();
                }
                else {
                    JSONObject obj = new JSONObject(incoming);


                    name = obj.getString("name");
                    nu = obj.getString("mobileno");
                    ad = obj.getString("address");
                    eid.setText("");
                    pwd.setText("");

                    SharedPreferences sp_userdetails=getSharedPreferences("donordetails",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editordonor=sp_userdetails.edit();

                    editordonor.putString("name",name);
                    editordonor.putString("mobile",nu);
                    editordonor.putString("address",ad);
                     editordonor.commit();


                    startActivity(new Intent(getApplicationContext(), NavigationActivity.class));


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
                    .url(urlregister)
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
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_register)
        {
            Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_home)
        {
            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_help)
        {
            Intent intent=new Intent(getApplicationContext(),HelpActivity.class);
            startActivity(intent);
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


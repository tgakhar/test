package com.example.beingamit.blooddonor;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ResponseCache;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity
{
    Spinner spGender,spBlood,spState,spWeightGroup;
    AutoCompleteTextView spCity;
    ArrayList citylist,statlist;
    AwesomeValidation awesomeValidation;
    String blood[]={"--Select Blood Group--","A+","B+","AB+","O+","A-","B-","AB-","O-"};
    String gend[]={"--Select Gender--","Male","Female"};
    String weightgroup[]={"Select Your Weight(in KG aprox)","30","40","50","60","70","80","90"};

    Context cntx;
    String url="https://raw.githubusercontent.com/nshntarora/Indian-Cities-JSON/master/cities-name-list.json";
    EditText tbName,tbAge,tbWeight,tbMobile,tbAdd,tbEmail,tbPass,tbRePass;
    Button btnSubmit;
    String n,a,w,g,b,m,add,s,c,e,p,rp,lat="0.0",lng="0.0";
    String url2="http://www.icsitprojects.com/blooddonor/adddonor.php";
    boolean resultotp=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.iconf);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        cntx=this;




        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);

        tbName=(EditText)findViewById(R.id.editName);
        tbAge=(EditText)findViewById(R.id.editAge);
        spWeightGroup=(Spinner) findViewById(R.id.editWeight);
        tbMobile=(EditText)findViewById(R.id.editMobile);
        tbAdd=(EditText)findViewById(R.id.editAddress);
        tbEmail=(EditText)findViewById(R.id.editEmail);
        tbPass=(EditText)findViewById(R.id.editPass);
        tbRePass=(EditText)findViewById(R.id.editRePass);
        spGender=(Spinner)findViewById(R.id.spinnerGender);
        spBlood=(Spinner)findViewById(R.id.spinnerBlood);
        //spState=(Spinner)findViewById(R.id.spinnerState);
        spCity=(AutoCompleteTextView) findViewById(R.id.spinnerCity);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, blood);
        spBlood.setAdapter(ad);
        ArrayAdapter ad1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, gend);
        spGender.setAdapter(ad1);
        ArrayAdapter adweight = new ArrayAdapter(this, android.R.layout.simple_list_item_1, weightgroup);
        spWeightGroup.setAdapter(adweight);



       //code for validation.....

        awesomeValidation.addValidation(this,R.id.editName,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.name_error);
        awesomeValidation.addValidation(this,R.id.editAge,"[2-5]{1}[0-9]{1}",R.string.age_error);

        awesomeValidation.addValidation(this,R.id.editMobile,"^[0-9]{10}$",R.string.mobile_error);
        awesomeValidation.addValidation(this,R.id.spinnerCity,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.city_error);
        //awesomeValidation.addValidation(this,R.id.editAddress,"^[0-9]+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)$",R.string.address_error);
        awesomeValidation.addValidation(this,R.id.editEmail, Patterns.EMAIL_ADDRESS,R.string.email_error);
        awesomeValidation.addValidation(this,R.id.editPass, "^[A-Za-z]{5,6}$",R.string.password_error);
        awesomeValidation.addValidation(this,R.id.editRePass, "^[A-Za-z]{5,6}$",R.string.password_error);














        new gettinCity().execute();
        btnSubmit=(Button)findViewById(R.id.buttonSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                n = tbName.getText().toString();
                a = tbAge.getText().toString();
                w = spWeightGroup.getSelectedItem().toString();
                m = tbMobile.getText().toString();
                add = tbAdd.getText().toString();
                e = tbEmail.getText().toString();
                p = tbPass.getText().toString();
                rp = tbRePass.getText().toString();
                g = spGender.getSelectedItem().toString();
                b = spBlood.getSelectedItem().toString();
                //s = spState.getSelectedItem().toString();
                c = spCity.getText().toString();

                if(awesomeValidation.validate()) {
                    if (p.equals(rp)) {
                        if (n.isEmpty() || a.isEmpty() || w.isEmpty() || m.isEmpty() || add.isEmpty() || e.isEmpty()
                                || p.isEmpty() || rp.isEmpty() || g.isEmpty() || b.isEmpty() || c.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Enter all field", Toast.LENGTH_LONG).show();
                        } else {

                            //getLat_Long();

                            SharedPreferences sp_userdetails = getSharedPreferences("donordetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editordonor = sp_userdetails.edit();
                            editordonor.putString("name", n);
                            editordonor.putString("gender", g);
                            editordonor.putString("age", a);
                            editordonor.putString("weight", w);
                            editordonor.putString("mobile", m);
                            editordonor.putString("bloodgroup", b);
                            editordonor.putString("city", c);
                            editordonor.putString("address", add);
                            editordonor.putString("email", e);
                            editordonor.putString("password", p);
                            editordonor.putString("cpassword", rp);
                            editordonor.putString("lattitude", "30.741482");
                            editordonor.putString("longitude", "76.768066");
                            editordonor.commit();

                            Intent intent = new Intent(RegisterActivity.this, OTPActivity.class);
                            startActivity(intent);


                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "Password doesn't match", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }

    public void getLat_Long()
    {
        String addre=tbAdd.getText().toString();
        Geocoder geocoder=new Geocoder(RegisterActivity.this, Locale.ENGLISH);


        try {
            List<Address> returnedaddresses = geocoder.getFromLocationName(addre, 1);

            if(returnedaddresses != null)
            {

                lat=  String.valueOf(returnedaddresses.get(0).getLatitude());
                lng=String.valueOf(returnedaddresses.get(0).getLongitude());



            }
            else
            {
                Toast.makeText(getApplicationContext(), "Address not found ", Toast.LENGTH_LONG).show();

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }




    class gettinCity extends AsyncTask<Void,Void,Void>
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
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            pd.dismiss();
            citylist=new ArrayList();
            //statlist=new ArrayList();
            try {
                JSONArray mainarr = new JSONArray(incommingJson);

                String city;
                for (int i = 0; i < mainarr.length(); i++)
                {
                    city = mainarr.getString(i);
                   citylist.add(city);

                }


                citylist.add(0,"--Select City--");
                ArrayAdapter ad2 = new ArrayAdapter(cntx, android.R.layout.simple_spinner_dropdown_item, citylist);
                spCity.setAdapter(ad2);


                        } catch (Exception ex)
            {
                            Log.d("Errrrrrrrrrr:",ex.toString());

                        }

                    }


        @Override
        protected Void doInBackground(Void... params)
        {

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
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login)
        {
            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
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

package com.example.beingamit.blooddonor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OTPActivity extends AppCompatActivity {

    String n,a,w,g,b,m,add,s="",c,e,p,rp,lat="0.0",lng="0.0";
    String url2;
    boolean resultotp=false;
    String otpmessage;
    EditText tbOtp;
    TextView tvOtpMessage;
    Button btnSubmit,btnResend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        tbOtp=(EditText)findViewById(R.id.editTextOtp);
        tvOtpMessage=(TextView)findViewById(R.id.textViewMessage);
        btnSubmit=(Button) findViewById(R.id.buttonSubmit);
        btnResend=(Button) findViewById(R.id.buttonReSentOtp);

        SharedPreferences sp_userdetails=getSharedPreferences("donordetails", Context.MODE_PRIVATE);
        n=sp_userdetails.getString("name","");
        a=sp_userdetails.getString("age","");
        w=sp_userdetails.getString("weight","");
        g=sp_userdetails.getString("gender","");
        b=sp_userdetails.getString("bloodgroup","");
        m=sp_userdetails.getString("mobile","");
        add=sp_userdetails.getString("address","");
        c=sp_userdetails.getString("city","");
        e=sp_userdetails.getString("email","");
        p=sp_userdetails.getString("password","");
        rp=sp_userdetails.getString("cpassword","");
        lat=sp_userdetails.getString("lattitude","");
        lng=sp_userdetails.getString("longitude","");
       // otpmessage = ""+((int)(Math.random()*9000)+1000);
        otpmessage ="123";
       // new OTPSender().execute();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
            String userotp=tbOtp.getText().toString();

                if(!userotp.isEmpty())
                {

                    //if (otpmessage.equals(userotp))
                        if (userotp.equals(userotp))
                    {

                        url2="http://www.icsitprojects.com/blooddonor/adddonor.php?name="+n+"&age="+a+"&weight="+w+"&gender="+g+"&bgroup="+b+"&mobile="+m+"&city="+c+"&address="+add+"&email="+e+"&password="+p;
                        url2=url2.replace("+","%2B");
                      /*  url2="http://www.icsitprojects.com/blooddonor/adddonor.php?name="+n+"&age="+a+
                                "&weight="+w+"&gender="+g+"&bgroup="+b+"&mobile="+m+
                                "&address="+add+"&state="+s+"&city="+c+"&email="+e+"&password="+p+"&lattitude="+lat+
                                "&longitude="+lng+"";

                       */
                       new RegDetails().execute();


                    }
                    else
                        {
                    tvOtpMessage.setText("Invalid OTP");
                     btnResend.setEnabled(true);
                    }
                }
                else
                {
                    tbOtp.setError("Fill your OTP");

                }



            }
        });




    }


    class OTPSender extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog pd;
        boolean isOtpsentt;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(OTPActivity.this);
            pd.setMessage("OTP Sending...");
            pd.show();
        }


        @Override
        protected Void doInBackground(Void... params)
        {
            isOtpsentt=sendOTP();

            return null;
        }


        public boolean sendOTP()
        {
             boolean isOTPsent=false;
            String mno="91"+m;
            String ur="http://193.105.74.159/api/v3/sendsms/plain?user=kapsystem&password=j5SkaRdY&sender=KAPNFO&SMSText="+otpmessage+"&type=longsms&GSM="+mno;

            try {
                URL url = new URL(ur);
                HttpURLConnection urlconnection;
                urlconnection = (HttpURLConnection) url.openConnection();
                urlconnection.connect();
                int rc = urlconnection.getResponseCode();
                if (rc == 200)
                    isOTPsent = true;

            }
            catch (Exception ex)
            {
                String er=ex.toString();
                Log.d("eeeer ",er);
            }

           return isOTPsent;

        }



        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            pd.dismiss();
            if (isOtpsentt)
            {
            tvOtpMessage.setText("OTP sent on your mobile no: XXXXXXX"+m.substring(6,10));
            }
            else
            {
              btnResend.setEnabled(true);

            }



        }


    }


    class RegDetails extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog pd;
        String incommingJson;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pd=new ProgressDialog(OTPActivity.this);
            pd.setMessage("loading...");
            pd.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            try
            {
                JSONObject obj=new JSONObject(incommingJson);
                int ress=obj.getInt("res");
                if(ress>0)
                {
                    Toast.makeText(getApplicationContext(), "Registration Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(OTPActivity.this,NavigationActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Sorry!! try again", Toast.LENGTH_SHORT).show();

                }

            }
            catch(Exception ex)
            {

           }
        }

        @Override
        protected                                        Void doInBackground(Void... params)
        {
            incommingJson = httpCall();

            return null;
        }

        public String httpCall()
        {
            String sb="";
            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder()
                    .url(url2)
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

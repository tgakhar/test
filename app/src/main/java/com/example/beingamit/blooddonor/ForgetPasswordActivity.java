package com.example.beingamit.blooddonor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPasswordActivity extends AppCompatActivity
{
    EditText tbEmail;
    Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        tbEmail=(EditText)findViewById(R.id.editEmailForget);
        btnGo=(Button)findViewById(R.id.buttonGoForget);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text;
                Intent intmail;
                text=tbEmail.getText().toString();
                String to[]={"cseamitkumar@gmail.com"};
                intmail=new Intent(Intent.ACTION_SEND);
                intmail.putExtra(Intent.EXTRA_EMAIL,to);
                intmail.putExtra(Intent.EXTRA_SUBJECT,"complaint");
                intmail.putExtra(Intent.EXTRA_TEXT,text);
                intmail.setType("message/rfc822");
                Toast.makeText(getApplicationContext(), "send", Toast.LENGTH_SHORT).show();
                startActivity(Intent.createChooser(intmail, "sending mail"));

            }
        });

    }


}

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

public class FeedbackActivity extends AppCompatActivity
{
    EditText tbFeedback;
    Button btnFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.iconf);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        tbFeedback=(EditText)findViewById(R.id.editFeedback);
        btnFeedback=(Button)findViewById(R.id.buttonSubmitFeedback);
        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text;
                Intent intmail;
                text=tbFeedback.getText().toString();
                String to[]={"cseamitkumar@gmail.com"};
                intmail=new Intent(Intent.ACTION_SEND);
                intmail.putExtra(Intent.EXTRA_EMAIL,to);
                intmail.putExtra(Intent.EXTRA_SUBJECT,"complaint");
                intmail.putExtra(Intent.EXTRA_TEXT,text);
                intmail.setType("message/rfc822");
                Toast.makeText(getApplicationContext(),"send", Toast.LENGTH_SHORT).show();
                startActivity(Intent.createChooser(intmail, "sending mail"));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
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

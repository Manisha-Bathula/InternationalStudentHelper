package com.gbcish.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.internationalstudenthelper.R;

public class ContactUsActivity extends AppCompatActivity {

    ImageButton imgphone;
    ImageButton imgsms;
    ImageButton imgemail;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        //Action Bar
        getSupportActionBar().setTitle("Contact Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgphone = findViewById(R.id.img_call);
        imgsms = findViewById(R.id.ic_sms);
        imgemail = findViewById(R.id.ic_email);

    //CALL
        imgphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: 6478650000"));
                startActivity(intent);

            }
        });

        //SEND TEXT MESSAGE
        imgsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent smsIntent = new Intent(Intent.ACTION_SENDTO,
                        Uri.parse("sms:6478650000"));
                smsIntent.putExtra("sms_body", "Need Information.");
                startActivity(smsIntent);
            }
        });

        //Email
        imgemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String to = "";
                String subject = "";
                String message = "";
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);
                // need this to prompts email client only
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client"));
            }
        });
    }

}
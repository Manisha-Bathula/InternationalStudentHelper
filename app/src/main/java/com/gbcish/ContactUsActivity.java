package com.gbcish;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.internationalstudenthelper.R;

public class ContactUsActivity extends AppCompatActivity {

    ImageButton imgphone;
    ImageButton imgsms;
    ImageButton imgemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        imgphone = findViewById(R.id.img_call);
        imgsms = findViewById(R.id.ic_sms);
        imgemail = findViewById(R.id.ic_email);

//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage("", null, "< message body>", null, null);

        imgphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call_Phone
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: 6478650000"));
                startActivity(intent);

            }
        });

//        imgsms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//                sendIntent.putExtra("sms_body", "");
//                        sendIntent.setType("vnd.android-dir/mms-sms");
//                startActivity(sendIntent);
//            }
//        });


//        imgemail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String to = toEmail.getText().toString();
//                String subject = emailSubject.getText().toString();
//                String message = emailBody.getText().toString();
//                Intent email = new Intent(Intent.ACTION_SEND);
//                email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
//                email.putExtra(Intent.EXTRA_SUBJECT, subject);
//                email.putExtra(Intent.EXTRA_TEXT, message);
//                // need this to prompts email client only
//                email.setType("message/rfc822");
//                startActivity(Intent.createChooser(email, "Choose an Email client"));
//
//            }
//        });




    }


}
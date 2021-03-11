package com.gbcish;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.internationalstudenthelper.R;

public class AboutUsActivity extends AppCompatActivity {
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //Action Bar
        getSupportActionBar().setTitle("About Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void gotodashboard(View view) {
        Intent intent = new Intent(AboutUsActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

    public void gotopost(View view) {
        Intent intent1 = new Intent(AboutUsActivity.this, PostActivity.class);
        startActivity(intent1);
    }

    public void gotocurrency(View view) {
        Intent intent2 = new Intent(AboutUsActivity.this, CurrencyConvertorActivity.class);
        startActivity(intent2);
    }

    public void gotocontact(View view) {
        Intent intent4 = new Intent(AboutUsActivity.this, ContactUsActivity.class);
        startActivity(intent4);
    }
}
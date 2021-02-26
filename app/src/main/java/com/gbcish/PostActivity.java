package com.gbcish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.internationalstudenthelper.R;


public class PostActivity extends AppCompatActivity {


    CardView rental_house, buy_laptop,post_job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //showing back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //set title fo the screen
        getSupportActionBar().setTitle("Post");


        //register card view with its id
        rental_house = findViewById(R.id.rental_house);
        post_job=findViewById(R.id.search_job);
        buy_laptop = findViewById(R.id.buy_laptop);


        buy_laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(PostActivity.this, ElectronicsActivity.class);
                startActivity(i);
            }
        });

        post_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(PostActivity.this, JobSearchActivity.class);
                startActivity(i);
            }
        });

        //implement onclick
        rental_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PostActivity.this, RentalActivity.class);
                startActivity(intent);
            }
        });
    }
    //this code is use for backbutton woking
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
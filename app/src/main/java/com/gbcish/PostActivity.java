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


        //implement onclick
        rental_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to do your code haer. this code will wokr when you click on rental house button
                Intent intent=new Intent(PostActivity.this, RentalActivity.class);
                startActivity(intent);
                //run the projcet and open emulator
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
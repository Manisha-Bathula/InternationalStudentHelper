package com.gbcish;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.internationalstudenthelper.R;
import com.gbcish.models.HelpFragmentCollectionAdapter;

public class HelpActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private HelpFragmentCollectionAdapter adapter;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        //Action Bar
        getSupportActionBar().setTitle("Help");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.pager);
        adapter = new HelpFragmentCollectionAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }
}
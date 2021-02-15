package com.gbcish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.internationalstudenthelper.R;
import com.gbcish.Adapters.ExploreAdapter;
import com.gbcish.models.PostModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExploreActivity extends AppCompatActivity implements ExploreAdapter.OnItemClickListener {
    private DatabaseReference databaseRef;
    private ArrayList<PostModel> postModelList=new ArrayList<>();
    private RecyclerView rentalRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ExploreAdapter exploreAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        rentalRecyclerView=findViewById(R.id.rentalRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        rentalRecyclerView.setLayoutManager(layoutManager);
        rentalRecyclerView.setItemAnimator(new DefaultItemAnimator());
        rentalRecyclerView.setNestedScrollingEnabled(false);

        exploreAdapter = new ExploreAdapter(postModelList, this);
        exploreAdapter.setOnItemClickListener(ExploreActivity.this);
        rentalRecyclerView.setAdapter(exploreAdapter);


        databaseRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference addRentalPosts = databaseRef.child("AddRentalPosts");

        addRentalPosts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap: snapshot.getChildren()){
                    PostModel c = snap.getValue(PostModel.class);
                    Log.d("Categories: ", c.getPost_rent() + " " + c.getPost_description());
                    postModelList.add(c);
                    exploreAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemClick(View view, int position, PostModel postModel) {

    }
}
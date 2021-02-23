package com.gbcish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

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
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setTitle("Explore");
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
                    retriveJobPosts(databaseRef);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.explore_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                exploreAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                exploreAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.explore_menu, menu);
//
//        // Associate searchable configuration with the SearchView
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView = (SearchView) MenuItemCompat
//                .getActionView(searchItem);
//        searchView.setSearchableInfo(searchManager
//                .getSearchableInfo(getComponentName()));
//        searchView.setMaxWidth(3);
//
//        // listening to search query text change
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // filter recycler view when query submitted
//                exploreAdapter.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                // filter recycler view when text is changed
//                exploreAdapter.getFilter().filter(query);
//                return false;
//            }
//        });
//        return true;
//    }
    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
    public void retriveJobPosts(DatabaseReference databaseRef2){

        final DatabaseReference addJobPosts = databaseRef2.child("AddJobPosts");

        addJobPosts.addValueEventListener(new ValueEventListener() {
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
    public void onItemClick( int position, PostModel postModel) {

        Intent it=new Intent(getApplicationContext(),ExploreDetailsActivity.class);
        it.putExtra("PostModel",postModel);
        startActivity(it);


    }
}
package com.gbcish;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.internationalstudenthelper.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    TextView tv_email;
    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAuth=FirebaseAuth.getInstance();
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home).setDrawerLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
        }
        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will 
            // automatically handle clicks on the Home/Up button, so long 
            // as you specify a parent acti
            // vity in AndroidManifest.xml.

            int id = item.getItemId();
            switch (id){
                case R.id.action_Prf:
                    Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                    startActivity(intent);
                    break;
                    case R.id.action_Lgt:
                        mAuth.signOut();
                        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(i);
                        finish();
                        break;
            }
            return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return false;
    }


    private void displaySelectedScreen(int itemId) {
        //creating fragment object 
        Fragment fragment = null;    //initializing the fragment object which is selected 
        switch (itemId) {
            case R.id.nav_home:
               fragment=new ExploreFragment();
                break;
            case R.id.nav_explore:
                Intent i=new Intent(DashboardActivity.this,ExploreActivity.class);
                startActivity(i);
                finish();

            case R.id.nav_post:
                Intent intent=new Intent(DashboardActivity.this, PostActivity.class);
                startActivity(intent);
                finish();
                break;
            case  R.id.nav_manage:
                Intent intent1=new Intent(DashboardActivity.this,ManageActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.nav_contactus:
                Intent intent2 = new Intent(DashboardActivity.this, ContactUsActivity.class);
                startActivity(intent2);
                finish();

        }
                           //replacing the fragment 
            if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment, fragment);
            ft.commit();
             }
            DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
    }










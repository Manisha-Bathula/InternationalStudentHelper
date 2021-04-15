package com.gbcish;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
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
import com.gbcish.Fragments.NewsFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    TextView tv_email;
    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth mAuth;
    private ActionBarDrawerToggle mDrawerToggle;

    AdView mAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        mAdView = findViewById(R.id.adViewDashboard);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("0314cafa-f8e9-42ac-a37d-286d257a2b05").build();
        mAdView.loadAd(adRequest);






        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("News");
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
    protected void onResume() {
        loadFragment(new NewsFragment());
        super.onResume();

    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
        }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }


    private void displaySelectedScreen(int itemId) {
        //creating fragment object 
        Fragment fragment = null;    //initializing the fragment object which is selected 
        switch (itemId) {
            case R.id.nav_home:
               fragment=new ExploreFragment();
                break;

            case R.id.nav_chats:
                startActivity(new Intent(getApplicationContext(),ChatNames.class));
                break;
            case R.id.nav_explore:
                Intent i=new Intent(DashboardActivity.this,ExploreActivity.class);
                startActivity(i);
                // do not finish home page. otherwise you can not go back to home page.
                //finish();
                break;

            case R.id.nav_post:
                Intent intent=new Intent(DashboardActivity.this, PostActivity.class);
                startActivity(intent);
                // do not finish home page. otherwise you can not go back to home page.
                //finish();
                break;

            case  R.id.nav_currency:
                Intent i1=new Intent(DashboardActivity.this,CurrencyConvertorActivity.class);
                startActivity(i1);
                // do not finish home page. otherwise you can not go back to home page.
                //finish();
                break;

            case R.id.nav_contactus:
                Intent intent2 = new Intent(DashboardActivity.this, ContactUsActivity.class);
                startActivity(intent2);
                // do not finish home page. otherwise you can not go back to home page.
                //finish();
                break;

            case R.id.nav_aboutus:
                Intent intent4 = new Intent(DashboardActivity.this, AboutUsActivity.class);
                startActivity(intent4);
                // do not finish home page. otherwise you can not go back to home page.
                //finish();
                break;

            case R.id.nav_profile:
                Intent intent3 = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent3);
                // do not finish home page. otherwise you can not go back to home page.
                //finish();
                break;

            case  R.id.nav_logout:
                mAuth.signOut();
                Intent intent5 = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent5);
                finish();
                break;


        }
                           //replacing the fragment 
           loadFragment(fragment);
            DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }


        private void loadFragment(Fragment fragment){
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, fragment);
                ft.commit();
            }
        }
    }










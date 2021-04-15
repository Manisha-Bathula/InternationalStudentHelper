package com.gbcish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internationalstudenthelper.R;
import com.gbcish.Adapters.ViewPagerAdapter;
import com.gbcish.models.PostImages;
import com.gbcish.models.PostModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import android.Manifest;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;

public class ExploreDetailsActivity extends AppCompatActivity {

    private TextView tv_title1, tv_category1, tv_rent, tv_description1,tv_address;
    private ImageView post_image;
    // creating object of ViewPager
    ViewPager mViewPager;
    ImageView bt_chat_seller;
    ViewPagerAdapter viewPagerAdapter;
    ArrayList<PostImages> images;
    Button bt_get_direction;
    ImageButton bt_sms_seller;
    private static final int REQUEST_CALL = 1;
    FirebaseFirestore db;
    PostModel postModel;
    CollectionReference cities;
    String email = "";
    String phoneNumber = "";
    private  String sellername,username,useremail,adPostedUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_details);
        db = FirebaseFirestore.getInstance();
        cities = db.collection("User");


        Intent it =getIntent();
        postModel= (PostModel) it.getSerializableExtra("PostModel");
        String ptitle = postModel.getPost_title();
        String pdescription = postModel.getPost_description();
        String pt=postModel.getPost_category();
        String p1t=postModel.getPost_city();
         sellername=postModel.getUser_id();

        adPostedUserID=postModel.getUser_id();

        bt_get_direction=findViewById(R.id.bt_get_direction);
        bt_sms_seller=findViewById(R.id.bt_sms_seller);
        bt_chat_seller=findViewById(R.id.bt_chat_seller);
        bt_chat_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChatBox.class).putExtra("username",username).putExtra("useremail",useremail).putExtra("adPostedUserID",adPostedUserID));
            }
        });

        bt_sms_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        bt_get_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(ExploreDetailsActivity.this,MapActivity.class);
                intent.putExtra("SelectedAdd",postModel);
                startActivity(intent);
            }
        });
        tv_title1=findViewById(R.id.tv_title1);
        tv_category1=findViewById(R.id.tv_category1);
        tv_rent=findViewById(R.id.tv_rent);
        tv_description1=findViewById(R.id.tv_description1);
        tv_address=findViewById(R.id.tv_address);
        post_image=findViewById(R.id.post_image);
        tv_title1.setText(postModel.getPost_title());
        tv_category1.setText(postModel.getPost_category());
        tv_rent.setText(postModel.getPost_rent()+" "+"CAD");
        tv_description1.setText(postModel.getPost_description());

        tv_address.setText(postModel.getFullAddress());

        // Initializing the ViewPager Object
        mViewPager = (ViewPager)findViewById(R.id.viewPagerMain);


        cities.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.d("Response", queryDocumentSnapshots.toString());
                for (DocumentSnapshot data:queryDocumentSnapshots.getDocuments()){
                    //Log.d("key", data.getId());
                    if (data.getString("Userid").equals(postModel.getUser_id())){
                        phoneNumber = data.getString("Phone");
                        email = data.getString("Email");
                        username=data.getString("Name");
                        //Toast.makeText(getApplicationContext(), phoneNumber+ email, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });






        // Initializing the ViewPagerAdapter
        viewPagerAdapter = new ViewPagerAdapter(ExploreDetailsActivity.this,postModel.getImageUrl() );

        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(viewPagerAdapter);

    }

    public void callseller(View view) {
        String dial = "tel:" + phoneNumber;
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ExploreDetailsActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }else {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }

    }

    public void smsseller(View view) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("smsto:" +phoneNumber)); // This ensures only SMS apps respond
        intent.putExtra("sms_body",
                "Is the mentioned ad still available. i.e.,  Title: " +postModel.getPost_title() + "  which is posted on " +postModel.getPost_current_date() );
        startActivity(intent);

    }

    public void emailseller(View view) {
        String to= email;
        String subject="Need Further Information on your below ad which is posted on " +postModel.getPost_current_date();
        String message="Title: " +postModel.getPost_title()  + "\n"
                + "Decription: "+postModel.getPost_description() + "\n " + "\n" + "\n" + "Thanks in advance.";
        Log.d("Email_Title", "emailseller: " +message);

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email Application:"));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(getApplicationContext(), "Permission granted seccuessfully");
                Toast.makeText(getApplication(), "Permission granted seccuessfully", Toast.LENGTH_SHORT).show();
                String dial = "tel:" + phoneNumber;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            } else {
                //showToast("Permission DENIED");
                Toast.makeText(getApplication(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void textmsgseller(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" +phoneNumber)); // This ensures only SMS apps respond
        intent.putExtra("sms_body",
                "Is the mentioned ad still available. i.e.,  Title: " +postModel.getPost_title() + "  which is posted on " +postModel.getPost_current_date() );
        startActivity(intent);
    }
}
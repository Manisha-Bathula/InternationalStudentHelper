package com.gbcish;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internationalstudenthelper.R;
import com.gbcish.Adapters.SelectedImagesAdapter;
import com.gbcish.models.PostImages;
import com.gbcish.models.PostModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ElectronicsActivity extends AppCompatActivity {

    EditText postTitle, postDescription, postPrice, postLocation,edt_street,edt_province,edt_postal;
    //CardView addImage;
    Spinner categorySpinner;
    TextView imageErrorMsg, CatErrorMsg;
    ImageView showSelectedImage, back_img;
    ///  Button  submitPost;
    String postCat = null;
    Uri postImageUri = null;
    String postImageLink = null;
    String uploadDate;
    String uploadTime;
    private DatabaseReference mDatabase;
    ProgressDialog progressDialog;
    FirebaseStorage storage;
    StorageReference storageReference;
    RecyclerView selectedImagesRV;
    private ArrayList<MediaFile> mediaFiles;
    private ArrayList<String> imageArray;
    private ArrayList<PostImages> array;
    String pattern = "A-Z";
    private String category = "Gadget";
    private Boolean imageEmpty = true;

    Calendar calender = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronics);

        postTitle = findViewById(R.id.title);
        postDescription = findViewById(R.id.w_r_u_selling);
        postPrice = findViewById(R.id.price);
        postLocation = findViewById(R.id.add_location);
        edt_street=findViewById(R.id.edt_street);
        edt_province=findViewById(R.id.edt_province);
        edt_postal=findViewById(R.id.edt_post_code);
        //addImage = findViewById(R.id.add_image);
        categorySpinner = findViewById(R.id.catagory);
        // submitPost = findViewById(R.id.submit_post);
        showSelectedImage = findViewById(R.id.show_selected_image);
        selectedImagesRV = findViewById(R.id.selectedImagesRV);
        back_img = findViewById(R.id.imageViewAPback);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
//        imageErrorMsg = findViewById(R.id.image_error_msg);
//        CatErrorMsg = findViewById(R.id.cat_error_msg);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        progressDialog = new ProgressDialog(ElectronicsActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading...");
        mediaFiles = new ArrayList<MediaFile>();
        imageArray = new ArrayList<String>();
        array = new ArrayList<PostImages>();
        // getSupportActionBar().setTitle("Create Post");
        String[] catArray = {"Select Item", "Mobiles",  "Laptops"};
        String currentdate = DateFormat.getDateInstance().format(calender.getTime());
        uploadDate = currentdate;
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        uploadTime = currentTime;
        // setting data to adapter
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, catArray);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        categorySpinner.setAdapter(dataAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(
                    AdapterView<?> adapterView, View view,
                    int i, long l) {
                if (!categorySpinner.getSelectedItem().toString().equals("Select Item")) {
                    postCat = categorySpinner.getSelectedItem().toString();
                    Log.d("spinnerItem", postCat);
                }


            }

            public void onNothingSelected(
                    AdapterView<?> adapterView) {

            }
        });
    }


    public void addPost(View view) {
        if (postTitle.getText().toString().isEmpty()) {
            postTitle.setError("Please enter title");
            postTitle.requestFocus();
        } else if (postCat == null) {
            CatErrorMsg.setVisibility(view.VISIBLE);
        } else if (postDescription.getText().toString().isEmpty()) {
            postDescription.setError("Please enter description");
            postDescription.requestFocus();
        } else if (postPrice.getText().toString().isEmpty()) {
            postPrice.setError("Please enter Price");
            postPrice.requestFocus();
        } else if (postLocation.getText().toString().isEmpty()) {
            postLocation.setError("Please enter city");
            postLocation.requestFocus();
        }else if (edt_street.getText().toString().isEmpty()){
            edt_street.setError("Please enter street name");
            edt_street.requestFocus();
        }else if (edt_province.getText().toString().isEmpty()){
            edt_province.setError("Please enter province name");
            edt_province.requestFocus();
        }else if (edt_postal.getText().toString().isEmpty()){
            edt_postal.setError("Please enter postal code");
            //here we can check postal code.
            edt_postal.requestFocus();
        }else if (edt_postal.getText().toString().matches(pattern)){
            edt_postal.setError("Postal Code must be in capital letters");
            edt_postal.requestFocus();
        }else if (imageEmpty) {
            Toast.makeText(this, "Please upload a image", Toast.LENGTH_SHORT).show();
            // imageErrorMsg.setVisibility(view.VISIBLE);
        }else {
            // imageErrorMsg.setVisibility(view.INVISIBLE);
            uploadImage();
        }
    }

    private void startAllPicker() {

        Intent intent = new Intent(this, FilePickerActivity.class);
        intent.putExtra(
                FilePickerActivity.CONFIGS,
                new Configurations.Builder()
                        .setCheckPermission(true)
                        .setSelectedMediaFiles(mediaFiles)
                        .setShowFiles(false)
                        .setShowImages(true)
                        .setShowVideos(false)
                        .setShowAudios(false)
                        .enableImageCapture(true)
                        .setMaxSelection(10)
                        .setSkipZeroSizeFiles(true)
                        .build()
        );
        startActivityForResult(intent, 1001);


    }

    void createPost() {
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String key = mDatabase.child("posts").push().getKey();
        PostModel user_posts = new PostModel(postTitle.getText().toString(),
                postCat,
                postDescription.getText().toString(),
                postPrice.getText().toString(),
                postLocation.getText().toString(),
                edt_street.getText().toString(),
                edt_province.getText().toString(),
                edt_postal.getText().toString(),
                uploadDate,
                key,
                uploadTime,
                currentuser,
                category,
                array
        );

        mDatabase.child("AddElectronicsPosts").child(key).setValue(user_posts).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                postCat = null;
                postTitle.getText().clear();
                postDescription.getText().clear();
                postPrice.getText().clear();
                postLocation.getText().clear();
                edt_postal.getText().clear();
                edt_province.getText().clear();
                edt_street.getText().clear();
                postImageLink = null;
                array.clear();
                imageEmpty = true;
                showSelectedImage.setImageResource(R.drawable.ic_photo);
                progressDialog.dismiss();

                Intent i= new Intent(ElectronicsActivity.this, ExploreActivity.class);
                startActivity(i);
                //Toast.makeText(PostActivity.this, "Post has been created", Toast.LENGTH_SHORT).show();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ElectronicsActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadImage() {


        progressDialog.show();
        if (!imageEmpty) {
            for (MediaFile item : mediaFiles) {
                final StorageReference ref = storageReference.child("electronicsimages/" + item.getUri().getLastPathSegment());

                ref.putFile(item.getUri())
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri downloadUrl) {
                                        postImageLink = String.valueOf(downloadUrl);
                                        // createPost();
                                        PostImages obj = new PostImages();
                                        obj.imageUrl = postImageLink.toString();

                                        array.add(obj);
                                        if (array.size() == mediaFiles.size()) {
                                            createPost();
                                        }
                                    }
                                });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(ElectronicsActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        }
    }

    public void selectImage(View view) {
        startAllPicker();
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (data != null) {
                ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
                Bitmap bitmap = null;
                mediaFiles.clear();
                mediaFiles.addAll(data.<MediaFile>getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES));
                if (mediaFiles.size() > 0) {
                    imageEmpty = false;
                }

                imageArray.clear();

                for (MediaFile item : mediaFiles) {
                    imageArray.add(item.getUri().toString());
//                    try {
//                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), item.getUri());
//                       // showSelectedImage.setImageBitmap(bitmap);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                }
                selectedImagesRV.setVisibility(View.VISIBLE);
                selectedImagesRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                SelectedImagesAdapter adapter = new SelectedImagesAdapter(ElectronicsActivity.this, imageArray);
                selectedImagesRV.setAdapter(adapter);
            }
        }
//        if (requestCode == 1000 && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri filePath = data.getData();
//                Log.d("imageUri", String.valueOf(filePath));
//            Bitmap bitmap = null;
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            showSelectedImage.setImageBitmap(bitmap);
//            postImageUri =filePath;
//        }
    }
}
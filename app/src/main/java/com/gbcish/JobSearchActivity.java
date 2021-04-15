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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JobSearchActivity extends AppCompatActivity {
    EditText postTitle, postDescription, postPrice, postLocation,edt_street,edt_province,edt_postal;
    //CardView addImage;
    Spinner categorySpinner;
    TextView imageErrorMsg, CatErrorMsg;
    ImageView showSelectedImage, back_img;
    ///  Button  submitPost;
    String postCat = null;
    Uri postImageUri = null;
    String postImageLink = null;
    String uploadTime;
    String uploadDate;
    String postalCodeJob;
    private DatabaseReference mDatabase;
    ProgressDialog progressDialog;
    FirebaseStorage storage;
    StorageReference storageReference;
    RecyclerView selectedImagesRV;
    private ArrayList<MediaFile> mediaFiles;
    private ArrayList<String> imageArray;
    private ArrayList<PostImages> array;
    String pattern = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";
    private String category = "Job";
    private Boolean imageEmpty = true;
    String postProvinance=null;
    String postCity=null;

    Calendar calender =Calendar.getInstance();

    Spinner sp_rent;
    ArrayList<String> proniceNames=new ArrayList<>();
    ArrayList<String> cityList=new ArrayList<>();
    AutoCompleteTextView autoCompleteTextView_city;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search);

        getSupportActionBar().hide();

        postTitle = findViewById(R.id.title);
        postDescription = findViewById(R.id.w_r_u_selling);
        postPrice = findViewById(R.id.price);
        postLocation = findViewById(R.id.add_location);
        edt_street = findViewById(R.id.edt_street);
        edt_province = findViewById(R.id.edt_province);
        edt_postal = findViewById(R.id.edt_post_code);
        sp_rent=findViewById(R.id.spinner_rent);
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
        progressDialog = new ProgressDialog(JobSearchActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading...");
        mediaFiles = new ArrayList<MediaFile>();
        imageArray = new ArrayList<String>();
        array = new ArrayList<PostImages>();
        // getSupportActionBar().setTitle("Create Post");
        postalCodeJob = String.valueOf(edt_postal);



        autoCompleteTextView_city=(AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewcity);

        cityList.add("Barrier");
        cityList.add("Brampton");
        cityList.add("Calgary");
        cityList.add("Edmonton");
        cityList.add("Hamilton");
        cityList.add("London");
        cityList.add("Mississauga");
        cityList.add("Oshawa");
        cityList.add("Quebec City");
        cityList.add("Toronto");
        cityList.add("Vancouver");
        cityList.add("Victoria");
        cityList.add("Windsor");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, cityList);

        autoCompleteTextView_city.setThreshold(2);
        autoCompleteTextView_city.setAdapter(adapter);


        autoCompleteTextView_city.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                autoCompleteTextView_city.showDropDown();
            }
        });


        proniceNames.add("Ontario");
        proniceNames.add("Quebec");
        proniceNames.add("Alberta");
        proniceNames.add("British Colombia");
        proniceNames.add("Nova Scotia");

        String[] catArray = {"Select Item", "Part-Time", "Full-Time"};
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


        ArrayAdapter<String> provinceAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,proniceNames);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_rent.setAdapter(provinceAdapter);


        sp_rent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!sp_rent.getSelectedItem().toString().equals("Select Item")){
                    postProvinance=sp_rent.getSelectedItem().toString();
                    Log.d("spinner_item",postProvinance);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        autoCompleteTextView_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                Toast.makeText(getApplicationContext()," selected"+parent.getItemAtPosition(pos), Toast.LENGTH_LONG).show();
                postCity= String.valueOf(parent.getItemAtPosition(pos));
            }
        });

//        autoCompleteTextView_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                postCity=adapterView.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        postPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();
                if (!input.isEmpty()) {
                    input = input.replace(",", "");
                    DecimalFormat format = new DecimalFormat("#,###,###");
                    String newPrice = format.format(Double.parseDouble(input));
                    postPrice.removeTextChangedListener(this);
                   // postPrice.setText(newPrice);
                    postPrice.setText(newPrice +".00");

                    postPrice.setSelection(newPrice.length());
                    postPrice.addTextChangedListener(this);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    public static boolean valPostalCode(String postcode){
        String regex = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";
        Pattern pc = Pattern.compile(regex);
        Matcher matcher = pc.matcher(postcode);
        boolean result = matcher.find();
        return  result;

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
            postPrice.setError("Please enter salary");
            postPrice.requestFocus();
        } else if (postCity==null) {
            Toast.makeText(this,"Please enter city",Toast.LENGTH_SHORT).show();

        }else if (edt_street.getText().toString().isEmpty()){
            edt_street.setError("Please enter street name");
            edt_street.requestFocus();
        }else if (postProvinance==null){
            Toast.makeText(this, "Please enter provinance name", Toast.LENGTH_SHORT).show();
        }else if (edt_postal.getText().toString().isEmpty()){
            edt_postal.setError("Please enter postal code");
            //here we can check postal code.
            edt_postal.requestFocus();
        }else if (valPostalCode(postalCodeJob)){
            edt_postal.setError("Enter Valid Postal Code");
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
        String price=postPrice.getText().toString();
        //price = price.replace(",", "");
        PostModel user_posts = new PostModel(postTitle.getText().toString(),
                postCat,
                postDescription.getText().toString(),
                price,
                postCity,
                edt_street.getText().toString(),
                postProvinance,
                edt_postal.getText().toString(),
                uploadDate,
                key,
                uploadTime,
                currentuser,
                category,
                array
        );

        mDatabase.child("AddJobPosts").child(key).setValue(user_posts).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                postCat = null;
                postProvinance=null;
                postTitle.getText().clear();
                postDescription.getText().clear();
                postPrice.getText().clear();
                postCity=null;
                edt_postal.getText().clear();
                edt_province.getText().clear();
                edt_street.getText().clear();
                postImageLink = null;
                array.clear();
                imageEmpty = true;
                showSelectedImage.setImageResource(R.drawable.ic_photo);
                progressDialog.dismiss();

                Intent i= new Intent(JobSearchActivity.this, ExploreActivity.class);
                startActivity(i);
                //Toast.makeText(PostActivity.this, "Post has been created", Toast.LENGTH_SHORT).show();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(JobSearchActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadImage() {


        progressDialog.show();
        if (!imageEmpty) {
            for (MediaFile item : mediaFiles) {
                final StorageReference ref = storageReference.child("jobimages/" + item.getUri().getLastPathSegment());

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

                                Toast.makeText(JobSearchActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                SelectedImagesAdapter adapter = new SelectedImagesAdapter(JobSearchActivity.this, imageArray);
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
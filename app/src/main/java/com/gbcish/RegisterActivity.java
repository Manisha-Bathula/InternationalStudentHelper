package com.gbcish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.internationalstudenthelper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText mFullName,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLogibBtn;
    FirebaseAuth fAuth;
    private AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName=findViewById(R.id.fullnameregister);
        mEmail=findViewById(R.id.emailregister);
        mPassword=findViewById(R.id.passwordregister);
        mPhone=findViewById(R.id.phoneregister);
        mRegisterBtn=findViewById(R.id.btnregister);
        mLogibBtn=findViewById(R.id.loginregister);

        fAuth=FirebaseAuth.getInstance();

        //ONCLICKINGG REGISTER BUTTON
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();

                //IF CURRENT USER, THEN DIRECTLY NAVIGATE TO MAINACTIVITY
                if (fAuth.getCurrentUser() != null) {
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    //Toast.makeText(this, "Existing user", Toast.LENGTH_SHORT).show();
                    finish();
                }

                //VALIDATION OF EMAIL AND PASSWORD FIELDS
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password must be >= 6 Characters");
                    return;
                }


                AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("User is created");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //REGISTER USER IN FIREBASE
                                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        });

                alertDialog.show();


                //REGISTER USER IN FIREBASE
//                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            //displayAlertDialog();
//
//
//
//
//
//                            //Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
//                            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                        }else{
//                            Toast.makeText(RegisterActivity.this, "Error ! " +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
            }
        });

            }


            public void login(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }


        }
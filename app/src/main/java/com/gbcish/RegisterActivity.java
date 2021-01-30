package com.gbcish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.internationalstudenthelper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText mFullName,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLogibBtn;
    FirebaseAuth fAuth;
    private AlertDialog.Builder builder;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseFirestore db;



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
        db=FirebaseFirestore.getInstance();

        //ONCLICKINGG REGISTER BUTTON
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name=mFullName.getText().toString().trim();
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String phone=mPhone.getText().toString().trim();

                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                    alertDialog.setTitle("Error!");
                    alertDialog.setMessage("Email & Password are required");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    return;
                }
                //VALIDATION OF EMAIL AND PASSWORD FIELDS
                else if (TextUtils.isEmpty(email)) {

                        mEmail.setError("Email is Required.");
                        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                        alertDialog.setTitle("Error!");
                        alertDialog.setMessage("Email is required");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        return;
                    }
                // onClick of button perform this simplest code.
                else if (email.matches(emailPattern))
                {
                    //Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                    alertDialog.setTitle("Error!");
                    alertDialog.setMessage("Invalid Email Address");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    //Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required");
                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                    alertDialog.setTitle("Error!");
                    alertDialog.setMessage("Password is required");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    return;
                } else

                if (password.length() < 6) {
                    mPassword.setError("Password must be >= 6 Characters");
                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                    alertDialog.setTitle("Error!");
                    alertDialog.setMessage("Password must be >= 6 Characters");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    return;
                }

                else {
                    //Alert Box
                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Creating new account?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    //REGISTER USER IN FIREBASE
                                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                String userID=fAuth.getCurrentUser().getUid();
                                                AddRegisterData(userID,name,email,password,phone);
                                                AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                                                alertDialog.setTitle("Success");
                                                alertDialog.setMessage("User is created");
                                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                                alertDialog.show();

                                                //Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                            } else {
                                                AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                                                alertDialog.setTitle("Error!");
                                                alertDialog.setMessage(" "+task.getException().getMessage());
                                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                                alertDialog.show();
                                                return;
                                                //Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }
                            });

                    alertDialog.show();

                    mFullName.getText().clear();
                    mEmail.getText().clear();
                    mPassword.getText().clear();
                    mPhone.getText().clear();
                }

              //startActivity(new Intent(getApplicationContext(), DashboardActivity.class));

           }
        });

            }

    private void AddRegisterData(String userID, String name, String email, String password, String phone) {
        Map<String,Object> user=new HashMap<>();
        user.put("Userid",userID);
        user.put("Name",name);
        user.put("Email",email);
        user.put("Phone",phone);
        user.put("Password",password);
        user.put("Propic","0");
        db.collection("User").add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("jeni","Documents Added with ID:"+documentReference.getId());
                        Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("jeni","Error adding document",e);
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                });


    }


    public void login(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }





        }
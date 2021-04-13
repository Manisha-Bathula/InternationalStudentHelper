package com.gbcish;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internationalstudenthelper.R;
import com.gbcish.models.Messages;
import com.gbcish.models.PostModel;
import com.gbcish.models.ReceiveChatMessages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firestore.v1.Value;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatNames extends AppCompatActivity {

    private TextView mTextView;
    private RecyclerView recycChatNames;
    private ArrayList<ReceiveChatMessages> chatNames;
    private DatabaseReference databaseRef;
    private String UUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_names);
        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        chatNames=new ArrayList<>();
        recycChatNames=findViewById(R.id.recycChatNames);
        databaseRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference Messages = databaseRef.child("messages");

        Messages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap: snapshot.getChildren()){
                    HashMap<String,String> maps=new HashMap();
                            UUID = snap.getKey();

                            if (currentFirebaseUser.getUid().equals(UUID)){
                                final DatabaseReference Messages = databaseRef.child("messages").child(UUID);


                            }
//                    Log.d("Categories: ", String.valueOf(snap.getValue()));
//                   ReceiveChatMessages c = (ReceiveChatMessages) snap.getValue();
//                    chatNames.add(c);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}
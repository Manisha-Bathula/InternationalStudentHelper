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
    private ArrayList<Messages> chatNames;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_names);

        chatNames=new ArrayList<>();
        recycChatNames=findViewById(R.id.recycChatNames);
        databaseRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference Messages = databaseRef.child("messages");

        Messages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap: snapshot.getChildren()){
                    HashMap<String,String> maps=new HashMap();
                            snap.getValue();
                    Log.d("Categories: ", String.valueOf(snap.getValue()));
                    com.gbcish.models.Messages c = snap.getValue(Messages.class);
                    Log.d("Categories: ", c.getFrom() + " " + c.getFrom());
                    chatNames.add(c);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}
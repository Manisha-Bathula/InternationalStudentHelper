package com.gbcish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.internationalstudenthelper.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.gbcish.models.ChatMessages;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ChatBoxActivity extends AppCompatActivity {

    private FloatingActionButton bt_send;
    private EditText et_input;
    private ListView list_of_messages;
    private FirebaseListAdapter<ChatMessages> adapter;
    private FirebaseListOptions<ChatMessages> options;
    private String sellername,chatid,currentuser;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
        Intent i=getIntent();
         currentuser=mAuth.getCurrentUser().getDisplayName();
         sellername="";
        chatid=sellername+currentuser;
//        mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//        sellername=user.getEmail().toString();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        Query query = databaseReference.orderByChild("chatID").equalTo(chatid);

     options = new FirebaseListOptions.Builder<ChatMessages>()
                .setLayout(R.layout.message)//Note: The guide doesn't mention this method, without it an exception is thrown that the layout has to be set.
                .setQuery(query,ChatMessages.class)
                .setLifecycleOwner(this)   //Added this
                .build();


        bt_send=findViewById(R.id.fab);
        et_input=findViewById(R.id.et_input);
        list_of_messages=findViewById(R.id.list_of_messages);

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(new ChatMessages(chatid,et_input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName())
                        );

                // Clear the input
                et_input.setText("");
            }
        });
        adapter = new FirebaseListAdapter<ChatMessages>(options) {
            @Override
            protected void populateView(View v, ChatMessages model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        list_of_messages.setAdapter(adapter);
    }
}
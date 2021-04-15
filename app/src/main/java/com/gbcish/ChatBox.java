package com.gbcish;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.internationalstudenthelper.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.gbcish.models.ChatMessages;
import com.gbcish.models.Messages;
import com.gbcish.models.ReceiveChatMessages;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatBox extends AppCompatActivity {

    private TextView mTextView;
    private FloatingActionButton btChat;
    private EditText et_input;
    String mCurrentUserId;  // login currently
    String mChatUser; // venkatpardha
    private FirebaseAuth mAuth;
    private DatabaseReference mRootReference;
    DatabaseReference mDatabaseReference;
    public static final int TOTAL_ITEM_TO_LOAD = 10;
    private int mCurrentPage = 1;
    private FirebaseListAdapter<Messages> adapter;
    private FirebaseListOptions<ChatMessages> options;
    //Solution for descending list on refresh
    private int itemPos = 0;
    private String mLastKey="";
    private String mPrevKey="";
    private ListView mMessagesList;
    private ArrayList<String> messagesList=new ArrayList<>();
     ArrayAdapter aadapter;
    private String username="";
    private String useremail="";
    private String adPostedUserID;
    private ReceiveChatMessages receiveChatMessages;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = findViewById(R.id.toolbar2);
//        setSupportActionBar(toolbar);
//
//        // toolbar fancy stuff
//        getSupportActionBar().setTitle("Chat Box");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i=getIntent();
        username=i.getStringExtra("username");
        useremail=i.getStringExtra("useremail");
        Log.d("useremail",""+useremail);
        adPostedUserID=i.getStringExtra("adPostedUserID");
        receiveChatMessages= (ReceiveChatMessages) i.getSerializableExtra("messagesreceived");
       if(receiveChatMessages!=null) {
           mChatUser = receiveChatMessages.getToUuid();
       }
       else {
           mChatUser=adPostedUserID;
       }
        et_input=findViewById(R.id.et_input);

        btChat=findViewById(R.id.fab);

        aadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, messagesList);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        mRootReference = FirebaseDatabase.getInstance().getReference();

        mMessagesList=findViewById(R.id.list_of_messages);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            mCurrentUserId = mAuth.getCurrentUser().getUid();

        }
        loadMessages();
        btChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = ""+FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+": "+et_input.getText().toString();
                if(!TextUtils.isEmpty(message)){


                    String current_user_ref = "messages/"+mCurrentUserId+"/"+mChatUser;
                    String chat_user_ref = "messages/"+ mChatUser +"/"+mCurrentUserId;



                    String push_id="";
                    try{
                        DatabaseReference user_message_push = mRootReference.child("messages")
                                .child(""+mCurrentUserId).child(""+mChatUser).push();
                         push_id = user_message_push.getKey();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    Toast.makeText(ChatBox.this, ""+mCurrentUserId, Toast.LENGTH_SHORT).show();



                    Map messageMap = new HashMap();
                    messageMap.put("message",message);
                    messageMap.put("seen",false);
                    messageMap.put("type","text");
                    messageMap.put("time", ServerValue.TIMESTAMP);
                    messageMap.put("from",mCurrentUserId);
                    messageMap.put("messagefromusername",username);
                    messageMap.put("messagefromuseremail",useremail);
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    Map messageUserMap = new HashMap();
                    messageUserMap.put(current_user_ref+"/"+push_id,messageMap);
                    messageUserMap.put(chat_user_ref+"/"+push_id,messageMap);

                    mRootReference.updateChildren(messageUserMap, new DatabaseReference.CompletionListener(){

                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if(databaseError != null){
                                Log.e("CHAT_ACTIVITY","Cannot add message to database");
                            }
                            else{
                                Toast.makeText(ChatBox.this, "Message sent", Toast.LENGTH_SHORT).show();
                                et_input.setText("");
                            }

                        }
                    });




                }


            }
        });





    }
    private void loadMessages() {

        DatabaseReference messageRef = mRootReference.child("messages").child(mCurrentUserId).child(mChatUser);
        Query messageQuery = messageRef.limitToLast(10);

        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Messages messages = (Messages) dataSnapshot.getValue(Messages.class);

                itemPos++;

                if(itemPos == 1){
                    String mMessageKey = dataSnapshot.getKey();

                    mLastKey = mMessageKey;
                    mPrevKey = mMessageKey;
                }

                messagesList.add(messages.getMessage());
                mMessagesList.setAdapter(aadapter);
                aadapter.notifyDataSetChanged();

//                mMessagesList.scrollTo(messagesList.size()-1,);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
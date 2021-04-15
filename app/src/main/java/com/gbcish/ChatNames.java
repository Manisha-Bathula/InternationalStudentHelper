package com.gbcish;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internationalstudenthelper.R;
import com.gbcish.Adapters.ChatListNamesAdapter;
import com.gbcish.models.Messages;
import com.gbcish.models.ReceiveChatMessages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatNames extends AppCompatActivity implements ChatListNamesAdapter.OnItemClickListener{

    private TextView mTextView;
    private RecyclerView recycChatNames;
    private ArrayList<Messages> chatNames;
    private DatabaseReference databaseRef;
    private String UUID;
    private String currentUUID;
    private int itemPos = 0;
    private String mLastKey="";
    private String mPrevKey="";
    private ListView mMessagesList;
    private ArrayList<ReceiveChatMessages> messagesList=new ArrayList<>();;
    LinearLayoutManager layoutManager;
    ChatListNamesAdapter chatListNamesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_names);
        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        currentUUID=currentFirebaseUser.getUid();
        chatNames=new ArrayList<>();
        recycChatNames=findViewById(R.id.recycChatNames);
         layoutManager = new LinearLayoutManager(this);
        recycChatNames.setLayoutManager(layoutManager);
        recycChatNames.setItemAnimator(new DefaultItemAnimator());
        recycChatNames.setNestedScrollingEnabled(false);

        chatListNamesAdapter = new ChatListNamesAdapter(messagesList, this);
        chatListNamesAdapter.setOnItemClickListener(ChatNames.this);
        recycChatNames.setAdapter(chatListNamesAdapter);
        databaseRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference Messages = databaseRef.child("messages");


        Messages.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            UUID=userSnapshot.getKey();
                            if(currentUUID.equals(UUID)) {
                            for (DataSnapshot programSnapshot : userSnapshot.getChildren()) {
                                for (DataSnapshot ogramSnapshot : programSnapshot.getChildren()) {

                                    Messages program = ogramSnapshot.getValue(Messages.class);
                                    ReceiveChatMessages r=new ReceiveChatMessages(programSnapshot.getKey(),UUID,program);
                                    Log.d("TAG", "TO"+UUID+"From: "+programSnapshot.getKey()+"" + program.getMessage());
                                    messagesList.add(r);

                                }
                            }
                        }
                    }
                        chatListNamesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        Messages.addListenerForSingleValueEvent(new ValueEventListener) {
//            public void onDataChange(DataSnapshot snapshot) {
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
//                        for (DataSnapshot programSnapshot : userSnapshot.getChildren()) {
//                            Program program = programSnapshot.getValue(Program.class);
//
//                        }
//                    }
//                }
//
//                public void onCancelled(FirebaseError firebaseError) {
//
//                }
//            });

//        Messages.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot snap: snapshot.getChildren()){
//                    HashMap<String,String> maps=new HashMap();
//                            UUID = snap.getKey();
//
//                            if (currentUUID.equals(UUID)){
//                                final DatabaseReference Messages2 = databaseRef.child("messages").child(UUID);
//
//                                String key = snap.getKey();
//                                Messages value = snap.getValue(Messages.class);
//                                Log.d("TAG", key + value.getFrom());
////                                Messages2.addValueEventListener(new ValueEventListener() {
////                                    @Override
////                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
////                                        for (DataSnapshot snap: snapshot.getChildren()) {
////
////                                            String UUID2=snap.getKey();
//////                                            loadMessages(Messages2,currentUUID,UUID2);
////                                            final DatabaseReference Messages3 = databaseRef.child("messages").child(UUID).child(UUID2);
////                                            Messages3.addValueEventListener(new ValueEventListener() {
////                                                @Override
////                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
////                                                    for (DataSnapshot snap: snapshot.getChildren()) {
////
////                                                        String UUID2 = snap.getKey();
////                                                        Messages finalmessages=snap.getValue(com.gbcish.models.Messages.class);
////                                                        String from=finalmessages.getFrom();
////                                                        chatNames.add(finalmessages);
////
////                                                    }
////                                                }
////
////                                                @Override
////                                                public void onCancelled(@NonNull DatabaseError error) {
////
////                                                }
////                                            });
//////
////
////                                        }
////                                        }
////
////                                    @Override
////                                    public void onCancelled(@NonNull DatabaseError error) {
////
////                                    }
////                                });
//
//                            }
////                    Log.d("Categories: ", String.valueOf(snap.getValue()));
////                   ReceiveChatMessages c = (ReceiveChatMessages) snap.getValue();
////                    chatNames.add(c);
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });




    }
    private void loadMessages(DatabaseReference mRootReference,String mCurrentUserId,String mChatUser ) {

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

//                messagesList.add(messages.getMessage());
//                mMessagesList.setAdapter(aadapter);
//                aadapter.notifyDataSetChanged();

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

    @Override
    public void onItemClick(int position, ReceiveChatMessages messagesreceived) {

        Intent intent=new Intent(getApplicationContext(), ChatBox.class);
        intent.putExtra("messagesreceived",messagesreceived);
        startActivity(intent);

    }
}
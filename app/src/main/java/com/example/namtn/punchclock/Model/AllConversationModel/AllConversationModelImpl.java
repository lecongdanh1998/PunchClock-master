package com.example.namtn.punchclock.Model.AllConversationModel;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.namtn.punchclock.Adapter.AllConversation.ConversationAdapter;
import com.example.namtn.punchclock.ModelView.Conversation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class AllConversationModelImpl implements AllConversationModel {

    private static final String TAG = "ALL_CONVERTSATION_MODEL";
    private Context mContext;
    private Activity mActivity;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mReference;
    private String author = "", caption = "", image = "";
    private ConversationAdapter mAdapter;
    private ArrayList<Conversation> mConversationArrayList;

    public AllConversationModelImpl(Context mContext) {
        this.mContext = mContext;
        this.mActivity = (Activity) mContext;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void addConversation(OnConversationListener listener, String data) {

    }

    @Override
    public void fetchConversation(OnConversationListener listener) {
        mConversationArrayList = new ArrayList<>();
        mReference = mFirebaseDatabase.getReference("Conversation");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mConversationArrayList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataChild : data.getChildren()) {
                        author = dataChild.child("Author").getValue().toString();
                        caption = "";
                        image = "";
                        for (DataSnapshot dataConversation : dataChild.getChildren()) {
                            if (dataConversation.child("Caption").getValue() != null) {
                                caption = dataConversation.child("Caption").getValue().toString();
                            }
                            if (dataConversation.child("Image").getValue() != null) {
                                image = dataConversation.child("Image").getValue().toString();
                            }
                        }
                        Log.d(TAG, "onDataChange: " + caption + author + image);
                        mConversationArrayList.add(new Conversation(author, caption, image, ""));
                    }
                }
                mAdapter = new ConversationAdapter(mContext, mConversationArrayList);
                listener.fetchAllConversationSuccess(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });
    }
}

package com.example.namtn.punchclock.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.namtn.punchclock.Adapter.AllConversation.ConversationAdapter;
import com.example.namtn.punchclock.ModelView.Conversation;
import com.example.namtn.punchclock.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.SimpleFormatter;

public class PostConversationActivity extends BaseActivity {

    private static final String TAG = "POST_CONVERSATION";
    private EditText mEditTextContents;
    private TextView mTextViewShare;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mReference;
    private Calendar mCalendar;
    private SimpleDateFormat mFormatter;

    @Override
    protected int getContentView() {
        return R.layout.activity_post_conversation;
    }

    @Override
    protected void initView() {
        mEditTextContents = findViewById(R.id.edt_contents_post_conversation);
        mTextViewShare = findViewById(R.id.txt_share_post_conversation);
    }

    @Override
    protected void initEventControl() {
        mTextViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postConversation();
            }
        });
    }

    @Override
    protected void initData() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }

    private void postConversation(){
        mCalendar = Calendar.getInstance();
        String contents = mEditTextContents.getText().toString().trim();
        mFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String conversationId = "Conversation" + mFormatter.format(mCalendar.getTime());
        mReference = mFirebaseDatabase.getReference("Conversation");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mReference.child("AllConversation").child(conversationId).child("Author").setValue("Neon");
                mReference.child("AllConversation").child(conversationId).child("Data").child("Caption").setValue(contents);
                mReference.child("AllConversation").child(conversationId).child("Data").child("Create_at").setValue(mFormatter.format(mCalendar.getTime()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });
    }
}

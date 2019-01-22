package com.example.namtn.punchclock.Model.ModelInfoUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.namtn.punchclock.Activity.ChatMessage.ChatMessageActivity;
import com.example.namtn.punchclock.Activity.UserActivity.LoginActivity;
import com.example.namtn.punchclock.Adapter.AdapterChatInforRecyr;
import com.example.namtn.punchclock.Adapter.AdapterInforRecyr;
import com.example.namtn.punchclock.Model.ModelChat.ContructorChatUser;
import com.example.namtn.punchclock.Model.ModelChat.ContructorChatUser2;
import com.example.namtn.punchclock.Notification.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class ModelInfoUser {
    ModelReponsetoPresenterInfoUser callback;
    Context context;
    Activity activity;
    ArrayList<ContructorInfoUser> arrayList;
    ArrayList<ContructorInfoUser> arrayListOnline;
    ArrayList<ContructorChatUser2> arrayListChatUser;
    private RecyclerView.Adapter mAdapter_details;
    private RecyclerView.LayoutManager mLayoutManager_details;
    private RecyclerView.Adapter mAdapter_detailsChat;
    private RecyclerView.LayoutManager mLayoutManager_detailsChat;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference, databaseReferenceChatUserid, databaseReferenceChatUserCheckConnect;
    private SimpleDateFormat dateFormat;
    private Date date;
    private Date date2;
    public static SharedPreferences dataLoginUser;
    public static SharedPreferences.Editor editorUser;

    public ModelInfoUser(ModelReponsetoPresenterInfoUser callback, Context context) {
        this.callback = callback;
        this.context = context;
        this.activity = (Activity) context;
        arrayList = new ArrayList<>();
        arrayListOnline = new ArrayList<>();
        arrayListChatUser = new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReferenceChatUserid = FirebaseDatabase.getInstance().getReference("LastChats-User").child(firebaseUser.getUid());
        databaseReferenceChatUserCheckConnect = FirebaseDatabase.getInstance().getReference("Users");
        dataLoginUser = context.getSharedPreferences("data_User", Context.MODE_PRIVATE);
    }

    public void initIntentView(int requestcode) {
        switch (requestcode) {
            case 0:
//                intent(UserActivity.class, 0);
                break;
        }
    }

    private void intent(Class c, int requestcode) {
        Intent intent = new Intent(activity, c);
        activity.startActivity(intent);
        switch (requestcode) {
            case 0:
                break;
            case 1:
                activity.finish();
                break;
        }
    }


    public void initLogout() {
        if (firebaseUser != null) {
            databaseReferenceChatUserCheckConnect.child(firebaseUser.getUid()).child("online").setValue("off");
        }
        editorUser = dataLoginUser.edit();
        editorUser.putString("useremail", "");
        editorUser.commit();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(context, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public void initDataListview(final RecyclerView mRecyclerView_details) {
        if (firebaseUser != null) {
            databaseReferenceChatUserCheckConnect.child(firebaseUser.getUid()).child("online").setValue("on");
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                arrayListOnline.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ContructorInfoUser user = snapshot.getValue(ContructorInfoUser.class);
                    assert user != null;
                    assert firebaseUser != null;
                    if (!user.getId().equals(firebaseUser.getUid())) {
                        if (user.getOnline().equals("on")) {
                            arrayListOnline.add(user);
                        }
                        arrayList.add(user);
                    }
                }
                mRecyclerView_details.setHasFixedSize(true);
                mLayoutManager_details = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                mRecyclerView_details.setLayoutManager(mLayoutManager_details);
                mAdapter_details = new AdapterInforRecyr(arrayListOnline, context);
                callback.onDataListview(mAdapter_details);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DatabaseError: ", "" + databaseError);
            }
        });
    }

    public void initDataChatUser(final RecyclerView mRecyclerView_details) {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        databaseReferenceChatUserid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayListChatUser.clear();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final ContructorChatUser user = snapshot.getValue(ContructorChatUser.class);
                    assert user != null;
                    assert firebaseUser != null;
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (user.getToId().toString().equals(arrayList.get(i).getId())) {
                            arrayListChatUser.add(new ContructorChatUser2(arrayList.get(i).getUsername(), arrayList.get(i).getImageURL(),
                                    user.getFromId(), user.getMessage(), user.getTime(), user.getToId(), user.getType(),
                                    user.getStatus(), arrayList.get(i).getOnline()
                            ));
                        }
                    }
                    Collections.sort(arrayListChatUser, new Comparator<ContructorChatUser2>() {
                        @Override
                        public int compare(ContructorChatUser2 o1, ContructorChatUser2 o2) {
                            try {
                                date = dateFormat.parse(o1.getTime());
                                date2 = dateFormat.parse(o2.getTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return Long.compare(date2.getTime(), date.getTime());
                        }
                    });

                }
                mRecyclerView_details.setHasFixedSize(true);
                mLayoutManager_detailsChat = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                mRecyclerView_details.setLayoutManager(mLayoutManager_detailsChat);
                mAdapter_detailsChat = new AdapterChatInforRecyr(arrayListChatUser, context);
                callback.onDataListviewChat(mAdapter_detailsChat);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DatabaseError: ", "" + databaseError);
            }
        });
        updateToken(FirebaseInstanceId.getInstance().getToken());
    }

    private void updateToken(String token) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(firebaseUser.getUid()).child("token").setValue(token);
    }

    public void initListChat(int position) {
        Intent intent = new Intent(context, ChatMessageActivity.class);
        intent.putExtra("userId", arrayList.get(position).getId());
        activity.startActivity(intent);
    }


}

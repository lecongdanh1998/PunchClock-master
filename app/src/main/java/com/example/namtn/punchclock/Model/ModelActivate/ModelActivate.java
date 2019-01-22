package com.example.namtn.punchclock.Model.ModelActivate;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.namtn.punchclock.Adapter.AdapterInforActivateRecyr;
import com.example.namtn.punchclock.Adapter.AdapterInforRecyr;
import com.example.namtn.punchclock.Model.ModelInfoUser.ContructorInfoUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ModelActivate {
    ModelReponsetoPresenterActivate callback;
    Context context;
    Activity activity;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    ArrayList<ContructorInfoUser> arrayList;
    ArrayList<ContructorInfoUser> arrayListOnline;
    private RecyclerView.Adapter mAdapter_details;
    private RecyclerView.LayoutManager mLayoutManager_details;
    public ModelActivate(ModelReponsetoPresenterActivate callback, Context context) {
        this.callback = callback;
        this.context = context;
        this.activity = (Activity) context;
        arrayList = new ArrayList<>();
        arrayListOnline = new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
    }

    public void initDataListview(final RecyclerView mRecyclerView_details) {
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
                mLayoutManager_details = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                mRecyclerView_details.setLayoutManager(mLayoutManager_details);
                mAdapter_details = new AdapterInforActivateRecyr(arrayListOnline, context);
                callback.onDataListview(mAdapter_details);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DatabaseError: ", "" + databaseError);
            }
        });
    }



}

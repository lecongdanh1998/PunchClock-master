package com.example.namtn.punchclock.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.namtn.punchclock.Activity.BaseFragment;
import com.example.namtn.punchclock.R;
import com.example.namtn.punchclock.component.CustomToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GroupFragment extends BaseFragment {

    private View mView;
    private OnFragmentListener mListener;
    private FirebaseDatabase mFirebaseDatabase;
    private Task<Void> mReference;
    private CustomToast mToast;
    private Context mContext;

    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent,
                                        Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_group, parent, false);
        return mView;
    }

    @Override
    public void initView() {
        mContext = getContext();
        mToast = new CustomToast(mContext);
    }

    @Override
    public void initEventButton() {

    }

    @Override
    public void initData() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void addConversation(String data) {
        mReference = mFirebaseDatabase.getReference("Conversations").child(data)
                .setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mToast.showToast("Add conversation success", 0);
                        } else {
                            mToast.showToast("Add conversation success", 0);
                        }
                    }
                });
    }
}

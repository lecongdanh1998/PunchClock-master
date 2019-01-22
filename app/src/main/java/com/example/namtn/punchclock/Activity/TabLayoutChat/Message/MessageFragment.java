package com.example.namtn.punchclock.Activity.TabLayoutChat.Message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.namtn.punchclock.Presenter.PresenterInfoUser.PresenterInfoUser;
import com.example.namtn.punchclock.Presenter.PresenterInfoUser.PresenterReponsetoViewInfoUser;
import com.example.namtn.punchclock.R;
import com.google.firebase.database.FirebaseDatabase;

public class MessageFragment extends Fragment implements View.OnClickListener, PresenterReponsetoViewInfoUser {
    PresenterInfoUser presenterInfoUser;
    RecyclerView RecyclerViewInfo, RecyclerViewChatInfo;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.message_fragment, container, false);
        initControl();
        initData();
        initOnClick();
        return view;
    }

    private void initControl() {
        RecyclerViewInfo = view.findViewById(R.id.RecyclerViewInfo);
        RecyclerViewChatInfo = view.findViewById(R.id.RecyclerViewChatInfo);
    }

    private void initData() {
        presenterInfoUser = new PresenterInfoUser(this, getContext());
        presenterInfoUser.initDataListview(RecyclerViewInfo);
    }

    private void initOnClick() {
//        img_account.setOnClickListener(this);
//        img_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_logout:
                presenterInfoUser.initLogout();
                break;
            case R.id.img_account:
                presenterInfoUser.initIntentView(0);
                break;
        }
    }

    @Override
    public void onDataListview(RecyclerView.Adapter adapterInfoUser) {
        presenterInfoUser.initDataChatUser(RecyclerViewChatInfo);
        RecyclerViewInfo.setAdapter(adapterInfoUser);
        adapterInfoUser.notifyDataSetChanged();
    }

    @Override
    public void onDataListviewChat(RecyclerView.Adapter adapterChatInfoUser) {
        RecyclerViewChatInfo.setAdapter(adapterChatInfoUser);
        adapterChatInfoUser.notifyDataSetChanged();
    }
}

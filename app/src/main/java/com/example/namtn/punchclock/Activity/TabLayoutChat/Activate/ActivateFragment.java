package com.example.namtn.punchclock.Activity.TabLayoutChat.Activate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.namtn.punchclock.Presenter.PresenterActivate.PresenterActivate;
import com.example.namtn.punchclock.Presenter.PresenterActivate.PresenterReponsetoViewActivate;
import com.example.namtn.punchclock.R;

public class ActivateFragment extends Fragment implements View.OnClickListener, PresenterReponsetoViewActivate {

    private View view;
    PresenterActivate presenterActivate;
    RecyclerView RecyclerViewInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activate_fragment, container, false);
        initControl();
        initData();
        initOnClick();
        return view;
    }

    private void initControl() {
        RecyclerViewInfo = view.findViewById(R.id.RecyclerViewInfo);
    }

    private void initData() {
        presenterActivate = new PresenterActivate(getContext(), this);
        presenterActivate.initDataListview(RecyclerViewInfo);
    }

    private void initOnClick() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDataListview(RecyclerView.Adapter adapterInfoActivateUser) {
        RecyclerViewInfo.setAdapter(adapterInfoActivateUser);
        adapterInfoActivateUser.notifyDataSetChanged();
    }
}

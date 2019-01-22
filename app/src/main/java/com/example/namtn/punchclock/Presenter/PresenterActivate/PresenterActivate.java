package com.example.namtn.punchclock.Presenter.PresenterActivate;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.namtn.punchclock.Model.ModelActivate.ModelActivate;
import com.example.namtn.punchclock.Model.ModelActivate.ModelReponsetoPresenterActivate;

public class PresenterActivate implements ModelReponsetoPresenterActivate {
    Context context;
    Activity activity;
    PresenterReponsetoViewActivate callback;
    ModelActivate modelActivate;

    public PresenterActivate(Context context, PresenterReponsetoViewActivate callback) {
        this.context = context;
        this.activity = (Activity) context;
        this.callback = callback;
        modelActivate = new ModelActivate(this, context);
    }

    public void initDataListview(RecyclerView mRecyclerView_details){
        modelActivate.initDataListview(mRecyclerView_details);
    }


    @Override
    public void onDataListview(RecyclerView.Adapter adapterInfoActivateUser) {
        callback.onDataListview(adapterInfoActivateUser);
    }
}

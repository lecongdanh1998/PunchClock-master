package com.example.namtn.punchclock.Presenter.PresenterInfoUser;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.namtn.punchclock.Model.ModelInfoUser.ModelInfoUser;
import com.example.namtn.punchclock.Model.ModelInfoUser.ModelReponsetoPresenterInfoUser;


public class PresenterInfoUser implements ModelReponsetoPresenterInfoUser {
    PresenterReponsetoViewInfoUser callback;
    Context context;
    Activity activity;
    ModelInfoUser modelInfoUser;

    public PresenterInfoUser(PresenterReponsetoViewInfoUser callback, Context context) {
        this.callback = callback;
        this.context = context;
        this.activity = (Activity) context;
        modelInfoUser = new ModelInfoUser(this, context);
    }


    public void initListChat(int position) {
        modelInfoUser.initListChat(position);
    }

    public void initLogout(){
        modelInfoUser.initLogout();
    }

    public void initIntentView(int requestcode){
        modelInfoUser.initIntentView(requestcode);
    }

    public void initDataChatUser(RecyclerView recyclerView) {
        modelInfoUser.initDataChatUser(recyclerView);
    }

    public void initDataListview(RecyclerView RecyclerViewInfo) {
        modelInfoUser.initDataListview(RecyclerViewInfo);
    }

    @Override
    public void onDataListview(RecyclerView.Adapter adapterInfoUser) {
        callback.onDataListview(adapterInfoUser);
    }

    @Override
    public void onDataListviewChat(RecyclerView.Adapter adapterChatInfoUser) {
        callback.onDataListviewChat(adapterChatInfoUser);
    }
}

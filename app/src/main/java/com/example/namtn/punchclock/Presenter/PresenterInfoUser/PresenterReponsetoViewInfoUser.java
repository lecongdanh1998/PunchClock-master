package com.example.namtn.punchclock.Presenter.PresenterInfoUser;

import android.support.v7.widget.RecyclerView;

public interface PresenterReponsetoViewInfoUser {
    void onDataListview(RecyclerView.Adapter adapterInfoUser);
    void onDataListviewChat(RecyclerView.Adapter adapterChatInfoUser);
}

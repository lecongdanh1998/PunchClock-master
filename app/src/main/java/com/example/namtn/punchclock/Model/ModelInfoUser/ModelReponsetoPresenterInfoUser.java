package com.example.namtn.punchclock.Model.ModelInfoUser;

import android.support.v7.widget.RecyclerView;

public interface ModelReponsetoPresenterInfoUser {
    void onDataListview(RecyclerView.Adapter adapterInfoUser);
    void onDataListviewChat(RecyclerView.Adapter adapterChatInfoUser);
}

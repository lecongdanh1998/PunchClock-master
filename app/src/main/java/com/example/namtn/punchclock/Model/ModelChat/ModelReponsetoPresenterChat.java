package com.example.namtn.punchclock.Model.ModelChat;

import android.support.v7.widget.RecyclerView;

public interface ModelReponsetoPresenterChat {
    void onData(String id, String image, String username);
    void onSendMessage(String str);
    void onDataListview(RecyclerView.Adapter adapterChat,int array);
}

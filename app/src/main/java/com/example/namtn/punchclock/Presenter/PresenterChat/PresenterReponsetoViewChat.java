package com.example.namtn.punchclock.Presenter.PresenterChat;

import android.support.v7.widget.RecyclerView;

public interface PresenterReponsetoViewChat {
    void onData(String id, String image, String username);
    void onSendMessage(String str);
    void onDataListview(RecyclerView.Adapter adapterChat,int array);
}

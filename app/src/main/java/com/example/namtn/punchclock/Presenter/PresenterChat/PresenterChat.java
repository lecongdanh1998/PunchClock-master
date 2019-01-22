package com.example.namtn.punchclock.Presenter.PresenterChat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;

import com.example.namtn.punchclock.Model.ModelChat.ModelChat;
import com.example.namtn.punchclock.Model.ModelChat.ModelReponsetoPresenterChat;


public class PresenterChat implements ModelReponsetoPresenterChat {
    PresenterReponsetoViewChat callback;
    Context context;
    Activity activity;
    ModelChat modelChat;

    public PresenterChat(PresenterReponsetoViewChat callback, Context context) {
        this.callback = callback;
        this.context = context;
        this.activity = (Activity) context;
        modelChat = new ModelChat(this,context);
    }

    public void initData() {
        modelChat.initData();
    }

    public void initSendMessage(String message){
        modelChat.initSendMessage(message);
    }

    public void initReadMessage(RecyclerView reyclerview_message_list){
        modelChat.initReadMessage(reyclerview_message_list);
    }

    @Override
    public void onData(String id, String image, String username) {
        callback.onData(id,image,username);
    }

    public void initSendMessageVideo(Uri filePath){
        modelChat.initSendMessageVideo(filePath);
    }

    public void initSendMessageImage(Bitmap filePath){
        modelChat.initSendMessageImage(filePath);
    }


    @Override
    public void onSendMessage(String str) {
        callback.onSendMessage(str);
    }

    @Override
    public void onDataListview(RecyclerView.Adapter adapterChat, int array) {
        callback.onDataListview(adapterChat,array);
    }
}

package com.example.namtn.punchclock.Presenter.PresenterUser;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;

import com.example.namtn.punchclock.Model.ModelUser.ModelReponsetoPresenterUser;
import com.example.namtn.punchclock.Model.ModelUser.ModelUser;



public class PresenterUser implements ModelReponsetoPresenterUser {
    PresenterReponsetoViewUser callback;
    Context context;
    Activity activity;
    ModelUser modelUser;

    public PresenterUser(PresenterReponsetoViewUser callback, Context context) {
        this.callback = callback;
        this.context = context;
        this.activity = (Activity) context;
        modelUser = new ModelUser(this, context);
    }

    public void initDataEdit(int requestcode){
        modelUser.initDataEdit(requestcode);
    }

    public void initDataEditImage(int requestcode, Bitmap imageBitmap){
        modelUser.initDataEditImage(requestcode,imageBitmap);
    }

    public void initData() {
        modelUser.initData();
    }

    @Override
    public void onData(String id, String email, String imageURL, String username,String birthday,String gender,String imageBackground) {
        callback.onData(id, email, imageURL, username,birthday,gender,imageBackground);
    }

    @Override
    public void onDataEditImage(int requestcodeLoai,int requestcode,Dialog dialogImage) {
        callback.onDataEditImage(requestcodeLoai,requestcode,dialogImage);
    }
}

package com.example.namtn.punchclock.Presenter.PresenterSignUp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ProgressBar;

import com.example.namtn.punchclock.Model.ModelSignUp.ModelReponsetoPresenterSignUp;
import com.example.namtn.punchclock.Model.ModelSignUp.ModelSignUp;



public class PresenterSignUp implements ModelReponsetoPresenterSignUp {
    PresenterReponseViewSignUp callback;
    Context context;
    Activity activity;
    ModelSignUp modelSignUp;

    public PresenterSignUp(PresenterReponseViewSignUp callback, Context context) {
        this.callback = callback;
        this.context = context;
        this.activity = (Activity) context;
        modelSignUp = new ModelSignUp(this, context);
    }

    public void initOnIntent(int requestcode) {
        modelSignUp.initOnIntent(requestcode);
    }

    public void initBtnSignUp(Bitmap imageBitmap,String email, String password, String username, final ProgressBar progressBar) {
        modelSignUp.initBtnSignUp(imageBitmap,email, password,username, progressBar);
    }

    @Override
    public void onBtnSignUp(String str) {
        callback.onBtnSignUp(str);
    }
}

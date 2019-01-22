package com.example.namtn.punchclock.Presenter.PresenterUser;

import android.app.Dialog;

public interface PresenterReponsetoViewUser {
    void onData(String id, String email, String imageURL, String username, String birthday, String gender, String imageBackground);
    void onDataEditImage(int requestcodeLoai, int requestcode, Dialog dialogImage);
}

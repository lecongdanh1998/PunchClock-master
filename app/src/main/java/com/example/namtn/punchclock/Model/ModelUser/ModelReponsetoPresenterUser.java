package com.example.namtn.punchclock.Model.ModelUser;

import android.app.Dialog;

public interface ModelReponsetoPresenterUser {
    void onData(String id, String email, String imageURL, String username, String birthday, String gender, String imageBackground);
    void onDataEditImage(int requestcodeLoai, int requestcode, Dialog dialogImage);
}

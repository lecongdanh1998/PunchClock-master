package com.example.namtn.punchclock.Presenter.UserPresenter.LoginPresenter;

public interface LoginPresenter {
    void loginUser(String email, String password);

    void getUserInfo();

    void IntentClass(Class s);

    void onDestroy();

    void signUpUser();
}

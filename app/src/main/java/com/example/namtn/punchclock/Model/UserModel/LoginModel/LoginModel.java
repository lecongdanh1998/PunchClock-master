package com.example.namtn.punchclock.Model.UserModel.LoginModel;

public interface LoginModel {

    interface onLoginListener {
        void showProgressDialog();

        void hideProgressDialog();

        void setErrorEmail(String s);

        void setErrorPassword(String s);

        void setLoginFailure(String error);

        void setLoginSuccess(String message);

        void getInfoFailure(String s);

        void getInfoSuccess(String s);

        void signUpUser();
    }

    void loginUser(String email, String password, onLoginListener onLoginListener);

    void signUpUser(onLoginListener onLoginListener);

    void getUserInfo(onLoginListener listener);

    void IntentClass(Class c);
}

package com.example.namtn.punchclock.Activity.UserActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.namtn.punchclock.Activity.BaseActivity;
import com.example.namtn.punchclock.Activity.EnterPassCodeActivity;
import com.example.namtn.punchclock.Model.UserModel.LoginModel.LoginModelImpl;
import com.example.namtn.punchclock.Presenter.UserPresenter.LoginPresenter.LoginPresenter;
import com.example.namtn.punchclock.Presenter.UserPresenter.LoginPresenter.LoginPresenterImpl;
import com.example.namtn.punchclock.R;
import com.example.namtn.punchclock.View.LoginView;

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener {

    private EditText mUserEmail, mUserPassword;
    private Button mButtonLogin,mButtonSignUp;
    private String strUser, strPass;
    private LoginPresenter mPresenterLogin;
    private ProgressBar mProgressBarLogin;
    private String TAG = "LOGIN_MAIN";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    String email = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "initView: create UI" );
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mUserEmail = findViewById(R.id.edit_user_email_login);
        mUserPassword = findViewById(R.id.edit_user_password_login);
        mButtonLogin = findViewById(R.id.btn_access_login);
        mProgressBarLogin = findViewById(R.id.progress_login);
        mProgressBarLogin.setVisibility(View.GONE);
        mButtonSignUp = findViewById(R.id.btn_signup);
    }

    @Override
    protected void initEventControl() {
        mButtonLogin.setOnClickListener(this);
        mButtonSignUp.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mPresenterLogin = new LoginPresenterImpl(this, new LoginModelImpl(this));
        preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        email = preferences.getString("email","");
        if(!email.toString().equals("")){
            mUserEmail.setText(email);
        }else {
            mUserEmail.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_access_login:
                loginUser();
                break;
            case R.id.btn_signup:
                SignUp();
                break;
        }
    }

    @Override
    public void showProgressDialog() {
        mProgressBarLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        mProgressBarLogin.setVisibility(View.GONE);
    }

    @Override
    public void setErrorEmail(String s) {
        mUserEmail.setHintTextColor(getResources().getColor(R.color.colorRed));
        mUserEmail.setHint(s);
    }

    @Override
    public void setErrorPass(String s) {
        mUserPassword.setHintTextColor(getResources().getColor(R.color.colorRed));
        mUserPassword.setHint(s);
    }

    @Override
    public void loginFailure(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess(String message) {
        mPresenterLogin.getUserInfo();
    }

    @Override
    public void getInfoFailure(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getInfoSuccess(String s) {
        mPresenterLogin.IntentClass(EnterPassCodeActivity.class);
    }

    @Override
    public void signUpUser() {
//        mPresenterLogin.IntentClass(SignUpActivity.class);
    }

    private void loginUser(){
        strUser = mUserEmail.getText().toString().trim();
        strPass= mUserPassword.getText().toString().trim();
        mPresenterLogin.loginUser(strUser, strPass);
    }

    private void SignUp(){
        mPresenterLogin.signUpUser();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenterLogin.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}

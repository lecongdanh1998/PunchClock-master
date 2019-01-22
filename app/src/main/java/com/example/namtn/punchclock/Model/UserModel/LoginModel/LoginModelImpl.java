package com.example.namtn.punchclock.Model.UserModel.LoginModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.namtn.punchclock.Activity.UserActivity.SignUpActivity;
import com.example.namtn.punchclock.Retrofit.RetrofitConfig.RetrofitAPIs;
import com.example.namtn.punchclock.Retrofit.RetrofitConfig.RetrofitUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginModelImpl implements LoginModel {

    private Context context;
    private Activity activity;
    private RetrofitAPIs retrofit;
    private RetrofitAPIs retrofitInfo;
    private CompositeDisposable compositeDisposable;
    private String TAG = "LOGIN_USER";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    String token = "";
    String email = "";
    private FirebaseAuth auth;
    public LoginModelImpl(Context context) {
        this.context = context;
        this.activity = (Activity) context;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll()
                    .build();
            StrictMode.setThreadPolicy(policy);
        }
        preferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        editor = preferences.edit();
        retrofit = RetrofitUtils.apiUserLogin();
        compositeDisposable = new CompositeDisposable();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void loginUser(String email, String password, onLoginListener listener) {
        listener.showProgressDialog();
        int errorCode = 0;
        String grant_type = "password";
        String client_id = "2";
        String client_secret = "s4ao1Y6WD7kxNUnkn0QeiJmJej7JckWVtPsC7Cqp";
        if (email.isEmpty()) {
            listener.hideProgressDialog();
            listener.setErrorEmail("Vui lòng nhập email");
            errorCode++;
        }
        if (password.isEmpty()) {
            listener.hideProgressDialog();
            listener.setErrorPassword("Vui lòng nhập password");
            errorCode++;
        }
        if (errorCode == 0) {
            compositeDisposable.add(retrofit.userLogin(email, password, grant_type, client_id,
                    client_secret)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            s -> {
                                auth.signInWithEmailAndPassword(email, "123456")
                                        .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                // If sign in fails, display a message to the user. If sign in succeeds
                                                // the auth state listener will be notified and logic to handle the
                                                // signed in user can be handled in the listener.
                                                listener.hideProgressDialog();
                                                if (!task.isSuccessful()) {
                                                    Log.d("Authentication: ", "" + task.getException());
                                                    // there was an error
                                                } else {
                                                    editor.putString("token", "Bearer " + s.getAccessToken());
                                                    editor.putString("refreshToken", s.getRefreshToken());
                                                    editor.putString("email", email);
                                                    editor.commit();
                                                    listener.setLoginSuccess("");
                                                    Log.d(TAG, "loginUser: " + s.getAccessToken());
                                                }
                                            }
                                        });
                            },
                            throwable -> {
                                listener.setLoginFailure("Vui lòng kiểm tra email hoặc password");
                                listener.hideProgressDialog();
                                Log.d(TAG, "loginUser: " + throwable.getMessage());
                            }
                    )
            );
        }
    }

    @Override
    public void signUpUser(onLoginListener listener) {
        this.IntentClass(SignUpActivity.class);
    }

    @Override
    public void IntentClass(Class c) {
        Intent intent = new Intent(context, c);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void getUserInfo(onLoginListener listener) {
        token = preferences.getString("token", "0");
        email = preferences.getString("email", "0");
        retrofitInfo = RetrofitUtils.apiUserInfo(token);
        CompositeDisposable compositeDisposable1 = new CompositeDisposable();
        compositeDisposable1.add(retrofitInfo.userInfo(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    editor.putString("userId", String.valueOf(s.getData().getId()));
                    editor.putString("userRole", String.valueOf(s.getData().getId()));
                    editor.commit();
                    listener.getInfoSuccess("Đăng nhập thành công ");
                    Log.d(TAG, "getUserInfo: " + s.getData().getId());
                    Log.d(TAG, "getUserInfo: " + s.getData().getRole());
                }, throwable -> {
                    Log.d(TAG, "getUserInfo: " + throwable.getMessage());
                    listener.getInfoFailure("Serve error");
                })
        );
    }
}

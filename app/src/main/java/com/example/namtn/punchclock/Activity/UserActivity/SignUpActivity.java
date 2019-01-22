package com.example.namtn.punchclock.Activity.UserActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.namtn.punchclock.Activity.BaseActivity;
import com.example.namtn.punchclock.Presenter.PresenterSignUp.PresenterReponseViewSignUp;
import com.example.namtn.punchclock.Presenter.PresenterSignUp.PresenterSignUp;
import com.example.namtn.punchclock.R;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends BaseActivity implements View.OnClickListener, PresenterReponseViewSignUp {
    private EditText inputEmail, inputPassword, inputUsername;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    PresenterSignUp presenterSignUp;
    String email, password, username;
    String image = "";
    CircleImageView imageViewAva;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void initView() {
        imageViewAva = findViewById(R.id.imageViewAva);
        inputUsername = findViewById(R.id.username);
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
    }

    @Override
    protected void initEventControl() {
        btnSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        btnResetPassword.setOnClickListener(this);
        imageViewAva.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        presenterSignUp = new PresenterSignUp(this, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                presenterSignUp.initOnIntent(0);
                break;
            case R.id.sign_up_button:
                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();
                username = inputUsername.getText().toString().trim();
                if (!image.equals("") && !email.equals("") && !password.equals("") && !username.equals("")) {
                    presenterSignUp.initBtnSignUp(imageBitmap, email, password, username, progressBar);
                } else {
                    Toast.makeText(this, "Vui Lòng không được để trống", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_reset_password:
                break;
            case R.id.imageViewAva:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                break;
        }

    }

    @Override
    public void onBtnSignUp(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == this.RESULT_OK && data != null) {
            filePath = data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageViewAva.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            image = "data";
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

}

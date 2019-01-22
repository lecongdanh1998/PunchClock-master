package com.example.namtn.punchclock.Activity.User;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.namtn.punchclock.Activity.BaseActivity;
import com.example.namtn.punchclock.Presenter.PresenterUser.PresenterReponsetoViewUser;
import com.example.namtn.punchclock.Presenter.PresenterUser.PresenterUser;
import com.example.namtn.punchclock.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends BaseActivity implements View.OnClickListener, PresenterReponsetoViewUser {
    ImageView img_back, img_logo_user;
    private int CAMERA_REQUEST = 1;
    private int PIC_CROP = 3;
    Bitmap imageBitmap, imageBitmapBackground;
    private final int PICK_IMAGE_REQUEST = 71;
    TextView txt_username, txt_edit, txt_usernameEdit, txt_emailEdit, txt_birthdayEdit, txt_genderEdit, txt_passwordEdit;
    CircleImageView img_avatar;
    PresenterUser presenterUser;
    ImageButton btn_editUsername, btn_imgBackgroundEdit, btn_editBirthday, btn_editGender, btn_editPassword, btn_email, btn_imgAvaEdit;
    int edit, loai;
    private Uri filePath;
    RelativeLayout relativeLayout_avatar_edit, relativeLayout_background_edit;
    Dialog dialog;
    private static final int CAMERA_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_user;
    }

    @Override
    protected void initView() {
        btn_imgBackgroundEdit = findViewById(R.id.btn_imgBackgroundEdit);
        img_logo_user = findViewById(R.id.img_logo_user);
        relativeLayout_background_edit = findViewById(R.id.relativeLayout_background_edit);
        relativeLayout_avatar_edit = findViewById(R.id.relativeLayout_avatar_edit);
        btn_imgAvaEdit = findViewById(R.id.btn_imgAvaEdit);
        img_back = findViewById(R.id.img_back);
        txt_username = findViewById(R.id.txt_username);
        txt_edit = findViewById(R.id.txt_edit);
        txt_usernameEdit = findViewById(R.id.txt_usernameedit);
        txt_emailEdit = findViewById(R.id.txt_email);
        txt_birthdayEdit = findViewById(R.id.txt_birthday);
        txt_genderEdit = findViewById(R.id.txt_gender);
        txt_passwordEdit = findViewById(R.id.txt_password);
        img_avatar = findViewById(R.id.img_avatar);
        btn_editUsername = findViewById(R.id.btn_editUsername);
        btn_editBirthday = findViewById(R.id.btn_editBirthday);
        btn_editGender = findViewById(R.id.btn_editGender);
        btn_editPassword = findViewById(R.id.btn_editPassword);
        btn_email = findViewById(R.id.btn_email);
    }

    @Override
    protected void initEventControl() {
        btn_imgBackgroundEdit.setOnClickListener(this);
        btn_imgAvaEdit.setOnClickListener(this);
        img_back.setOnClickListener(this);
        txt_edit.setOnClickListener(this);
        btn_editUsername.setOnClickListener(this);
        btn_editBirthday.setOnClickListener(this);
        btn_editGender.setOnClickListener(this);
        btn_editPassword.setOnClickListener(this);
    }


    @Override
    protected void initData() {
        presenterUser = new PresenterUser(this, this);
        presenterUser.initData();
    }

    private void initOnClick() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.txt_edit:
                if (edit == 0) {
                    ButtonEnable();
                } else {
                    ButtonDisnable();
                }
                break;
            case R.id.btn_editUsername:
                presenterUser.initDataEdit(1);
                break;
            case R.id.btn_editBirthday:
                presenterUser.initDataEdit(2);
                break;
            case R.id.btn_editGender:
                presenterUser.initDataEdit(3);
                break;
            case R.id.btn_editPassword:
                presenterUser.initDataEdit(4);
                break;
            case R.id.btn_imgAvaEdit:
                presenterUser.initDataEditImage(1, imageBitmap);
                break;
            case R.id.btn_imgBackgroundEdit:
                presenterUser.initDataEditImage(2, imageBitmapBackground);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (edit == 0) {
            super.onBackPressed();
        } else {
            ButtonDisnable();
        }

    }

    public void ButtonEnable() {
        txt_edit.setText("Cancel");
        btn_email.setVisibility(View.INVISIBLE);
        btn_editUsername.setVisibility(View.VISIBLE);
        btn_editBirthday.setVisibility(View.VISIBLE);
        btn_editGender.setVisibility(View.VISIBLE);
        btn_editPassword.setVisibility(View.INVISIBLE);
        relativeLayout_avatar_edit.setVisibility(View.VISIBLE);
        relativeLayout_background_edit.setVisibility(View.VISIBLE);
        edit = 1;
    }

    public void ButtonDisnable() {
        txt_edit.setText("Edit");
        btn_email.setVisibility(View.GONE);
        btn_editUsername.setVisibility(View.GONE);
        btn_editBirthday.setVisibility(View.GONE);
        btn_editGender.setVisibility(View.GONE);
        btn_editPassword.setVisibility(View.GONE);
        relativeLayout_avatar_edit.setVisibility(View.GONE);
        relativeLayout_background_edit.setVisibility(View.GONE);
        edit = 0;
    }


    @SuppressLint("ResourceType")
    @Override
    public void onData(String id, String email, String imageURL, String username, String birthday, String gender, String imageBackground) {
        txt_username.setText(username);
        txt_usernameEdit.setText(username);
        txt_emailEdit.setText(email);
        if (birthday.toString().equals("")) {
            txt_birthdayEdit.setText("--");
        } else {
            txt_birthdayEdit.setText(birthday);
        }
        if (gender.toString().equals("")) {
            txt_genderEdit.setText("--");
        } else {
            txt_genderEdit.setText(gender);
        }
        if (imageBackground.toString().equals("default")) {
            img_logo_user.setImageResource(R.color.color_background);
        } else {
            Picasso.get()
                    .load(imageBackground)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(img_logo_user);
        }
        Picasso.get()
                .load(imageURL)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(img_avatar);
    }

    @Override
    public void onDataEditImage(int requestcodeLoai, int requestcode, Dialog dialogImage) {
        switch (requestcodeLoai) {
            case 1:
                loai = requestcodeLoai;
                switch (requestcode) {
                    case 1:
                        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent1, CAMERA_REQUEST_CODE);
                        intent1.setType("image/*");
                        dialog = dialogImage;
                        break;
                    case 2:
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        intent.putExtra("crop", "true");
                        intent.putExtra("aspectX", 200);
                        intent.putExtra("aspectY", 200);
                        intent.putExtra("outputX", 200);
                        intent.putExtra("outputY", 200);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                        dialog = dialogImage;
                        break;
                }
                break;
            case 2:
                loai = requestcodeLoai;
                switch (requestcode) {
                    case 1:
                        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent1, CAMERA_REQUEST_CODE);
                        intent1.setType("image/*");
                        dialog = dialogImage;
                        break;
                    case 2:
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        intent.putExtra("crop", "true");
                        intent.putExtra("aspectX", 0);
                        intent.putExtra("aspectY", 0);
                        intent.putExtra("outputX", 200);
                        intent.putExtra("outputY", 150);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                        dialog = dialogImage;
                        break;
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == this.RESULT_OK && data != null) {
            // get the Uri for the captured image
            dialog.cancel();
            switch (loai) {
                case 1:
                    filePath = data.getData();
                    imageBitmap = (Bitmap) data.getExtras().get("data");
                    presenterUser.initDataEditImage(loai, imageBitmap);
                    break;
                case 2:
                    filePath = data.getData();
                    imageBitmapBackground = (Bitmap) data.getExtras().get("data");
                    presenterUser.initDataEditImage(loai, imageBitmapBackground);
                    break;
            }

        }
        if (resultCode == this.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                // get the Uri for the captured image
                filePath = data.getData();
                performCrop();
            } else if (requestCode == CAMERA_REQUEST_CODE) {
                dialog.cancel();
                switch (loai) {
                    case 1:
                        filePath = data.getData();
                        imageBitmap = (Bitmap) data.getExtras().get("data");
                        presenterUser.initDataEditImage(loai, imageBitmap);
                        break;
                    case 2:
                        filePath = data.getData();
                        imageBitmapBackground = (Bitmap) data.getExtras().get("data");
                        presenterUser.initDataEditImage(loai, imageBitmapBackground);
                        break;
                }

            } else if (requestCode == PIC_CROP) {
                Bundle extras = data.getExtras();
                Bitmap thePic = extras.getParcelable("data");
                dialog.cancel();
                switch (loai) {
                    case 1:
                        presenterUser.initDataEditImage(loai, thePic);
                        break;
                    case 2:
                        presenterUser.initDataEditImage(loai, thePic);
                        break;
                }
            }
        }
    }

    private void performCrop() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(filePath, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            switch (loai) {
                case 1:
                    cropIntent.putExtra("aspectX", 200);
                    cropIntent.putExtra("aspectY", 200);
                    // indicate output X and Y
                    cropIntent.putExtra("outputX", 200);
                    cropIntent.putExtra("outputY", 200);
                    break;
                case 2:
                    cropIntent.putExtra("aspectX", 0);
                    cropIntent.putExtra("aspectY", 0);
                    cropIntent.putExtra("outputX", 200);
                    cropIntent.putExtra("outputY", 150);
                    break;
            }

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {

        }
    }
}

package com.example.namtn.punchclock.Activity.ChatMessage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.namtn.punchclock.Activity.BaseActivity;
import com.example.namtn.punchclock.Presenter.PresenterChat.PresenterChat;
import com.example.namtn.punchclock.Presenter.PresenterChat.PresenterReponsetoViewChat;
import com.example.namtn.punchclock.R;
import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class ChatMessageActivity extends BaseActivity implements PresenterReponsetoViewChat, View.OnClickListener {
    Button button_chatbox_send;
    EditText edittext_chatbox;
    //    ListView listOfMessages;
    View rootView;
    RecyclerView reyclerview_message_list;
    ImageView img_back, img_user, button_img_send;
    TextView txt_username;
    PresenterChat presenterChat;
    String message;
    int keyboardHeight, size;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    boolean notify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_chat_message;
    }

    @Override
    protected void initView() {
        img_back = findViewById(R.id.img_back);
        img_user = findViewById(R.id.img_user);
        txt_username = findViewById(R.id.txt_username);
        reyclerview_message_list = findViewById(R.id.reyclerview_message_list);
        edittext_chatbox = (EditText) findViewById(R.id.edittext_chatbox);
        button_chatbox_send = findViewById(R.id.button_chatbox_send);
        button_img_send = findViewById(R.id.button_img_send);
    }

    @Override
    protected void initEventControl() {
        img_back.setOnClickListener(this);
        button_chatbox_send.setOnClickListener(this);
        button_img_send.setOnClickListener(this);
        rootView = getWindow().getDecorView().getRootView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                rootView.getWindowVisibleDisplayFrame(rect);
                int screenHeight = rootView.getHeight();
                keyboardHeight = screenHeight - (rect.bottom - rect.top);
                if (keyboardHeight > screenHeight / 3) {
                    if (notify == false) {
                        reyclerview_message_list.smoothScrollToPosition(size - 1);
                        notify = true;
                    }

                } else {
                    notify = false;
                }
            }
        });

    }

    @Override
    protected void initData() {
        edittext_chatbox.requestFocus();
        presenterChat = new PresenterChat(this, this);
        presenterChat.initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.button_chatbox_send:
                message = edittext_chatbox.getText().toString().trim();
                presenterChat.initSendMessage(message);
                edittext_chatbox.setText("");
                break;
            case R.id.button_img_send:
                Intent intent = new Intent();
                intent.setType("image/* video/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                break;
        }
    }

    @Override
    public void onData(String id, String image, String username) {
        txt_username.setText(username.toString().trim());
        Picasso.get()
                .load(image)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(img_user);
        presenterChat.initReadMessage(reyclerview_message_list);
    }

    @Override
    public void onSendMessage(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataListview(RecyclerView.Adapter adapterChat, int array) {
        reyclerview_message_list.setAdapter(adapterChat);
        adapterChat.notifyDataSetChanged();
        size = array;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                if (filePath.toString().contains("image")) {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    presenterChat.initSendMessageImage(bitmap);
                } else if (filePath.toString().contains("video")) {
                    //handle video
                    presenterChat.initSendMessageVideo(filePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

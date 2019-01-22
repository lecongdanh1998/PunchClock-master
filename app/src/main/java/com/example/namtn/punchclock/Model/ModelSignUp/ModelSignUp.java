package com.example.namtn.punchclock.Model.ModelSignUp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.namtn.punchclock.Activity.UserActivity.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.UUID;


public class ModelSignUp {
    ModelReponsetoPresenterSignUp callback;
    Context context;
    Activity activity;
    private FirebaseAuth auth;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    FirebaseStorage storage;

    public ModelSignUp(ModelReponsetoPresenterSignUp callback, Context context) {
        this.callback = callback;
        this.context = context;
        this.activity = (Activity) context;
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }


    public void initBtnSignUp(final Bitmap imageBitmap, final String email, final String password, final String username, final ProgressBar progressBar) {
        if (TextUtils.isEmpty(email)) {
            callback.onBtnSignUp("Enter email address!");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            callback.onBtnSignUp("Enter password!");
            return;
        }

        if (password.length() < 6) {
            callback.onBtnSignUp("Password too short, enter minimum 6 characters!");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        final StorageReference filepath = storageReference.child("message_image").child(UUID.randomUUID().toString());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        filepath.putBytes(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(final Uri uri) {
                                auth.createUserWithEmailAndPassword(email, "123456")
                                        .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                callback.onBtnSignUp("createUserWithEmail:onComplete:" + task.isSuccessful());
                                                progressBar.setVisibility(View.GONE);
                                                if (!task.isSuccessful()) {
                                                    callback.onBtnSignUp("Authentication failed." + task.getException());
                                                    Log.d("Authentication: ", "" + task.getException());
                                                } else {
                                                    FirebaseUser firebaseUser = auth.getCurrentUser();
                                                    String userId = firebaseUser.getUid();
                                                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                                                    HashMap<String, Object> hashMap = new HashMap<>();
                                                    hashMap.put("id", userId);
                                                    hashMap.put("username", username);
                                                    hashMap.put("imageURL", uri.toString());
                                                    hashMap.put("online", "off");
                                                    hashMap.put("birthday","");
                                                    hashMap.put("gender","");
                                                    hashMap.put("imageBackground","default");
                                                    databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                initIntent(LoginActivity.class);
                                                            }
                                                        }
                                                    });

                                                }
                                            }
                                        });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                    }
                });
    }

    private void initIntent(Class c) {
        activity.startActivity(new Intent(context, c));
        activity.finish();
    }

    public void initOnIntent(int requestcode) {
        switch (requestcode) {
            case 0:
                activity.finish();
                break;
        }
    }

}

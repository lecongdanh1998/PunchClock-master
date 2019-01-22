package com.example.namtn.punchclock.Model.ModelChat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.namtn.punchclock.Adapter.AdapterChatRecyr;
import com.example.namtn.punchclock.Model.ModelInfoUser.ContructorInfoUser;
import com.example.namtn.punchclock.Model.ModelInfoUser.ContructorInfoUser2;
import com.example.namtn.punchclock.Notification.APIService;
import com.example.namtn.punchclock.Notification.Client;
import com.example.namtn.punchclock.Notification.Data;
import com.example.namtn.punchclock.Notification.MyReponse;
import com.example.namtn.punchclock.Notification.Sender;
import com.example.namtn.punchclock.Notification.Token;
import com.example.namtn.punchclock.R;
import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelChat {
    long time1, time2, time3;
    ModelReponsetoPresenterChat callback;
    Context context;
    Activity activity;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference, databaseReferenceChat, databaseReferenceChatUser, databaseReferenceid;
    String uid, date;
    Date date2, date3, date4;
    Intent intent;
    ArrayList<ContructorChat1> arrayList, arrayList1;
    AdapterChatRecyr adapterChat;
    SimpleDateFormat dateFormat;
    Date date1;
    String imageUrl, username, current_user_ref, chat_user_ref;
    private RecyclerView.Adapter mAdapter_detailsChat;
    private RecyclerView.LayoutManager mLayoutManager_detailsChat;
    FirebaseStorage storage;
    StorageReference storageReference;
    APIService apiService;
    boolean notify = false;
    ArrayList<ContructorInfoUser> arrayListUser;
    ArrayList<ContructorInfoUser2> arrayListUser2;

    public ModelChat(ModelReponsetoPresenterChat callback, Context context) {
        this.callback = callback;
        this.context = context;
        this.activity = (Activity) context;
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        intent = activity.getIntent();
        uid = intent.getStringExtra("userId");
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        databaseReferenceid = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        databaseReferenceChat = FirebaseDatabase.getInstance().getReference("Chats");
        arrayList = new ArrayList<>();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        arrayListUser = new ArrayList<>();
        arrayListUser2 = new ArrayList<>();
        arrayList1 = new ArrayList<>();
    }

    public void initData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ContructorInfoUser user = dataSnapshot.getValue(ContructorInfoUser.class);
                username = user.getUsername();
                imageUrl = user.getImageURL();
                arrayListUser.add(user);
                callback.onData(user.getId(), user.getImageURL(), user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReferenceid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ContructorInfoUser2 user = dataSnapshot.getValue(ContructorInfoUser2.class);
                username = user.getUsername();
                imageUrl = user.getImageURL();
                arrayListUser2.add(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void initSendMessage(final String message) {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = dateFormat.format(Calendar.getInstance().getTime());
        if (!message.equals("")) {
            //Chats
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("sender", firebaseUser.getUid());
            hashMap.put("receiver", uid);
            hashMap.put("type", "text");
            hashMap.put("message", message);
            hashMap.put("time", date);
            reference.child("Chats").push().setValue(hashMap);
            //Chats-User : From
            DatabaseReference referencefrom = FirebaseDatabase.getInstance().getReference();
            HashMap<String, Object> hashMapfrom = new HashMap<>();
            hashMapfrom.put("fromId", firebaseUser.getUid());
            hashMapfrom.put("message", message);
            hashMapfrom.put("type", "text");
            hashMapfrom.put("time", date);
            hashMapfrom.put("toId", uid);
            referencefrom.child("Chats-User").child(firebaseUser.getUid()).child(uid).push().setValue(hashMapfrom);
            //Chats-User : To
            DatabaseReference referenceto = FirebaseDatabase.getInstance().getReference();
            HashMap<String, Object> hashMapto = new HashMap<>();
            hashMapto.put("fromId", uid);
            hashMapto.put("message", message);
            hashMapto.put("type", "text");
            hashMapto.put("time", date);
            hashMapto.put("toId", firebaseUser.getUid());
            referenceto.child("Chats-User").child(uid).child(firebaseUser.getUid()).push().setValue(hashMapto);
            //LastChast-User : From
            DatabaseReference referenceLastChatfrom = FirebaseDatabase.getInstance().getReference();
            HashMap<String, Object> hashMapLastChatfrom = new HashMap<>();
            hashMapLastChatfrom.put("fromId", firebaseUser.getUid());
            hashMapLastChatfrom.put("message", message);
            hashMapLastChatfrom.put("type", "text");
            hashMapLastChatfrom.put("status", "from");
            hashMapLastChatfrom.put("time", date);
            hashMapLastChatfrom.put("toId", uid);
            referenceLastChatfrom.child("LastChats-User").child(firebaseUser.getUid()).child(uid).setValue(hashMapLastChatfrom);
            //LastChast-User : To
            DatabaseReference referenceLastChatto = FirebaseDatabase.getInstance().getReference();
            HashMap<String, Object> hashMapLastChatto = new HashMap<>();
            hashMapLastChatto.put("fromId", uid);
            hashMapLastChatto.put("message", message);
            hashMapLastChatto.put("type", "text");
            hashMapLastChatto.put("status", "to");
            hashMapLastChatto.put("time", date);
            hashMapLastChatto.put("toId", firebaseUser.getUid());
            referenceLastChatto.child("LastChats-User").child(uid).child(firebaseUser.getUid()).setValue(hashMapLastChatto);
            notify = true;
            DatabaseReference referenceNotification = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
            referenceNotification.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ContructorInfoUser user = dataSnapshot.getValue(ContructorInfoUser.class);
                    if (notify) {
                        sendNotification(uid, user.getUsername(), user.getImageURL(), message, "text");
                    }
                    notify = false;

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            callback.onSendMessage("Không được để trống");
        }

    }

    private void sendNotification(String receiver, final String username, String images, final String message, String status) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = reference.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DatabaseReference reference1 = reference.child(snapshot.getKey());
                    reference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Token token = dataSnapshot.getValue(Token.class);
                            Data data = null;
                            if (status.toString().equals("text")) {
                                data = new Data(firebaseUser.getUid(), images, username + " đã gửi bạn một tin nhắn", username,
                                        uid);
                            } else if (status.toString().equals("images")) {
                                data = new Data(firebaseUser.getUid(), images, username + " đã gửi bạn một hình ảnh", username,
                                        uid);
                            } else if (status.toString().equals("videos")) {
                                data = new Data(firebaseUser.getUid(), images, username + " đã gửi bạn một video", username,
                                        uid);
                            }
                            Sender sender = new Sender(data, token.getToken());
                            apiService.sendNotification(sender)
                                    .enqueue(new Callback<MyReponse>() {
                                        @Override
                                        public void onResponse(Call<MyReponse> call, Response<MyReponse> response) {
                                            if (response.code() == 200) {
                                                if (response.body().success != 1) {
                                                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<MyReponse> call, Throwable t) {
                                            Log.d("(Call<MyReponse> ", "" + t.getMessage());
                                        }
                                    });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("DatabaseError ", "" + databaseError.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DatabaseError ", "" + databaseError.getMessage());
            }
        });

    }


    public void initReadMessage(final RecyclerView reyclerview_message_list) {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        databaseReferenceChat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ContructorChat user = snapshot.getValue(ContructorChat.class);
                    if (user.getReceiver().equals(uid) && user.getSender().equals(firebaseUser.getUid())
                            || user.getReceiver().equals(firebaseUser.getUid()) && user.getSender().equals(uid)
                            ) {
                        arrayList.add(new ContructorChat1(
                                user.getSender(),
                                user.getReceiver(),
                                user.getType(),
                                user.getMessage(),
                                user.getTime(),
                                arrayListUser.get(0).getUsername(),
                                arrayListUser.get(0).getImageURL(),
                                arrayListUser2.get(0).getImageURL(),
                                "1", "1"
                        ));
                    }

                }
                arrayList1.clear();
                for (int i = 0; i < arrayList.size(); i++) {
                    if (i == 0) {
                        arrayList1.add(new ContructorChat1(
                                arrayList.get(i).getSender(),
                                arrayList.get(i).getReceiver(),
                                arrayList.get(i).getType(),
                                arrayList.get(i).getMessage(),
                                arrayList.get(i).getTime(),
                                arrayListUser.get(0).getUsername(),
                                arrayListUser.get(0).getImageURL(),
                                arrayListUser2.get(0).getImageURL(),
                                "1", "5"
                        ));
                    } else {
                        try {
                            Date date1 = dateFormat.parse(arrayList.get(i - 1).getTime());
                            Date date2 = dateFormat.parse(arrayList.get(i).getTime());
                            long millisecondLastSend1 = date1.getTime();
                            long millisecondLastSend2 = date2.getTime();
                            long minutes = (millisecondLastSend2 - millisecondLastSend1) / 60 / 1000;
                            if (minutes < 5) {
                                if (arrayList.get(i).getSender().toString().equals(arrayList.get(i - 1).getSender().toString()) || arrayList.get(i).getReceiver().toString().equals(arrayList.get(i - 1).getReceiver().toString())) {
                                    arrayList1.add(new ContructorChat1(
                                            arrayList.get(i).getSender(),
                                            arrayList.get(i).getReceiver(),
                                            arrayList.get(i).getType(),
                                            arrayList.get(i).getMessage(),
                                            arrayList.get(i).getTime(),
                                            arrayListUser.get(0).getUsername(),
                                            arrayListUser.get(0).getImageURL(),
                                            arrayListUser2.get(0).getImageURL(),
                                            "0", "0"
                                    ));
                                } else if (!arrayList.get(i).getSender().toString().equals(arrayList.get(i - 1).getSender().toString()) || !arrayList.get(i).getReceiver().toString().equals(arrayList.get(i - 1).getReceiver().toString())) {
                                    arrayList1.add(new ContructorChat1(
                                            arrayList.get(i).getSender(),
                                            arrayList.get(i).getReceiver(),
                                            arrayList.get(i).getType(),
                                            arrayList.get(i).getMessage(),
                                            arrayList.get(i).getTime(),
                                            arrayListUser.get(0).getUsername(),
                                            arrayListUser.get(0).getImageURL(),
                                            arrayListUser2.get(0).getImageURL(),
                                            "0", "1"
                                    ));
                                }
                            } else if (minutes >= 5) {
                                arrayList1.add(new ContructorChat1(
                                        arrayList.get(i).getSender(),
                                        arrayList.get(i).getReceiver(),
                                        arrayList.get(i).getType(),
                                        arrayList.get(i).getMessage(),
                                        arrayList.get(i).getTime(),
                                        arrayListUser.get(0).getUsername(),
                                        arrayListUser.get(0).getImageURL(),
                                        arrayListUser2.get(0).getImageURL(),
                                        "1", "1"
                                ));
                                Log.d("ArrayList3 ", "" + minutes);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }

                reyclerview_message_list.setHasFixedSize(true);
                mLayoutManager_detailsChat = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                reyclerview_message_list.setLayoutManager(mLayoutManager_detailsChat);
                adapterChat = new AdapterChatRecyr(arrayList1, context, activity);
                if (arrayList1.size() > 0) {
                    reyclerview_message_list.scrollToPosition(arrayList1.size() - 1);

                }
//                reyclerview_message_list.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
//
//                {
//                    @Override
//                    public void onLayoutChange(View v, int left, int top, int right, int bottom,
//                                               int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                        if (arrayList1.size() > 0) {
//                            reyclerview_message_list.smoothScrollToPosition(arrayList1.size() - 1);
//                        }
//                    }
//                });


                callback.onDataListview(adapterChat, arrayList1.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void initSendMessageImage(Bitmap bitmap) {
        final StorageReference filepath = storageReference.child("message_image").child(UUID.randomUUID().toString());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] data = baos.toByteArray();
        filepath.putBytes(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();
                                sendImage(uri);
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


    public void initSendMessageVideo(final Uri filePath) {
        final StorageReference videoRef = storageReference.child("message_videos").child(UUID.randomUUID().toString());
        videoRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(context, "Upload Thành công", Toast.LENGTH_SHORT).show();
                videoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();
                        sendVideo(uri);
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int progress = (int) ((100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                Toast.makeText(context, "Upload is " + progress + "% done", Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.v("Error: ", e.getMessage());
            }
        });

    }

    private void sendImage(Uri uri) {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = dateFormat.format(Calendar.getInstance().getTime());
        //Chat
        DatabaseReference user_message_push = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", firebaseUser.getUid());
        hashMap.put("receiver", uid);
        hashMap.put("type", "image");
        hashMap.put("message", uri.toString());
        hashMap.put("time", date);
        user_message_push.child("Chats").push().setValue(hashMap);
        //Chats-User : From
        DatabaseReference referencefrom = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMapfrom = new HashMap<>();
        hashMapfrom.put("fromId", firebaseUser.getUid());
        hashMapfrom.put("message", uri.toString());
        hashMapfrom.put("type", "image");
        hashMapfrom.put("time", date);
        hashMapfrom.put("toId", uid);
        referencefrom.child("Chats-User").child(firebaseUser.getUid()).child(uid).push().setValue(hashMapfrom);
        //Chats-User : To
        DatabaseReference referenceto = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMapto = new HashMap<>();
        hashMapto.put("fromId", uid);
        hashMapto.put("message", uri.toString());
        hashMapto.put("type", "image");
        hashMapto.put("time", date);
        hashMapto.put("toId", firebaseUser.getUid());
        referenceto.child("Chats-User").child(uid).child(firebaseUser.getUid()).push().setValue(hashMapto);
        //LastChast-User : From
        DatabaseReference referenceLastChatfrom = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMapLastChatfrom = new HashMap<>();
        hashMapLastChatfrom.put("fromId", firebaseUser.getUid());
        hashMapLastChatfrom.put("message", uri.toString());
        hashMapLastChatfrom.put("type", "image");
        hashMapLastChatfrom.put("status", "from");
        hashMapLastChatfrom.put("time", date);
        hashMapLastChatfrom.put("toId", uid);
        referenceLastChatfrom.child("LastChats-User").child(firebaseUser.getUid()).child(uid).setValue(hashMapLastChatfrom);
        //LastChast-User : To
        DatabaseReference referenceLastChatto = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMapLastChatto = new HashMap<>();
        hashMapLastChatto.put("fromId", uid);
        hashMapLastChatto.put("message", uri.toString());
        hashMapLastChatto.put("type", "image");
        hashMapLastChatto.put("status", "to");
        hashMapLastChatto.put("time", date);
        hashMapLastChatto.put("toId", firebaseUser.getUid());
        referenceLastChatto.child("LastChats-User").child(uid).child(firebaseUser.getUid()).setValue(hashMapLastChatto);
        notify = true;
        DatabaseReference referenceNotification = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        referenceNotification.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ContructorInfoUser user = dataSnapshot.getValue(ContructorInfoUser.class);
                if (notify) {
                    sendNotification(uid, user.getUsername(), user.getImageURL(), uri.toString(), "images");
                }
                notify = false;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendVideo(Uri uri) {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = dateFormat.format(Calendar.getInstance().getTime());
        //Chats
        DatabaseReference user_message_push = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", firebaseUser.getUid());
        hashMap.put("receiver", uid);
        hashMap.put("type", "video");
        hashMap.put("message", uri.toString());
        hashMap.put("time", date);
        user_message_push.child("Chats").push().setValue(hashMap);
        //Chats-User : From
        DatabaseReference referencefrom = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMapfrom = new HashMap<>();
        hashMapfrom.put("fromId", firebaseUser.getUid());
        hashMapfrom.put("message", uri.toString());
        hashMapfrom.put("type", "video");
        hashMapfrom.put("time", date);
        hashMapfrom.put("toId", uid);
        referencefrom.child("Chats-User").child(firebaseUser.getUid()).child(uid).push().setValue(hashMapfrom);
        //Chats-User : To
        DatabaseReference referenceto = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMapto = new HashMap<>();
        hashMapto.put("fromId", uid);
        hashMapto.put("message", uri.toString());
        hashMapto.put("type", "video");
        hashMapto.put("time", date);
        hashMapto.put("toId", firebaseUser.getUid());
        referenceto.child("Chats-User").child(uid).child(firebaseUser.getUid()).push().setValue(hashMapto);
        //LastChast-User : From
        DatabaseReference referenceLastChatfrom = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMapLastChatfrom = new HashMap<>();
        hashMapLastChatfrom.put("fromId", firebaseUser.getUid());
        hashMapLastChatfrom.put("message", uri.toString());
        hashMapLastChatfrom.put("type", "video");
        hashMapLastChatfrom.put("status", "from");
        hashMapLastChatfrom.put("time", date);
        hashMapLastChatfrom.put("toId", uid);
        referenceLastChatfrom.child("LastChats-User").child(firebaseUser.getUid()).child(uid).setValue(hashMapLastChatfrom);
        //LastChast-User : To
        DatabaseReference referenceLastChatto = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMapLastChatto = new HashMap<>();
        hashMapLastChatto.put("fromId", uid);
        hashMapLastChatto.put("message", uri.toString());
        hashMapLastChatto.put("type", "video");
        hashMapLastChatto.put("status", "to");
        hashMapLastChatto.put("time", date);
        hashMapLastChatto.put("toId", firebaseUser.getUid());
        referenceLastChatto.child("LastChats-User").child(uid).child(firebaseUser.getUid()).setValue(hashMapLastChatto);
        notify = true;
        DatabaseReference referenceNotification = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        referenceNotification.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ContructorInfoUser user = dataSnapshot.getValue(ContructorInfoUser.class);
                if (notify) {
                    sendNotification(uid, user.getUsername(), user.getImageURL(), uri.toString(), "videos");
                }
                notify = false;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

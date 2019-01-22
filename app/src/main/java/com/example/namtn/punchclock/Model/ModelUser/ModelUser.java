package com.example.namtn.punchclock.Model.ModelUser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.namtn.punchclock.Model.ModelInfoUser.ContructorInfoUser;
import com.example.namtn.punchclock.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


public class ModelUser {
    ModelReponsetoPresenterUser callback;
    Context context;
    Activity activity;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    Dialog dialog;
    StorageReference storageReference;
    FirebaseStorage storage;
    ArrayList<ContructorInfoUser> arrayList;
    ContructorInfoUser user;
    private ProgressDialog progressDialog;
    String birthday = "";
    String gender = "";
    String myFormat = "dd/MM/yyyy";
    Date date;
    int day1, month1, year1;

    public ModelUser(ModelReponsetoPresenterUser callback, Context context) {
        this.callback = callback;
        this.context = context;
        this.activity = (Activity) context;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        progressDialog = new ProgressDialog(context);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    private void setContentDialog(String title, String messager) {
        progressDialog.setTitle(title);
        progressDialog.setMessage(messager);
        progressDialog.setCancelable(false);
    }

    public void initData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(ContructorInfoUser.class);
                callback.onData(user.getId(), firebaseUser.getEmail(), user.getImageURL(), user.getUsername(), user.getBirthday(), user.getGender(), user.getImageBackground());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void initDataEdit(final int requestcode) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView txt_name = dialog.findViewById(R.id.txt_name_edit);
        final EditText ext_username = dialog.findViewById(R.id.ext_username);
        final EditText ext_password = dialog.findViewById(R.id.ext_password);
        final EditText ext_passwordnew = dialog.findViewById(R.id.ext_passwordnew);
        final EditText ext_passwordnewchange = dialog.findViewById(R.id.ext_passwordnewchange);
        final Calendar myCalendar = Calendar.getInstance();
        RadioGroup radioGroupGender = dialog.findViewById(R.id.radioGroupGender);
        RadioButton radioButton_Nam = dialog.findViewById(R.id.radioButton_nam);
        RadioButton radioButton_Nu = dialog.findViewById(R.id.radioButton_nu);
        RelativeLayout relativeLayout_password = dialog.findViewById(R.id.relative_password);
        DatePicker datePicker = dialog.findViewById(R.id.DatePicker);
        final Button btn_submit = dialog.findViewById(R.id.btn_submit);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        dialog.setCancelable(false);
        btn_submit.setEnabled(false);
        btn_submit.setAlpha(0.5f);
        switch (requestcode) {
            case 1:
                txt_name.setText("Username");
                radioGroupGender.setVisibility(View.GONE);
                ext_username.setText(user.getUsername());
                ext_username.setVisibility(View.VISIBLE);
                relativeLayout_password.setVisibility(View.GONE);
                datePicker.setVisibility(View.GONE);
                ext_username.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().equals("")) {
                            btn_submit.setEnabled(false);
                            btn_submit.setAlpha(0.5f);
                        } else if (s.toString().equals(user.getUsername())) {
                            btn_submit.setEnabled(false);
                            btn_submit.setAlpha(0.5f);
                        } else {
                            btn_submit.setEnabled(true);
                            btn_submit.setAlpha(1);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
            case 2:
                radioGroupGender.setVisibility(View.GONE);
                txt_name.setText("Birthday");
                ext_username.setVisibility(View.GONE);
                datePicker.setVisibility(View.VISIBLE);
                relativeLayout_password.setVisibility(View.GONE);
                SimpleDateFormat fomat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat yearNam = new SimpleDateFormat("yyyy");
                SimpleDateFormat monthThang = new SimpleDateFormat("MM");
                SimpleDateFormat dayNgay = new SimpleDateFormat("dd");
                try {
                    if (!user.getBirthday().toString().trim().equals("")) {
                        date = fomat.parse(user.getBirthday());
                        day1 = Integer.valueOf(dayNgay.format(date.getTime()));
                        month1 = Integer.valueOf(monthThang.format(date.getTime())) - 1;
                        year1 = Integer.valueOf(yearNam.format(date.getTime()));
                    } else {
                        btn_submit.setEnabled(true);
                        btn_submit.setAlpha(1);
                        day1 = myCalendar.get(Calendar.DAY_OF_MONTH);
                        month1 = myCalendar.get(Calendar.MONTH);
                        year1 = myCalendar.get(Calendar.YEAR);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                datePicker.init(year1, month1, day1, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        birthday = sdf.format(myCalendar.getTime());
                        if (!user.getBirthday().toString().trim().equals(birthday)) {
                            btn_submit.setEnabled(true);
                            btn_submit.setAlpha(1);
                        } else {
                            btn_submit.setEnabled(false);
                            btn_submit.setAlpha((float) 0.5);
                        }
                        //In which you need put here
                    }
                });

                break;
            case 3:
                txt_name.setText("Gender");
                ext_username.setVisibility(View.GONE);
                datePicker.setVisibility(View.GONE);
                relativeLayout_password.setVisibility(View.GONE);
                radioGroupGender.setVisibility(View.VISIBLE);
                if (user.getGender().toString().equals("")) {
                    radioButton_Nam.setChecked(true);
                } else if (user.getGender().toString().equals("Nam")) {
                    radioButton_Nam.setChecked(true);
                } else if (user.getGender().toString().equals("Nữ")) {
                    radioButton_Nu.setChecked(true);
                }
                if (radioButton_Nam.isChecked()) {
                    gender = "Nam";
                    if (user.getGender().equals(gender)) {
                        btn_submit.setEnabled(false);
                        btn_submit.setAlpha((float) 0.5);
                    } else {
                        btn_submit.setEnabled(true);
                        btn_submit.setAlpha(1);
                    }
                } else if (radioButton_Nu.isChecked()) {
                    gender = "Nữ";
                    if (user.getGender().equals(gender)) {
                        btn_submit.setEnabled(false);
                        btn_submit.setAlpha((float) 0.5);
                    } else {
                        btn_submit.setEnabled(true);
                        btn_submit.setAlpha(1);
                    }
                }
                radioButton_Nam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            gender = "Nam";
                            if (user.getGender().equals(gender)) {
                                btn_submit.setEnabled(false);
                                btn_submit.setAlpha((float) 0.5);
                            } else {
                                btn_submit.setEnabled(true);
                                btn_submit.setAlpha(1);
                            }
                        }
                    }
                });
                radioButton_Nu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            gender = "Nữ";
                            if (user.getGender().equals(gender)) {
                                btn_submit.setEnabled(false);
                                btn_submit.setAlpha((float) 0.5);
                            } else {
                                btn_submit.setEnabled(true);
                                btn_submit.setAlpha(1);
                            }
                        }
                    }
                });
                break;
            case 4:
                txt_name.setText("Password");
                radioGroupGender.setVisibility(View.GONE);
                ext_username.setVisibility(View.GONE);
                datePicker.setVisibility(View.GONE);
                relativeLayout_password.setVisibility(View.VISIBLE);
                ext_passwordnew.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().equals("")
                                || ext_password.getText().toString().equals("")
                                || ext_passwordnewchange.getText().toString().equals("")) {
                            btn_submit.setEnabled(false);
                            btn_submit.setAlpha(0.5f);
                        } else if (!s.toString().equals("")
                                && !ext_password.getText().toString().equals("")
                                && !ext_passwordnewchange.getText().toString().equals("")) {
                            btn_submit.setEnabled(true);
                            btn_submit.setAlpha(1);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                ext_passwordnewchange.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().equals("")
                                || ext_password.getText().toString().equals("")
                                || ext_passwordnew.getText().toString().equals("")) {
                            btn_submit.setEnabled(false);
                            btn_submit.setAlpha(0.5f);
                        } else if (!s.toString().equals("")
                                && !ext_password.getText().toString().equals("")
                                && !ext_passwordnew.getText().toString().equals("")) {
                            btn_submit.setEnabled(true);
                            btn_submit.setAlpha(1);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                ext_password.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().equals("")
                                || ext_passwordnew.getText().toString().equals("")
                                || ext_passwordnewchange.getText().toString().equals("")) {
                            btn_submit.setEnabled(false);
                            btn_submit.setAlpha(0.5f);
                        } else if (!s.toString().equals("")
                                && !ext_passwordnew.getText().toString().equals("")
                                && !ext_passwordnewchange.getText().toString().equals("")) {
                            btn_submit.setEnabled(true);
                            btn_submit.setAlpha(1);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;

        }
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (requestcode) {
                    case 1:
                        if (!ext_username.getText().toString().equals("")) {
                            setContentDialog("Cập nhật", "Vui lòng chờ...");
                            progressDialog.show();
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                            reference.child("Users").child(firebaseUser.getUid()).child("username").setValue(ext_username.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()) {
                                        progressDialog.cancel();
                                        Toast.makeText(context, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        progressDialog.cancel();
                                        dialog.cancel();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(context, "Vui lòng không để trống", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case 2:
                        if (birthday.equals("")) {
                            day1 = myCalendar.get(Calendar.DAY_OF_MONTH);
                            month1 = myCalendar.get(Calendar.MONTH);
                            year1 = myCalendar.get(Calendar.YEAR);
                            birthday = day1 + "/" + month1 + 1 + "/" + year1;
                        }
                        setContentDialog("Cập nhật", "Vui lòng chờ...");
                        progressDialog.show();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                        reference.child("Users").child(firebaseUser.getUid()).child("birthday").setValue(birthday).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {
                                    progressDialog.cancel();
                                    Toast.makeText(context, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    progressDialog.cancel();
                                    dialog.cancel();
                                }
                            }
                        });

                        break;
                    case 3:
                        setContentDialog("Cập nhật", "Vui lòng chờ...");
                        progressDialog.show();
                        DatabaseReference referenceGender = FirebaseDatabase.getInstance().getReference();
                        referenceGender.child("Users").child(firebaseUser.getUid()).child("gender").setValue(gender).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {
                                    progressDialog.cancel();
                                    Toast.makeText(context, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    progressDialog.cancel();
                                    dialog.cancel();
                                }
                            }
                        });

                        break;
                    case 4:
                        if (ext_passwordnew.getText().toString().equals(ext_passwordnewchange.getText().toString().trim())) {
                            if (ext_passwordnew.getText().toString().length() < 6 || ext_passwordnewchange.getText().toString().length() < 6) {
                                Toast.makeText(context, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            setContentDialog("Cập nhật", "Vui lòng chờ...");
                            progressDialog.show();
                            AuthCredential credential = EmailAuthProvider
                                    .getCredential(firebaseUser.getEmail(), ext_password.getText().toString().trim());
                            firebaseUser.reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                firebaseUser.updatePassword(ext_passwordnew.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            progressDialog.cancel();
                                                            dialog.cancel();
                                                            Toast.makeText(context, "Password updated", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            progressDialog.cancel();
                                                            Toast.makeText(context, "Error password not updated", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            } else {
                                                progressDialog.cancel();
                                                Toast.makeText(context, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(context, "Mật khẩu mới xác nhận sai", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });


        dialog.show();
    }

    @SuppressLint("ResourceType")
    public void initDataEditImage(final int requestcode, final Bitmap imageBitmap) {
        final Dialog dialogImage = new Dialog(context);
        dialogImage.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogImage.setContentView(R.layout.dialog_image_edit);
        dialogImage.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView txt_name = dialogImage.findViewById(R.id.txt_name_edit);
        CircleImageView imageViewAva = dialogImage.findViewById(R.id.imageViewAva);
        ImageButton btn_camera = dialogImage.findViewById(R.id.btn_camera);
        ImageButton btn_gallery = dialogImage.findViewById(R.id.btn_gallery);
        final Button btn_submit = dialogImage.findViewById(R.id.btn_submit);
        RelativeLayout relativeLayout_Background = dialogImage.findViewById(R.id.relativeLayout_Background);
        RelativeLayout relativeLayout_Avatar = dialogImage.findViewById(R.id.relativeLayout_Avatar);
        ImageView imageViewBackground = dialogImage.findViewById(R.id.imageViewBackground);
        Button btn_cancel = dialogImage.findViewById(R.id.btn_cancel);
        btn_submit.setEnabled(false);
        btn_submit.setAlpha((float) 0.5);
        switch (requestcode) {
            case 1:
                txt_name.setText("Avatar");
                relativeLayout_Avatar.setVisibility(View.VISIBLE);
                relativeLayout_Background.setVisibility(View.GONE);
                if (imageBitmap == null) {
                    Picasso.get()
                            .load(user.getImageURL())
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .into(imageViewAva);
                } else {
                    imageViewAva.setImageBitmap(imageBitmap);
                    btn_submit.setEnabled(true);
                    btn_submit.setAlpha(1);
                }
                break;
            case 2:
                txt_name.setText("Background");
                relativeLayout_Avatar.setVisibility(View.GONE);
                relativeLayout_Background.setVisibility(View.VISIBLE);
                if (imageBitmap == null) {
                    Picasso.get()
                            .load(user.getImageBackground())
                            .placeholder(R.color.color_background)
                            .error(R.color.color_background)
                            .into(imageViewBackground);
                } else {
                    imageViewBackground.setImageBitmap(imageBitmap);
                    btn_submit.setEnabled(true);
                    btn_submit.setAlpha(1);
                }
                break;
        }


        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (requestcode) {
                    case 1:
                        callback.onDataEditImage(requestcode, 1, dialogImage);
                        break;
                    case 2:
                        callback.onDataEditImage(requestcode, 1, dialogImage);
                        break;
                }
            }
        });
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (requestcode) {
                    case 1:
                        callback.onDataEditImage(requestcode, 2, dialogImage);
                        break;
                    case 2:
                        callback.onDataEditImage(requestcode, 2, dialogImage);
                        break;
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogImage.cancel();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (requestcode) {
                    case 1:
                        setContentDialog("Cập nhật", "Vui lòng chờ...");
                        progressDialog.show();
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
                                                DatabaseReference referenceImage = FirebaseDatabase.getInstance().getReference();
                                                referenceImage.child("Users").child(firebaseUser.getUid()).child("imageURL").setValue(uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (!task.isSuccessful()) {
                                                            progressDialog.cancel();
                                                            Toast.makeText(context, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            progressDialog.cancel();
                                                            dialogImage.cancel();
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
                                        progressDialog.cancel();
                                        Toast.makeText(context, "Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                                .getTotalByteCount());
                                        Toast.makeText(context, "Upload is " + progress + "% done", Toast.LENGTH_LONG).show();
                                    }
                                });
                        break;
                    case 2:
                        setContentDialog("Cập nhật", "Vui lòng chờ...");
                        progressDialog.show();
                        final StorageReference filepath1 = storageReference.child("message_image").child(UUID.randomUUID().toString());
                        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos1);
                        byte[] data1 = baos1.toByteArray();
                        filepath1.putBytes(data1)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        filepath1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(final Uri uri) {
                                                DatabaseReference referenceImage = FirebaseDatabase.getInstance().getReference();
                                                referenceImage.child("Users").child(firebaseUser.getUid()).child("imageBackground").setValue(uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (!task.isSuccessful()) {
                                                            progressDialog.cancel();
                                                            Toast.makeText(context, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            progressDialog.cancel();
                                                            dialogImage.cancel();
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
                                        progressDialog.cancel();
                                        Toast.makeText(context, "Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                                .getTotalByteCount());
                                        Toast.makeText(context, "Upload is " + progress + "% done", Toast.LENGTH_LONG).show();
                                    }
                                });
                        break;
                }

            }
        });

        dialogImage.setCancelable(false);
        dialogImage.show();
    }


}

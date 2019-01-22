package com.example.namtn.punchclock.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.namtn.punchclock.Model.ModelChat.ContructorChat1;
import com.example.namtn.punchclock.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterChatRecyr extends RecyclerView.Adapter {
    ArrayList<ContructorChat1> arrayList;
    Context context;
    LayoutInflater inflater;
    FirebaseUser firebaseUser;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    SimpleDateFormat dateFormat, timeFormat, timeFormat1;
    Date date, date1;
    Activity activity;
    long millisecondToday, millisecondLastSend;
    View viewactivity;

    public AdapterChatRecyr(ArrayList<ContructorChat1> arrayList, Context context, Activity activity) {
        this.arrayList = arrayList;
        this.context = context;
        this.activity = activity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewactivity = activity.findViewById(android.R.id.content);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;

        if (i == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_message_sent, viewGroup, false);
            return new SentMessageHolder(view);
        } else if (i == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_message_received, viewGroup, false);
            return new RecyclerViewHolderChatUser(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ContructorChat1 message = (ContructorChat1) arrayList.get(i);
        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) viewHolder).bind(message);
                ((SentMessageHolder) viewHolder).setItemClickListener(new ItemClick() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        if (isLongClick)
                            Toast.makeText(context, "Không có chức năng", Toast.LENGTH_SHORT).show();
                        else {
                            if (viewactivity != null) {
                                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                        }

                    }
                });
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((RecyclerViewHolderChatUser) viewHolder).bind(message);
                ((RecyclerViewHolderChatUser) viewHolder).setItemClickListener(new ItemClick() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        if (isLongClick)
                            Toast.makeText(context, "Không có chức năng", Toast.LENGTH_SHORT).show();
                        else {
                            if (viewactivity != null) {
                                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                        }

                    }
                });
                break;
        }
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ContructorChat1 message = (ContructorChat1) arrayList.get(position);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (message.getSender().equals(firebaseUser.getUid())) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    class RecyclerViewHolderChatUser extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener // Implement 2 sự kiện onClick và onLongClick
    {
        CircleImageView profileImage;
        TextView messageText, timeText, nameText;
        ImageView imageView;
        VideoView videoView;
        RelativeLayout relativeLayoutAvatar;
        LinearLayout linearLayoutVideo;
        private ItemClick itemClickListener;

        public RecyclerViewHolderChatUser(View itemView) {
            super(itemView);
            relativeLayoutAvatar = itemView.findViewById(R.id.relativeLayoutAvatar);
            imageView = itemView.findViewById(R.id.imageLeft);
            linearLayoutVideo = itemView.findViewById(R.id.linearLayoutVideo);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);
            profileImage = itemView.findViewById(R.id.image_message_profile);
            videoView = itemView.findViewById(R.id.VideoLeft);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClick itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false); // Gọi interface , false là vì đây là onClick
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true); // Gọi interface , true là vì đây là onLongClick
            return true;
        }

        void bind(ContructorChat1 message) {
            messageText.setText(message.getMessage());
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            timeFormat = new SimpleDateFormat("EEE, MMM dd, HH:mm");
            try {
                date = dateFormat.parse(message.getTime());
                timeText.setText(timeFormat.format(date.getTime()));
                if (message.getStatus().toString().equals("0")) {
                    timeText.setVisibility(View.GONE);
                } else {
                    timeText.setVisibility(View.VISIBLE);
                }
                if (message.getStatus1().toString().equals("0")) {
                    relativeLayoutAvatar.setVisibility(View.INVISIBLE);
                } else {
                    relativeLayoutAvatar.setVisibility(View.VISIBLE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (message.getType().toString().equals("text")) {
                imageView.setVisibility(View.GONE);
                messageText.setVisibility(View.VISIBLE);
                linearLayoutVideo.setVisibility(View.GONE);
                messageText.setText(message.getMessage());
            } else if (message.getType().toString().equals("image")) {
                messageText.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                linearLayoutVideo.setVisibility(View.GONE);
                Picasso.get().load(message.getMessage()).placeholder(R.drawable.ic_launcher_background).into(imageView);
            } else if (message.getType().toString().equals("video")) {
                imageView.setVisibility(View.GONE);
                messageText.setVisibility(View.GONE);
                linearLayoutVideo.setVisibility(View.VISIBLE);
                videoView.setVideoPath(message.getMessage());
                videoView.start();
            }


            // Format the stored timestamp into a readable String using method.
            nameText.setText(message.getUsername());
            Picasso.get()
                    .load(message.getImageURL())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(profileImage);
            // Insert the profile image from the URL into the ImageView.
        }


        //Tạo setter cho biến itemClickListenenr
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView messageText, timeText;
        ImageView imageView;
        VideoView videoView;
        CircleImageView image_message_profile;
        LinearLayout linearLayoutVideo;
        RelativeLayout relativeLayoutAvatar;
        private ItemClick itemClickListener;

        SentMessageHolder(View itemView) {
            super(itemView);
            relativeLayoutAvatar = itemView.findViewById(R.id.relativeLayoutAvatar);
            linearLayoutVideo = itemView.findViewById(R.id.linearLayoutVideo);
            imageView = itemView.findViewById(R.id.images);
            image_message_profile = itemView.findViewById(R.id.image_message_profile);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            videoView = itemView.findViewById(R.id.videoRight);
            itemView.setOnClickListener(this); // Mấu chốt ở đây , set sự kiên onClick cho View
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClick itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false); // Gọi interface , false là vì đây là onClick
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true); // Gọi interface , true là vì đây là onLongClick
            return true;
        }

        void bind(ContructorChat1 message) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            timeFormat = new SimpleDateFormat("EEE, MMM dd, HH:mm");
            try {
                date = dateFormat.parse(message.getTime());
                timeText.setText(timeFormat.format(date.getTime()));
                if (message.getStatus().toString().equals("0")) {
                    timeText.setVisibility(View.GONE);
                } else {
                    timeText.setVisibility(View.VISIBLE);
                }
                if (message.getStatus1().toString().equals("0")) {
                    relativeLayoutAvatar.setVisibility(View.INVISIBLE);
                } else {
                    relativeLayoutAvatar.setVisibility(View.VISIBLE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (message.getType().toString().equals("text")) {
                imageView.setVisibility(View.GONE);
                messageText.setVisibility(View.VISIBLE);
                linearLayoutVideo.setVisibility(View.GONE);
                messageText.setText(message.getMessage());
            } else if (message.getType().toString().equals("image")) {
                messageText.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                linearLayoutVideo.setVisibility(View.GONE);
                Picasso.get().load(message.getMessage()).placeholder(R.drawable.ic_launcher_background).into(imageView);
            } else if (message.getType().toString().equals("video")) {
                messageText.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                linearLayoutVideo.setVisibility(View.VISIBLE);
                videoView.setVideoPath(message.getMessage());
                videoView.start();
            }
            Picasso.get()
                    .load(message.getImageUrlUid())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(image_message_profile);
            // Format the stored timestamp into a readable String using method.


        }
    }

}



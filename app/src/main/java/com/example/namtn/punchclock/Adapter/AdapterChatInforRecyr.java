package com.example.namtn.punchclock.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.namtn.punchclock.Activity.ChatMessage.ChatMessageActivity;
import com.example.namtn.punchclock.Adapter.Attendance.ItemClick;
import com.example.namtn.punchclock.Model.ModelChat.ContructorChatUser2;
import com.example.namtn.punchclock.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
public class AdapterChatInforRecyr extends RecyclerView.Adapter<RecyclerViewHolderChat> {
    ArrayList<ContructorChatUser2> arrayList;
    Context context;
    LayoutInflater inflater;
    SimpleDateFormat dateFormat, timeFormat, dayFormat,monthFormat;
    Date date, date2;
    long millisecondToday, millisecondLastSend;

    public AdapterChatInforRecyr(ArrayList<ContructorChatUser2> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RecyclerViewHolderChat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_rycechat, viewGroup, false);
        RecyclerViewHolderChat vh = new RecyclerViewHolderChat(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolderChat recyclerViewHolder, int i) {
        final ContructorChatUser2 user = arrayList.get(i);
        recyclerViewHolder.txt_name.setText(user.getUsername());
        if (user.getOnline().toString().equals("on")) {
            recyclerViewHolder.relativeLayout_on.setVisibility(View.VISIBLE);
            recyclerViewHolder.relativeLayout_off.setVisibility(View.GONE);
        } else if (user.getOnline().toString().equals("off")) {
            recyclerViewHolder.relativeLayout_on.setVisibility(View.GONE);
            recyclerViewHolder.relativeLayout_off.setVisibility(View.VISIBLE);
        }

        if (user.getStatus().toString().equals("from")) {
            if (user.getType().toString().equals("text")) {
                recyclerViewHolder.txt_Content.setText("Bạn: " + user.getMessage());
            } else if (user.getType().toString().equals("image")) {
                recyclerViewHolder.txt_Content.setText("Bạn đã gửi một ảnh");
            } else if (user.getType().toString().equals("video")) {
                recyclerViewHolder.txt_Content.setText("Bạn đã gửi một video");
            }
        } else if (user.getStatus().toString().equals("to")) {
            if (user.getType().toString().equals("text")) {
                recyclerViewHolder.txt_Content.setText(user.getMessage());
            } else if (user.getType().toString().equals("image")) {
                recyclerViewHolder.txt_Content.setText(user.getUsername() + " đã gửi hình ảnh cho bạn");
            } else if (user.getType().toString().equals("video")) {
                recyclerViewHolder.txt_Content.setText(user.getUsername() + " đã gửi một video");
            }
        }
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timeFormat = new SimpleDateFormat("HH:mm");
        dayFormat = new SimpleDateFormat("dd");
        monthFormat = new SimpleDateFormat("MM");
        try {
            date = dateFormat.parse(user.getTime());
            millisecondLastSend = date.getTime();
            millisecondToday = Calendar.getInstance().getTimeInMillis();
            long minutes = (millisecondToday - millisecondLastSend) / 60 / 1000;
            long hous = minutes / 60;
            long day = hous / 24;
            if (hous <= 24) {
                recyclerViewHolder.txt_time.setText(timeFormat.format(date.getTime()));
            } else if (hous > 24) {
                if(day > 30){
                    recyclerViewHolder.txt_time.setText(monthFormat.format(date.getTime()));
                }else if(day <= 30){
                    recyclerViewHolder.txt_time.setText(dayFormat.format(date.getTime()));
                }
            }


//            if (minutes < 59) {
//                recyclerViewHolder.txt_time.setText(minutes + " phút trước");
//            }
//            if (hous <= 24 && minutes >= 60) {
//                recyclerViewHolder.txt_time.setText(hous + " giờ trước");
//            }
//            if (day < 5 && hous > 24) {
//                recyclerViewHolder.txt_time.setText(day + " ngày trước");
//            }


            /// 24 / 60 / 60 / 1000
//            recyclerViewHolder.txt_time.setText(timeFormat.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Picasso.get()
                .load(user.getImageURL())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(recyclerViewHolder.imagesChanel);
        recyclerViewHolder.setItemClickListener(new ItemClick() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick)
                    Toast.makeText(context, "Không có chức năng", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(context, ChatMessageActivity.class);
                    intent.putExtra("userId", arrayList.get(position).getToId());
                    context.startActivity(intent);
//                    Intent
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

class RecyclerViewHolderChat extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener // Implement 2 sự kiện onClick và onLongClick
{
    RelativeLayout relativeLayout_off, relativeLayout_on;
    CircleImageView imagesChanel;
    TextView txt_name, txt_Content, txt_time;
    private ItemClick itemClickListener; // Khai báo interface

    public RecyclerViewHolderChat(View itemView) {
        super(itemView);
        relativeLayout_off = itemView.findViewById(R.id.relativeLayout_off);
        relativeLayout_on = itemView.findViewById(R.id.relativeLayout_on);
        imagesChanel = itemView.findViewById(R.id.imageViewAva);
        txt_name = itemView.findViewById(R.id.txt_name);
        txt_time = itemView.findViewById(R.id.txt_time);
        txt_Content = itemView.findViewById(R.id.txt_content);
        itemView.setOnClickListener(this); // Mấu chốt ở đây , set sự kiên onClick cho View
        itemView.setOnLongClickListener(this); // Mấu chốt ở đây , set sự kiên onLongClick cho View
    }

    //Tạo setter cho biến itemClickListenenr
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
}

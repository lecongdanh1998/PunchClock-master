package com.example.namtn.punchclock.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.namtn.punchclock.Activity.ChatMessage.ChatMessageActivity;
import com.example.namtn.punchclock.Adapter.Attendance.ItemClick;
import com.example.namtn.punchclock.Model.ModelInfoUser.ContructorInfoUser;
import com.example.namtn.punchclock.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterInforActivateRecyr extends RecyclerView.Adapter<RecyclerViewActivateHolder> {
    ArrayList<ContructorInfoUser> arrayList;
    Context context;
    LayoutInflater inflater;

    public AdapterInforActivateRecyr(ArrayList<ContructorInfoUser> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RecyclerViewActivateHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_ryce_activate, viewGroup, false);
        RecyclerViewActivateHolder vh = new RecyclerViewActivateHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewActivateHolder recyclerViewHolder, int i) {
        final ContructorInfoUser user = arrayList.get(i);
        if (user.getOnline().toString().equals("on")) {
            recyclerViewHolder.txt_name.setText(user.getUsername());
            Log.d("Image", "" + user.getImageURL());
            Picasso.get()
                    .load(user.getImageURL())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(recyclerViewHolder.imagesChanel);
        }
        recyclerViewHolder.setItemClickListener(new ItemClick() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick)
                    Toast.makeText(context, "Không có chức năng", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(context, ChatMessageActivity.class);
                    intent.putExtra("userId", arrayList.get(position).getId());
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

class RecyclerViewActivateHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener // Implement 2 sự kiện onClick và onLongClick
{
    CircleImageView imagesChanel;
    TextView txt_name;
    private ItemClick itemClickListener; // Khai báo interface

    public RecyclerViewActivateHolder(View itemView) {
        super(itemView);
        imagesChanel = itemView.findViewById(R.id.imageViewAva);
        txt_name = itemView.findViewById(R.id.txt_name);
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

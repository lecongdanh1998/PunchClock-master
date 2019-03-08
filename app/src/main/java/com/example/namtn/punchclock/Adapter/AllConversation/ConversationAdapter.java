package com.example.namtn.punchclock.Adapter.AllConversation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.namtn.punchclock.ModelView.Conversation;
import com.example.namtn.punchclock.R;
import com.example.namtn.punchclock.Util.ConvertSize;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ConversationAdapter extends BaseAdapter {

    private Context mContext;
    private List<Conversation> mListConversation;
    private LayoutInflater inflater;
    private ConvertSize mConvertSize;

    public ConversationAdapter(Context mContext, List<Conversation> mListConversation) {
        this.mContext = mContext;
        this.mListConversation = mListConversation;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mConvertSize = new ConvertSize(mContext);
    }

    @Override
    public int getCount() {
        return mListConversation.size();
    }

    @Override
    public Object getItem(int position) {
        return mListConversation.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView mTextViewName;
        ImageView mImageViewAvatar;
        LinearLayout mViewContents;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_item_conversation, null);
            mViewHolder.mTextViewName = convertView.findViewById(R.id.txt_name_conversation);
            mViewHolder.mImageViewAvatar = convertView.findViewById(R.id.img_avatar_conversation);
            mViewHolder.mViewContents = convertView.findViewById(R.id.view_content_conversation);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        Conversation mConversation = mListConversation.get(position);
        setDataConversation(mViewHolder, convertView, mConversation);
        return convertView;
    }

    private void setDataConversation(ViewHolder mViewHolder, View convertView, Conversation mConversation) {
        mViewHolder.mTextViewName.setText(mConversation.getAuthor());
        mViewHolder.mViewContents.removeAllViews();
        TextView mTextViewCaption;
        ImageView mImageViewContents;
        Activity mActivity = (Activity) mContext;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        /*
         * width / height
         * 2 / 3
         * (width x 3 / 2) - mConvertSize.convertToPx(16dp) - mConvertSize.convertToPx(toolbar) = height
         * because root layout set padding 16px
         * then we increase 16px, but layout param set height px.
         * => height(px)
         * */
        Log.e("Width", "" + width);
        Log.e("height", "" + height);
        if (!mConversation.getCaption().isEmpty()) {
            mTextViewCaption = new TextView(mContext);
            mTextViewCaption.setText(mConversation.getCaption());
            mTextViewCaption.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    mContext.getResources().getDimension(R.dimen.textMedium));
            mTextViewCaption.setTextColor(mContext.getResources().getColor(R.color.black));
//            LinearLayout.LayoutParams mLayoutParamsTextCaption =
//                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                            ViewGroup.LayoutParams.WRAP_CONTENT);
            mViewHolder.mViewContents.addView(mTextViewCaption);
        }
        if (!mConversation.getImage().isEmpty()) {
            int heightImage =
                    (width * 3 / 4) - mConvertSize.convertToPx(16) - mConvertSize.convertToPx(56);
            mImageViewContents = new ImageView(mContext);
            Picasso.get().load(mConversation
                    .getImage())
                    .fit()
                    .centerCrop()
                    .into(mImageViewContents);
            LinearLayout.LayoutParams mLayoutParamsImage =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            heightImage);
            mLayoutParamsImage.setMargins(0, 16, 0, 0);
            mViewHolder.mViewContents.addView(mImageViewContents, mLayoutParamsImage);
        }
    }
}

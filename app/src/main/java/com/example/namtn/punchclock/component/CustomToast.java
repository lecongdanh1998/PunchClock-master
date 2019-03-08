package com.example.namtn.punchclock.component;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.namtn.punchclock.R;


public class CustomToast {

    private Context mContext;
    private Activity mActivity;

    public CustomToast(Context mContext) {
        this.mContext = mContext;
        this.mActivity = (Activity) mContext;
    }

    public void showToast(String message, int status) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_custom,
                (ViewGroup) mActivity.findViewById(R.id.toast_custom_root));

        LinearLayout mLinearLayout = layout.findViewById(R.id.linear_bg_toast_custom);
        if (status == 0) {
            mLinearLayout.setBackground(mContext.getResources().getDrawable(R.drawable.custom_toast_success));
        } else if (status == 1) {
            mLinearLayout.setBackground(mContext.getResources().getDrawable(R.drawable.custom_toast_error));
        } else if (status == 2) {
            mLinearLayout.setBackground(mContext.getResources().getDrawable(R.drawable.custom_toast_warning));
        } else if (status == 3) {
            mLinearLayout.setBackground(mContext.getResources().getDrawable(R.drawable.custom_toast_primary));
        } else if (status == 4) {
            mLinearLayout.setBackground(mContext.getResources().getDrawable(R.drawable.custom_toast_infor));
        }
        TextView text = (TextView) layout.findViewById(R.id.txt_contents_toast);
        text.setText(message);
        Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.BOTTOM, 0, 30);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}

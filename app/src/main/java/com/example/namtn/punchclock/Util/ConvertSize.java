package com.example.namtn.punchclock.Util;

import android.app.Activity;
import android.content.Context;

public class ConvertSize {

    private Context mContext;
    private Activity mActivity;

    public ConvertSize(Context mContext) {
        this.mContext = mContext;
        mActivity = (Activity) mContext;
    }

    public int convertToPx(int dp) {
        // Get the screen's density scale
        final float scale = mActivity.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * scale + 0.5f);
    }
}

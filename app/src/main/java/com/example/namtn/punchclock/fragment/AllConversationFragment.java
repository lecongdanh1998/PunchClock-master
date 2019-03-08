package com.example.namtn.punchclock.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.namtn.punchclock.Activity.BaseFragment;
import com.example.namtn.punchclock.Activity.PostConversationActivity;
import com.example.namtn.punchclock.Adapter.AllConversation.ConversationAdapter;
import com.example.namtn.punchclock.Model.AllConversationModel.AllConversationModelImpl;
import com.example.namtn.punchclock.Presenter.AllConversationPresenter.AlLConversationPresenter;
import com.example.namtn.punchclock.Presenter.AllConversationPresenter.AlLConversationPresenterImpl;
import com.example.namtn.punchclock.R;
import com.example.namtn.punchclock.View.AllConversationView;

public class AllConversationFragment extends BaseFragment implements AllConversationView {

    private static final String TAG = "ALL_FRAGMENT";
    private View mView;
    private Context mContext;
    private AlLConversationPresenter mPresenter;
    private ListView mListView;
    private EditText mEditTextPost;

    public AllConversationFragment() {
        // Required empty public constructor
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent,
                                        Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_all_conversation, parent, false);
        return mView;
    }

    @Override
    public void initView() {
        mContext = getContext();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        mListView = mView.findViewById(R.id.lv_all_conversation);
        mEditTextPost = mView.findViewById(R.id.edt_contents_fragment_conversation);
    }

    @Override
    public void initEventButton() {
        mEditTextPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PostConversationActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        mPresenter = new AlLConversationPresenterImpl(new AllConversationModelImpl(mContext), this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void fetchAllConversationSuccess(ConversationAdapter adapter) {
        Log.d(TAG, "fetchAllConversationSuccess: ");
        adapter.notifyDataSetChanged();
        mListView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }
}

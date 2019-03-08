package com.example.namtn.punchclock.Activity.Conversation;

import android.support.design.widget.TabLayout;

import com.example.namtn.punchclock.Activity.BaseActivity;
import com.example.namtn.punchclock.Adapter.AllConversation.ConversationAdapter;
import com.example.namtn.punchclock.Adapter.FragmentAdapter;
import com.example.namtn.punchclock.ModelView.FragmentInformation;
import com.example.namtn.punchclock.R;
import com.example.namtn.punchclock.View.AllConversationView;
import com.example.namtn.punchclock.component.CustomViewPager;
import com.example.namtn.punchclock.fragment.AllConversationFragment;
import com.example.namtn.punchclock.fragment.GroupFragment;

import java.util.ArrayList;

public class ConversationActivity extends BaseActivity implements AllConversationView {
    private TabLayout mTabLayoutMain;
    private CustomViewPager mViewPagerMain;
    private ArrayList<FragmentInformation> mListFragmentInformation;
    private FragmentAdapter mFragmentAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_conversation;
    }

    @Override
    protected void initView() {
        mTabLayoutMain = findViewById(R.id.tab_layout_main);
        mViewPagerMain = findViewById(R.id.view_pager_layout_main);
    }

    @Override
    protected void initEventControl() {

    }

    @Override
    protected void initData() {
        mListFragmentInformation = new ArrayList<>();
        mListFragmentInformation.add(new FragmentInformation(0, "All Conversation", new AllConversationFragment()));
        mListFragmentInformation.add(new FragmentInformation(0, "Groups", new GroupFragment()));
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mListFragmentInformation);
        //adding new layout for viewpager
        mViewPagerMain.setAdapter(mFragmentAdapter);
        mTabLayoutMain.setupWithViewPager(mViewPagerMain);
    }

    @Override
    public void fetchAllConversationSuccess(ConversationAdapter adapter) {

    }
}
